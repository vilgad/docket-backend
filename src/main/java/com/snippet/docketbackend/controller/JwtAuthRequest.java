package com.snippet.docketbackend.controller;

import lombok.Data;

@Data
public class JwtAuthRequest {
	private String username;
	
	private String password;
	
}
