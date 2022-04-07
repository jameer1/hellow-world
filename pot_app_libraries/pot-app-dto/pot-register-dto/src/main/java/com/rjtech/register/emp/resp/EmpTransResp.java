package com.rjtech.register.emp.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpTransferReqApprTO;

public class EmpTransResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -5432262693070261426L;

    private List<EmpTransferReqApprTO> empReqTransTOs = new ArrayList<EmpTransferReqApprTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private EmpReqTransOnLoadResp empReqTransOnLoadResp = new EmpReqTransOnLoadResp();
    private Map<Long, LabelKeyTO> transferEmpMap = new HashMap<Long, LabelKeyTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<EmpTransferReqApprTO> getEmpReqTransTOs() {
        return empReqTransTOs;
    }

    public void setEmpReqTransTOs(List<EmpTransferReqApprTO> empReqTransTOs) {
        this.empReqTransTOs = empReqTransTOs;
    }

    public EmpReqTransOnLoadResp getEmpReqTransOnLoadResp() {
        return empReqTransOnLoadResp;
    }

    public void setEmpReqTransOnLoadResp(EmpReqTransOnLoadResp empReqTransOnLoadResp) {
        this.empReqTransOnLoadResp = empReqTransOnLoadResp;
    }

    public Map<Long, LabelKeyTO> getTransferEmpMap() {
        return transferEmpMap;
    }

    public void setTransferEmpMap(Map<Long, LabelKeyTO> transferEmpMap) {
        this.transferEmpMap = transferEmpMap;
    }

}
