package com.nnk.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.springboot.domain.User;

public interface IUserRepository extends JpaRepository<User, Integer> {

}
