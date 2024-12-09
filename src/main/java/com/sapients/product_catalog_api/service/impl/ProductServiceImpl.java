package com.sapients.product_catalog_api.service.impl;

import com.sapients.product_catalog_api.dto.ApiResponse;
import com.sapients.product_catalog_api.dto.ProductDTO;
import com.sapients.product_catalog_api.dto.ProductResponseDTO;
import com.sapients.product_catalog_api.exception.ProductServiceException;
import com.sapients.product_catalog_api.mapper.ProductMapper;
import com.sapients.product_catalog_api.model.Product;
import com.sapients.product_catalog_api.repository.ProductRepository;
import com.sapients.product_catalog_api.service.ProductService;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * REST service for managing products.
 */

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Value("${external.api.url}")
    private String externalApiUrl;

    @PersistenceContext
    private final EntityManager entityManager;

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(EntityManager entityManager, ProductRepository productRepository, ProductMapper productMapper) {
        this.entityManager = entityManager;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * Creates a new product.
     * @param productDTO the product data transfer object
     * @return the response entity containing the API response with the created product
     */
    @Override
    public ResponseEntity<ApiResponse<ProductDTO>> createProduct(ProductDTO productDTO) {
        log.info("Creating product: {}", productDTO);
        try {
            Product product = productMapper.toModel(productDTO);
            ProductDTO savedProduct = productMapper.toDTO(productRepository.save(product));
            log.info("Product created: {}", savedProduct);
            return ResponseEntity.ok(new ApiResponse<>(true, "Product created successfully", savedProduct));
        } catch (Exception e) {
            log.error("Error creating product: {}", e.getMessage());
            throw new ProductServiceException("Error creating product", e);
        }
    }

    /**
     * Retrieves all products.
     * @return the response entity containing the API response with the list of all products
     */
    @Override
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProducts() {
        log.info("Fetching all products");
        try {
            List<ProductDTO> products = productRepository.findAll().stream()
                    .map(productMapper::toDTO)
                    .collect(Collectors.toList());
            if (products.isEmpty()) {
                log.info("No products found");
                return ResponseEntity.ok(new ApiResponse<>(false, "Product not found", null));
            }
            log.info("Fetched {} products", products.size());
            return ResponseEntity.ok(new ApiResponse<>(true, "Products retrieved successfully", products));
        } catch (Exception e) {
            log.error("Error fetching all products: {}", e.getMessage());
            throw new ProductServiceException("Error fetching all products", e);
        }
    }

    /**
     * Retrieves a product by its ID.
     * @param id the ID of the product
     * @return the response entity containing the API response with the product
     */
    @Override
    public ResponseEntity<ApiResponse<ProductDTO>> getProductById(Long id) {
        log.info("Fetching product by ID: {}", id);
        try {
            return productRepository.findById(id)
                    .map(product -> {
                        log.info("Product found: {}", product);
                        return ResponseEntity.ok(new ApiResponse<>(true, "Product retrieved successfully", productMapper.toDTO(product)));
                    })
                    .orElseGet(() -> {
                        log.warn("Product not found with ID: {}", id);
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Product not found", null));
                    });
        } catch (Exception e) {
            log.error("Error fetching product by ID: {}", e.getMessage());
            throw new ProductServiceException("Error fetching product by ID", e);
        }
    }

    /**
     * Loads products from an external API.
     * @return the response entity containing the API response with a success message
     */
    @Override
    @Transactional
    @Retry(name = "loadProducts", fallbackMethod = "fallbackLoadProducts")
    public ResponseEntity<ApiResponse<String>> loadProducts() {
        log.info("Loading products from external API: {}", externalApiUrl);
        try {
            HttpResponse<ProductResponseDTO> response = fetchProductDataFromExternalApi();

            if (response.getStatus() == 200 && response.getBody() != null) {
                log.info("Successfully fetched products from external API");
                saveProducts(response.getBody().getProducts());
                return ResponseEntity.ok(new ApiResponse<>(true, "Products loaded successfully", ""));
            } else {
                log.error("Failed to fetch products: {}", response.getStatusText());
                throw new ProductServiceException("Failed to fetch products: " + response.getStatusText());
            }
        } catch (Exception e) {
            log.error("Error loading products: {}", e.getMessage());
            throw new ProductServiceException("Error loading products", e);
        }
    }

    /**
     * Fetches product data from the external API.
     * @return the HTTP response containing the product response DTO
     */
    private HttpResponse<ProductResponseDTO> fetchProductDataFromExternalApi() {
        return Unirest.get(externalApiUrl).asObject(ProductResponseDTO.class);
    }

    /**
     * Saves a list of products.
     * @param productDTOs the list of product data transfer objects
     */
    private void saveProducts(List<ProductDTO> productDTOs) {
        try {
            List<Product> products = productDTOs.stream()
                    .map(productMapper::toModel)
                    .collect(Collectors.toList());
            productRepository.saveAll(products);
            log.info("Saved {} products", products.size());
        } catch (ObjectOptimisticLockingFailureException e) {
            log.error("Product was updated or deleted by another transaction", e);
            throw new ProductServiceException("Product was updated or deleted by another transaction", e);
        } catch (Exception e) {
            log.error("Error saving products: {}", e.getMessage());
            throw new ProductServiceException("Error saving products", e);
        }
    }

    /**
     * Fallback method for loading products.
     * @param t the throwable
     */
    private void fallbackLoadProducts(Throwable t) {
        log.error("Failed to load products from external API: {}", t.getMessage());
    }

    /**
     * Updates a product by its ID.
     * @param id the ID of the product
     * @param productDTO the product data transfer object
     * @return the response entity containing the API response with the updated product
     */
    @Override
    public ResponseEntity<ApiResponse<ProductDTO>> updateProduct(Long id, ProductDTO productDTO) {
        log.info("Updating product with ID: {}", id);
        try {
            return productRepository.findById(id)
                    .map(product -> {
                        productMapper.updateProductFromDTO(productDTO, product);
                        ProductDTO updatedProduct = productMapper.toDTO(productRepository.save(product));
                        log.info("Product updated: {}", updatedProduct);
                        return ResponseEntity.ok(new ApiResponse<>(true, "Product updated successfully", updatedProduct));
                    })
                    .orElseGet(() -> {
                        log.warn("Product not found with ID: {}", id);
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Product not found", null));
                    });
        } catch (Exception e) {
            log.error("Error updating product: {}", e.getMessage());
            throw new ProductServiceException("Error updating product", e);
        }
    }

    /**
     * Deletes a product by its ID.
     * @param id the ID of the product
     * @return the response entity containing the API response with a success message
     */
    @Override
    public ResponseEntity<ApiResponse<Void>> deleteProduct(Long id) {
        log.info("Deleting product with ID: {}", id);
        try {
            if (productRepository.existsById(id)) {
                productRepository.deleteById(id);
                log.info("Product deleted with ID: {}", id);
                return ResponseEntity.ok(new ApiResponse<>(true, "Product deleted successfully", null));
            } else {
                log.warn("Product not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Product not found", null));
            }
        } catch (Exception e) {
            log.error("Error deleting product: {}", e.getMessage());
            throw new ProductServiceException("Error deleting product", e);
        }
    }

    /**
     * Searches for products by a keyword.
     * @param keyword the keyword to search for
     * @return the response entity containing the API response with the set of matching products
     */
    @Override
    @Transactional
    public ResponseEntity<ApiResponse<Set<ProductDTO>>> searchProducts(String keyword) {
        log.info("Searching products with keyword: {}", keyword);
        try {
            SearchSession searchSession = Search.session(entityManager);
            List<Product> products = searchSession.search(Product.class)
                    .where(f -> f.match()
                            .fields("title", "description")
                            .matching(keyword))
                    .fetchAllHits();

            if (products.isEmpty()) {
                log.warn("No products found matching keyword: {}", keyword);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Products Not Found", Collections.emptySet()));
            }

            Set<ProductDTO> productDTOs = products.stream()
                    .map(productMapper::toDTO)
                    .collect(Collectors.toSet());
            log.info("Found {} products matching keyword: {}", productDTOs.size(), keyword);
            return ResponseEntity.ok(new ApiResponse<>(true, "Products found successfully", productDTOs));
        } catch (Exception e) {
            log.error("Error searching products: {}", e.getMessage());
            throw new ProductServiceException("Error searching products", e);
        }
    }

    /**
     * Finds a product by its ID or SKU.
     * @param id the ID of the product
     * @param sku the SKU of the product
     * @return the response entity containing the API response with the product
     */
    public ResponseEntity<ApiResponse<ProductDTO>> findProductByIdOrSku(Long id, String sku) {
        log.info("Finding product by ID: {} or SKU: {}", id, sku);
        try {
            Optional<ProductDTO> product;
            if (id != null) {
                product = productRepository.findById(id).map(productMapper::toDTO);
            } else if (sku != null) {
                product = productRepository.findBySku(sku).map(productMapper::toDTO);
            } else {
                log.warn("No ID or SKU provided for product search");
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "No ID or SKU provided", null));
            }

            return product.map(p -> {
                log.info("Product found: {}", p);
                return ResponseEntity.ok(new ApiResponse<>(true, "Product found successfully", p));
            }).orElseGet(() -> {
                log.warn("Product not found with ID: {} or SKU: {}", id, sku);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Product not found", null));
            });
        } catch (Exception e) {
            log.error("Error finding product by ID or SKU: {}", e.getMessage());
            throw new ProductServiceException("Error finding product by ID or SKU", e);
        }
    }
}