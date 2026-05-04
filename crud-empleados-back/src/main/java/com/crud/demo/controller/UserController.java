package com.crud.demo.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crud.demo.dto.UserCreateDTO;
import com.crud.demo.dto.UserResponseDTO;
import com.crud.demo.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
// @CrossOrigin(origins = "http://localhost:4200")
public class UserController {

  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Page<UserResponseDTO>> getUsers(
    @RequestParam(required = false) String search, 
    @PageableDefault(size = 10, sort = "id") Pageable pageable
  ) {
    Page<UserResponseDTO> users = service.getUsers(search, pageable);
    return ResponseEntity.ok(users);
  }

  @PostMapping
  public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO userCreateDTO) {
    UserResponseDTO dto = service.createUser(userCreateDTO);
    return ResponseEntity.ok(dto);
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String admin() {
    return "Esto solo puede leerlo un admin";
  }

  @PreAuthorize("hasRole('ADMIN') or authentication.principal.equals(#id)")
  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
    UserResponseDTO user = service.getUserById(id);
    return ResponseEntity.ok(user);
  }
}
