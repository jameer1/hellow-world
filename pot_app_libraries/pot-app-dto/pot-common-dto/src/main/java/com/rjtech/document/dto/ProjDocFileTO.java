package com.rjtech.document.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.web.multipart.MultipartFile;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjDocFileTO implements Serializable {

    private static final long serialVersionUID = -8427668023657929545L;
    private Long id;
    private String code;
    private String name;
    private String fileContentId;
    private String fileType;
    private String fileSize;
    private String version;
    private String fileStatus;
    private Long folderId;
    private Long parentId;
    private Long projId;
    private Date updatedOn;
    private String updatedBy;
    private Integer status;
    private byte[] fileContent;
    private String createdBy;
    private String category;
    private String description;
    private String uniqueKey;
    private MultipartFile multipartFile;
    private Integer fileObjectIndex;
    private String folderPath;
    private String fromSource;
    private Long userId;
    private Date createdOn;
    private Long empDocumentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
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

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getFileContentId() {
        return fileContentId;
    }

    public void setFileContentId(String fileContentId) {
        this.fileContentId = fileContentId;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
    public Integer getFileObjectIndex() {
        return fileObjectIndex;
    }

    public void setFileObjectIndex(Integer fileObjectIndex) {
        this.fileObjectIndex = fileObjectIndex;
    }
    
    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }
        
    public String getFromSource() {
        return fromSource;
    }

    public void setFromSource(String fromSource) {
        this.fromSource = fromSource;
    }
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
    
    public Long getEmpDocumentId() {
    	return empDocumentId;
    }
    
    public void setEmpDocumentId( Long empDocumentId ) {
    	this.empDocumentId = empDocumentId;
    }
    
    public String toString() {
    	return "name:"+name+" code:"+code+" description:"+description+" fileType:"+fileType+" filesize:"+fileSize+"folderPath:"+folderPath+" projId:"+projId;
    }
}