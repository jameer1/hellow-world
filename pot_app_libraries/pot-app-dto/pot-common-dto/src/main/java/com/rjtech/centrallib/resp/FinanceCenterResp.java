package com.rjtech.centrallib.resp;

import java.util.ArrayList;

import java.util.List;

import com.rjtech.common.dto.FinanceCenterRecordTo;
import com.rjtech.common.resp.AppResp;
import com.rjtech.centrallib.dto.RegisterOnLoadTO;

public class FinanceCenterResp extends AppResp {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private List<FinanceCenterRecordTo> financeCenterRecordTos = new ArrayList<FinanceCenterRecordTo>();
    private RegisterOnLoadTO registerOnLoadTO = new RegisterOnLoadTO();
    public RegisterOnLoadTO getRegisterOnLoadTO() {
		return registerOnLoadTO;
	}
	public void setRegisterOnLoadTO(RegisterOnLoadTO registerOnLoadTO) {
		this.registerOnLoadTO = registerOnLoadTO;
	}
	public List<FinanceCenterRecordTo> getFinanceCenterRecordTos() {
        return financeCenterRecordTos;
    }
    public void setFinanceCenterRecordTos(List<FinanceCenterRecordTo> financeCenterRecordTos) {
        this.financeCenterRecordTos = financeCenterRecordTos;
    }
    
    

}
