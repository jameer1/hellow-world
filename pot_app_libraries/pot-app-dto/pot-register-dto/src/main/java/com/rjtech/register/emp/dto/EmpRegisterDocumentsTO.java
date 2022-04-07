package com.rjtech.register.emp.dto;

import com.rjtech.common.dto.ProjectTO;

public class EmpRegisterDocumentsTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -862908917506844518L;
    private Long id;
    private String type;
    private String fileName;
    private String mimeType;
    private String filePath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
