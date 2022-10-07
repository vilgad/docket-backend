package com.snippet.docketbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.snippet.docketbackend.exceptions.ResourceNotFoundException;
import com.snippet.docketbackend.models.User;
import com.snippet.docketbackend.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User createUser(User user) {
		User savedUser = this.userRepo.save(user);
		return savedUser;
	}

	@Override
	public User updateUser(User user, Integer userId) throws Exception {

		Optional<User> user1 = this.userRepo.findById(userId);

		if (user1.isEmpty()) {
			throw new Exception("User not found with userId: " + userId);
		}

		user1.get().setName(user.getName());
		user1.get().setEmail(user.getEmail());
		user1.get().setPassword(user.getPassword());

		User updatedUser = this.userRepo.save(user);
		return updatedUser;
	}

	@Override
	public User getUserById(Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

		return user;
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		return users;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user);
	}

	@Override
	public User registerNewUser(User user) {
		// encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		User newUser = this.userRepo.save(user);

		return newUser;
	}

}
