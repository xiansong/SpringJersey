package com.pwz.rest.test;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class ElecReadingServiceTest {

	private final String URL = "https://utilityservice.cloudfoundry.com/service/elecreadings";

	@Test
	public void testGetReading() {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("zengx@tcd.ie", "123456"));
		WebResource webResource = client.resource(URL + "/1");
		ClientResponse response = webResource.accept(MediaType.APPLICATION_XML)
				.get(ClientResponse.class);
		System.out.println(response.getEntity(String.class));
		assertEquals(200, response.getStatus());
	}

	@Test
	public void testGetReadings() {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("zengx@tcd.ie", "123456"));
		WebResource webResource = client.resource(URL + "/account/1");
		ClientResponse response = webResource.accept(MediaType.APPLICATION_XML)
				.get(ClientResponse.class);
		System.out.println(response.getEntity(String.class));
		assertEquals(200, response.getStatus());
	}

	@Test
	public void testAddAndDelete() {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("lester101@gmail.com", "123456"));
		WebResource webResource = client.resource(URL + "/add");
		MultivaluedMap<String, String> requestEntity = new MultivaluedMapImpl();
		requestEntity.add("elecAcctId", "12");
		requestEntity.add("dayReading", "50");
		requestEntity.add("nightReading", "50");
		requestEntity.add("readDate", new Date().toString());
		ClientResponse response = webResource.accept(MediaType.TEXT_PLAIN)
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.post(ClientResponse.class, requestEntity);
		assertEquals(201, response.getStatus());
		int readingId = Integer.valueOf(response.getEntity(String.class));
		System.out.println(readingId);
		//delete test resource
		WebResource webResource1 = client.resource(URL + "/delete/"+readingId);
		ClientResponse response1 = webResource1.delete(ClientResponse.class);
		assertEquals(200, response1.getStatus());
	}

}
