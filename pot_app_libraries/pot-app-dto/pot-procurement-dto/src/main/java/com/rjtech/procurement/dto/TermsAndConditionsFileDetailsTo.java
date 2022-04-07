package com.rjtech.procurement.dto;

import java.io.Serializable;

public class TermsAndConditionsFileDetailsTo implements Serializable {

    private static final long serialVersionUID = 1351119188757318068L;

    private Long id;

    private String name;

    private String code;

    private String category;

    private String version;

    private String fileStatus;

    private String fileSize;

    private String fileType;

    private Long projDocFileId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getProjDocFileId() {
        return projDocFileId;
    }

    public void setProjDocFileId(Long projDocFileId) {
        this.projDocFileId = projDocFileId;
    }

}
