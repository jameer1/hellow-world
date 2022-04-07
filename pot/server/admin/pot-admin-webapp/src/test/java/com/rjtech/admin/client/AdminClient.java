package com.rjtech.admin.client;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.rjtech.admin.constans.AdminURLConstants;

public class AdminClient {

	public static void main(String[] args) {
		// testGetModules();
		// testSaveObject();
		testDeleteModule();
	}

	public static void testDeleteModule() {
		final String uri = "http://localhost:8080/" + AdminURLConstants.ROLE_PARH_URL + AdminURLConstants.DELETE_MODULE;

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		Long value = 8l;
		String result = restTemplate.getForObject(uri, String.class, value);
		System.out.println("Deleted --" + result);
	}

}