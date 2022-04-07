package com.rjtech.register.emp.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;

public class ProjEmpRegLabelKeyTOResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -5615159806235549635L;
    private List<LabelKeyTO> projEmpReglabelKeyTOs = new ArrayList<LabelKeyTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<LabelKeyTO> getProjEmpReglabelKeyTOs() {
        return projEmpReglabelKeyTOs;
    }

    public void setProjEmpReglabelKeyTOs(List<LabelKeyTO> projEmpReglabelKeyTOs) {
        this.projEmpReglabelKeyTOs = projEmpReglabelKeyTOs;
    }

}
