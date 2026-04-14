package com.crud.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.crud.demo.dto.LoginRequestDTO;
import com.crud.demo.dto.LoginResponseDTO;
import com.crud.demo.entity.User;
import com.crud.demo.repository.UserRepository;
import com.crud.demo.utils.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {

  private final UserRepository repository;
  private final BCryptPasswordEncoder encoder;
  private final JwtUtil jwtUtil;

  public AuthServiceImpl(UserRepository repository, BCryptPasswordEncoder encoder, JwtUtil jwtUtil) {
    this.repository = repository;
    this.encoder = encoder;
    this.jwtUtil = jwtUtil;
  }

  @Override
  public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
    User user = repository.findByEmail(loginRequestDTO.getEmail())
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    boolean isPasswordMatch = encoder.matches(
        loginRequestDTO.getPassword(),
        user.getPassword());

    if (!isPasswordMatch) {
      throw new RuntimeException("Credenciales inválidas");
    }

    LoginResponseDTO response = new LoginResponseDTO();
    response.setToken(jwtUtil.generateToken(user.getEmail()));
    return response;

  }
}