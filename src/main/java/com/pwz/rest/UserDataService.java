package com.pwz.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.pwz.dao.UserDAO;
import com.pwz.model.User;

@Component
@Path("/users")
public class UserDataService {
	@Autowired
	UserDAO userDAO;

	@GET
	@Path("{username}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getUser(@PathParam("username") final String username) {
		try {
			if (SecurityContextHolder.getContext().getAuthentication()
					.getName().equals(username)) {
				return Response.ok(userDAO.getUser(username)).build();
			} else {
				return Response.status(406)
						.entity("Resource not owned by you.").build();
			}
		} catch (IncorrectResultSizeDataAccessException e) {
			return Response.status(404).entity("Resource not exist.").build();
		} catch (DataAccessException e) {
			return Response.serverError().entity("Server error.").build();
		}
	}

	@GET
	@Path("list")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getUsers() {
		List<User> users = null;
		try {
			users = userDAO.listUsers();
		} catch (DataAccessException e) {
			Response.serverError().entity("Server error").build();
		}
		return Response.ok(users.toArray(new User[users.size()])).build();
	}

	@POST
	@Path("add")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Response register(@FormParam("username") final String username,
			@FormParam("password") final String password) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		User user = new User();
		user.setUsername(username);
		user.setPassword(encoder.encode(password));
		try {
			return Response.status(201)
					.entity(String.valueOf(userDAO.register(user))).build();
		} catch (DataIntegrityViolationException e) {
			return Response.serverError().entity("user name exist").build();
		} catch (DataAccessException e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	@PUT
	@Path("update")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Response update(@FormParam("userId") final int userId,
			@FormParam("password") String password) {
		User user = null;
		try {
			user = userDAO.getUser(userId);
			if (!SecurityContextHolder.getContext().getAuthentication()
					.getName().equals(user.getUsername())) {
				return Response.status(406)
						.entity("Resource not owned by you.").build();
			}
		} catch (DataAccessException e) {
			return Response.serverError()
					.entity("User not find or server error.").build();
		}

		PasswordEncoder encoder = new BCryptPasswordEncoder();
		if (encoder.matches(password, user.getPassword())) {
			return Response.notModified("Not changed.").build();
		}

		user.setPassword(encoder.encode(password));
		try {
			userDAO.update(user);
			return Response.ok("Updated.").build();
		} catch (DataAccessException e) {
			return Response.serverError().entity("Server error.").build();
		}
	}

	@DELETE
	@Path("delete/{userId}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response removeUser(@PathParam("userId") final int userId) {
		try {
			if (!SecurityContextHolder.getContext().getAuthentication()
					.getName().equals(userDAO.getUser(userId).getUsername())) {
				return Response.status(406)
						.entity("Resource not owned by you.").build();
			}
		} catch (DataAccessException e) {
			return Response.serverError()
					.entity("User not find or server error.").build();
		}
		try {
			userDAO.delete(userId);
		} catch (DataAccessException e) {
			Response.serverError().entity("Server error").build();
		}
		return Response.ok("true").build();
	}

}
