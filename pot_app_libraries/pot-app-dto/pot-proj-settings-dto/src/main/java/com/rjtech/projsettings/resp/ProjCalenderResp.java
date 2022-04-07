package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.CalenderTO;

public class ProjCalenderResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -3380976308311085678L;

    private List<CalenderTO> projCalenderTOs = null;

    public List<CalenderTO> getProjCalenderTOs() {
        return projCalenderTOs;
    }

    public void setProjCalenderTOs(List<CalenderTO> projCalenderTOs) {
        this.projCalenderTOs = projCalenderTOs;
    }

    public ProjCalenderResp() {
        projCalenderTOs = new ArrayList<CalenderTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

}
