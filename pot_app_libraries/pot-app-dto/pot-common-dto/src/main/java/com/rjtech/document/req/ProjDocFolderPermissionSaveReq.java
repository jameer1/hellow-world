package com.rjtech.document.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.document.dto.ProjDocFolderPermissionTO;

public class ProjDocFolderPermissionSaveReq extends ProjectTO {

    private static final long serialVersionUID = -3763356942184775462L;

    private List<ProjDocFolderPermissionTO> projDocFolderPermissionTOs = new ArrayList<ProjDocFolderPermissionTO>();
    private Long folderId;

    public List<ProjDocFolderPermissionTO> getProjDocFolderPermissionTOs() {
        return projDocFolderPermissionTOs;
    }

    public void setProjDocFolderPermissionTOs(List<ProjDocFolderPermissionTO> projDocFolderPermissionTOs) {
        this.projDocFolderPermissionTOs = projDocFolderPermissionTOs;
    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

}