package com.rjtech.projectlib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjMaterialClassTO;

public class ProjMaterialClassResp extends AppResp {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<ProjMaterialClassTO> projMaterialClassTOs = null;

    public ProjMaterialClassResp() {
        projMaterialClassTOs = new ArrayList<ProjMaterialClassTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjMaterialClassTO> getProjMaterialClassTOs() {
        return projMaterialClassTOs;
    }

}
