package com.rjtech.common.service.impl.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.rjtech.common.service.rest.RestClientService;
import com.rjtech.common.service.rest.util.RestCommonUtil;
import com.rjtech.mw.app.exception.AppMWException;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.appuser.utils.ApplicationAuthConstants;

@Service(value = "restClientService")
public class RestClientServiceImpl implements RestClientService {

    private static final Logger logger = LoggerFactory.getLogger(RestClientServiceImpl.class);

    private static final String ERROR_MESSAGE_KEY = "message";

    private static final String ERROR_CODE_KEY = "code";

    private static final String ERROR_ERRORTYPE_KEY = "errorType";

    public <T> void verifyResponseEntity(ResponseEntity<T> responseEntity) {
        HttpStatus statusCode = responseEntity.getStatusCode();
        //logger.error("From exception HttpStatus statusCode :{} ", statusCode.value());
        if (statusCode.is2xxSuccessful() && responseEntity.getHeaders().containsKey(ERROR_MESSAGE_KEY)) {
            MultiValueMap<String, String> headermap = responseEntity.getHeaders();
            List<String> messageKeys = headermap.get(ERROR_MESSAGE_KEY);
            List<String> codeKeys = headermap.get(ERROR_CODE_KEY);
            List<String> errorTypeKeys = headermap.get(ERROR_ERRORTYPE_KEY);

            String msg = null;
            String code = null;
            String errorType = null;

            if (RestCommonUtil.isListHasData(messageKeys)) {
                msg = messageKeys.get(0);
            }

            if (RestCommonUtil.isListHasData(codeKeys)) {
                code = codeKeys.get(0);
            }

            if (RestCommonUtil.isListHasData(errorTypeKeys)) {
                errorType = errorTypeKeys.get(0);
            }

            //logger.error("From exception HttpStatus msg: {} - code: {} - errorType:{}", msg, code, errorType);
            if (RestCommonUtil.isNotBlankStr(msg) && RestCommonUtil.isNotBlankStr(code)) {
                throw new AppMWException(code, null, msg, errorType);
            }

        }

    }

    public HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(ApplicationAuthConstants.POTTOKEN, AppUserUtils.getToken());
        headers.add("Content-type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Accept-Encoding", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    public HttpHeaders getHeadersWithOutUserDetails() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }
}
