package com.rjtech.procurement.dto;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.document.dto.ProjDocFileTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PreContractDocsTO extends ProjectTO {

    private static final long serialVersionUID = -2259705151414587003L;
    private Long id;
    private Long preContractId;
    private Long preDocContentId;
    private String fileType;
    private String fileSize;
    private String version;
    private String date;
    private String modeType;
    private String description;
    private PreContractTO precontractTO = new PreContractTO();
    private List<PreContractDocContentTO> preContractDocContentTOs = new ArrayList<>();
    private ProjDocFileTO projDocFileTO;
    private Long projDocFileTOId;
    private String uniqueKey;
    private Integer fileObjectIndex;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
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

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public PreContractTO getPrecontractTO() {
        return precontractTO;
    }

    public void setPrecontractTO(PreContractTO precontractTO) {
        this.precontractTO = precontractTO;
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

    public ProjDocFileTO getProjDocFileTO() {
        return projDocFileTO;
    }

    public void setProjDocFileTO(ProjDocFileTO projDocFileTO) {
        this.projDocFileTO = projDocFileTO;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProjDocFileTOId() {
        return projDocFileTOId;
    }

    public void setProjDocFileTOId(Long projDocFileTOId) {
        this.projDocFileTOId = projDocFileTOId;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public Integer getFileObjectIndex() {
        return fileObjectIndex;
    }

    public void setFileObjectIndex(Integer fileObjectIndex) {
        this.fileObjectIndex = fileObjectIndex;
    }

}
