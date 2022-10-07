package com.snippet.docketbackend.security;

import com.snippet.docketbackend.models.User;

import lombok.Data;

@Data
public class JwtAuthResponse {

	private String token;
	
	private User user;
}
