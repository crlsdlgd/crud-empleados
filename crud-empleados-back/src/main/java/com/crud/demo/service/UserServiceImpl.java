package com.crud.demo.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.crud.demo.dto.UserCreateDTO;
import com.crud.demo.dto.UserResponseDTO;
import com.crud.demo.entity.User;
import com.crud.demo.mapper.UserMapper;
import com.crud.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository repository;
  private final UserMapper mapper;
  private final BCryptPasswordEncoder passwordEncoder;
  
  public UserServiceImpl (UserRepository repository,  UserMapper mapper, BCryptPasswordEncoder passwordEncoder) {
    this.repository = repository;
    this.mapper = mapper;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public List<UserResponseDTO> getUsers() {
    return repository.findAll() // trae List<Usuario>
    .stream() // List<Usuario> se convierte en Stream<Usuario> para iterarlo con programacion funcional (map, filter, ...)
    .map(mapper::entityToResponseDTO) // com .map iteramos cada empleado. con mapper convertimos Usuario a UsuarioDTO | Stream<Usuario> -> Stream<UsuarioDTO>
    .toList(); // transformamos Stream<UsuarioDTO> a List<UsuarioDTO>
  }

  @Override
  public UserResponseDTO createUser(UserCreateDTO userCreateDTO) {
    if (repository.existsByEmail(userCreateDTO.getEmail())){
      throw new RuntimeException("El email ya está registrado");
    }
    User user = mapper.createDtoToEntity(userCreateDTO);
    user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));// aqui hasheamos el password
    User userSaved = repository.save(user);
    return mapper.entityToResponseDTO(userSaved);
  }
}
