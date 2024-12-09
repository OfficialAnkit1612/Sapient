package com.sapients.product_catalog_api.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Meta {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String barcode;
    private String qrCode;
}