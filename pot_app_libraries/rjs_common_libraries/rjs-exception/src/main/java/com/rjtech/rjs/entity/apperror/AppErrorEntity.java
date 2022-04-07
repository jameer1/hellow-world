package com.rjtech.rjs.entity.apperror;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "APP_ERROR")
public class AppErrorEntity implements Serializable {

	private static final long serialVersionUID = 6802824147918504575L;

	public AppErrorEntity() {

	}

	@Id
	@Column(name = "ERR_CODE")
	private String code;

	@Column(name = "ERR_MSG")
	private String message;
	
	
	@Column(name = "ERR_TYPE")
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
