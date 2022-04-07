package com.rjtech.pot.mw.restconfig.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.service.rest.RestClientService;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.appuser.utils.ApplicationAuthConstants;

public abstract class RestConfigServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(RestConfigServiceImpl.class);

    @Autowired
    protected RestTemplate restTemplate;

    @Autowired
    protected RestClientService restClientService;

    @Autowired
    protected String mwcentralLibUrl;

    @Autowired
    protected String mwprojectLibUrl;

    @Autowired
    protected String mwadminUrl;

    @Autowired
    protected String mwprocurementUrl;

    @Autowired
    protected String mwprojsettingsUrl;

    @Autowired
    protected String timeManagementUrl;

    @Autowired
    protected String mwRegisterUrl;

    @Autowired
    protected String mwfinanceUrl;

    @Autowired
    protected String mwnotificationUrl;

    @Autowired
    protected String mwReportsUrl;

    protected String getAdminExchangeUrl(String endPointURI) {    	
        return CommonUtil.getUrl(mwadminUrl, endPointURI);
    }

    protected String getRegisterExchangeUrl(String endPointURI) {
    	//System.out.println("From getAdminExchangeUrl function");
    	//System.out.println(CommonUtil.getUrl(mwRegisterUrl, endPointURI));
        return CommonUtil.getUrl(mwRegisterUrl, endPointURI);
    }

    protected String getCentralLibExchangeUrl(String endPointURI) {
        return CommonUtil.getUrl(mwcentralLibUrl, endPointURI);
    }

    protected String getProjectLibExchangeUrl(String endPointURI) {
        return CommonUtil.getUrl(mwprojectLibUrl, endPointURI);
    }

    protected String getProcurementExchangeUrl(String endPointURI) {
        return CommonUtil.getUrl(mwprocurementUrl, endPointURI);
    }
    
    protected String getProjectSettingExchangeUrl(String endPointURI) {
        return CommonUtil.getUrl(mwprojsettingsUrl, endPointURI);
    }

    protected String getTimeManagementExchangeUrl( String endPointURI ) {    	
    	//System.out.println("From getTimeManagementExchangeUrl function");
    	//System.out.println(CommonUtil.getUrl(timeManagementUrl, endPointURI));
    	return CommonUtil.getUrl(timeManagementUrl, endPointURI);
    }
    
    protected ResponseEntity<String> getProcuremntPOSTRestTemplate(String inputJson, String endPointURI) {
        String url = mwprocurementUrl + endPointURI;
        log.info("getProcuremntPOSTRestTemplate  {}", url);
        ResponseEntity<String> strResponse = constructPOSTRestTemplate(url, inputJson);
        return strResponse;
    }

    protected ResponseEntity<String> getProjectSettingsPOSTRestTemplate(String inputJson, String endPointURI) {
        String url = mwprojsettingsUrl + endPointURI;
        ResponseEntity<String> strResponse = constructPOSTRestTemplate(url, inputJson);
        return strResponse;
    }

    protected ResponseEntity<String> getTimeManagementPOSTRestTemplate(String inputJson, String endPointURI) {
        String url = timeManagementUrl + endPointURI;
        ResponseEntity<String> strResponse = constructPOSTRestTemplate(url, inputJson);
        return strResponse;
    }

    protected ResponseEntity<String> getFinancePOSTRestTemplate(String inputJson, String endPointURI) {
        String url = mwfinanceUrl + endPointURI;
        ResponseEntity<String> strResponse = constructPOSTRestTemplate(url, inputJson);
        return strResponse;
    }

    protected ResponseEntity<String> getNotificationPOSTRestTemplate(String inputJson, String endPointURI) {
        String url = mwnotificationUrl + endPointURI;
        ResponseEntity<String> strResponse = constructPOSTRestTemplate(url, inputJson);
        return strResponse;
    }

    protected ResponseEntity<String> constructPOSTRestTemplate(String url, String inputJson) {
        ResponseEntity<String> strResponse = null;
        HttpHeaders headers = restClientService.getHttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<String>(inputJson, headers);
        strResponse = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        restClientService.verifyResponseEntity(strResponse);
        return strResponse;
    }

    protected ResponseEntity<String> constructPOSTRestTemplateWithMultipart(String url, MultipartFile file,
            String inputJson) {
        ResponseEntity<String> strResponse = null;
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        if (file == null) {
            map.add("file", null);
        } else {

            try {
                final String fileName = file.getOriginalFilename();
                ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
                    @Override
                    public String getFilename() {
                        return fileName;
                    }
                };
                map.add("file", resource);
            } catch (IOException e) {
                log.error("Could read file {}", e);
            }
        }
        map.add("clientRegReq", inputJson);
        HttpHeaders headers = restClientService.getHttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
                map, headers);
        strResponse = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        restClientService.verifyResponseEntity(strResponse);

        return strResponse;
    }
    
    protected ResponseEntity<String> constructPOSTRestTemplateWithMultipleMultipart(String url, MultipartFile file,
            String inputJson) {
        ResponseEntity<String> strResponse = null;
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        if (file == null) {
            map.add("file", null);
        } else {

            try {
                final String fileName = file.getOriginalFilename();
                ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
                    @Override
                    public String getFilename() {
                        return fileName;
                    }
                };
                map.add("file", resource);
            } catch (IOException e) {
                log.error("Could read file {}", e);
            }
        }
        map.add("clientRegReq", inputJson);
        HttpHeaders headers = restClientService.getHttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
                map, headers);
        strResponse = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        restClientService.verifyResponseEntity(strResponse);

        return strResponse;
    }

    //added mulfileupload
    protected ResponseEntity<String> constructPOSTRestTemplateWithMultipartFile(String endPoint, MultipartFile files,
            String inputJson, String parameterName) {
        ResponseEntity<String> strResponse = null;
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        if (files == null) {
            map.add("files", null);
        } else {

            try {
                final String fileName = files.getOriginalFilename();
                ByteArrayResource resource = new ByteArrayResource(files.getBytes()) {
                    @Override
                    public String getFilename() {
                        return fileName;
                    }
                };
                map.add("files", resource);
            } catch (IOException e) {
                log.error("Could read file {}", e);
            }
        }
        map.add(parameterName, inputJson);
        HttpHeaders headers = restClientService.getHttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
                map, headers);
        strResponse = restTemplate.exchange(endPoint, HttpMethod.POST, requestEntity, String.class);
        restClientService.verifyResponseEntity(strResponse);

        return strResponse;
    }

    protected ResponseEntity<String> constructPOSTRestTemplateWithMultipartFiles(String endPoint, MultipartFile[] files,
            String inputJson, String parameterName) {
        ResponseEntity<String> strResponse = null;
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        if (files == null) {
            map.add("files", null);
        } else {

            try {
                for (MultipartFile file : files) {
                    final String fileName = file.getOriginalFilename();
                    System.out.println(fileName);
                    ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
                        @Override
                        public String getFilename() {
                            return fileName;
                        }
                    };
                    map.add("files", resource);
                }
            } catch (IOException e) {
                log.error("Could read file {}", e);
            }
        }
        map.add(parameterName, inputJson);
        HttpHeaders headers = restClientService.getHttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
                map, headers);
        strResponse = restTemplate.exchange(endPoint, HttpMethod.POST, requestEntity, String.class);
        restClientService.verifyResponseEntity(strResponse);

        return strResponse;
    }

    protected ResponseEntity<String> constructPOSTRestTemplateWithMultipleMultiPartFiles(String endPoint,
            MultipartFile planFile, MultipartFile actualFile, String fromJson) {
        ResponseEntity<String> strResponse = null;
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        if (planFile == null) {
            map.add("planFile", null);
        } else {
            try {
            final String fileName = planFile.getOriginalFilename();
            ByteArrayResource resource = new ByteArrayResource(planFile.getBytes()) {
                @Override
                public String getFilename() {
                    return fileName;
                }
            };
            map.add("planFile", resource);
        } catch (IOException e) {
            log.error("Could read file {}", e);
        }
    }
         if (actualFile == null) {
            map.add("actualFile", null);
        }
         else
         {
             try {
                 final String fileName = actualFile.getOriginalFilename();
                 ByteArrayResource resource = new ByteArrayResource(actualFile.getBytes()) {
                     @Override
                     public String getFilename() {
                         return fileName;
                     }
                 };
                 map.add("actualFile", resource);
             } catch (IOException e) {
                 log.error("Could read file {}", e);
             }
         }
        map.add("clientRegReq", fromJson);
        HttpHeaders headers = restClientService.getHttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
                map, headers);
        strResponse = restTemplate.exchange(endPoint, HttpMethod.POST, requestEntity, String.class);
        restClientService.verifyResponseEntity(strResponse);

        return strResponse;
    }

    protected ResponseEntity<String> getRegistersPOSTRestTemplate(String inputJson, String endPointURI) {
        String url = mwRegisterUrl + endPointURI;
        ResponseEntity<String> strResponse = constructPOSTRestTemplate(url, inputJson);
        return strResponse;
    }

    protected ResponseEntity<String> getReportsPOSTRestTemplate(String inputJson, String endPointURI) {
        String url = mwReportsUrl + endPointURI;
        ResponseEntity<String> strResponse = constructPOSTRestTemplate(url, inputJson);
        return strResponse;
    }

    protected <T> ResponseEntity<T> constructGETRestTemplate(String url, Class<T> responseClazzType) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(ApplicationAuthConstants.POTTOKEN, AppUserUtils.getToken());
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseClazzType);
    }
    
    protected <T> ResponseEntity<T> constructPOSTRestTemplate(String url, String inputJson,
            Class<T> responseClazzType) {
        ResponseEntity<T> strResponse = null;
        HttpHeaders headers = restClientService.getHttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<String>(inputJson, headers);
        strResponse = restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseClazzType);
        T body = strResponse.getBody();
        System.out.println("under constructPOSTRestTemplate function");
        System.out.println("Http response status: " + strResponse.getStatusCodeValue());
        System.out.println("Http response body: " + AppUtils.toJson(body));
        restClientService.verifyResponseEntity(strResponse);
        System.out.println("Returnng response to service");
        return strResponse;
    }

}