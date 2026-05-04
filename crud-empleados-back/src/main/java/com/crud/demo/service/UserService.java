package com.crud.demo.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.crud.demo.dto.UserCreateDTO;
import com.crud.demo.dto.UserResponseDTO;

public interface UserService {

  Page<UserResponseDTO> getUsers(String search, Pageable pageable);

  UserResponseDTO createUser(UserCreateDTO userCreateDTO);

  UserResponseDTO getUserById(Long id);
}
