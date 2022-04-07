package com.rjtech.procurement.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.procurement.dto.PreContractDocsTO;

public class PrecontractDocSaveReq extends ProcurementGetReq {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<PreContractDocsTO> preContractDocsTOs = new ArrayList<PreContractDocsTO>();
    private Long clientId;
    private String category;
    private Long projId;

    public List<PreContractDocsTO> getPreContractDocsTOs() {
        return preContractDocsTOs;
    }

    public void setPreContractDocsTOs(List<PreContractDocsTO> preContractDocsTOs) {
        this.preContractDocsTOs = preContractDocsTOs;
    }
    
    public Long getClientId()
    {
    	return clientId;
    }
    
    public void setClientId( Long clientId )
    {
    	this.clientId = clientId;
    }
    
    public String getCategory()
    {
    	return category;
    }
    
    public void setCategory( String category )
    {
    	this.category = category;
    }
    
    public Long getProjId()
    {
    	return projId;
    }
    
    public void setProjId( Long projId )
    {
    	this.projId = projId;
    }
    
    public String toString()
    {
    	String return_str = "";
    	for(PreContractDocsTO preContractDocsTO:preContractDocsTOs)
    	{
    		return_str += preContractDocsTO.getFileSize();
    	}
    	return "from PrecontractDocSaveReq crmId:"+getClientId()+" category:"+getCategory()+return_str;
    }
}
