package com.outfit7.fun7gamebackend.repository;

import com.outfit7.fun7gamebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
