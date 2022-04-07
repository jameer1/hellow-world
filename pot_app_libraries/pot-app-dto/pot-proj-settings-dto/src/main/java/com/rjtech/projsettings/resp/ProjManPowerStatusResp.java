package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjManPowerStatusTO;

public class ProjManPowerStatusResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 5453711541705887220L;

    private List<ProjManPowerStatusTO> projManPowerStatusTOs = null;

    public ProjManPowerStatusResp() {
        projManPowerStatusTOs = new ArrayList<ProjManPowerStatusTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjManPowerStatusTO> getProjManPowerStatusTOs() {
        return projManPowerStatusTOs;
    }

    public void setProjManPowerStatusTOs(List<ProjManPowerStatusTO> projManPowerStatusTOs) {
        this.projManPowerStatusTOs = projManPowerStatusTOs;
    }

}
