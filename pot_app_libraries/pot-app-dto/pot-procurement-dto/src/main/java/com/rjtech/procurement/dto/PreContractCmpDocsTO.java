package com.rjtech.procurement.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.document.dto.ProjDocFileTO;

public class PreContractCmpDocsTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 7924040939374842141L;

    private Long id;
    private Long preContractCmpId;
    private boolean fromVendor;
    private String code;
    private String name;
    private String date;
    private String mimeType;
    private String version;
    private String modeType;
    private String sender;
    private String receiver;
    private String fileSize;
    public Integer fileObjectIndex;
    private PreContractTO precontractTO = new PreContractTO();
    private ProjDocFileTO projDocFileTO;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPreContractCmpId() {
        return preContractCmpId;
    }

    public void setPreContractCmpId(Long preContractId) {
        this.preContractCmpId = preContractId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
    
    public boolean isFromVendor() {
        return fromVendor;
    }

    public void setFromVendor(boolean fromVendor) {
        this.fromVendor = fromVendor;
    }

    public PreContractTO getPrecontractTO() {
        return precontractTO;
    }

    public void setPrecontractTO(PreContractTO precontractTO) {
        this.precontractTO = precontractTO;
    }

    public Integer getFileObjectIndex() {
    	return fileObjectIndex;
    }
    
    public void setFileObjectIndex(Integer fileObjectIndex) {
    	this.fileObjectIndex = fileObjectIndex;
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

    public void setDescription( String description ) {
        this.description = description;
    }
}
