package com.app.rakbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.rakbank.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
