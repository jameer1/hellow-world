
package com.rjtech.document.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * The persistent class for the proj_document_folder_permission database table.
 * 
 */
@Entity
@Table(name = "proj_document_folder_permission")
public class ProjDocFolderPermissionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PDFP_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PDFP_PDF_ID")
    private ProjDocFolderEntity folder;

    @ManyToOne
    @JoinColumn(name = "PDFP_USR_ID")
    private UserMstrEntity userId;

    @Column(name = "PDFP_IS_READ")
    private boolean readAccess;

    @Column(name = "PDFP_IS_WRITE")
    private boolean writeAccess;

    @Column(name = "PDFP_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PDFP_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PDFP_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PDFP_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PDFP_UPDATED_ON")
    private Date updatedOn;
    
    @ManyToOne
    @JoinColumn(name = "PDFP_PROJ_ID_FK") 
    private ProjMstrEntity projectEntity;

    public ProjDocFolderPermissionEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjDocFolderEntity getFolder() {
        return folder;
    }

    public void setFolder(ProjDocFolderEntity folder) {
        this.folder = folder;
    }

    public boolean isReadAccess() {
        return readAccess;
    }

    public void setReadAccess(boolean readAccess) {
        this.readAccess = readAccess;
    }

    public boolean isWriteAccess() {
        return writeAccess;
    }

    public void setWriteAccess(boolean writeAccess) {
        this.writeAccess = writeAccess;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public UserMstrEntity getUserId() {
        return userId;
    }

    public void setUserId(UserMstrEntity userId) {
        this.userId = userId;
    }

    public UserMstrEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserMstrEntity createdBy) {
        this.createdBy = createdBy;
    }

    public UserMstrEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserMstrEntity updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ProjMstrEntity getProjectEntity() {
        return projectEntity;
    }

    public void setProjectEntity( ProjMstrEntity projectEntity ) {
        this.projectEntity = projectEntity;
    }
    
    public String toString() {
    	return "From ProjDocFolderPermissionEntity userId"+userId+" isReadAccess:"+readAccess+" writeAccess:"+writeAccess+" folder:"+folder.getId()+" user:"+userId.getUserId()+" proj:"+projectEntity.getProjectId();
    }
}