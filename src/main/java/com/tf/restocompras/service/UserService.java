package com.tf.restocompras.service;

import com.tf.restocompras.error.NotFoundException;
import com.tf.restocompras.model.user.UserCreateRequestDto;
import com.tf.restocompras.model.user.UserResponseDto;
import com.tf.restocompras.model.user.UserUpdateRequestDto;
import com.tf.restocompras.repository.UserRepository;
import com.tf.restocompras.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    private final UserMapper userMapper;

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

    public UserResponseDto saveUser(UserCreateRequestDto userCreateRequestDto) {
        var user = userMapper.mapDtoToEntity(userCreateRequestDto);
        var userSaved = userRepository.save(user);
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
