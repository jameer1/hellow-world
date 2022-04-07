package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.LongTermLeasingDtlTO;

public class LongTermLeasingSaveReq extends LongTermLeasingGetReq {

    private static final long serialVersionUID = 2740448397989044791L;
    private String folderCategory;
    private Long userId;

    private List<LongTermLeasingDtlTO> longTermLeasingDtlTOs = new ArrayList<LongTermLeasingDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<LongTermLeasingDtlTO> getLongTermLeasingDtlTOs() {
        return longTermLeasingDtlTOs;
    }

    public void setLongTermLeasingDtlTOs(List<LongTermLeasingDtlTO> longTermLeasingDtlTOs) {
        this.longTermLeasingDtlTOs = longTermLeasingDtlTOs;
    }

    public String getFolderCategory() {
    	return folderCategory;
    }
    
    public void setFolderCategory() {
    	this.folderCategory = folderCategory;
    }
    
    public Long getUserId() {
    	return userId;
    }
    
    public void setUserId() {
    	this.userId = userId;
    }
}
