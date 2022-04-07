package com.rjtech.projectlib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjSOETrackTO;

public class ProjSOETrackLogResp extends AppResp {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<ProjSOETrackTO> projSOETrackTOs = null;
    
    public ProjSOETrackLogResp() {
    	projSOETrackTOs = new ArrayList<ProjSOETrackTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }
    
    public List<ProjSOETrackTO> getProjSOETrackTOs() {
        return projSOETrackTOs;
    }
   
}
