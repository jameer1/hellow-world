package com.rjtech.document.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.document.dto.ProjDocFileTO;

public class ProjDocFileResp extends AppResp {

    private static final long serialVersionUID = -3763356942184775462L;

    private List<ProjDocFileTO> projDocFileTOs = new ArrayList<ProjDocFileTO>();

    public List<ProjDocFileTO> getProjDocFileTOs() {
        return projDocFileTOs;
    }

    public void setProjDocFileTOs(List<ProjDocFileTO> projDocFileTOs) {
        this.projDocFileTOs = projDocFileTOs;
    }

}