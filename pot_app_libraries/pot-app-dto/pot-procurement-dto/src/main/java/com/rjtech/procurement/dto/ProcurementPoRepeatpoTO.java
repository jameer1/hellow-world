package com.rjtech.procurement.dto;



import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.common.dto.WorkFlowStatusTO;

public class ProcurementPoRepeatpoTO extends ProjectTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5408444683801231000L;
	
	
	 	private Long id;
	   
		private Long parentpoid;
	    private Long parentscheduleid;
	    private String startDate;
	    private String finishDate;
	    private String deliveryplace;
	    private Integer quantity;
	    private boolean bid;
	    
	    
	    private WorkFlowStatusTO workFlowStatusTO = new WorkFlowStatusTO();
	    private Map<Long, Integer> externalWorkFlowMap = new HashMap<>();
	    private String currencyCode;
	    private String projCode;
	    private String preContractType;
	    private PreContractReqApprTO preContractReqApprTO = new PreContractReqApprTO();
	    private List<PreContractReqApprTO> preContractReqApprTOs = new ArrayList<>();
	    private List<PreContractDocsTO> preContractDocsTOs = new ArrayList<>();
	    private List<PreContractCmpTO> preContractCmpTOs = new ArrayList<>();
	    private List<PreContractEmpDtlTO> preContractEmpDtlTOs = new ArrayList<>();
	    private List<PreContractPlantDtlTO> preContractPlantDtlTOs = new ArrayList<>();
	    private List<PreContractMaterialDtlTO> preContractMaterialDtlTOs = new ArrayList<>();
	    private List<PreContractServiceDtlTO> preContractServiceDtlTOs = new ArrayList<>();
	    private List<PrecontractSowDtlTO> precontractSowDtlTOs = new ArrayList<>();
	   // private List<ProcurementPoRepeatpoTO> procurementRepeatPODtlTOs = new ArrayList<>();

		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		
		 public Long getParentpoid() {
				return parentpoid;
			}
			public void setParentpoid(Long parentpoid) {
				this.parentpoid = parentpoid;
			}
		public Long getParentscheduleid() {
			return parentscheduleid;
		}
		public void setParentscheduleid(Long parentscheduleid) {
			this.parentscheduleid = parentscheduleid;
		}
		public String getStartDate() {
			return startDate;
		}
		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}
		public String getFinishDate() {
			return finishDate;
		}
		public void setFinishDate(String finishDate) {
			this.finishDate = finishDate;
		}
		public String getDeliveryplace() {
			return deliveryplace;
		}
		public void setDeliveryplace(String deliveryplace) {
			this.deliveryplace = deliveryplace;
		}
		public Integer getQuantity() {
			return quantity;
		}
		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}
		public boolean isBid() {
			return bid;
		}
		public void setBid(boolean bid) {
			this.bid = bid;
		}
		public WorkFlowStatusTO getWorkFlowStatusTO() {
			return workFlowStatusTO;
		}
		public void setWorkFlowStatusTO(WorkFlowStatusTO workFlowStatusTO) {
			this.workFlowStatusTO = workFlowStatusTO;
		}
		public Map<Long, Integer> getExternalWorkFlowMap() {
			return externalWorkFlowMap;
		}
		public void setExternalWorkFlowMap(Map<Long, Integer> externalWorkFlowMap) {
			this.externalWorkFlowMap = externalWorkFlowMap;
		}
		public String getCurrencyCode() {
			return currencyCode;
		}
		public void setCurrencyCode(String currencyCode) {
			this.currencyCode = currencyCode;
		}
		public String getProjCode() {
			return projCode;
		}
		public void setProjCode(String projCode) {
			this.projCode = projCode;
		}
		public String getPreContractType() {
			return preContractType;
		}
		public void setPreContractType(String preContractType) {
			this.preContractType = preContractType;
		}
		public PreContractReqApprTO getPreContractReqApprTO() {
			return preContractReqApprTO;
		}
		public void setPreContractReqApprTO(PreContractReqApprTO preContractReqApprTO) {
			this.preContractReqApprTO = preContractReqApprTO;
		}
		public List<PreContractReqApprTO> getPreContractReqApprTOs() {
			return preContractReqApprTOs;
		}
		public void setPreContractReqApprTOs(List<PreContractReqApprTO> preContractReqApprTOs) {
			this.preContractReqApprTOs = preContractReqApprTOs;
		}
		public List<PreContractDocsTO> getPreContractDocsTOs() {
			return preContractDocsTOs;
		}
		public void setPreContractDocsTOs(List<PreContractDocsTO> preContractDocsTOs) {
			this.preContractDocsTOs = preContractDocsTOs;
		}
		public List<PreContractCmpTO> getPreContractCmpTOs() {
			return preContractCmpTOs;
		}
		public void setPreContractCmpTOs(List<PreContractCmpTO> preContractCmpTOs) {
			this.preContractCmpTOs = preContractCmpTOs;
		}
		public List<PreContractEmpDtlTO> getPreContractEmpDtlTOs() {
			return preContractEmpDtlTOs;
		}
		public void setPreContractEmpDtlTOs(List<PreContractEmpDtlTO> preContractEmpDtlTOs) {
			this.preContractEmpDtlTOs = preContractEmpDtlTOs;
		}
		public List<PreContractPlantDtlTO> getPreContractPlantDtlTOs() {
			return preContractPlantDtlTOs;
		}
		public void setPreContractPlantDtlTOs(List<PreContractPlantDtlTO> preContractPlantDtlTOs) {
			this.preContractPlantDtlTOs = preContractPlantDtlTOs;
		}
		public List<PreContractMaterialDtlTO> getPreContractMaterialDtlTOs() {
			return preContractMaterialDtlTOs;
		}
		public void setPreContractMaterialDtlTOs(List<PreContractMaterialDtlTO> preContractMaterialDtlTOs) {
			this.preContractMaterialDtlTOs = preContractMaterialDtlTOs;
		}
		public List<PreContractServiceDtlTO> getPreContractServiceDtlTOs() {
			return preContractServiceDtlTOs;
		}
		public void setPreContractServiceDtlTOs(List<PreContractServiceDtlTO> preContractServiceDtlTOs) {
			this.preContractServiceDtlTOs = preContractServiceDtlTOs;
		}
		public List<PrecontractSowDtlTO> getPrecontractSowDtlTOs() {
			return precontractSowDtlTOs;
		}
		public void setPrecontractSowDtlTOs(List<PrecontractSowDtlTO> precontractSowDtlTOs) {
			this.precontractSowDtlTOs = precontractSowDtlTOs;
		}
		/*public List<ProcurementPoRepeatpoTO> getProcurementRepeatPODtlTOs() {
			return procurementRepeatPODtlTOs;
		}
		public void setProcurementRepeatPODtlTOs(List<ProcurementPoRepeatpoTO> procurementRepeatPODtlTOs) {
			this.procurementRepeatPODtlTOs = procurementRepeatPODtlTOs;
		}*/

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
		
	   

}
