package com.ecommerce.backend.mappers;

import com.ecommerce.backend.entities.Order;
import com.ecommerce.backend.dtos.OrderWithItemsDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {OrderItemMapper.class, UserMapper.class})
public interface OrderWithItemsMapper {
    Order toEntity(OrderWithItemsDto orderWithItemsDto);

    @AfterMapping
    default void linkOrderItems(@MappingTarget Order order) {
        order.getOrderItems().forEach(orderItem -> orderItem.setOrder(order));
    }

    OrderWithItemsDto toDto(Order order);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Order partialUpdate(OrderWithItemsDto orderWithItemsDto, @MappingTarget Order order);
}