package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjSOWItemTO;
import com.rjtech.projsettings.dto.ProjProgressTO;

public class ProjProgressOnLoadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -5473028267061191893L;
    private List<ProjSOWItemTO> projSOWItemTOs = null;
    private ProjProgressTO projProgressTO = null;
    private List<ProjProgressTO> projProgressTOs = null;

    public ProjProgressOnLoadResp() {
        projSOWItemTOs = new ArrayList<ProjSOWItemTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projProgressTO = new ProjProgressTO();
        projProgressTOs = new ArrayList<ProjProgressTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjSOWItemTO> getProjSOWItemTOs() {
        return projSOWItemTOs;
    }

    public void setProjSOWItemTOs(List<ProjSOWItemTO> projSOWItemTOs) {
        this.projSOWItemTOs = projSOWItemTOs;
    }

    public ProjProgressTO getProjProgressTO() {
        return projProgressTO;
    }

    public void setProjProgressTO(ProjProgressTO projProgressTO) {
        this.projProgressTO = projProgressTO;
    }

    public List<ProjProgressTO> getProjProgressTOs() {
        return projProgressTOs;
    }

    public void setProjProgressTOs(List<ProjProgressTO> projProgressTOs) {
        this.projProgressTOs = projProgressTOs;
    }

}
