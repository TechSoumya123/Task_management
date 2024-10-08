package com.soumya.taskproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soumya.taskproject.dto.LoginDto;
import com.soumya.taskproject.dto.UserDto;
import com.soumya.taskproject.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/auth")
@AllArgsConstructor
public class AuthController {

	private UserService userService;

	private AuthenticationManager authenticationManager;

	@PostMapping(path = "/register")
	public ResponseEntity<UserDto> createUser(@RequestBody(required = true) UserDto userDto) {
		return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
	}

	@PostMapping(path = "/login")
	public ResponseEntity<String> loginUser(@RequestBody LoginDto loginDto) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseEntity<>("User logged in successfully.", HttpStatus.OK);
	}

}
