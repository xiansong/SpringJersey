package com.pwz.rest;

import java.util.Date;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.pwz.dao.GasAcctDAO;
import com.pwz.dao.GasReadingDAO;
import com.pwz.model.GasReading;

@Component
@Path("gasreadings")
public class GasReadingService {

	@Autowired
	private GasReadingDAO gasRDAO;
	@Autowired
	private GasAcctDAO gasDAO;

	@POST
	@Path("add")
	@Produces(MediaType.TEXT_PLAIN)
	public Response add(@FormParam("gasAcctId") final int gasAcctId,
			@FormParam("reading") final int reading,
			@FormParam("readDate") final Date readDate) {
		try {
			if (!SecurityContextHolder.getContext().getAuthentication()
					.getName()
					.equals(gasDAO.getAccount(gasAcctId).getUsername())) {
				return Response.status(406)
						.entity("Resource identity not correct.").build();
			}
		} catch (DataAccessException e) {
			return Response.serverError()
					.entity("Failed to retrieve resource identity.").build();
		}
		GasReading gasReading = new GasReading();
		gasReading.setGasAcctId(gasAcctId);
		gasReading.setReading(reading);
		gasReading.setReadDate(readDate);
		try {
			return Response.status(201)
					.entity(String.valueOf(gasRDAO.addReading(gasReading)))
					.build();
		} catch (DataAccessException e) {
			return Response.serverError().entity("Server error.").build();
		}
	}

	@GET
	@Path("{readingId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getReading(@PathParam("readingId") final int readingId) {
		try {
			GasReading gasReading = gasRDAO.getReading(readingId);
			if (gasReading.getUsername().equals(
					SecurityContextHolder.getContext().getAuthentication()
							.getName())) {
				return Response.ok(gasReading).build();
			} else {
				return Response.status(406)
						.entity("Resource not owned by you.").build();
			}
		} catch (IncorrectResultSizeDataAccessException e) {
			return Response.status(404).entity("Resource not exist.").build();
		} catch (DataAccessException e) {
			return Response.serverError().entity("Server error").build();
		}
	}

	@GET
	@Path("account/{gasAcctId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getReadings(@PathParam("gasAcctId") final int gasAcctId) {
		try {
			if (!SecurityContextHolder.getContext().getAuthentication()
					.getName()
					.equals(gasDAO.getAccount(gasAcctId).getUsername())) {
				return Response.status(406).entity("Resource not owned by you")
						.build();
			}
		} catch (DataAccessException e) {
			return Response.serverError()
					.entity("Failed to retrieve resource identity.").build();
		}
		try {
			List<GasReading> readings = gasRDAO.getReadings(gasAcctId);
			if (readings.isEmpty()) {
				return Response.status(404).entity("Resource is empty.")
						.build();
			}
			return Response.ok(
					readings.toArray(new GasReading[readings.size()])).build();
		} catch (DataAccessException e) {
			return Response.serverError().entity("Server error.").build();
		}
	}

	@DELETE
	@Path("delete/{readingId}")
	public Response removeReading(@PathParam("readingId") int readingId) {
		try {
			if (!SecurityContextHolder.getContext().getAuthentication()
					.getName()
					.equals(gasRDAO.getReading(readingId).getUsername())) {
				return Response.status(406)
						.entity("Resource not owned by you.").build();
			}
		} catch (DataAccessException e) {
			return Response.serverError()
					.entity("Failed to retrieve user identity for resource.")
					.build();
		}
		try {
			gasRDAO.deleteReading(readingId);
			return Response.ok("Deleted.").build();
		} catch (DataAccessException e) {
			return Response.serverError().entity("Server error.").build();
		}
	}

}
