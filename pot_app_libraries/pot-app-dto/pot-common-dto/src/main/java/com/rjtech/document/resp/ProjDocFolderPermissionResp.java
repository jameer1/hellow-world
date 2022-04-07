package com.rjtech.document.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;

import com.rjtech.document.dto.ProjDocFolderPermissionTO;

public class ProjDocFolderPermissionResp extends AppResp {

    private static final long serialVersionUID = -3763356942184775462L;

    private List<ProjDocFolderPermissionTO> projDocFolderPermissionTOs = new ArrayList<ProjDocFolderPermissionTO>();
    private Map<Long, ProjDocFolderPermissionTO> projUserClassMap = new HashMap<Long, ProjDocFolderPermissionTO>(
            5);
    private List<LabelKeyTO> labelKeyTOsmap = new ArrayList<LabelKeyTO>();

    public Map<Long, ProjDocFolderPermissionTO> getProjUserClassMap() {
        return projUserClassMap;
    }

    public void setProjUserClassMap(Map<Long, ProjDocFolderPermissionTO> projUserClassMap) {
        this.projUserClassMap = projUserClassMap;
    }

    public List<ProjDocFolderPermissionTO> getProjDocFolderPermissionTOs() {
        return projDocFolderPermissionTOs;
    }

    public void setProjDocFolderPermissionTOs(List<ProjDocFolderPermissionTO> projDocFolderPermissionTOs) {
        this.projDocFolderPermissionTOs = projDocFolderPermissionTOs;
    }

    public List<LabelKeyTO> getLabelKeyTOsmap() {
        return labelKeyTOsmap;
    }

    public void setLabelKeyTOsmap(List<LabelKeyTO> labelKeyTOsmap) {
        this.labelKeyTOsmap = labelKeyTOsmap;
    }

}