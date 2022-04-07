package com.rjtech.projectlib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjSORActivityLogTO;

public class ProjSORActivityLogResp extends AppResp {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<ProjSORActivityLogTO> projSORActivityLogTOs = new ArrayList<ProjSORActivityLogTO>();
    
    public List<ProjSORActivityLogTO> getProjSORActivityLogTOs() {
        return projSORActivityLogTOs;
    }

    public void setProjSORActivityLogTOs( List<ProjSORActivityLogTO> projSORActivityLogTOs ) {
        this.projSORActivityLogTOs = projSORActivityLogTOs;
    }
}
