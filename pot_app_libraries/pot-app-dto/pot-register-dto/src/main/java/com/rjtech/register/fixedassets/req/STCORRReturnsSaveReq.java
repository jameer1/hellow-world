package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.STCORRReturnsDtlTO;

public class STCORRReturnsSaveReq extends STCORRReturnsGetReq {
    
    private List<STCORRReturnsDtlTO> stcorrReturnsDtlTOs = new ArrayList<STCORRReturnsDtlTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private String folderCategory;
    private Long userId;
    private static final long serialVersionUID = 2740448397989044791L;

    public List<STCORRReturnsDtlTO> getStcorrReturnsDtlTOs() {
        return stcorrReturnsDtlTOs;
    }

    public void setStcorrReturnsDtlTOs(List<STCORRReturnsDtlTO> stcorrReturnsDtlTOs) {
        this.stcorrReturnsDtlTOs = stcorrReturnsDtlTOs;
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
