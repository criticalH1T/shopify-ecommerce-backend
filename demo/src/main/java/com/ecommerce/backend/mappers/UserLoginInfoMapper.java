package com.ecommerce.backend.mappers;

import com.ecommerce.backend.dtos.UserLoginInfoDto;
import com.ecommerce.backend.entities.User;
import com.ecommerce.backend.entities.UserLoginInfo;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface UserLoginInfoMapper {
    UserLoginInfo toEntity(UserLoginInfoDto userLoginInfoDto);

    @AfterMapping
    default void linkUser(@MappingTarget UserLoginInfo userLoginInfo) {
        User user = userLoginInfo.getUser();
        if (user != null) {
            user.setUserLoginInfo(userLoginInfo);
        }
    }

    UserLoginInfoDto toDto(UserLoginInfo userLoginInfo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserLoginInfo partialUpdate(UserLoginInfoDto userLoginInfoDto, @MappingTarget UserLoginInfo userLoginInfo);
}