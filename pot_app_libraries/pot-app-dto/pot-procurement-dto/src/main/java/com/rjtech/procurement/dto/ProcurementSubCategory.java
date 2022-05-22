/**
 * 
 */
package com.rjtech.procurement.dto;

/**
 * @author sowmya
 *
 */
public class ProcurementSubCategory  {
	
	  private static final long serialVersionUID = -2082680906920319979L;
	  
	  private Long ProcCatId;
	  
	  private String procurSubCatCode;
	  
	  private String ProcurementType;
	  
	  private String status;
	  
	  private String ProcurSubCatName;

	public Long getProcCatId() {
		return ProcCatId;
	}

	public void setProcCatId(Long procCatId) {
		ProcCatId = procCatId;
	}

	public String getProcurSubCatCode() {
		return procurSubCatCode;
	}

	public void setProcurSubCatCode(String procurSubCatCode) {
		this.procurSubCatCode = procurSubCatCode;
	}

	public String getProcurementType() {
		return ProcurementType;
	}

	public void setProcurementType(String procurementType) {
		ProcurementType = procurementType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProcurSubCatName() {
		return ProcurSubCatName;
	}

	public void setProcurSubCatName(String procurSubCatName) {
		ProcurSubCatName = procurSubCatName;
	}

	  
	  
	  
	   

}
