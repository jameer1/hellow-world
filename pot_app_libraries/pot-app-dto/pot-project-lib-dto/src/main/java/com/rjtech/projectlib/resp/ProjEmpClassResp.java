package com.rjtech.projectlib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjEmpClassTO;

public class ProjEmpClassResp extends AppResp {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<ProjEmpClassTO> projEmpClassTOs = null;

    public ProjEmpClassResp() {
        projEmpClassTOs = new ArrayList<ProjEmpClassTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjEmpClassTO> getProjEmpClassTOs() {
        return projEmpClassTOs;
    }

}
