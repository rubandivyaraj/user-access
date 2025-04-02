package com.security.user.access.security;

import com.security.user.access.dao.entity.UsersEO;
import com.security.user.access.dao.repo.UsersRepository;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersEO userDetail = usersRepository.findByUsernameAndIsEnabled(username, true);
        return User.builder().username(userDetail.getUsername()).password(userDetail.getPassword()).build();
    }
}
