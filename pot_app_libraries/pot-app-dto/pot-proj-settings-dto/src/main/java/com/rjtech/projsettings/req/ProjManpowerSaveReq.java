package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjManpowerTO;

public class ProjManpowerSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 8028797844866057660L;
    private List<ProjManpowerTO> projManpowerTOs = new ArrayList<ProjManpowerTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjManpowerTO> getProjManpowerTOs() {
        return projManpowerTOs;
    }

    public void setProjManpowerTOs(List<ProjManpowerTO> projManpowerTOs) {
        this.projManpowerTOs = projManpowerTOs;
    }

}
