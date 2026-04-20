package com.crud.demo.service;

import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
  public List<UserResponseDTO> getUsers() {
    return userRepository.findAll() // trae List<Usuario>
        .stream() // List<Usuario> se convierte en Stream<Usuario> para iterarlo con programacion
                  // funcional (map, filter, ...)
        .map(userMapper::entityToResponseDTO) // com .map iteramos cada empleado. con mapper convertimos Usuario a
        // UsuarioDTO | Stream<Usuario> -> Stream<UsuarioDTO>
        .toList(); // transformamos Stream<UsuarioDTO> a List<UsuarioDTO>
  }

  @Override
  public UserResponseDTO createUser(UserCreateDTO userCreateDTO) {
    if (userRepository.existsByEmail(userCreateDTO.getEmail())) {
      throw new RuntimeException("email already exists");
    }
    User user = userMapper.createDtoToEntity(userCreateDTO);
    user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
    Role roleUser = roleRepository.findByName("ROLE_USER")
        .orElseThrow();
    user.setRoles(Set.of(roleUser));
    User userSaved = userRepository.save(user);
    return userMapper.entityToResponseDTO(userSaved);
  }
}
