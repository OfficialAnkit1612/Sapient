package com.sapients.product_catalog_api.dto;

import com.sapients.product_catalog_api.model.Dimensions;
import com.sapients.product_catalog_api.model.Meta;
import com.sapients.product_catalog_api.model.Review;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    @NotNull
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String category;

    @DecimalMin(value = "0.0", inclusive = false)
    private Double price;

    @DecimalMin(value = "0.0", inclusive = false)
    @DecimalMax(value = "100.0")
    private Double discountPercentage;

    @DecimalMin(value = "0.0", inclusive = false)
    @DecimalMax(value = "5.0")
    private Double rating;

    @Min(0)
    private Long stock;

    @NotEmpty
    private List<String> tags;

    @NotBlank
    private String sku;

    @DecimalMin(value = "0.0", inclusive = false)
    private Double weight;

    @NotNull
    private DimensionsDTO dimensions;

    @NotBlank
    private String warrantyInformation;

    @NotBlank
    private String shippingInformation;

    @NotBlank
    private String availabilityStatus;

    @NotEmpty
    private List<ReviewDTO> reviews;

    @NotBlank
    private String returnPolicy;

    @Min(1)
    private Long minimumOrderQuantity;

    @NotNull
    private MetaDTO meta;

    @NotEmpty
    private List<String> images;

    @NotBlank
    private String thumbnail;
}