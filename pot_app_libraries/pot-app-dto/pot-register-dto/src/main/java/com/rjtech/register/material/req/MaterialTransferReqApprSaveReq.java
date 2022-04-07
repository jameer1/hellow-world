package com.rjtech.register.material.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.material.dto.MaterialTransferReqApprTO;

public class MaterialTransferReqApprSaveReq extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private List<MaterialTransferReqApprTO> materialTransferReqApprTOs = new ArrayList<MaterialTransferReqApprTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    private MaterialTransReq materialTransReq = new MaterialTransReq();

    public List<MaterialTransferReqApprTO> getMaterialTransferReqApprTOs() {
        return materialTransferReqApprTOs;
    }

    public void setMaterialTransferReqApprTOs(List<MaterialTransferReqApprTO> materialTransferReqApprTOs) {
        this.materialTransferReqApprTOs = materialTransferReqApprTOs;
    }

    public MaterialTransReq getMaterialTransReq() {
        return materialTransReq;
    }

    public void setMaterialTransReq(MaterialTransReq materialTransReq) {
        this.materialTransReq = materialTransReq;
    }

}
