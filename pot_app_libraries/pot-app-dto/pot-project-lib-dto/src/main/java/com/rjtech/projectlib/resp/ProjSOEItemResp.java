package com.rjtech.projectlib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjSOEItemTO;

public class ProjSOEItemResp extends AppResp {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<ProjSOEItemTO> projSOEItemTOs = null;

    public ProjSOEItemResp() {
        projSOEItemTOs = new ArrayList<ProjSOEItemTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjSOEItemTO> getProjSOEItemTOs() {
        return projSOEItemTOs;
    }

}
