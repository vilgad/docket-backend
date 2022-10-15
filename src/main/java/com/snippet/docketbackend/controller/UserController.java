package com.snippet.docketbackend.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.snippet.docketbackend.models.User;
import com.snippet.docketbackend.services.UserService;
import com.snippet.docketbackend.utils.Response;
import com.snippet.docketbackend.utils.ResponseStatus;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;

	// POST-create user
	@PostMapping("/")
	public Response createUser(@Valid @RequestBody User user) {
		return userService.createUser(user);
	}

	// PUT- update user

	@PutMapping("/{userId}")
	public Response updateUser(
		@RequestParam(required = false) String email,
		@RequestParam(required = false) String name,
		@RequestParam(required = false) String password,
		@RequestParam(required = false) String linkName,
		@PathVariable("userId") Long uid
	) {
		return userService.updateUser(email, name, password, linkName, uid);
	}

	// DELETE -delete user
	@DeleteMapping("/{userId}")
	public Response deleteUser(@PathVariable("userId") Long uid) {
		this.userService.deleteUser(uid);
		return new Response(
				"User with uId: " + uid + " Deleted",
				new ResponseStatus(
						HttpStatus.OK.value(),
						HttpStatus.OK.getReasonPhrase()));
	}

	// GET - user get
	@GetMapping("/")
	public Response getAllUsers() {
		return new Response(
				"Users Fetched Successfully",
				new ResponseStatus(
						HttpStatus.OK.value(),
						HttpStatus.OK.getReasonPhrase()),
				this.userService.getAllUsers());
	}

	// GET - user get
	@GetMapping("/{userId}")
	public Response getSingleUser(@PathVariable Long userId) {
		return new Response(
				"User fetched Successfully",
				new ResponseStatus(
						HttpStatus.OK.value(),
						HttpStatus.OK.getReasonPhrase()),
				this.userService.getUserById(userId));
	}

	@PutMapping("/updateLinkName/{userId}")
	public Response updateLinkName(@RequestParam String linkName,
			@PathVariable("userId") Long uid) {
		return userService.updateLinkName(linkName, uid);
	}

}
