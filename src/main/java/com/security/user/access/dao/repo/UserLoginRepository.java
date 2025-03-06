package com.security.user.access.dao.repo;

import com.security.user.access.dao.entity.UserLoginEO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLoginEO, Integer> {

    UserLoginEO findByUserName(String username);

}
