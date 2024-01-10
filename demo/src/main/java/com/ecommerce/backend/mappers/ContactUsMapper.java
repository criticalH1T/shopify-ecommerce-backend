package com.ecommerce.backend.mappers;

import com.ecommerce.backend.dtos.ContactUsDto;
import com.ecommerce.backend.entities.ContactUs;

import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ContactUsMapper {

    ContactUs toEntity(ContactUsDto contactUsDto);

    @InheritInverseConfiguration(name = "toEntity")
    ContactUsDto toDto(ContactUs contactUs);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ContactUs partialUpdate(ContactUsDto contactUsDto, @MappingTarget ContactUs contactUs);
}
