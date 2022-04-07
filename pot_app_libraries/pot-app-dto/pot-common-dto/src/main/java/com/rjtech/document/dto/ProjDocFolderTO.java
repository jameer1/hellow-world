
package com.rjtech.document.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;

public class ProjDocFolderTO  extends ClientTO {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Long projId;
    private Long parentId;
    private String parentName;   
    private String createdBy;
    private List<ProjDocFolderTO> childProjDocFolderTOs = new ArrayList<ProjDocFolderTO>();
    private String folderType;
    private Boolean isReadAccess;
    private Boolean isWriteAccess;

    public ProjDocFolderTO() {
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

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    
    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<ProjDocFolderTO> getChildProjDocFolderTOs() {
        return childProjDocFolderTOs;
    }

    public void setChildProjDocFolderTOs(List<ProjDocFolderTO> childProjDocFolderTOs) {
        this.childProjDocFolderTOs = childProjDocFolderTOs;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getFolderType() {
        return folderType;
    }

    public void setFolderType(String folderType) {
        this.folderType = folderType;
    }
    
    public Boolean getIsReadAccess() {
        return isReadAccess;
    }

    public void setIsReadAccess(Boolean isReadAccess) {
        this.isReadAccess = isReadAccess;
    }
    
    public Boolean getIsWriteAccess() {
        return isWriteAccess;
    }

    public void setIsWriteAccess( Boolean isWriteAccess ) {
        this.isWriteAccess = isWriteAccess;
    }
}