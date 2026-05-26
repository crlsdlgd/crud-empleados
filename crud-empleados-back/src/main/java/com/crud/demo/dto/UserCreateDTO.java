package com.crud.demo.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserCreateDTO {
  @NotBlank(message = "El nombre es obligatorio")
  @Size(max = 60, message = "El nombre no puede superar 60 caracteres")
  private String nombre;

  @NotBlank(message = "El apellido es obligatorio")
  @Size(max = 60, message = "El apellido no puede superar 60 caracteres")
  private String apellido;

  @NotBlank(message = "El email es obligatorio")
  @Email(message = "El email no tiene un formato válido")
  private String email;

  @NotBlank(message = "La contraseña es obligatoria")
  @Size(min = 8, message = "La contraseña debe tener mínimo 8 caracteres")
  @Pattern(
      regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._-])[A-Za-z\\d@$!%*?&._-]{8,}$",
      message = "La contraseña debe tener mayúsculas, minúsculas, números y caracteres especiales"
  )
  private String password;

  @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
  private LocalDate fechaNacimiento;

  public UserCreateDTO() {
  }

  public UserCreateDTO (String nombre, String apellido, String email, String password, LocalDate fechaNacimiento) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.email = email;
    this.password = password;
    this.fechaNacimiento = fechaNacimiento;
  }

  public String getNombre(){
    return nombre;
  }

  public void setNombre(String nombre){
    this.nombre = nombre;
  }

  public String getApellido(){
    return apellido;
  }

  public void setApellido(String apellido){
    this.apellido = apellido;
  }

  public String getEmail(){
    return email;
  }

  public void setEmail(String email){
    this.email = email;
  }

  public String getPassword(){
    return password;
  }

  public void setPassword(String password){
    this.password = password;
  }

  public LocalDate getFechaNacimiento() {
    return fechaNacimiento;
  }
  
  public void setFechaNacimiento(LocalDate fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
  }
}
