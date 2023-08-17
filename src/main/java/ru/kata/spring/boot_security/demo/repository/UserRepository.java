package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.name = :name")
    User findByNameWithRoles(@Param("name") String name);
}

