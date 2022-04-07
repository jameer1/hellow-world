package com.rjtech.projsettings.req;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.projsettings.dto.ProjGeneralMstrTO;

public class ProjGeneralSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -7889377083586677720L;
    private ProjGeneralMstrTO projGeneralMstrTO = new ProjGeneralMstrTO();

    public ProjGeneralMstrTO getProjGeneralMstrTO() {
        return projGeneralMstrTO;
    }

    public void setProjGeneralMstrTO(ProjGeneralMstrTO projGeneralMstrTO) {
        this.projGeneralMstrTO = projGeneralMstrTO;
    }

}
