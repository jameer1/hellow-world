
package com.rjtech.document.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

/**
 * The persistent class for the proj_document_folder database table.
 * 
 */
@Entity
@Table(name = "proj_document_folder")
public class ProjDocFolderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PDF_ID")
    private Long id;

    @Column(name = "PDF_NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "PDF_EPM_ID")
    private ProjMstrEntity projId;

    @ManyToOne
    @JoinColumn(name = "PDF_PARENT_ID")
    private ProjDocFolderEntity projDocFolderEntity;

    @OneToMany(mappedBy = "projDocFolderEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProjDocFolderEntity> childEntities = new ArrayList<>();

    @Column(name = "PDF_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PDF_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PDF_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PDF_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PDF_UPDATED_ON")
    private Date updatedOn;
    
    @Column(name = "UPLOAD_PATH")
    private String uploadFolder;

    @OneToMany(mappedBy = "folder", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ProjDocFolderPermissionEntity> folderPermissions = new ArrayList<>();
    
    @Column(name = "FOLDER_TYPE")
    private String folderType;

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

    public ProjDocFolderEntity getProjDocFolderEntity() {
        return projDocFolderEntity;
    }

    public void setProjDocFolderEntity(ProjDocFolderEntity projDocFolderEntity) {
        this.projDocFolderEntity = projDocFolderEntity;
    }

    public List<ProjDocFolderEntity> getChildEntities() {
        return childEntities;
    }

    public void setChildEntities(List<ProjDocFolderEntity> childEntities) {
        this.childEntities = childEntities;
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

    public List<ProjDocFolderPermissionEntity> getFolderPermissions() {
        return folderPermissions;
    }

    public void setFolderPermissions(List<ProjDocFolderPermissionEntity> folderPermissions) {
        this.folderPermissions = folderPermissions;
    }

    public ProjMstrEntity getProjId() {
        return projId;
    }

    public void setProjId(ProjMstrEntity projId) {
        this.projId = projId;
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
    
    public String getUploadFolder() {
        return uploadFolder;
    }

    public void setUploadFolder( String uploadFolder ) {
        this.uploadFolder = uploadFolder;
    }
    
    public String getFolderType() {
        return folderType;
    }

    public void setFolderType( String folderType ) {
        this.folderType = folderType;
    }
    
    public String toString()
    {
    	return "from ProjDocFolderEntity projId:"+projId+" name:"+name+" upload path:"+uploadFolder+" id:"+id+" foldertpe:"+folderType;
    }
}