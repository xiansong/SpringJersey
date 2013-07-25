package com.pwz.rest.test;

import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public final class UserDataServiceTest {

	private static final String URL = "https://utilityservice.cloudfoundry.com/service/users";

	@Test
	public void testGetUserbyXML() {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("zengx@tcd.ie", "123456"));
		WebResource webResource = client.resource(URL + "/zengx@tcd.ie");
		ClientResponse response = webResource.accept(MediaType.APPLICATION_XML)
				.get(ClientResponse.class);
		System.out.println(response.getEntity(String.class));
		assertEquals(200, response.getStatus());
	}

	@Test
	public void testGetUserbyJSON() {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("zengx@tcd.ie", "123456"));
		WebResource webResource = client.resource(URL + "/zengx@tcd.ie");
		ClientResponse response = webResource
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		System.out.println(response.getEntity(String.class));
		assertEquals(200, response.getStatus());
	}

	@Test
	public void testGetOtherUser() {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("zengx@tcd.ie", "123456"));
		WebResource webResource = client.resource(URL + "/newuser1");
		ClientResponse response = webResource
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		System.out.println(response.getEntity(String.class));
		assertEquals(406, response.getStatus());
	}

	@Test
	public void testGetUsersByCustomer() {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("lester101@gmail.com", "123456"));
		WebResource webResource = client.resource(URL + "/list");
		ClientResponse response = webResource
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		System.out.println(response.getStatus());
		assertEquals(403, response.getStatus());
	}
	
	@Test
	public void testGetUsersByAdmin(){
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("zengx@tcd.ie", "123456"));
		WebResource webResource = client.resource(URL + "/list");
		ClientResponse response = webResource
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		System.out.println(response.getEntity(String.class));
		assertEquals(200, response.getStatus());
	}

	@Test
	public void testRegisterOk() {
		Client client = Client.create();
		WebResource webResource = client.resource(URL + "/add");
		MultivaluedMap<String, String> requestEntity = new MultivaluedMapImpl();
		requestEntity.add("username", "newuser2");
		requestEntity.add("password", "12345");
		ClientResponse response = webResource.accept(MediaType.TEXT_PLAIN)
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.post(ClientResponse.class, requestEntity);
		assertEquals(201, response.getStatus());
		int userId = Integer.valueOf(response.getEntity(String.class));
		System.out.println(userId);
		// delete newuser2
		client.addFilter(new HTTPBasicAuthFilter("newuser2", "12345"));
		WebResource webResource1 = client.resource(URL + "/delete/" + userId);
		ClientResponse response1 = webResource1.delete(ClientResponse.class);
		System.out.println(response1.getEntity(String.class));
		assertEquals(200, response1.getStatus());
	}

	@Test
	public void testUpdate() {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("zengx@tcd.ie", "123456"));
		WebResource webResource = client.resource(URL + "/update");
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("userId", "1");
		params.add("password", "123456");
		ClientResponse response = webResource.type(
				MediaType.APPLICATION_FORM_URLENCODED_TYPE).put(
				ClientResponse.class, params);
		System.out.println(response.getEntityTag()
				+ response.getEntity(String.class));
		assertEquals(304, response.getStatus());
	}

}
