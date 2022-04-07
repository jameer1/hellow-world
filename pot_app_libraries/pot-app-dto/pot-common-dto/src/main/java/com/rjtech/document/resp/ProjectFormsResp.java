package com.rjtech.document.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.document.dto.FormsTO;

public class ProjectFormsResp extends AppResp {

    private static final long serialVersionUID = -3763356942184775462L;

    private List<FormsTO> formsTOs = new ArrayList<FormsTO>();

    public List<FormsTO> getFormsTOs() {  	
        return formsTOs;
    }

    public void setFormsTOs(List<FormsTO> formsTOs) {
        this.formsTOs = formsTOs;
    }

}