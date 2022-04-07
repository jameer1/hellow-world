package com.rjtech.procurement.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class PreContractDistributionDocTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 5559342478970854101L;

    private Long id;
    private Long preContractId;
    private Long preDocContentId;
    private String code;
    private String name;
    private String mimeType;
    private Integer version;
    private String date;
    private String modeType;
    private String fileSize;
    private String description;
    private PreContractTO precontractTO = new PreContractTO();
    private List<PreContractDocContentTO> preContractDocContentTOs = new ArrayList<PreContractDocContentTO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPreContractId() {
        return preContractId;
    }

    public void setPreContractId(Long preContractId) {
        this.preContractId = preContractId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(String modeType) {
        this.modeType = modeType;
    }

    public Long getPreDocContentId() {
        return preDocContentId;
    }

    public void setPreDocContentId(Long preDocContentId) {
        this.preDocContentId = preDocContentId;
    }

    public List<PreContractDocContentTO> getPreContractDocContentTOs() {
        return preContractDocContentTOs;
    }

    public void setPreContractDocContentTOs(List<PreContractDocContentTO> preContractDocContentTOs) {
        this.preContractDocContentTOs = preContractDocContentTOs;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public PreContractTO getPrecontractTO() {
        return precontractTO;
    }

    public void setPrecontractTO(PreContractTO precontractTO) {
        this.precontractTO = precontractTO;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
