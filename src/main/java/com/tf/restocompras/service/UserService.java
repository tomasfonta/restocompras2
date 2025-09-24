package com.tf.restocompras.service;

import com.tf.restocompras.config.security.ApplicationRoles;
import com.tf.restocompras.error.NotFoundException;
import com.tf.restocompras.model.restaurant.Restaurant;
import com.tf.restocompras.model.supplier.Supplier;
import com.tf.restocompras.model.user.*;
import com.tf.restocompras.repository.RestaurantRepository;
import com.tf.restocompras.repository.SupplierRepository;
import com.tf.restocompras.repository.UserRepository;
import com.tf.restocompras.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final SupplierRepository supplierRepository;

    private final UserMapper userMapper;
    private final RestaurantRepository restaurantRepository;

    public List<UserResponseDto> getAllUsers() {

        return userRepository.findAll().stream().map(userMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto getUserById(Long id) {

        var user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User : " + id + " not found"));

        return userMapper.mapEntityToDto(user);
    }

    public UserResponseDto getUserByUsername(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User : " + username + " not found"));
        return userMapper.mapEntityToDto(user);
    }

    public UserResponseDto getUserByEmail(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with mail: " + email + " not found"));
        return userMapper.mapEntityToDto(user);
    }

    @Transactional
    public UserResponseDto saveUser(UserCreateRequestDto userCreateRequestDto) {
        User user = userMapper.mapDtoToEntity(userCreateRequestDto);
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(encoder.encode(userCreateRequestDto.password()));
        user.setRole(ApplicationRoles.USER);
        var userSaved = userRepository.save(user);

        // If the user is a SUPPLIER, create a Supplier entity and attach it to the user
        if (user.getUserBusinessType() == UserBusinessType.SUPPLIER) {
            Supplier supplier = new Supplier();
            supplier.setName(user.getUsername());
            supplier.setMail(user.getEmail());
            supplier.setAddress("Completar");
            supplier.setPhoneNumber("Completar");
            supplier.setRating(5.0);
            supplier.setWebsite("Completar");
            supplier.setUser(userSaved);
            supplierRepository.save(supplier);
            userSaved.setSupplier(supplier);
        }

        if (user.getUserBusinessType() == UserBusinessType.RESTAURANT) {
            Restaurant restaurant = Restaurant.builder()
                    .mail(user.getEmail())
                    .address("Completar")
                    .name(user.getUsername())
                    .phoneNumber("Completar")
                    .website("Completar")
                    .user(userSaved)
                    .build();

            restaurantRepository.save(restaurant);
            userSaved.setRestaurant(restaurant);
        }

        return userMapper.mapEntityToDto(userSaved);
    }

    public UserResponseDto updateUser(UserUpdateRequestDto userUpdateRequestDto) {
        var user = userRepository.findById(userUpdateRequestDto.id())
                .orElseThrow(() -> new NotFoundException("User : " + userUpdateRequestDto.id() + " not found"));
        user.setEmail(userUpdateRequestDto.email());
        user.setUsername(userUpdateRequestDto.name());
        user.setPassword(userUpdateRequestDto.password());
        var userSaved = userRepository.save(user);
        return userMapper.mapEntityToDto(userSaved);
    }

    public void deleteUser(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User : " + id + " not found"));
        userRepository.delete(user);
    }
}
