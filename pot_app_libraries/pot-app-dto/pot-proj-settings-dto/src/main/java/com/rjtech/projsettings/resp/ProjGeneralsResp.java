package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.projsettings.dto.ProjGeneralMstrTO;

public class ProjGeneralsResp extends AppResp {

    private static final long serialVersionUID = -4919936129162230851L;

    private ProjGeneralMstrTO projGeneralMstrTO = new ProjGeneralMstrTO();

    private List<ProjGeneralMstrTO> projGeneralMstrTOs = new ArrayList<>();

    public ProjGeneralsResp() {
    }

    public ProjGeneralMstrTO getProjGeneralMstrTO() {
        return projGeneralMstrTO;
    }

    public void setProjGeneralMstrTO(ProjGeneralMstrTO projGeneralMstrTO) {
        this.projGeneralMstrTO = projGeneralMstrTO;
    }

    public List<ProjGeneralMstrTO> getProjGeneralMstrTOs() {
        return projGeneralMstrTOs;
    }

    public void setProjGeneralMstrTOs(List<ProjGeneralMstrTO> projGeneralMstrTOs) {
        this.projGeneralMstrTOs = projGeneralMstrTOs;
    }

}
