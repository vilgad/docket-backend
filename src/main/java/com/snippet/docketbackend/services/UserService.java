package com.snippet.docketbackend.services;

import com.snippet.docketbackend.models.User;
import com.snippet.docketbackend.utils.Response;

public interface UserService {

	Response registerNewUser(User user);

	Response createUser(User user);

	Response updateUser(User user, Integer userId);

	Response getUserById(Integer userId);

	Response getAllUsers();

	Response deleteUser(Integer userId);
}
