package com.rjtech.procurement.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.math.MathContext;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.common.dto.WorkFlowStatusTO;

public class PreContractTO extends ProjectTO {

    private static final long serialVersionUID = -6748335769511761914L;

    private Long id;
    private String desc;
    private String reqCode;
    private String responeDate;
    private String closeDate;
    private String revisedCloseDate;
    private Boolean isLatest;
    private Integer approveStatus;
    private String contractStageStatus;
    private String purchaseOrderStatus;
    private Long reqUsrId;
    private Date currentDate = new Date();
    private boolean allowMultiplePurchaseOrders;
    private Integer poStatus;
    private Integer estimateTotalCost;
    private String resEmp;
    private String epsName;
    private String projName;
    private BigDecimal paymentDays;
    
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
    private List<ProcurementNormalTimeTO> procurementNormalTimeTOs = new ArrayList<>();

    // for RepeatPO
    private boolean isRepeatPOApproved;
    private boolean isRepeatPOInitiated;
    private boolean isRepeatPOCreated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getPoStatus() {
        return poStatus;
    }

    public void setPoStatus(Integer poStatus) {
        this.poStatus = poStatus;
    }
    
    public WorkFlowStatusTO getWorkFlowStatusTO() {
        return workFlowStatusTO;
    }

    public void setWorkFlowStatusTO(WorkFlowStatusTO workFlowStatusTO) {
        this.workFlowStatusTO = workFlowStatusTO;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public String getResEmp() {
        return resEmp;
    }

    public void setResEmp(String resEmp) {
        this.resEmp = resEmp;
    }
    
    public Long getReqUsrId() {
        return reqUsrId;
    }

    public void setReqUsrId(Long reqUsrId) {
        this.reqUsrId = reqUsrId;
    }

    public String getPreContractType() {
        return preContractType;
    }

    public void setPreContractType(String preContractType) {
        this.preContractType = preContractType;
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

    public String getReqCode() {
        return reqCode;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getResponeDate() {
        return responeDate;
    }

    public void setResponeDate(String responeDate) {
        this.responeDate = responeDate;
    }

    public List<PreContractReqApprTO> getPreContractReqApprTOs() {
        return preContractReqApprTOs;
    }

    public void setPreContractReqApprTOs(List<PreContractReqApprTO> preContractReqApprTOs) {
        this.preContractReqApprTOs = preContractReqApprTOs;
    }

    public PreContractReqApprTO getPreContractReqApprTO() {
        return preContractReqApprTO;
    }

    public void setPreContractReqApprTO(PreContractReqApprTO preContractReqApprTO) {
        this.preContractReqApprTO = preContractReqApprTO;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public String getRevisedCloseDate() {
        return revisedCloseDate;
    }

    public void setRevisedCloseDate(String revisedCloseDate) {
        this.revisedCloseDate = revisedCloseDate;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Integer getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getContractStageStatus() {
        return contractStageStatus;
    }

    public void setContractStageStatus(String contractStageStatus) {
        this.contractStageStatus = contractStageStatus;
    }

    public String getPurchaseOrderStatus() {
        return purchaseOrderStatus;
    }

    public void setPurchaseOrderStatus(String purchaseOrderStatus) {
        this.purchaseOrderStatus = purchaseOrderStatus;
    }

    public boolean isAllowMultiplePurchaseOrders() {
        return allowMultiplePurchaseOrders;
    }

    public void setAllowMultiplePurchaseOrders(boolean allowMultiplePurchaseOrders) {
        this.allowMultiplePurchaseOrders = allowMultiplePurchaseOrders;
    }

    public Boolean getIsLatest() {
        return isLatest;
    }

    public void setIsLatest(Boolean isLatest) {
        this.isLatest = isLatest;
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

    public Map<Long, Integer> getExternalWorkFlowMap() {
        return externalWorkFlowMap;
    }

    public void setExternalWorkFlowMap(Map<Long, Integer> externalWorkFlowMap) {
        this.externalWorkFlowMap = externalWorkFlowMap;
    }
    
    public BigDecimal getPaymentDays() {
        return paymentDays;
    }

    public void setPaymentDays(BigDecimal paymentDays) {
        this.paymentDays = paymentDays;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    public boolean isRepeatPOApproved() {
        return isRepeatPOApproved;
    }

    public void setRepeatPOApproved(boolean repeatPOApproved) {
        isRepeatPOApproved = repeatPOApproved;
    }

    public boolean isRepeatPOInitiated() {
        return isRepeatPOInitiated;
    }

    public void setRepeatPOInitiated(boolean repeatPOInitiated) {
        isRepeatPOInitiated = repeatPOInitiated;
    }

    public boolean isRepeatPOCreated() {
        return isRepeatPOCreated;
    }

    public void setRepeatPOCreated(boolean repeatPOCreated) {
        isRepeatPOCreated = repeatPOCreated;
    }

    public List<ProcurementNormalTimeTO> getProcurementNormalTimeTOs() {
        return procurementNormalTimeTOs;
    }

    public void setProcurementNormalTimeTOs(List<ProcurementNormalTimeTO> procurementNormalTimeTOs) {
        this.procurementNormalTimeTOs = procurementNormalTimeTOs;
    }
    
    public Integer getEstimateTotalCost() {
        return estimateTotalCost;
    }

    public void setEstimateTotalCost(Integer estimateTotalCost) {
        this.estimateTotalCost = estimateTotalCost;
    }
    
    public String getEpsName() {
        return epsName;
    }

    public void setEpsName(String epsName) {
        this.epsName = epsName;
    }
    
    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        PreContractTO other = (PreContractTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}