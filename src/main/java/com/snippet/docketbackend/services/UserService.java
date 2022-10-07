package com.snippet.docketbackend.services;

import java.util.List;

import com.snippet.docketbackend.models.User;

public interface UserService {

	User registerNewUser(User user);

	User createUser(User user);

	User updateUser(User user, Integer userId) throws Exception;

	User getUserById(Integer userId);

	List<User> getAllUsers();

	void deleteUser(Integer userId);

}
