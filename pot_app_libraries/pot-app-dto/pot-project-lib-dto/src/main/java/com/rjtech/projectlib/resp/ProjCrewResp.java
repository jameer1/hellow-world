package com.rjtech.projectlib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjCrewTO;

public class ProjCrewResp extends AppResp {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<ProjCrewTO> projCrewTOs = null;

    public ProjCrewResp() {
        projCrewTOs = new ArrayList<ProjCrewTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjCrewTO> getProjCrewTOs() {
        return projCrewTOs;
    }

    public void setProjCrewTOs(List<ProjCrewTO> projCrewTOs) {
        this.projCrewTOs = projCrewTOs;
    }

}
