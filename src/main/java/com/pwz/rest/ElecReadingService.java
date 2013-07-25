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

import com.pwz.dao.ElecAcctDAO;
import com.pwz.dao.ElecReadingDAO;
import com.pwz.model.ElecReading;

@Component
@Path("elecreadings")
public class ElecReadingService {

	@Autowired
	private ElecReadingDAO elecRDAO;
	@Autowired
	private ElecAcctDAO elecDAO;

	@POST
	@Path("add")
	@Produces(MediaType.TEXT_PLAIN)
	public Response add(@FormParam("elecAcctId") int elecAcctId,
			@FormParam("dayReading") int dayReading,
			@FormParam("nightReading") int nightReading,
			@FormParam("readDate") Date readDate) {
		try {
			if (!SecurityContextHolder.getContext().getAuthentication()
					.getName()
					.equals(elecDAO.getAccount(elecAcctId).getUsername())) {
				return Response.status(406)
						.entity("Resource identity not correct.").build();
			}
		} catch (DataAccessException e) {
			return Response.serverError()
					.entity("Failed to retrieve resource identity.").build();
		}
		ElecReading reading = new ElecReading();
		reading.setElecAcctId(elecAcctId);
		reading.setDayReading(dayReading);
		reading.setNightReading(nightReading);
		reading.setReadDate(readDate);
		try {
			return Response.status(201)
					.entity(String.valueOf(elecRDAO.addReading(reading)))
					.build();
		} catch (DataAccessException e) {
			return Response.serverError().entity("Server error.").build();
		}
	}

	@GET
	@Path("{readingId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getReading(@PathParam("readingId") int readingId) {
		try {
			ElecReading reading = elecRDAO.getReading(readingId);
			if (reading.getUsername().equals(
					SecurityContextHolder.getContext().getAuthentication()
							.getName())) {
				return Response.ok(reading).build();
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
	@Path("account/{elecAcctId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getReadings(@PathParam("elecAcctId") int elecAcctId) {
		try {
			if (!SecurityContextHolder.getContext().getAuthentication()
					.getName()
					.equals(elecDAO.getAccount(elecAcctId).getUsername())) {
				return Response.status(406).entity("Resource not owned by you")
						.build();
			}
		} catch (DataAccessException e) {
			return Response.serverError()
					.entity("Failed to retrieve resource identity.").build();
		}
		try {
			List<ElecReading> readings = elecRDAO.getReadings(elecAcctId);
			if (readings.isEmpty()) {
				return Response.status(404).entity("Resource not exist.")
						.build();
			}
			return Response.ok(
					readings.toArray(new ElecReading[readings.size()])).build();
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
					.equals(elecRDAO.getReading(readingId).getUsername())) {
				return Response.status(406)
						.entity("Resource not owned by you.").build();
			}
		} catch (DataAccessException e) {
			return Response.serverError()
					.entity("Failed to retrieve user identity for resource.")
					.build();
		}
		try {
			elecRDAO.deleteReading(readingId);
			return Response.ok("Deleted").build();
		} catch (DataAccessException e) {
			return Response.serverError().entity("Server error.").build();
		}
	}

}
