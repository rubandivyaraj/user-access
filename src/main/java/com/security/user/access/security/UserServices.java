package com.security.user.access.security;

import com.security.user.access.dao.entity.UserLoginEO;
import com.security.user.access.dao.repo.UserLoginRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServices implements UserDetailsService {

    private final UserLoginRepository userLoginRepository;

    public UserServices(UserLoginRepository userLoginRepository) {
        this.userLoginRepository = userLoginRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLoginEO userDetail = userLoginRepository.findByUserName(username);
        return User.builder().username(userDetail.getUserName()).password(userDetail.getPassword()).build();
    }
}
