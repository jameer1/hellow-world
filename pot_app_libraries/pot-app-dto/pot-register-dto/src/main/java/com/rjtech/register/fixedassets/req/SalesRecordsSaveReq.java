package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.SalesRecordsDtlTO;

public class SalesRecordsSaveReq extends AppResp {

    private static final long serialVersionUID = 2740448397989044791L;    
    private String folderCategory;
    public Long userId;

    private List<SalesRecordsDtlTO> salesRecordsDtlTOs = new ArrayList<SalesRecordsDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<SalesRecordsDtlTO> getSalesRecordsDtlTOs() {
        return salesRecordsDtlTOs;
    }

    public void setSalesRecordsDtlTOs(List<SalesRecordsDtlTO> salesRecordsDtlTOs) {
        this.salesRecordsDtlTOs = salesRecordsDtlTOs;
    }
    
    public void setFolderCategory( String folderCategory ) {
    	this.folderCategory = folderCategory;
    }
    
    public String getFolderCategory() {
    	return folderCategory;
    }
    
    public void setUserId( Long userId ) {
    	this.userId = userId;
    }
    
    public Long getUserId() {
    	return userId;
    }

}
