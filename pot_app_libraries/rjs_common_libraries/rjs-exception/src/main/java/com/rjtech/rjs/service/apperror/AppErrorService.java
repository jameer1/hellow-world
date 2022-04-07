package com.rjtech.rjs.service.apperror;

import java.util.Map;

import com.rjtech.rjs.dto.apperror.AppErrorCodeTO;



public interface AppErrorService {
	
	Map<String, AppErrorCodeTO> getAppErrorCodes();
	AppErrorCodeTO  getAppErrorCodeTObyKey(String code) ;	
	String  getAppErrorMessagebyKey(String code) ;
	AppErrorCodeTO  getAppErrorCodeWithInputsByKey(String code,String[] args);

}
