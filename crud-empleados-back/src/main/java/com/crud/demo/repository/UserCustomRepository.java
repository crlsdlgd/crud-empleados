package com.crud.demo.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.crud.demo.entity.User;

public interface UserCustomRepository {
  Page<User> searchUsers(
    String search,
    LocalDate minBirthDate,
    LocalDate maxBirthDate,
    Pageable pageable
  );
}
