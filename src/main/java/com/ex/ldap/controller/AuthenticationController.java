package com.hsbc.ldap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hsbc.ldap.dto.AuthenticateRequest;
import com.hsbc.ldap.service.AuthenticationService;

@RestController
public class AuthenticationController {
	
	
	@Autowired
	AuthenticationService authenticationService;

	@PostMapping(value="/token")
	public String authenticate(@RequestBody AuthenticateRequest authenticateRequest)
	{
		return authenticationService.authenticate(authenticateRequest);
	}
}
