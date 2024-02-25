package com.nnk.springboot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.springboot.domain.User;

public interface IUserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);
}
