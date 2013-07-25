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

import com.pwz.dao.GasAcctDAO;
import com.pwz.dao.UserDAO;
import com.pwz.model.GasAccount;

@Component
@Path("gasaccts")
public class GasAcctService {

	@Autowired
	private GasAcctDAO gasDAO;
	@Autowired
	private UserDAO userDAO;

	@GET
	@Path("{acctId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getAccount(@PathParam("acctId") final int acctId) {
		try {
			GasAccount account = gasDAO.getAccount(acctId);
			if (account.getUsername().equals(
					SecurityContextHolder.getContext().getAuthentication()
							.getName())) {
				return Response.ok(account).build();
			} else {
				return Response.status(406)
						.entity("Resource not owned by you.").build();
			}
		} catch (IncorrectResultSizeDataAccessException e) {
			return Response.status(404).entity("Resource not found.").build();
		} catch (DataAccessException e) {
			return Response.serverError().entity("Server error.").build();
		}
	}

	@GET
	@Path("user/{userId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getAccounts(@PathParam("userId") final int userId) {
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
			List<GasAccount> gasAccts = gasDAO.listAccounts(userId);
			return Response.ok(
					gasAccts.toArray(new GasAccount[gasAccts.size()])).build();
		} catch (IncorrectResultSizeDataAccessException e) {
			return Response.status(404).entity("Resource not found.").build();
		} catch (DataAccessException e) {
			return Response.serverError().entity("Server error").build();
		}
	}

	@POST
	@Path("add")
	@Produces(MediaType.TEXT_PLAIN)
	public Response register(@FormParam("userId") final int userId,
			@FormParam("companyName") final String companyName,
			@FormParam("acctNumber") final String acctNumber,
			@FormParam("GPRN") final long GPRN) {
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
		GasAccount account = new GasAccount();
		account.setUserId(userId);
		account.setCompanyName(companyName);
		account.setAcctNumber(acctNumber);
		account.setGPRN(GPRN);
		try {
			return Response.status(201)
					.entity(String.valueOf(gasDAO.addAccount(account))).build();
		} catch (DataIntegrityViolationException e) {
			return Response.serverError().entity("account exist").build();
		} catch (DataAccessException e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	@PUT
	@Path("update")
	public Response update(@FormParam("acctId") final int acctId,
			@FormParam("companyName") final String companyName,
			@FormParam("acctNumber") final String acctNumber,
			@FormParam("MPRN") final long GPRN) {
		try {
			if (!SecurityContextHolder.getContext().getAuthentication()
					.getName().equals(gasDAO.getAccount(acctId).getUsername())) {
				return Response.status(406)
						.entity("Resource not owned by you.").build();
			}
		} catch (DataAccessException e) {
			return Response.serverError()
					.entity("Failed to retrieve user identity for resource.")
					.build();
		}
		GasAccount account = new GasAccount();
		account.setGasAcctId(acctId);
		account.setCompanyName(companyName);
		account.setAcctNumber(acctNumber);
		account.setGPRN(GPRN);
		try {
			gasDAO.updateAccount(account);
			return Response.ok().entity("Updated.").build();
		} catch (DataAccessException e) {
			return Response.serverError().entity("Server error.").build();
		}
	}

	@DELETE
	@Path("delete/{acctId}")
	public Response removeUser(@PathParam("acctId") final int acctId) {
		try {
			if (!SecurityContextHolder.getContext().getAuthentication()
					.getName().equals(gasDAO.getAccount(acctId).getUsername())) {
				return Response.status(406)
						.entity("Resource not owned by you.").build();
			}
		} catch (DataAccessException e) {
			return Response.serverError()
					.entity("Failed to retrieve user identity for resource.")
					.build();
		}
		try {
			gasDAO.deleteAccount(acctId);
			return Response.ok("true").build();
		} catch (DataAccessException e) {
			return Response.serverError().entity("Server error").build();
		}
	}

}
