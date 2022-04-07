package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.AssetCostValueStatusDtlTO;
import com.rjtech.register.fixedassets.dto.AssetLifeStatusDtlTO;

public class AssetCostValueStatusSaveReq extends AssetLifeStatusDtlTO {
    private static final long serialVersionUID = 2740448397989044793L;
    private String folderCategory;
    private Long userId;
    
    private List<AssetCostValueStatusDtlTO> assetCostValueStatusDtlTOs = new ArrayList<AssetCostValueStatusDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<AssetCostValueStatusDtlTO> getAssetCostValueStatusDtlTOs() {
        return assetCostValueStatusDtlTOs;
    }

    public void setAssetCostValueStatusDtlTOs(List<AssetCostValueStatusDtlTO> assetCostValueStatusDtlTOs) {
        this.assetCostValueStatusDtlTOs = assetCostValueStatusDtlTOs;
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
