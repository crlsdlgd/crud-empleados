package com.crud.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.crud.demo.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository{
  
  @PersistenceContext
  private EntityManager entityManager;

  @Override
public Page<User> searchUsers(
    String search,
    LocalDate minBirthDate,
    LocalDate maxBirthDate,
    Pageable pageable
) {

    StringBuilder whereClause = new StringBuilder("""
        WHERE status = true
    """);

    if (search != null && !search.isBlank()) {
        whereClause.append("""
            AND (
                LOWER(u.nombre) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(u.apellido) LIKE LOWER(CONCAT('%', :search, '%'))
            )
        """);
    }

    if (minBirthDate != null) {
        whereClause.append("""
            AND u.fechaNacimiento >= :minBirthDate
        """);
    }

    if (maxBirthDate != null) {
        whereClause.append("""
            AND u.fechaNacimiento <= :maxBirthDate
        """);
    }

    // =========================
    // QUERY PRINCIPAL
    // =========================

    StringBuilder jpql = new StringBuilder("""
        SELECT u
        FROM User u
    """);

    jpql.append(whereClause);

    // SORT
    if (pageable.getSort().isSorted()) {

        jpql.append(" ORDER BY ");

        List<String> orders = pageable.getSort()
            .stream()
            .map(order ->
                "u." + order.getProperty() + " " + order.getDirection().name()
            )
            .toList();

        jpql.append(String.join(", ", orders));
    }

    TypedQuery<User> query =
        entityManager.createQuery(jpql.toString(), User.class);

    // =========================
    // QUERY COUNT
    // =========================

    StringBuilder countJpql = new StringBuilder("""
        SELECT COUNT(u)
        FROM User u
    """);

    countJpql.append(whereClause);

    TypedQuery<Long> countQuery =
        entityManager.createQuery(countJpql.toString(), Long.class);

    // =========================
    // PARAMETROS
    // =========================

    if (search != null && !search.isBlank()) {
        query.setParameter("search", search);
        countQuery.setParameter("search", search);
    }

    if (minBirthDate != null) {
        query.setParameter("minBirthDate", minBirthDate);
        countQuery.setParameter("minBirthDate", minBirthDate);
    }

    if (maxBirthDate != null) {
        query.setParameter("maxBirthDate", maxBirthDate);
        countQuery.setParameter("maxBirthDate", maxBirthDate);
    }

    // =========================
    // PAGINACION
    // =========================

    query.setFirstResult((int) pageable.getOffset());

    query.setMaxResults(pageable.getPageSize());

    List<User> users = query.getResultList();

    Long total = countQuery.getSingleResult();

    // =========================
    // PAGE RESPONSE
    // =========================

    return new PageImpl<>(
        users,
        pageable,
        total
    );
}
  
}
