package com.rjtech.projsettings.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.rjtech.common.dto.ProjectTO;

public class ProjectTangibleTO extends ProjectTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date date;
	private Long tangibleItemId;
	private String tangibleCode;
	private String tangibleName;
	private Long costCodeId;
	private String costCode;
	private String costCodeName;
	private Long sowId;
	private String sowCode;
	private String sowName;
	private String uom;
	private BigDecimal estimatedQuantity;
	private BigDecimal estimatedHours;
	private BigDecimal actualQuantity;
	private BigDecimal actualHours;
	
	public Long getTangibleItemId() {
		return tangibleItemId;
	}
	public void setTangibleItemId(Long tangibleItemId) {
		this.tangibleItemId = tangibleItemId;
	}
	public String getTangibleCode() {
		return tangibleCode;
	}
	public void setTangibleCode(String tangibleCode) {
		this.tangibleCode = tangibleCode;
	}
	public String getTangibleName() {
		return tangibleName;
	}
	public void setTangibleName(String tangibleName) {
		this.tangibleName = tangibleName;
	}
	public Long getCostCodeId() {
		return costCodeId;
	}
	public void setCostCodeId(Long costCodeId) {
		this.costCodeId = costCodeId;
	}
	public String getCostCode() {
		return costCode;
	}
	public void setCostCode(String costCode) {
		this.costCode = costCode;
	}
	public String getCostCodeName() {
		return costCodeName;
	}
	public void setCostCodeName(String costCodeName) {
		this.costCodeName = costCodeName;
	}
	public Long getSowId() {
		return sowId;
	}
	public void setSowId(Long sowId) {
		this.sowId = sowId;
	}
	public String getSowCode() {
		return sowCode;
	}
	public void setSowCode(String sowCode) {
		this.sowCode = sowCode;
	}
	public String getSowName() {
		return sowName;
	}
	public void setSowName(String sowName) {
		this.sowName = sowName;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public BigDecimal getEstimatedQuantity() {
		return estimatedQuantity;
	}
	public void setEstimatedQuantity(BigDecimal estimatedQuantity) {
		this.estimatedQuantity = estimatedQuantity;
	}
	public BigDecimal getEstimatedHours() {
		return estimatedHours;
	}
	public void setEstimatedHours(BigDecimal estimatedHours) {
		this.estimatedHours = estimatedHours;
	}
	public BigDecimal getActualQuantity() {
		return actualQuantity;
	}
	public void setActualQuantity(BigDecimal actualQuantity) {
		this.actualQuantity = actualQuantity;
	}
	public BigDecimal getActualHours() {
		return actualHours;
	}
	public void setActualHours(BigDecimal actualHours) {
		this.actualHours = actualHours;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
