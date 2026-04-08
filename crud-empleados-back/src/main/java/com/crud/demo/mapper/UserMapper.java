package com.crud.demo.mapper;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.crud.demo.dto.UserCreateDTO;
import com.crud.demo.dto.UserResponseDTO;
import com.crud.demo.entity.User;

@Component
public class UserMapper {
  
  public UserResponseDTO entityToResponseDTO(User user) {
    return new UserResponseDTO(
      user.getId(), 
      user.getNombre(), 
      user.getApellido(), 
      user.getEmail()
    );
  }

  public User createDtoToEntity (UserCreateDTO userDTO) {
    User user = new User();
    user.setNombre(userDTO.getNombre());
    user.setApellido(userDTO.getApellido());
    user.setEmail(userDTO.getEmail());
    user.setPassword(userDTO.getPassword());

    user.setStatus(true);
    user.setCreatedAt(new Date());
    user.setUpdatedAt(new Date());
    return user;
  }
}
