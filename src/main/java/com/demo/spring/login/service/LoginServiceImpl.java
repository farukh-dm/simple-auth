package com.demo.spring.login.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.demo.spring.login.beans.CurrentUser;
import com.demo.spring.login.beans.LoginDto;

@Component(value="loginServiceImpl")
public class LoginServiceImpl implements LoginService {

	@Override
	public CurrentUser authenticate(LoginDto loginDto) {
		CurrentUser currentUser = null;
		
		// TODO: Do necessary validation
		currentUser = new CurrentUser(loginDto.getEmail(), "Lorem");
		
		return currentUser;
	}

	@Override
	public CurrentUser getCurrentUser(HttpServletRequest request) {
		
		CurrentUser currentUser = null;
		try {
			
			currentUser = (CurrentUser) request.getSession(false).getAttribute("currentUser");
			
		} catch(Exception e) {
			
		}
		return currentUser;
	}

}
