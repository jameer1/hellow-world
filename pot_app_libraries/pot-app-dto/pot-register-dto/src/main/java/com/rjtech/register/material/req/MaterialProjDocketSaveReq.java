package com.rjtech.register.material.req;

import java.io.Serializable;

import com.rjtech.register.material.dto.MaterialProjDocketTO;

public class MaterialProjDocketSaveReq implements Serializable {

    private static final long serialVersionUID = -1390510080496694128L;

    private MaterialProjDocketTO materialProjDocketTO = new MaterialProjDocketTO();

    public MaterialProjDocketTO getMaterialProjDocketTO() {
        return materialProjDocketTO;
    }

}
