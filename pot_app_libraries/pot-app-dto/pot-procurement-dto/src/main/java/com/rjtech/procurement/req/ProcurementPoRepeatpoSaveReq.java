package com.rjtech.procurement.req;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.procurement.dto.PreContractTO;
import com.rjtech.procurement.dto.PrecontractSowDtlTO;
import com.rjtech.procurement.dto.ProcurementPoRepeatpoTO;

public class ProcurementPoRepeatpoSaveReq extends ProjectTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 475966004706594280L;
	
	 private ProcurementPoRepeatpoTO procurementPoRepeatpoTO = new ProcurementPoRepeatpoTO();
	 private Long purchaseOrderId;
	 private Long preContractCmpId;
	 private PreContractTO preContractTO = new PreContractTO();
	private Long approvedBy;
	
	public ProcurementPoRepeatpoTO getProcurementPoRepeatpoTO() {
		return procurementPoRepeatpoTO;
	}

	public void setProcurementPoRepeatpoTO(ProcurementPoRepeatpoTO procurementPoRepeatpoTO) {
		this.procurementPoRepeatpoTO = procurementPoRepeatpoTO;
	}

	public Long getPreContractCmpId() {
		return preContractCmpId;
	}

	public void setPreContractCmpId(Long preContractCmpId) {
		this.preContractCmpId = preContractCmpId;
	}

	public Long getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(Long purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public PreContractTO getPreContractTO() {
		return preContractTO;
	}

	public void setPreContractTO(PreContractTO preContractTO) {
		this.preContractTO = preContractTO;
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		result.append( this.getClass().getName() );
		result.append( " Object {" );
		result.append(newLine);

		//determine fields declared in this class only (no fields of superclass)
		Field[] fields = this.getClass().getDeclaredFields();

		//print field names paired with their values
		for ( Field field : fields  ) {
			result.append("  ");
			try {
				result.append( field.getName() );
				result.append(": ");
				//requires access to private field:
				result.append( field.get(this) );
			} catch ( IllegalAccessException ex ) {
				System.out.println(ex);
			}
			result.append(newLine);
		}
		result.append("}");

		return result.toString();
	}

	public Long getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(Long approvedBy) {
		this.approvedBy = approvedBy;
	}
}
