package com.crud.demo.service;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.crud.demo.dto.UserCreateDTO;
import com.crud.demo.dto.UserResponseDTO;
import com.crud.demo.entity.Role;
import com.crud.demo.entity.User;
import com.crud.demo.mapper.UserMapper;
import com.crud.demo.repository.RoleRepository;
import com.crud.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final UserMapper userMapper;
  private final BCryptPasswordEncoder passwordEncoder;

  public UserServiceImpl(
      UserRepository userRepository,
      RoleRepository roleRepository,
      UserMapper userMapper,
      BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.userMapper = userMapper;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Page<UserResponseDTO> getUsers(String search, Integer minEdad, Integer maxEdad, Pageable pageable) {
    
    LocalDate today = LocalDate.now();
    LocalDate minBirthDate = null; 
    LocalDate maxBirthDate = null; 

    if (maxEdad != null) {
      minBirthDate = today.minusYears(maxEdad);
    }
    
    if (minEdad != null) {
      maxBirthDate = today.minusYears(minEdad);
    }

    Page<User> users = userRepository.searchUsers(search, minBirthDate, maxBirthDate, pageable);
    return users.map(userMapper::entityToResponseDTO);
  }

  @Override
  public UserResponseDTO createUser(UserCreateDTO userCreateDTO) {
    if (userRepository.existsByEmail(userCreateDTO.getEmail())) {
      throw new ResponseStatusException(
      HttpStatus.BAD_REQUEST,
      "Email ya registrado"
);
    }
    User user = userMapper.createDtoToEntity(userCreateDTO);
    user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
    Role roleUser = roleRepository.findByName("ROLE_USER")
        .orElseThrow();
    user.setRoles(Set.of(roleUser));
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    Long currentUserId = (Long) auth.getPrincipal();
    user.setCreatedBy(currentUserId);
    User userSaved = userRepository.save(user);
    return userMapper.entityToResponseDTO(userSaved);
  }

  @Override
  public UserResponseDTO getUserById(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Usuario no encontrado"));
    return userMapper.entityToResponseDTO(user);
  }
}
