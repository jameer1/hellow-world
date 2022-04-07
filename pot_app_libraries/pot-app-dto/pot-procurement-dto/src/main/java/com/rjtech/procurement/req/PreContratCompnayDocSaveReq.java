package com.rjtech.procurement.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.constants.ApplicationConstants;
import com.rjtech.procurement.dto.PreContractCmpDocsTO;

public class PreContratCompnayDocSaveReq extends ProcurementGetReq {

    private static final long serialVersionUID = 1L;
    private String folderCategory;
    private Long userId;

    private List<PreContractCmpDocsTO> preContractCmpDocsTOs = new ArrayList<PreContractCmpDocsTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<PreContractCmpDocsTO> getPreContractCmpDocsTOs() {
        return preContractCmpDocsTOs;
    }

    public void setPreContractCmpDocsTOs(List<PreContractCmpDocsTO> preContractCmpDocsTOs) {
        this.preContractCmpDocsTOs = preContractCmpDocsTOs;
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
