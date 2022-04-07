package com.rjtech.projectlib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjSOEActivityLogTO;

public class ProjSOEActivityLogResp extends AppResp {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<ProjSOEActivityLogTO> projSOEActivityLogTOs = new ArrayList<ProjSOEActivityLogTO>();
    
    public List<ProjSOEActivityLogTO> getProjSOEActivityLogTOs() {
        return projSOEActivityLogTOs;
    }

    public void setProjSOEActivityLogTOs( List<ProjSOEActivityLogTO> projSOEActivityLogTOs ) {
        this.projSOEActivityLogTOs = projSOEActivityLogTOs;
    }
}
