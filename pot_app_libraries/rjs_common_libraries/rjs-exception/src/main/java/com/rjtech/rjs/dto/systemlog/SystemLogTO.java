package com.rjtech.rjs.dto.systemlog;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

public class SystemLogTO implements Serializable {

	private static final long serialVersionUID = 2607553278663415126L;

	private Long sylLogId;

	private String sylCodeFile;

	private String sylCodeItem;

	private String sylErrorCode;

	private Throwable sylException;

	private Object[] sylInput;

	private Timestamp sylLastUpdateDate;

	private Long sylLastUpdatedBy;

	private String sylLogType;

	private String sylModule;

	private Object[] sylOutput;

	private String sylRemark;

	private String sylSource;
	
	private String defaultErrMsg;

	public SystemLogTO() {
	}

	public Long getSylLogId() {
		return sylLogId;
	}

	public void setSylLogId(Long sylLogId) {
		this.sylLogId = sylLogId;
	}

	public String getSylCodeFile() {
		return sylCodeFile;
	}

	public void setSylCodeFile(String sylCodeFile) {
		this.sylCodeFile = sylCodeFile;
	}

	public String getSylCodeItem() {
		return sylCodeItem;
	}

	public void setSylCodeItem(String sylCodeItem) {
		this.sylCodeItem = sylCodeItem;
	}

	public String getSylErrorCode() {
		return sylErrorCode;
	}

	public void setSylErrorCode(String sylErrorCode) {
		this.sylErrorCode = sylErrorCode;
	}

	public Throwable getSylException() {
		return sylException;
	}

	public void setSylException(Throwable sylException) {
		this.sylException = sylException;
	}

	public Timestamp getSylLastUpdateDate() {
		return sylLastUpdateDate;
	}

	public void setSylLastUpdateDate(Timestamp sylLastUpdateDate) {
		this.sylLastUpdateDate = sylLastUpdateDate;
	}

	public Long getSylLastUpdatedBy() {
		return sylLastUpdatedBy;
	}

	public void setSylLastUpdatedBy(Long sylLastUpdatedBy) {
		this.sylLastUpdatedBy = sylLastUpdatedBy;
	}

	public String getSylLogType() {
		return sylLogType;
	}

	public void setSylLogType(String sylLogType) {
		this.sylLogType = sylLogType;
	}

	public String getSylModule() {
		return sylModule;
	}

	public void setSylModule(String sylModule) {
		this.sylModule = sylModule;
	}


	public String getSylRemark() {
		return sylRemark;
	}

	public void setSylRemark(String sylRemark) {
		this.sylRemark = sylRemark;
	}

	public String getSylSource() {
		return sylSource;
	}

	public void setSylSource(String sylSource) {
		this.sylSource = sylSource;
	}

	public Object[] getSylInput() {
		return sylInput;
	}

	public void setSylInput(Object[] sylInput) {
		 if (null != sylInput) {
	            this.sylInput = Arrays.copyOf(sylInput,sylInput.length);
	        } else {
	            this.sylInput = null;
	        }
	}

	public Object[] getSylOutput() {
		return sylOutput;
	}

	public void setSylOutput(Object[] sylOutput) {
		this.sylOutput = sylOutput;
	}

	public String getDefaultErrMsg() {
		return defaultErrMsg;
	}

	public void setDefaultErrMsg(String defaultErrMsg) {
		this.defaultErrMsg = defaultErrMsg;
	}
	
	

}
