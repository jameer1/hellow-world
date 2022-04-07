package com.rjtech.rjs.entity.exception.log;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the SYSTEM_LOG database table.
 * 
 */
@Entity
@Table(name = "SYSTEM_LOG")
@NamedQuery(name = "SystemLogEntity.findAll", query = "SELECT e FROM SystemLogEntity  e")
public class SystemLogEntity implements Serializable {

	private static final long serialVersionUID = 1626235469604432055L;

	@Id
	@Column(name = "SYL_LOG_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long logId;

	@Column(name = "SYL_FILE_NAME")
	private String fileName;

	@Column(name = "SYL_ITEM_CODE")
	private String itemCode;

	@Column(name = "SYL_ERROR_CODE")
	private String errorCode;

	@Lob
	@Column(name = "SYL_EXCEPTION")
	private String exception;

	@Lob
	@Column(name = "SYL_INPUT")
	private String input;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SYL_CREATED_ON", updatable = false)
	private Date createdOn;

	@Column(name = "SYL_CREATED_BY", updatable = false)
	private Long createdBy;

	@Column(name = "SYL_LOG_TYPE")
	private String logType;

	@Column(name = "SYL_MODULE")
	private String module;

	@Column(name = "SYL_REMARKS")
	private String remark;

	@Column(name = "SYL_SOURCE")
	private String source;

	public SystemLogEntity() {
	}

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}