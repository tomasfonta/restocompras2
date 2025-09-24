package com.tf.restocompras.service.mapper;

import com.tf.restocompras.model.restaurant.Restaurant;
import com.tf.restocompras.model.restaurant.RestaurantCreateRequestDto;
import com.tf.restocompras.model.restaurant.RestaurantResponseDto;
import com.tf.restocompras.model.restaurant.RestaurantUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface RestaurantMapper {

    RestaurantResponseDto mapEntityToDto(Restaurant restaurant);

    List<RestaurantResponseDto> mapEntitiesToDtos(List<Restaurant> restaurants);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Restaurant mapCreateDtoToEntity(RestaurantCreateRequestDto restaurantCreateRequestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void mapUpdateDtoToEntity(RestaurantUpdateRequestDto restaurantUpdateRequestDto, @MappingTarget Restaurant restaurant);
}
