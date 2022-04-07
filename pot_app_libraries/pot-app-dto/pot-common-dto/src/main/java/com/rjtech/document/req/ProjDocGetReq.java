package com.rjtech.document.req;

import com.rjtech.common.dto.ProjectTO;

public class ProjDocGetReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String permission;
    private String folderCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
    
    public String getFolderCategory() {
        return folderCategory;
    }

    public void setFolderCategory(String folderCategory) {
        this.folderCategory = folderCategory;
    }
    
}