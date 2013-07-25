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

public class ElecAcctServiceTest {

	private final String URL = "https://utilityservice.cloudfoundry.com/service/elecaccts";

	@Test
	public void testGetAccountById() {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("zengx@tcd.ie", "123456"));
		WebResource webResource = client.resource(URL + "/1");
		ClientResponse response = webResource.accept(MediaType.APPLICATION_XML)
				.get(ClientResponse.class);
		System.out.println(response.getEntity(String.class));
		assertEquals(200, response.getStatus());
	}

	@Test
	public void testGetOtherAccount() {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("lester101@gmail.com", "123456"));
		WebResource webResource = client.resource(URL + "/1");
		ClientResponse response = webResource.accept(MediaType.APPLICATION_XML)
				.get(ClientResponse.class);
		System.out.println(response.getEntity(String.class));
		assertEquals(406, response.getStatus());
	}

	@Test
	public void testGetAccounts() {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("zengx@tcd.ie", "123456"));
		WebResource webResource = client.resource(URL + "/user/1");
		ClientResponse response = webResource.accept(MediaType.APPLICATION_XML)
				.get(ClientResponse.class);
		System.out.println(response.getEntity(String.class));
		assertEquals(200, response.getStatus());
	}

	@Test
	public void testAddAndDeleteAccount() {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("lester101@gmail.com", "123456"));
		WebResource webResource = client.resource(URL + "/add");
		MultivaluedMap<String, String> requestEntity = new MultivaluedMapImpl();
		requestEntity.add("userId", "37");
		requestEntity.add("companyName", "ESB");
		requestEntity.add("acctNumber", "ESB12344534");
		requestEntity.add("MPRN", "12345678911");
		ClientResponse response = webResource.accept(MediaType.TEXT_PLAIN)
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.post(ClientResponse.class, requestEntity);
		assertEquals(201, response.getStatus());
		int acctId = Integer.valueOf(response.getEntity(String.class));
		System.out.println(acctId);
		// delete test resource
		WebResource webResource1 = client.resource(URL + "/delete/"+acctId);
		ClientResponse response1 = webResource1.delete(ClientResponse.class);
		assertEquals(200, response1.getStatus());
	}

	@Test
	public void testUpdateAccount() {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("lester101@gmail.com", "123456"));
		WebResource webResource = client.resource(URL + "/update");
		MultivaluedMap<String, String> requestEntity = new MultivaluedMapImpl();
		requestEntity.add("acctId", "12");
		requestEntity.add("companyName", "ESB2");
		requestEntity.add("acctNumber", "ESB12030494");
		requestEntity.add("MPRN", "12345678901");
		ClientResponse response = webResource.type(
				MediaType.APPLICATION_FORM_URLENCODED_TYPE).put(
				ClientResponse.class, requestEntity);
		System.out.println(response.getEntity(String.class));
		assertEquals(200, response.getStatus());
	}

}
