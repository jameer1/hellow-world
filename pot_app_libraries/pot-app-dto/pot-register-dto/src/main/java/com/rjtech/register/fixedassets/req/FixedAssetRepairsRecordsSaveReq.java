package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.FixedAssetRepairsRecordsDtlTO;

public class FixedAssetRepairsRecordsSaveReq extends FixedAssetRepairsRecordsDtlTO {

    private static final long serialVersionUID = 2740448397989044791L;
    private String folderCategory;
    private Long userId;

    private List<FixedAssetRepairsRecordsDtlTO> assetsRepaisSchedulesTO = new ArrayList<FixedAssetRepairsRecordsDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<FixedAssetRepairsRecordsDtlTO> getAssetsRepaisSchedulesTO() {
        return assetsRepaisSchedulesTO;
    }

    public void setAssetsRepaisSchedulesTO(List<FixedAssetRepairsRecordsDtlTO> assetsRepaisSchedulesTO) {
        this.assetsRepaisSchedulesTO = assetsRepaisSchedulesTO;
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
