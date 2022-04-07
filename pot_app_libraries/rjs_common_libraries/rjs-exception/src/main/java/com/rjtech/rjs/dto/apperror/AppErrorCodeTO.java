package com.rjtech.rjs.dto.apperror;

import java.io.Serializable;

public class AppErrorCodeTO implements Serializable {


	private static final long serialVersionUID = 6493780207526967618L;
	
	public AppErrorCodeTO(){
		
	}

	
	private String code;
	private String message;
	private String errorType;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	
	
}
