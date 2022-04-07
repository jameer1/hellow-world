package com.rjtech.centrallib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.TangibleClassTO;
import com.rjtech.common.resp.AppResp;

public class TangibleClassResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<TangibleClassTO> tangibleClassTOs = new ArrayList<TangibleClassTO>();

    public List<TangibleClassTO> getTangibleClassTOs() {
        return tangibleClassTOs;
    }

    public void setTangibleClassTOs(List<TangibleClassTO> tangibleClassTOs) {
        this.tangibleClassTOs = tangibleClassTOs;
    }

}
