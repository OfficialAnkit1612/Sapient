package com.sapients.product_catalog_api.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Indexed
@Table(name = "products")
public class Product {
    @Id
    private Long id;

    @FullTextField
    private String title;

    @FullTextField
    private String description;
    private String category;
    private Double price;
    private Double discountPercentage;
    private Double rating;
    private Long stock;

    @ElementCollection
    @CollectionTable(name = "product_tags", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "tag")
    private List<String> tags;

    private String sku;
    private Double weight;

    @Embedded
    private Dimensions dimensions;

    private String warrantyInformation;
    private String shippingInformation;
    private String availabilityStatus;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<Review> reviews;

    private String returnPolicy;
    private Long minimumOrderQuantity;

    @Embedded
    private Meta meta;

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image")
    private List<String> images;

    private String thumbnail;
}