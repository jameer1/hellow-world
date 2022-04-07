
package com.rjtech.document.dto;

import java.io.Serializable;

import com.rjtech.common.dto.LabelKeyTO;

public class ProjDocFolderPermissionTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long folderId;
    private Long userId;
    private LabelKeyTO labelKeyTO = new LabelKeyTO();
    private boolean readAccess;
    private boolean writeAccess;
    private Integer status;
    private Long projId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public LabelKeyTO getLabelKeyTO() {
        return labelKeyTO;
    }

    public void setLabelKeyTO(LabelKeyTO labelKeyTO) {
        this.labelKeyTO = labelKeyTO;
    }
    
    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String toString() {
    	return "From ProjDocFolderPermissionTO projId:"+projId+" readaccess:"+readAccess;
    }
}