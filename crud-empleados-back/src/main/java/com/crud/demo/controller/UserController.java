package com.crud.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.demo.dto.UserCreateDTO;
import com.crud.demo.dto.UserResponseDTO;
import com.crud.demo.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
// @CrossOrigin(origins = "http://localhost:4200")
public class UserController  {
  
  private UserService service;

  public UserController(UserService service){
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<UserResponseDTO>> getUsers() {
    List<UserResponseDTO> users = service.getUsers();
    return ResponseEntity.ok(users);
  }

  @PostMapping
  public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO userCreateDTO) {
    UserResponseDTO dto = service.createUser(userCreateDTO);
    return ResponseEntity.ok(dto);
  }
}
