package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.TangibleClassTO;
import com.rjtech.common.dto.ClientTO;


public class TangibleClassSaveReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<TangibleClassTO> tangibleClassTOs = new ArrayList<TangibleClassTO>(
            5);

	public List<TangibleClassTO> getTangibleClassTOs() {
		return tangibleClassTOs;
	}

	public void setTangibleClassTOs(List<TangibleClassTO> tangibleClassTOs) {
		this.tangibleClassTOs = tangibleClassTOs;
	}

   

}
