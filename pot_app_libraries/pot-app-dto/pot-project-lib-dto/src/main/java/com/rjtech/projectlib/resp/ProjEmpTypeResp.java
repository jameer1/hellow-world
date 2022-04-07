package com.rjtech.projectlib.resp;

import java.util.ArrayList;
import java.util.List;
import com.rjtech.projectlib.dto.ProjEmpCatgTO;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;

public class ProjEmpTypeResp extends AppResp {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<ProjEmpCatgTO> projEmpCatgTOs = null;

    public ProjEmpTypeResp() {
        projEmpCatgTOs = new ArrayList<ProjEmpCatgTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjEmpCatgTO> getProjEmpCatgTOs() {
        return projEmpCatgTOs;
    }

}
