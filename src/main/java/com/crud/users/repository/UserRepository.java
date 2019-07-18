package com.crud.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.users.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}