package com.sapients.product_catalog_api.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Dimensions {
    private Double width;
    private Double height;
    private Double depth;
}