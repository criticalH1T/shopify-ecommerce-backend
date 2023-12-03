package com.ecommerce.backend.mappers;

import com.ecommerce.backend.dtos.ProductDto;
import com.ecommerce.backend.entities.Product;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    @Mapping(source = "categoryCategoryName", target = "category.categoryName")
    @Mapping(source = "categoryId", target = "category.id")
    Product toEntity(ProductDto productDto);

    @InheritInverseConfiguration(name = "toEntity")
    ProductDto toDto(Product product);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product partialUpdate(ProductDto productDto, @MappingTarget Product product);
}