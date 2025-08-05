package com.tf.restocompras.service;

import com.tf.restocompras.error.NotFoundException;
import com.tf.restocompras.model.restaurant.Restaurant;
import com.tf.restocompras.model.restaurant.RestaurantCreateRequestDto;
import com.tf.restocompras.model.restaurant.RestaurantResponseDto;
import com.tf.restocompras.model.restaurant.RestaurantUpdateRequestDto;
import com.tf.restocompras.model.user.User;
import com.tf.restocompras.repository.RestaurantRepository;
import com.tf.restocompras.repository.UserRepository;
import com.tf.restocompras.service.mapper.RestaurantMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final UserRepository userRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
        this.userRepository = userRepository;
    }

    public List<RestaurantResponseDto> getAllRestaurants() {
        return restaurantMapper.mapEntitiesToDtos(restaurantRepository.findAll());
    }

    public RestaurantResponseDto getRestaurantById(Long id) {
        return restaurantMapper.mapEntityToDto(restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant not found with id " + id)));
    }

    public RestaurantResponseDto createRestaurant(RestaurantCreateRequestDto restaurantCreateRequestDto) {
        Restaurant restaurant = restaurantMapper.mapCreateDtoToEntity(restaurantCreateRequestDto);
        return restaurantMapper.mapEntityToDto(restaurantRepository.save(restaurant));
    }

    public RestaurantResponseDto updateRestaurant(RestaurantUpdateRequestDto restaurantUpdateRequestDto) {
        Restaurant existingRestaurant = restaurantRepository.findById(restaurantUpdateRequestDto.id())
                .orElseThrow(() -> new NotFoundException("Restaurant not found with id " + restaurantUpdateRequestDto.id()));

        restaurantMapper.mapUpdateDtoToEntity(restaurantUpdateRequestDto, existingRestaurant);
        return restaurantMapper.mapEntityToDto(restaurantRepository.save(existingRestaurant));
    }

    public void deleteRestaurant(Long id) {
        if (!restaurantRepository.existsById(id)) {
            throw new NotFoundException("Restaurant not found with id " + id);
        }
        restaurantRepository.deleteById(id);
    }

    @Transactional
    public RestaurantResponseDto addUserToRestaurant(Long restaurantId, Long userId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant not found with id " + restaurantId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id " + userId));

        restaurant.getUsers().add(user);
        user.setRestaurant(restaurant);

        return restaurantMapper.mapEntityToDto(restaurantRepository.save(restaurant));
    }
}
