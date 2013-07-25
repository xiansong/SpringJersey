package com.pwz.rest;

import java.util.List;

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
import org.springframework.stereotype.Component;

import com.pwz.dao.ElecAcctDAO;
import com.pwz.dao.UserDAO;
import com.pwz.model.ElecAccount;

@Component
@Path("elecaccts")
public class ElecAcctService {

	@Autowired
	private ElecAcctDAO elecDAO;
	@Autowired
	private UserDAO userDAO;

	@GET
	@Path("{acctId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getAccount(@PathParam("acctId") int acctId) {
		try {
			ElecAccount account = elecDAO.getAccount(acctId);
			if (account.getUsername().equals(
					SecurityContextHolder.getContext().getAuthentication()
							.getName())) {
				return Response.ok(account).build();
			} else {
				return Response.status(406)
						.entity("Resource not owned by you.").build();
			}
		} catch (IncorrectResultSizeDataAccessException e) {
			return Response.status(404).entity("Resource not found").build();
		} catch (DataAccessException e) {
			return Response.serverError().entity("Server error.").build();
		}
	}

	@GET
	@Path("user/{userId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getAccounts(@PathParam("userId") int userId) {
		try {
			if (!SecurityContextHolder.getContext().getAuthentication()
					.getName().equals(userDAO.getUser(userId).getUsername())) {
				return Response.status(406)
						.entity("Resource not owned by you.").build();
			}
		} catch (DataAccessException e) {
			return Response.serverError()
					.entity("Failed to retrieve user identity").build();
		}
		try {
			List<ElecAccount> elecAccts = elecDAO.listAccounts(userId);
			return Response.ok(
					elecAccts.toArray(new ElecAccount[elecAccts.size()]))
					.build();
		} catch (IncorrectResultSizeDataAccessException e) {
			return Response.status(404).entity("Resource not found").build();
		} catch (DataAccessException e) {
			return Response.serverError().entity("Server error").build();
		}
	}

	@POST
	@Path("add")
	@Produces(MediaType.TEXT_PLAIN)
	public Response register(@FormParam("userId") int userId,
			@FormParam("companyName") String companyName,
			@FormParam("acctNumber") String acctNumber,
			@FormParam("MPRN") long MPRN) {
		try {
			if (!SecurityContextHolder.getContext().getAuthentication()
					.getName().equals(userDAO.getUser(userId).getUsername())) {
				return Response.status(406)
						.entity("User identity not correct.").build();
			}
		} catch (DataAccessException e) {
			return Response.serverError()
					.entity("Failed to retrieve user identity").build();
		}

		ElecAccount account = new ElecAccount();
		account.setUserId(userId);
		account.setCompanyName(companyName);
		account.setAcctNumber(acctNumber);
		account.setMPRN(MPRN);
		try {
			return Response.status(201)
					.entity(String.valueOf(elecDAO.addAccount(account)))
					.build();
		} catch (DataIntegrityViolationException e) {
			return Response.serverError().entity("account exist").build();
		} catch (DataAccessException e) {
			return Response.serverError().entity("Server error.").build();
		}
	}

	@PUT
	@Path("update")
	public Response update(@FormParam("acctId") int acctId,
			@FormParam("companyName") String companyName,
			@FormParam("acctNumber") String acctNumber,
			@FormParam("MPRN") long MPRN) {
		try {
			if (!SecurityContextHolder.getContext().getAuthentication()
					.getName().equals(elecDAO.getAccount(acctId).getUsername())) {
				return Response.status(406)
						.entity("Resource not owned by you.").build();
			}
		} catch (DataAccessException e) {
			return Response.serverError()
					.entity("Failed to retrieve user identity for resource.")
					.build();
		}
		ElecAccount account = new ElecAccount();
		account.setElecAcctId(acctId);
		account.setCompanyName(companyName);
		account.setAcctNumber(acctNumber);
		account.setMPRN(MPRN);
		try {
			elecDAO.updateAccount(account);
			return Response.ok().entity("Updated.").build();
		} catch (DataAccessException e) {
			return Response.serverError().entity("Failed to update.").build();
		}
	}

	@DELETE
	@Path("delete/{acctId}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response removeUser(@PathParam("acctId") int acctId) {
		try {
			if (!SecurityContextHolder.getContext().getAuthentication()
					.getName().equals(elecDAO.getAccount(acctId).getUsername())) {
				return Response.status(406)
						.entity("Resource not owned by you.").build();
			}
		} catch (DataAccessException e) {
			return Response.serverError()
					.entity("Failed to retrieve user identity for resource")
					.build();
		}
		try {
			elecDAO.deleteAccount(acctId);
			return Response.ok("true").build();
		} catch (DataAccessException e) {
			return Response.serverError().entity("Server error").build();
		}
	}

}
