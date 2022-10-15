package com.snippet.docketbackend.services;

import com.snippet.docketbackend.models.User;
import com.snippet.docketbackend.utils.Response;

public interface UserService {
	Response registerNewUser(User user);

	Response createUser(User user);

	Response updateUser(String email, String name, String password, String linkName, Long userId);

	Response getUserById(Long userId);

	Response getAllUsers();

	Response deleteUser(Long userId);

	Response updateLinkName(String linkName, Long uid);
}
