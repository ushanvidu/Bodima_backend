package com.Test.Bodima.Repo;




import com.Test.Bodima.Model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

    @Repository
    public interface LoginRepository extends JpaRepository<Login, Integer> {

        // Find by username (for login authentication)
        Optional<Login> findByUsername(String username);

        // Find by user ID (1:1 relationship check)
        Optional<Login> findByUserUserId(Integer userId);

        // Custom query with join (example)
        @Query("SELECT l FROM Login l JOIN FETCH l.user WHERE l.username = :username")
        Optional<Login> findByUsernameWithUser(@Param("username") String username);

        // Check if username exists (for registration validation)
        boolean existsByUsername(String username);

        // Check if user already has a login (1:1 relationship enforcement)
        boolean existsByUserUserId(Integer userId);
    }

