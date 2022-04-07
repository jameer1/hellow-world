package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.EPSProjectTO;
import com.rjtech.common.dto.ProjectTO;

public class ProjSaveReq extends ProjectTO {
	
    private static final long serialVersionUID = -6671175298681215590L;
    private List<EPSProjectTO> projs = new ArrayList<EPSProjectTO>();

    public List<EPSProjectTO> getProjs() {
        return this.projs;
    }
   // this is comment from Dinesh. For testing
    public void setProjs(List<EPSProjectTO> projs) {
        this.projs = projs;
    }
}
