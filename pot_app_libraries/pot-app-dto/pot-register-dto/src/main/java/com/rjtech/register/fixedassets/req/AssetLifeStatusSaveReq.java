package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.AssetLifeStatusDtlTO;

public class AssetLifeStatusSaveReq extends AssetLifeStatusDtlTO {
    private static final long serialVersionUID = 2740448397989044793L;
    private String folderCategory;
    private Long userId;

    private List<AssetLifeStatusDtlTO> assetLifeStatusDtlTOs = new ArrayList<AssetLifeStatusDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<AssetLifeStatusDtlTO> getAssetLifeStatusDtlTOs() {
        return assetLifeStatusDtlTOs;
    }

    public void setAssetLifeStatusDtlTOs(List<AssetLifeStatusDtlTO> assetLifeStatusDtlTOs) {
        this.assetLifeStatusDtlTOs = assetLifeStatusDtlTOs;
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
