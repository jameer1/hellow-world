package com.rjtech.common.service.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public interface RestClientService {

    <T> void verifyResponseEntity(ResponseEntity<T> responseEntity);

    HttpHeaders getHttpHeaders();

    HttpHeaders getHeadersWithOutUserDetails();
}
