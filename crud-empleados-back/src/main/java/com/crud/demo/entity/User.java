package com.crud.demo.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombre", length = 60, nullable = false)
	private String nombre;

	@Column(name = "apellido", length = 60, nullable = false)
	private String apellido;

	@Column(name = "email",  length = 60, nullable = false, unique = true)
	private String email;

	@Column(name = "status",  length = 60, nullable = false)
	private Boolean status;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "created_at",  length = 60, nullable = false)
	private Date createdAt;

	@Column(name = "updated_at",  length = 60, nullable = false)
	private Date updatedAt;

	public User() {
	}

	public User(String nombre, String apellido, String email, Boolean status, String password, Date createdAt, Date updatedAt) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.status = status;
		this.password = password;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Empleado{" + "id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + ", status=" + status + ", password=" + password + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
	}
}
