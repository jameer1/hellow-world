package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.common.dto.EPSProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class ProjSaveReq extends ClientTO {
	
    private static final long serialVersionUID = -6671175298681215590L;
    
    private List<EPSProjectTO> projs = new ArrayList<EPSProjectTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<EPSProjectTO> getProjs() {
        return this.projs;
    }

    public void setProjs(List<EPSProjectTO> projs) {
        this.projs = projs;
    }
}
