package com.security.user.access.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.user.access.dao.entity.UsersEO;
import com.security.user.access.dao.repo.UsersRepository;

@Service
public class UserServices implements UserDetailsService {

	private final UsersRepository usersRepository;

	public UserServices(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsersEO userDetail = usersRepository.findByUserName(username);
		return User.builder().username(userDetail.getUserName()).password(userDetail.getPassword()).build();
	}
}
