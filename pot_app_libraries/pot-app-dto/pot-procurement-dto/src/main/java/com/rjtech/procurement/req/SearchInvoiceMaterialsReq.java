/**
 * 
 */
package com.rjtech.procurement.req;

import com.rjtech.common.dto.ProjectTO;

/**
 * @author sowmya
 *
 */
public class SearchInvoiceMaterialsReq extends ProjectTO{

	private static final long serialVersionUID = 1L;
	
	private Long companyId;
	private String invoceFromDate;
	private String invoceToDate;
	private Long purchaseId;
	private Long precontractId;
	private String pcName;
	private String pocSubCatName;
	private String payableCat;
	private String unitsOfMeasure;
	
	
	
	public String getPcName() {
		return pcName;
	}
	public void setPcName(String pcName) {
		this.pcName = pcName;
	}
	public String getPocSubCatName() {
		return pocSubCatName;
	}
	public void setPocSubCatName(String pocSubCatName) {
		this.pocSubCatName = pocSubCatName;
	}
	public String getPayableCat() {
		return payableCat;
	}
	public void setPayableCat(String payableCat) {
		this.payableCat = payableCat;
	}
	public String getUnitsOfMeasure() {
		return unitsOfMeasure;
	}
	public void setUnitsOfMeasure(String unitsOfMeasure) {
		this.unitsOfMeasure = unitsOfMeasure;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getInvoceFromDate() {
		return invoceFromDate;
	}
	public void setInvoceFromDate(String invoceFromDate) {
		this.invoceFromDate = invoceFromDate;
	}
	public String getInvoceToDate() {
		return invoceToDate;
	}
	public void setInvoceToDate(String invoceToDate) {
		this.invoceToDate = invoceToDate;
	}
	public Long getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(Long purchaseId) {
		this.purchaseId = purchaseId;
	}
	public Long getPrecontractId() {
		return precontractId;
	}
	public void setPrecontractId(Long precontractId) {
		this.precontractId = precontractId;
	}
	
	

}
