package com.rjtech.projectlib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;

public class ProjRestrictedMaterialClassResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 8270098975745736029L;

    public ProjRestrictedMaterialClassResp() {

    }

    private List<Long> restrictedProjMaterail = new ArrayList<Long>();

    public List<Long> getRestrictedProjMaterail() {
        return restrictedProjMaterail;
    }

    public void setRestrictedProjMaterail(List<Long> restrictedProjMaterail) {
        this.restrictedProjMaterail = restrictedProjMaterail;
    }

}
