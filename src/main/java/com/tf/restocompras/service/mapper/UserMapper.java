package com.tf.restocompras.service.mapper;

import com.tf.restocompras.model.user.User;
import com.tf.restocompras.model.user.UserCreateRequestDto;
import com.tf.restocompras.model.user.UserResponseDto;
import com.tf.restocompras.model.user.UserUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface UserMapper {

    @Mapping(target = "name", source = "user.username")
    UserResponseDto mapEntityToDto(User user);

    @Mapping(target = "username", source = "dto.name")
    User mapDtoToEntity(UserUpdateRequestDto dto);

    @Mapping(target = "username", source = "dto.name")
    User mapDtoToEntity(UserCreateRequestDto dto);
}
