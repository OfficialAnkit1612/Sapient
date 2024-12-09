package com.sapients.product_catalog_api.service;

import com.sapients.product_catalog_api.dto.ApiResponse;
import com.sapients.product_catalog_api.dto.ProductDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface ProductService {
    ResponseEntity<ApiResponse<ProductDTO>> createProduct(ProductDTO productDTO);
    ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProducts();
    ResponseEntity<ApiResponse<ProductDTO>> getProductById(Long id);
    ResponseEntity<ApiResponse<String>> loadProducts();
    ResponseEntity<ApiResponse<ProductDTO>> updateProduct(Long id, ProductDTO productDTO);
    ResponseEntity<ApiResponse<Void>> deleteProduct(Long id);
    ResponseEntity<ApiResponse<Set<ProductDTO>>> searchProducts(String keyword);
    ResponseEntity<ApiResponse<ProductDTO>> findProductByIdOrSku(Long id, String sku);
}