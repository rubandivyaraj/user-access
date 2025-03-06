package com.security.user.access.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_login")
@Getter
@Setter
public class UserLoginEO {

	@Id
	private Integer userId;
	private String userName;
	private String password;
	private String token;
	private boolean isEnabled;
}
