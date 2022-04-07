package com.rjtech.procurement.resp;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.procurement.dto.PreContractTO;
import com.rjtech.procurement.dto.PreContractCostCodeTO;
import com.rjtech.procurement.dto.InvoiceMaterialTo;


public class InvoiceMaterialResp extends AppResp {

   
    private static final long serialVersionUID = 6526217036270683215L;

    private List<InvoiceMaterialTo> invoiceMaterialTos = null;
    private Integer totalValue;
 
    public List<InvoiceMaterialTo> getInvoiceMaterialTos() {
        return invoiceMaterialTos;
    }

    public void setInvoiceMaterialTos(List<InvoiceMaterialTo> invoiceMaterialTos) {
        this.invoiceMaterialTos = invoiceMaterialTos;
    }
    
    public Integer getTotalValue() {
    	return totalValue;
    }
    
    public void setTotalValue(Integer totalValue) {
    	this.totalValue = totalValue;
    }
}
