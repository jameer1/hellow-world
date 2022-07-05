package com.rjtech.user.resp;

public class ChangePasswordResponse {

	private String message;
	
	private String encodedValue;
	
	public ChangePasswordResponse(){}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEncodedValue() {
		return encodedValue;
	}

	public void setEncodedValue(String encodedValue) {
		this.encodedValue = encodedValue;
	}

	public ChangePasswordResponse(String message, String encodedValue) {
		super();
		this.message = message;
		this.encodedValue = encodedValue;
	}

	@Override
	public String toString() {
		return "ChangePasswordResponse [message=" + message + ", encodedValue=" + encodedValue + "]";
	}
	
	
	
}
