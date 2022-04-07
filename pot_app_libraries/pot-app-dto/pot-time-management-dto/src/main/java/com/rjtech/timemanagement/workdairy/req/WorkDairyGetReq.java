package com.rjtech.timemanagement.workdairy.req;

import java.util.ArrayList;
import java.util.List;
import com.rjtech.common.dto.ProjectTO;

public class WorkDairyGetReq extends ProjectTO {

    private static final long serialVersionUID = 3754852758992336728L;

    private Long workDairyId;
    private Long crewId;
    private String apprStatus;
    private String workDairyDate;
    private String fromWorkDairyDate;
    private String contractType;
    private List<Long> workDairyProgressIds = new ArrayList<Long>();
    private List<Long> workDairyManpowerIds = new ArrayList<Long>();
    private List<Long> workDairyPlantIds = new ArrayList<Long>();
    private String workDairyDeleteType;
    private List<Long> costIds = new ArrayList<Long>();

    public Long getWorkDairyId() {
        return workDairyId;
    }

    public void setWorkDairyId(Long workDairyId) {
        this.workDairyId = workDairyId;
    }

    public Long getCrewId() {
        return crewId;
    }

    public void setCrewId(Long crewId) {
        this.crewId = crewId;
    }

    public String getWorkDairyDate() {
        return workDairyDate;
    }

    public void setWorkDairyDate(String workDairyDate) {
        this.workDairyDate = workDairyDate;
    }

    public String getFromWorkDairyDate() {
        return fromWorkDairyDate;
    }

    public void setFromWorkDairyDate(String fromWorkDairyDate) {
        this.fromWorkDairyDate = fromWorkDairyDate;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }
    
    public List<Long> getWorkDairyProgressIds() {
        return workDairyProgressIds;
    }

    public void setWorkDairyProgressIds( List<Long> workDairyProgressIds ) {
        this.workDairyProgressIds = workDairyProgressIds;
    }
    
    public List<Long> getWorkDairyManpowerIds() {
        return workDairyManpowerIds;
    }

    public void setWorkDairyManpowerIds( List<Long> workDairyManpowerIds ) {
        this.workDairyManpowerIds = workDairyManpowerIds;
    }
    
    public List<Long> getWorkDairyPlantIds() {
        return workDairyPlantIds;
    }

    public void setWorkDairyPlantIds( List<Long> workDairyPlantIds ) {
        this.workDairyPlantIds = workDairyPlantIds;
    }
    
    public String getWorkDairyDeleteType() {
    	return workDairyDeleteType;
    }
    
    public void setWorkDairyDeleteType( String workDairyDeleteType ) {
    	this.workDairyDeleteType = workDairyDeleteType;
    }
    
    public List<Long> getCostIds() {
        return costIds;
    }

    public void setCostIds( List<Long> costIds ) {
        this.costIds = costIds;
    }

}
