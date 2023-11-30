package com.ecommerce.backend.mappers;

import com.ecommerce.backend.entities.Recipe;
import com.ecommerce.backend.dtos.RecipeDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {ProductMapper.class})
public interface RecipeMapper {
    Recipe toEntity(RecipeDto recipeDto);

    RecipeDto toDto(Recipe recipe);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Recipe partialUpdate(RecipeDto recipeDto, @MappingTarget Recipe recipe);
}