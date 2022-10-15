package com.snippet.docketbackend.services.Implementations;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.snippet.docketbackend.models.User;
import com.snippet.docketbackend.repository.UserRepo;
import com.snippet.docketbackend.services.UserService;
import com.snippet.docketbackend.utils.Response;
import com.snippet.docketbackend.utils.ResponseStatus;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Response createUser(User user) {
		Optional<User> userOptional = userRepo.findByEmail(user.getEmail());

		if (userOptional.isEmpty()) {
			userRepo.save(user);

			return new Response(
					"User Created",
					new ResponseStatus(
							HttpStatus.OK.value(),
							HttpStatus.OK.getReasonPhrase()));
		}

		return new Response(
				"User with email: " + user.getEmail() + " already exists",
				new ResponseStatus(
						HttpStatus.CONFLICT.value(),
						HttpStatus.CONFLICT.getReasonPhrase()));
	}

	@Transactional
	@Override
	public Response updateUser(String email, String name, String password, String linkName, Long userId) {
		Optional<User> user1 = this.userRepo.findById(userId);

		if (user1.isEmpty()) {
			return new Response(
					"User does not exist",
					new ResponseStatus(
							HttpStatus.NOT_FOUND.value(),
							HttpStatus.NOT_FOUND.getReasonPhrase()));
		}

		if (name != null) {
			user1.get().setName(name);
		}

		if (password != null) {
			user1.get().setPassword(passwordEncoder.encode(password));
		}

		if (linkName != null) {
			Optional<User> userOptional = userRepo.findByLinkName(linkName);

			if (userOptional.isPresent()) {
				return new Response(
						"link already taken",
						new ResponseStatus(
								HttpStatus.BAD_REQUEST.value(),
								HttpStatus.BAD_REQUEST.getReasonPhrase()));
			}

			user1.get().setLinkName(linkName);
		}

		if (email != null) {
			Optional<User> userOptional = userRepo.findByEmail(email);

			if (userOptional.isPresent()) {
				return new Response(
						"User with this email already exist",
						new ResponseStatus(
								HttpStatus.BAD_REQUEST.value(),
								HttpStatus.BAD_REQUEST.getReasonPhrase()));
			}

			user1.get().setEmail(email);
		}

		return new Response(
				"User updated successfully",
				new ResponseStatus(
						HttpStatus.OK.value(),
						HttpStatus.OK.getReasonPhrase()));
	}

	@Override
	public Response getUserById(Long userId) {
		Optional<User> user1 = userRepo.findById(userId);

		if (user1.isEmpty()) {
			return new Response(
					"User does not exist",
					new ResponseStatus(
							HttpStatus.BAD_REQUEST.value(),
							HttpStatus.BAD_REQUEST.getReasonPhrase()));
		}

		return new Response(
				"User fetched successfully",
				new ResponseStatus(
						HttpStatus.OK.value(),
						HttpStatus.OK.getReasonPhrase()),
				user1.get());
	}

	@Override
	public Response getAllUsers() {
		List<User> users = userRepo.findAll();

		if (users.isEmpty()) {
			return new Response(
					"User fetched successfully",
					new ResponseStatus(
							HttpStatus.NOT_FOUND.value(),
							HttpStatus.NOT_FOUND.getReasonPhrase()));
		}

		return new Response(
				"Users fetched successfully",
				new ResponseStatus(
						HttpStatus.OK.value(),
						HttpStatus.OK.getReasonPhrase()),
				users);
	}

	@Transactional
	@Override
	public Response deleteUser(Long userId) {
		Optional<User> user1 = userRepo.findById(userId);

		if (user1.isEmpty()) {
			return new Response(
					"User does not exist",
					new ResponseStatus(
							HttpStatus.BAD_REQUEST.value(),
							HttpStatus.BAD_REQUEST.getReasonPhrase()));
		}

		userRepo.delete(user1.get());

		return new Response(
				"User deleted successfully",
				new ResponseStatus(
						HttpStatus.OK.value(),
						HttpStatus.OK.getReasonPhrase()));
	}

	@Override
	public Response registerNewUser(User user) {
		Optional<User> user1 = userRepo.findByEmail(user.getEmail());

		if (user1.isEmpty()) {
			user.setPassword(this.passwordEncoder.encode(user.getPassword()));
			User newUser = this.userRepo.save(user);

			return new Response(
					"User created",
					new ResponseStatus(
							HttpStatus.OK.value(),
							HttpStatus.OK.getReasonPhrase()),
					newUser);
		}

		return new Response(
				"User with this email already exists",
				new ResponseStatus(
						HttpStatus.CONFLICT.value(),
						HttpStatus.CONFLICT.getReasonPhrase()));
	}

	@Transactional
	@Override
	public Response updateLinkName(String linkName, Long uid) {
		Optional<User> user1 = this.userRepo.findById(uid);

		if (user1.isEmpty()) {
			return new Response(
					"User does not exist",
					new ResponseStatus(
							HttpStatus.BAD_REQUEST.value(),
							HttpStatus.BAD_REQUEST.getReasonPhrase()));
		}

		Optional<User> user = userRepo.findByLinkName(linkName);

		if (user.isPresent()) {
			return new Response(
					"This link is already taken",
					new ResponseStatus(
							HttpStatus.BAD_REQUEST.value(),
							HttpStatus.BAD_REQUEST.getReasonPhrase()));
		}

		return new Response(
				"Link updated successfully",
				new ResponseStatus(
						HttpStatus.OK.value(),
						HttpStatus.OK.getReasonPhrase()));
	}
}
