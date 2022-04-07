package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.MaterialClassTO;
import com.rjtech.common.dto.ClientTO;


public class MaterialClassSaveReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<MaterialClassTO> materialClassTOs = new ArrayList<MaterialClassTO>(
            5);

    public List<MaterialClassTO> getMaterialClassTOs() {
        return materialClassTOs;
    }

    public void setMaterialClassTOs(List<MaterialClassTO> materialClassTOs) {
        this.materialClassTOs = materialClassTOs;
    }

}
