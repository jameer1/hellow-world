package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjWorkDairyMstrTO;

public class ProjWorkDairySaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 5270095009496332835L;
    private List<ProjWorkDairyMstrTO> projWorkDairyMstrTOs = new ArrayList<ProjWorkDairyMstrTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjWorkDairyMstrTO> getProjWorkDairyMstrTOs() {
        return projWorkDairyMstrTOs;
    }

    public void setProjWorkDairyMstrTOs(List<ProjWorkDairyMstrTO> projWorkDairyMstrTOs) {
        this.projWorkDairyMstrTOs = projWorkDairyMstrTOs;
    }

}
