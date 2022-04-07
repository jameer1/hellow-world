package com.rjtech.document.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.document.dto.ProjDocCatagoryTO;

public class ProjDocCatagoryResp extends AppResp {

    private static final long serialVersionUID = -3763356942184775462L;

    private List<ProjDocCatagoryTO> projDocCatagoryTOs = new ArrayList<ProjDocCatagoryTO>();

    public List<ProjDocCatagoryTO> getProjDocCatagoryTOs() {
        return projDocCatagoryTOs;
    }

    public void setProjDocCatagoryTOs(List<ProjDocCatagoryTO> projDocCatagoryTOs) {
        this.projDocCatagoryTOs = projDocCatagoryTOs;
    }

}