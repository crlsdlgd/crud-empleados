package com.crud.demo.service;

import com.crud.demo.dto.LoginRequestDTO;
import com.crud.demo.dto.LoginResponseDTO;

public interface AuthService {
  LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
}
