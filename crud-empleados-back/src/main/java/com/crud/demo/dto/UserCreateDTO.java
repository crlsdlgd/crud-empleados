package com.crud.demo.dto;

import java.time.LocalDate;

public class UserCreateDTO {
  private String nombre;
  private String apellido;
  private String email;
  private String password;
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

  public void setpassword(String password){
    this.password = password;
  }

  public LocalDate getFechaNacimiento() {
    return fechaNacimiento;
  }
  
  public void setFechaNacimiento(LocalDate fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
  }
}
