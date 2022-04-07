package com.rjtech.document.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

/**
 * The persistent class for the proj_document_file database table.
 * 
 */
@Entity
@Table(name = "proj_document_file")
public class ProjDocFileEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PDFL_ID")
    private Long id;

    @Column(name = "PDFL_FILE_NAME")
    private String name;

    @Column(name = "PDFL_FILE_TYPE")
    private String fileType;

    @Column(name = "PDFL_FILE_SIZE")
    private String fileSize;

    @Column(name = "PDFL_VERSION")
    private String version;

    @Column(name = "PDFL_DOC_STATUS")
    private String fileStatus;

    @ManyToOne
    @JoinColumn(name = "PDFL_PDF_ID")
    private ProjDocFolderEntity folderId;

    @Column(name = "PDFL_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PDFL_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PDFL_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PDFL_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PDFL_UPDATED_ON")
    private Date updatedOn;

    @Column(name = "PDFL_FILE_CATEGORY")
    private String category;

    @Column(name = "PDFL_FILE_DESCRIPTION")
    private String description;

    @Column(name = "PDFL_FILE_UNIQUE_KEY")
    private String uniqueKey;
    
    @ManyToOne
    @JoinColumn(name = "PROJ_ID_FK")
    private ProjMstrEntity projectId;
    
    @Column(name = "FILE_UPLOAD_PATH")
    private String folderPath;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

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

    public void setFolderId(ProjDocFolderEntity folderId) {
        this.folderId = folderId;
    }

    public void setCreatedBy(UserMstrEntity createdBy) {
        this.createdBy = createdBy;
    }

    public void setUpdatedBy(UserMstrEntity updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
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

    public ProjDocFolderEntity getFolderId() {
        return folderId;
    }

    public UserMstrEntity getCreatedBy() {
        return createdBy;
    }

    public UserMstrEntity getUpdatedBy() {
        return updatedBy;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }
    
    public ProjMstrEntity getProjectId() {
        return projectId;
    }

    public void setProjectId(ProjMstrEntity projectId) {
        this.projectId = projectId;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }
    
    public String toString() {
    	return "id"+id+" folderpath:"+folderPath;
    }
}