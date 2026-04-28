package com.crud.demo.service;

import java.util.List;

import com.crud.demo.dto.UserCreateDTO;
import com.crud.demo.dto.UserResponseDTO;

public interface UserService {
  List<UserResponseDTO> getUsers();

  UserResponseDTO createUser(UserCreateDTO userCreateDTO);

  UserResponseDTO getUserById(Long id);
}
