package com.security.user.access.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.user.access.dao.entity.UsersEO;

@Repository
public interface UsersRepository extends JpaRepository<UsersEO, Integer> {

	UsersEO findByUserName(String username);

}
