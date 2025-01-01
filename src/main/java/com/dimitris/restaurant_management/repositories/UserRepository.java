package com.dimitris.restaurant_management.repositories;

import com.dimitris.restaurant_management.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("FROM User u LEFT JOIN FETCH u.roleList WHERE u.username = :username")
    Optional<User> findByUsernameWithRoles(@Param("username") String username);
}
