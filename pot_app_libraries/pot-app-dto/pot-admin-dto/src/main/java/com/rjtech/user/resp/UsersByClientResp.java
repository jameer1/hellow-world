package com.rjtech.user.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;

public class UsersByClientResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -6671175298681215590L;

    private List<LabelKeyTO> labelKeyTOs = new ArrayList<LabelKeyTO>();

    public UsersByClientResp() {
    }

    public List<LabelKeyTO> getLabelKeyTOs() {
        return labelKeyTOs;
    }

    public void setLabelKeyTOs(List<LabelKeyTO> labelKeyTOs) {
        this.labelKeyTOs = labelKeyTOs;
    }

}
