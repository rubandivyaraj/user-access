package com.security.user.access.security;

import com.security.user.access.dao.entity.UsersEO;
import com.security.user.access.dao.repo.UsersRepository;
import com.security.user.access.exception.UserAccessException;
import com.security.user.access.exception.UserAccessExceptionCode;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServices implements UserDetailsService {

    private final UsersRepository usersRepository;

    public UserServices(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UsersEO userDetail = usersRepository.findByUsernameAndIsEnabled(username, true);
        if(userDetail == null)
            throw new UserAccessException(UserAccessExceptionCode.USER_INVALID);

        return User.builder().username(userDetail.getUsername()).password(userDetail.getPassword()).build();
    }
}
