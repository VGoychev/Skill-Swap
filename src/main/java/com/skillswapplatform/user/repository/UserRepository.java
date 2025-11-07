package com.skillswapplatform.user.repository;

import com.skillswapplatform.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User getUserByUsername(String username);

    User getUserByEmail(String email);
}
