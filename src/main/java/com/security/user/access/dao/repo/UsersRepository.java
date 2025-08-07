package com.security.user.access.dao.repo;

import com.security.user.access.dao.entity.UsersEO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UsersEO, Integer> {

    UsersEO findByUsernameAndIsEnabled(String username, boolean enable);

    UsersEO findByUsername(String username);

}
