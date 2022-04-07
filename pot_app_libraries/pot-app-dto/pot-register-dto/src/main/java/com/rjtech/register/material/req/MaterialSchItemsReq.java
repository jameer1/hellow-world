package com.rjtech.register.material.req;

import java.io.Serializable;
import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;

public class MaterialSchItemsReq implements Serializable {

    private static final long serialVersionUID = -2838834075636461154L;

    public MaterialSchItemsReq() {

    }

    private LabelKeyTO reqProjLabelKeyTO = new LabelKeyTO();

    private List<Long> materialClassList;

    private String materialDockReqType = null;

    public LabelKeyTO getReqProjLabelKeyTO() {
        return reqProjLabelKeyTO;
    }

    public void setReqProjLabelKeyTO(LabelKeyTO reqProjLabelKeyTO) {
        this.reqProjLabelKeyTO = reqProjLabelKeyTO;
    }

    public List<Long> getMaterialClassList() {
        return materialClassList;
    }

    public void setMaterialClassList(List<Long> materialClassList) {
        this.materialClassList = materialClassList;
    }

    public String getMaterialDockReqType() {
        return materialDockReqType;
    }

    public void setMaterialDockReqType(String materialDockReqType) {
        this.materialDockReqType = materialDockReqType;
    }

}
