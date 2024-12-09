package com.sapients.product_catalog_api.mapper.impl;

import com.sapients.product_catalog_api.dto.DimensionsDTO;
import com.sapients.product_catalog_api.dto.MetaDTO;
import com.sapients.product_catalog_api.dto.ProductDTO;
import com.sapients.product_catalog_api.dto.ReviewDTO;
import com.sapients.product_catalog_api.mapper.ProductMapper;
import com.sapients.product_catalog_api.model.Dimensions;
import com.sapients.product_catalog_api.model.Meta;
import com.sapients.product_catalog_api.model.Product;
import com.sapients.product_catalog_api.model.Review;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toModel(ProductDTO productDTO) {
        if ( productDTO == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.id( productDTO.getId() );
        product.title( productDTO.getTitle() );
        product.description( productDTO.getDescription() );
        product.category( productDTO.getCategory() );
        product.price( productDTO.getPrice() );
        product.discountPercentage( productDTO.getDiscountPercentage() );
        product.rating( productDTO.getRating() );
        product.stock( productDTO.getStock() );
        List<String> list = productDTO.getTags();
        if ( list != null ) {
            product.tags( new ArrayList<String>( list ) );
        }
        product.sku( productDTO.getSku() );
        product.weight( productDTO.getWeight() );
        product.dimensions( dimensionsDTOToDimensions( productDTO.getDimensions() ) );
        product.warrantyInformation( productDTO.getWarrantyInformation() );
        product.shippingInformation( productDTO.getShippingInformation() );
        product.availabilityStatus( productDTO.getAvailabilityStatus() );
        product.reviews( reviewDTOListToReviewList( productDTO.getReviews() ) );
        product.returnPolicy( productDTO.getReturnPolicy() );
        product.minimumOrderQuantity( productDTO.getMinimumOrderQuantity() );
        product.meta( metaDTOToMeta( productDTO.getMeta() ) );
        List<String> list2 = productDTO.getImages();
        if ( list2 != null ) {
            product.images( new ArrayList<String>( list2 ) );
        }
        product.thumbnail( productDTO.getThumbnail() );

        return product.build();
    }

    @Override
    public ProductDTO toDTO(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDTO.ProductDTOBuilder productDTO = ProductDTO.builder();

        productDTO.id( product.getId() );
        productDTO.title( product.getTitle() );
        productDTO.description( product.getDescription() );
        productDTO.category( product.getCategory() );
        productDTO.price( product.getPrice() );
        productDTO.discountPercentage( product.getDiscountPercentage() );
        productDTO.rating( product.getRating() );
        productDTO.stock( product.getStock() );
        List<String> list = product.getTags();
        if ( list != null ) {
            productDTO.tags( new ArrayList<String>( list ) );
        }
        productDTO.sku( product.getSku() );
        productDTO.weight( product.getWeight() );
        productDTO.dimensions( dimensionsToDimensionsDTO( product.getDimensions() ) );
        productDTO.warrantyInformation( product.getWarrantyInformation() );
        productDTO.shippingInformation( product.getShippingInformation() );
        productDTO.availabilityStatus( product.getAvailabilityStatus() );
        productDTO.reviews( reviewListToReviewDTOList( product.getReviews() ) );
        productDTO.returnPolicy( product.getReturnPolicy() );
        productDTO.minimumOrderQuantity( product.getMinimumOrderQuantity() );
        productDTO.meta( metaToMetaDTO( product.getMeta() ) );
        List<String> list2 = product.getImages();
        if ( list2 != null ) {
            productDTO.images( new ArrayList<String>( list2 ) );
        }
        productDTO.thumbnail( product.getThumbnail() );

        return productDTO.build();
    }

    @Override
    public void updateProductFromDTO(ProductDTO productDTO, Product product) {
        if ( productDTO == null ) {
            return;
        }

        product.setId( productDTO.getId() );
        product.setTitle( productDTO.getTitle() );
        product.setDescription( productDTO.getDescription() );
        product.setCategory( productDTO.getCategory() );
        product.setPrice( productDTO.getPrice() );
        product.setDiscountPercentage( productDTO.getDiscountPercentage() );
        product.setRating( productDTO.getRating() );
        product.setStock( productDTO.getStock() );
        if ( product.getTags() != null ) {
            List<String> list = productDTO.getTags();
            if ( list != null ) {
                product.getTags().clear();
                product.getTags().addAll( list );
            }
            else {
                product.setTags( null );
            }
        }
        else {
            List<String> list = productDTO.getTags();
            if ( list != null ) {
                product.setTags( new ArrayList<String>( list ) );
            }
        }
        product.setSku( productDTO.getSku() );
        product.setWeight( productDTO.getWeight() );
        if ( productDTO.getDimensions() != null ) {
            if ( product.getDimensions() == null ) {
                product.setDimensions( Dimensions.builder().build() );
            }
            dimensionsDTOToDimensions1( productDTO.getDimensions(), product.getDimensions() );
        }
        else {
            product.setDimensions( null );
        }
        product.setWarrantyInformation( productDTO.getWarrantyInformation() );
        product.setShippingInformation( productDTO.getShippingInformation() );
        product.setAvailabilityStatus( productDTO.getAvailabilityStatus() );
        if ( product.getReviews() != null ) {
            List<Review> list1 = reviewDTOListToReviewList( productDTO.getReviews() );
            if ( list1 != null ) {
                product.getReviews().clear();
                product.getReviews().addAll( list1 );
            }
            else {
                product.setReviews( null );
            }
        }
        else {
            List<Review> list1 = reviewDTOListToReviewList( productDTO.getReviews() );
            if ( list1 != null ) {
                product.setReviews( list1 );
            }
        }
        product.setReturnPolicy( productDTO.getReturnPolicy() );
        product.setMinimumOrderQuantity( productDTO.getMinimumOrderQuantity() );
        if ( productDTO.getMeta() != null ) {
            if ( product.getMeta() == null ) {
                product.setMeta( Meta.builder().build() );
            }
            metaDTOToMeta1( productDTO.getMeta(), product.getMeta() );
        }
        else {
            product.setMeta( null );
        }
        if ( product.getImages() != null ) {
            List<String> list2 = productDTO.getImages();
            if ( list2 != null ) {
                product.getImages().clear();
                product.getImages().addAll( list2 );
            }
            else {
                product.setImages( null );
            }
        }
        else {
            List<String> list2 = productDTO.getImages();
            if ( list2 != null ) {
                product.setImages( new ArrayList<String>( list2 ) );
            }
        }
        product.setThumbnail( productDTO.getThumbnail() );
    }

    protected Dimensions dimensionsDTOToDimensions(DimensionsDTO dimensionsDTO) {
        if ( dimensionsDTO == null ) {
            return null;
        }

        Dimensions.DimensionsBuilder dimensions = Dimensions.builder();

        dimensions.width( dimensionsDTO.getWidth() );
        dimensions.height( dimensionsDTO.getHeight() );
        dimensions.depth( dimensionsDTO.getDepth() );

        return dimensions.build();
    }

    protected Review reviewDTOToReview(ReviewDTO reviewDTO) {
        if ( reviewDTO == null ) {
            return null;
        }

        Review.ReviewBuilder review = Review.builder();

        review.rating( reviewDTO.getRating() );
        review.comment( reviewDTO.getComment() );
        review.date( reviewDTO.getDate() );
        review.reviewerName( reviewDTO.getReviewerName() );
        review.reviewerEmail( reviewDTO.getReviewerEmail() );

        return review.build();
    }

    protected List<Review> reviewDTOListToReviewList(List<ReviewDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Review> list1 = new ArrayList<Review>( list.size() );
        for ( ReviewDTO reviewDTO : list ) {
            list1.add( reviewDTOToReview( reviewDTO ) );
        }

        return list1;
    }

    protected Meta metaDTOToMeta(MetaDTO metaDTO) {
        if ( metaDTO == null ) {
            return null;
        }

        Meta.MetaBuilder meta = Meta.builder();

        meta.createdAt( metaDTO.getCreatedAt() );
        meta.updatedAt( metaDTO.getUpdatedAt() );
        meta.barcode( metaDTO.getBarcode() );
        meta.qrCode( metaDTO.getQrCode() );

        return meta.build();
    }

    protected DimensionsDTO dimensionsToDimensionsDTO(Dimensions dimensions) {
        if ( dimensions == null ) {
            return null;
        }

        DimensionsDTO.DimensionsDTOBuilder dimensionsDTO = DimensionsDTO.builder();

        dimensionsDTO.width( dimensions.getWidth() );
        dimensionsDTO.height( dimensions.getHeight() );
        dimensionsDTO.depth( dimensions.getDepth() );

        return dimensionsDTO.build();
    }

    protected ReviewDTO reviewToReviewDTO(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewDTO.ReviewDTOBuilder reviewDTO = ReviewDTO.builder();

        reviewDTO.rating( review.getRating() );
        reviewDTO.comment( review.getComment() );
        reviewDTO.date( review.getDate() );
        reviewDTO.reviewerName( review.getReviewerName() );
        reviewDTO.reviewerEmail( review.getReviewerEmail() );

        return reviewDTO.build();
    }

    protected List<ReviewDTO> reviewListToReviewDTOList(List<Review> list) {
        if ( list == null ) {
            return null;
        }

        List<ReviewDTO> list1 = new ArrayList<ReviewDTO>( list.size() );
        for ( Review review : list ) {
            list1.add( reviewToReviewDTO( review ) );
        }

        return list1;
    }

    protected MetaDTO metaToMetaDTO(Meta meta) {
        if ( meta == null ) {
            return null;
        }

        MetaDTO.MetaDTOBuilder metaDTO = MetaDTO.builder();

        metaDTO.createdAt( meta.getCreatedAt() );
        metaDTO.updatedAt( meta.getUpdatedAt() );
        metaDTO.barcode( meta.getBarcode() );
        metaDTO.qrCode( meta.getQrCode() );

        return metaDTO.build();
    }

    protected void dimensionsDTOToDimensions1(DimensionsDTO dimensionsDTO, Dimensions mappingTarget) {
        if ( dimensionsDTO == null ) {
            return;
        }

        mappingTarget.setWidth( dimensionsDTO.getWidth() );
        mappingTarget.setHeight( dimensionsDTO.getHeight() );
        mappingTarget.setDepth( dimensionsDTO.getDepth() );
    }

    protected void metaDTOToMeta1(MetaDTO metaDTO, Meta mappingTarget) {
        if ( metaDTO == null ) {
            return;
        }

        mappingTarget.setCreatedAt( metaDTO.getCreatedAt() );
        mappingTarget.setUpdatedAt( metaDTO.getUpdatedAt() );
        mappingTarget.setBarcode( metaDTO.getBarcode() );
        mappingTarget.setQrCode( metaDTO.getQrCode() );
    }
}
