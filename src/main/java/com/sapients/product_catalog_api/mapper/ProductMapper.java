package com.sapients.product_catalog_api.mapper;

import com.sapients.product_catalog_api.dto.ProductDTO;
import com.sapients.product_catalog_api.model.Product;

import org.springframework.stereotype.Component;

@Component
public interface ProductMapper {

    Product toModel(ProductDTO productDTO);

    ProductDTO toDTO(Product product);

    void updateProductFromDTO(ProductDTO productDTO, Product product);
}