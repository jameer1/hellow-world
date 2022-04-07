
package com.rjtech.timemanagement.workdairy.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class WorkDairyTO extends ProjectTO {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String workDiaryCode;
    private String workDairyDate;
    private Long crewId;
    private String crewCode;
    private Long weatherId;
    private String weatherCode;
    private Long shiftId;
    private String shiftCode;
    private String apprStatus;
    private String contractType;
    private String contractNo;
    private Long purId;
    private Long reqUserId;
    private String reqComments;
    private String apprComments;
    private Long preContractId;
    private Long preContractCmpId;
    private Long internalApprUserId;
    private Long clientApprUserId;
    private boolean clientApproval;
    private boolean newRequired;
    private Double empMaxHrs;
    private Double plantMaxHrs;
    private String notificationMsg;
    private String notificationStatus;
    private String createdBy;
    private String internalApprBy;
    private String clientApprBy;
    private boolean timeFlag;

    private List<WorkDairyCostCodeTO> workDairyCostCodeTOs = new ArrayList<WorkDairyCostCodeTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public String getApprComments() {
        return apprComments;
    }

    public void setApprComments(String apprComments) {
        this.apprComments = apprComments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkDiaryCode() {
        return workDiaryCode;
    }

    public void setWorkDiaryCode(String workDiaryCode) {
        this.workDiaryCode = workDiaryCode;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public boolean isClientApproval() {
        return clientApproval;
    }

    public void setClientApproval(boolean clientApproval) {
        this.clientApproval = clientApproval;
    }

    public String getWorkDairyDate() {
        return workDairyDate;
    }

    public void setWorkDairyDate(String workDairyDate) {
        this.workDairyDate = workDairyDate;
    }

    public Long getCrewId() {
        return crewId;
    }

    public void setCrewId(Long crewId) {
        this.crewId = crewId;
    }

    public Long getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(Long weatherId) {
        this.weatherId = weatherId;
    }

    public Long getShiftId() {
        return shiftId;
    }

    public void setShiftId(Long shiftId) {
        this.shiftId = shiftId;
    }

    public Long getPurId() {
        return purId;
    }

    public void setPurId(Long purId) {
        this.purId = purId;
    }

    public Long getPreContractId() {
        return preContractId;
    }

    public void setPreContractId(Long preContractId) {
        this.preContractId = preContractId;
    }

    public Long getPreContractCmpId() {
        return preContractCmpId;
    }

    public void setPreContractCmpId(Long preContractCmpId) {
        this.preContractCmpId = preContractCmpId;
    }

    public Long getInternalApprUserId() {
        return internalApprUserId;
    }

    public void setInternalApprUserId(Long internalApprUserId) {
        this.internalApprUserId = internalApprUserId;
    }

    public Long getClientApprUserId() {
        return clientApprUserId;
    }

    public void setClientApprUserId(Long clientApprUserId) {
        this.clientApprUserId = clientApprUserId;
    }

    public List<WorkDairyCostCodeTO> getWorkDairyCostCodeTOs() {
        return workDairyCostCodeTOs;
    }

    public void setWorkDairyCostCodeTOs(List<WorkDairyCostCodeTO> workDairyCostCodeTOs) {
        this.workDairyCostCodeTOs = workDairyCostCodeTOs;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public boolean isNewRequired() {
        return newRequired;
    }

    public void setNewRequired(boolean newRequired) {
        this.newRequired = newRequired;
    }

    public Double getEmpMaxHrs() {
        return empMaxHrs;
    }

    public void setEmpMaxHrs(Double empMaxHrs) {
        this.empMaxHrs = empMaxHrs;
    }

    public Double getPlantMaxHrs() {
        return plantMaxHrs;
    }

    public void setPlantMaxHrs(Double plantMaxHrs) {
        this.plantMaxHrs = plantMaxHrs;
    }

    public Long getReqUserId() {
        return reqUserId;
    }

    public void setReqUserId(Long reqUserId) {
        this.reqUserId = reqUserId;
    }

    public String getReqComments() {
        return reqComments;
    }

    public void setReqComments(String reqComments) {
        this.reqComments = reqComments;
    }

    public String getCrewCode() {
        return crewCode;
    }

    public void setCrewCode(String crewCode) {
        this.crewCode = crewCode;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public String getShiftCode() {
        return shiftCode;
    }

    public void setShiftCode(String shiftCode) {
        this.shiftCode = shiftCode;
    }

    public String getNotificationMsg() {
        return notificationMsg;
    }

    public void setNotificationMsg(String notificationMsg) {
        this.notificationMsg = notificationMsg;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }
    
    public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getInternalApprBy() {
		return internalApprBy;
	}

	public void setInternalApprBy(String internalApprBy) {
		this.internalApprBy = internalApprBy;
	}
	
	public String getClientApprBy() {
		return clientApprBy;
	}

	public void setClientApprBy(String clientApprBy) {
		this.clientApprBy = clientApprBy;
	}
	
	public boolean isTimeFlag() {
        return timeFlag;
    }

    public void setTimeFlag(boolean timeFlag) {
        this.timeFlag = timeFlag;
    }
}