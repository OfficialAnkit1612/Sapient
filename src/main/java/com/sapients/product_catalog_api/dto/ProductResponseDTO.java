package com.sapients.product_catalog_api.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProductResponseDTO {
    private List<ProductDTO> products;
    private Long total;
    private Long skip;
    private Long limit;
}