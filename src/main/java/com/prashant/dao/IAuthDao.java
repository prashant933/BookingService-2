package com.prashant.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prashant.model.User;

public interface IAuthDao extends JpaRepository<User, Integer> {
	User findByEmail(String email);
}
