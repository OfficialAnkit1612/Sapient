package com.sapients.product_catalog_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO {
    @Min(1)
    @Max(5)
    private Double rating;

    @NotBlank
    private String comment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime date;

    @NotBlank
    private String reviewerName;

    @Email
    @NotBlank
    private String reviewerEmail;
}