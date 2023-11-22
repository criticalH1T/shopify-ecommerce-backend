package com.ecommerce.backend.mappers;

import com.ecommerce.backend.dtos.UserDto;
import com.ecommerce.backend.entities.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toEntity(UserDto userDto);

    @AfterMapping
    default void linkOrders(@MappingTarget User user) {
        user.getOrders().forEach(order -> order.setUser(user));
    }

    UserDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserDto userDto, @MappingTarget User user);
}