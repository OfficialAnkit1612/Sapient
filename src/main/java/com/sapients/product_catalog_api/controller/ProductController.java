package com.sapients.product_catalog_api.controller;

import com.sapients.product_catalog_api.dto.ApiResponse;
import com.sapients.product_catalog_api.dto.ProductDTO;
import com.sapients.product_catalog_api.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * REST controller for managing products.
 */
@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Management", description = "APIs for managing products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Creates a new product and returns the created product.
     */
    @Operation(summary = "Create a new product", description = "Creates a new product and returns the created product")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product created successfully", content = @Content(schema = @Schema(implementation = ProductDTO.class)))
    @PostMapping
    public ResponseEntity<ApiResponse<ProductDTO>> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    /**
     * Retrieves a list of all products.
     */
    @Operation(summary = "Get all products", description = "Retrieves a list of all products")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Products retrieved successfully", content = @Content(schema = @Schema(implementation = ProductDTO.class)))
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProducts() {
        return productService.getAllProducts();
    }

    /**
     * Retrieves a product by its ID.
     */
    @Operation(summary = "Get product by ID", description = "Retrieves a product by its ID")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product retrieved successfully", content = @Content(schema = @Schema(implementation = ProductDTO.class)))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found")
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(
            @Parameter(description = "ID of the product to be retrieved")
            @PathVariable @Digits(integer = 10, fraction = 0) Long id) {
        return productService.getProductById(id);
    }

    /**
     * Loads products into the system.
     */
    @Operation(summary = "Load products", description = "Loads products into the system")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Products loaded successfully")
    @PostMapping("/load")
    public ResponseEntity<ApiResponse<String>> loadProducts() {
        return productService.loadProducts();
    }

    /**
     * Updates an existing product by its ID.
     */
    @Operation(summary = "Update a product", description = "Updates an existing product by its ID")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product updated successfully", content = @Content(schema = @Schema(implementation = ProductDTO.class)))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @Parameter(description = "ID of the product to be updated")
            @PathVariable @Digits(integer = 10, fraction = 0) Long id,
            @Valid @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO);
    }

    /**
     * Deletes a product by its ID.
     */
    @Operation(summary = "Delete a product", description = "Deletes a product by its ID")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(
            @Parameter(description = "ID of the product to be deleted")
            @PathVariable @Digits(integer = 10, fraction = 0) Long id) {
        return productService.deleteProduct(id);
    }

    /**
     * Searches for products by a given keyword.
     */
    @Operation(summary = "Search products by keyword", description = "Searches for products by a given keyword")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Products found successfully", content = @Content(schema = @Schema(implementation = ProductDTO.class)))
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Set<ProductDTO>>> searchProducts(
            @Parameter(description = "Keyword to search for products")
            @RequestParam @Pattern(regexp = "^[a-zA-Z0-9 ]+$") String keyword) {
        return productService.searchProducts(keyword);
    }

    /**
     * Finds a product by its ID or SKU.
     */
    @Operation(summary = "Find product by ID or SKU", description = "Finds a product by its ID or SKU")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product found successfully", content = @Content(schema = @Schema(implementation = ProductDTO.class)))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found")
    @GetMapping("/find")
    public ResponseEntity<?> findProductByIdOrSku(
            @Parameter(description = "ID of the product to be found")
            @RequestParam(required = false) @Digits(integer = 10, fraction = 0) Long id,
            @Parameter(description = "SKU of the product to be found")
            @RequestParam(required = false) @Pattern(regexp = "^[a-zA-Z0-9-]+$") String sku) {
        return productService.findProductByIdOrSku(id, sku);
    }
}