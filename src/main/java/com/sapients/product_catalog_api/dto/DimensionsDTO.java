package com.sapients.product_catalog_api.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DimensionsDTO {
    @DecimalMin(value = "0.0", inclusive = false)
    private Double width;

    @DecimalMin(value = "0.0", inclusive = false)
    private Double height;

    @DecimalMin(value = "0.0", inclusive = false)
    private Double depth;
}