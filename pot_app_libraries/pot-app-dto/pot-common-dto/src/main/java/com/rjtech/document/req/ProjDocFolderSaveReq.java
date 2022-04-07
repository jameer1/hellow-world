package com.rjtech.document.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.document.dto.ProjDocFolderTO;

public class ProjDocFolderSaveReq extends ProjectTO {

    private static final long serialVersionUID = -3763356942184775462L;

    private List<ProjDocFolderTO> projDocFolderTOs = new ArrayList<ProjDocFolderTO>();

    public List<ProjDocFolderTO> getProjDocFolderTOs() {
        return projDocFolderTOs;
    }

    public void setProjDocFolderTOs(List<ProjDocFolderTO> projDocFolderTOs) {
        this.projDocFolderTOs = projDocFolderTOs;
    }

}