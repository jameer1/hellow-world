package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.LongTermLeaseActualRetrunsDtlTO;

public class LongTermLeaseActualRetrunsSaveReq extends LongTermLeaseActualRetrunsDtlTO {
    private static final long serialVersionUID = 2740448397989044792L;
    private String folderCategory;
    private Long userId;
    
    private List<LongTermLeaseActualRetrunsDtlTO> longTermLeaseActualRetrunsDtlTOs = new ArrayList<LongTermLeaseActualRetrunsDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<LongTermLeaseActualRetrunsDtlTO> getLongTermLeaseActualRetrunsDtlTOs() {
        return longTermLeaseActualRetrunsDtlTOs;
    }

    public void setLongTermLeaseActualRetrunsDtlTOs(
            List<LongTermLeaseActualRetrunsDtlTO> longTermLeaseActualRetrunsDtlTOs) {
        this.longTermLeaseActualRetrunsDtlTOs = longTermLeaseActualRetrunsDtlTOs;
    }

    public String getFolderCategory() {
    	return folderCategory;
    }
    
    public void setFolderCategory( String folderCategory ) {
    	this.folderCategory = folderCategory;
    }
    
    public Long getUserId() {
    	return userId;
    }
    
    public void setUserId( Long userId ) {
    	this.userId = userId;
    }
}
