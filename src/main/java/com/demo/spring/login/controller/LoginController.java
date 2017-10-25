/**
 * 
 */
package com.demo.spring.login.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.demo.spring.login.beans.CurrentUser;
import com.demo.spring.login.beans.LoginDto;
import com.demo.spring.login.service.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	LoginService loginServiceImpl;
	
	@RequestMapping(value="login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request,
		HttpServletResponse response, HttpSession session) {
		
		HttpSession currentSsession = request.getSession(false);
		if(null != currentSsession) {
			currentSsession.invalidate();
			currentSsession = request.getSession(true);
		}
		ModelAndView mv = new ModelAndView("login/_form");
		return mv;
		
	}
	
	@RequestMapping(value="authenticate", method = RequestMethod.POST)
	public void authenticate(
		@ModelAttribute("loginDto") LoginDto loginDto,
		HttpServletRequest request, HttpServletResponse response, 
		HttpSession session) throws IOException {
		
		CurrentUser currentUser = loginServiceImpl.authenticate(loginDto);
		String redirectUrl = null;
		
		if(null != currentUser) {
			HttpSession currentSsession = request.getSession(false);
			if(null != currentSsession) {
				currentSsession.invalidate();
			}
			currentSsession = request.getSession(true);
			currentSsession.setAttribute("currentUser", currentUser);
			
			redirectUrl = request.getContextPath() + "/user/1";
			
		} else {
			redirectUrl = request.getContextPath() + "/login";
		}
		
		response.sendRedirect(redirectUrl);
		
	}
	
	@RequestMapping(value="logout", method = RequestMethod.GET)
	public void logout(HttpServletRequest request,
		HttpServletResponse response) throws IOException {
		
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath() + "/login");
		
	}
	
}
