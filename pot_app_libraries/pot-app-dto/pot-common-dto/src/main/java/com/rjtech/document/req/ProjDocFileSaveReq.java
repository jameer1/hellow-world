package com.rjtech.document.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.document.dto.ProjDocFileTO;

public class ProjDocFileSaveReq extends ProjectTO {

    private static final long serialVersionUID = -3763356942184775462L;

    private List<ProjDocFileTO> projDocFileTOs = new ArrayList<ProjDocFileTO>();
    private Long folderId;
    private Long createdBy;
    private Long updatedBy;
    private String folderCategory;

    public List<ProjDocFileTO> getProjDocFileTOs() {
        return projDocFileTOs;
    }

    public void setProjDocFileTOs(List<ProjDocFileTO> projDocFileTOs) {
        this.projDocFileTOs = projDocFileTOs;
    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    
    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    public String getFolderCategory() {
        return folderCategory;
    }

    public void setFolderCategory(String folderCategory) {
        this.folderCategory = folderCategory;
    }
    
    public String toString() {
    	return "folderCategory:"+folderCategory;
    }
}