package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.FixedAssetAqulisitionRecordsDtlTO;

public class FixedAssetPurachaseRecordsSaveReq extends FixedAssetAqulisitionRecordsDtlTO {

    private static final long serialVersionUID = 2740448397989044791L;
    private String folderCategory;
    private Long userId;

    private List<FixedAssetAqulisitionRecordsDtlTO> fixedAssetAqulisitionRecordsDtlTO = new ArrayList<FixedAssetAqulisitionRecordsDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<FixedAssetAqulisitionRecordsDtlTO> getFixedAssetAqulisitionRecordsDtlTO() {
        return fixedAssetAqulisitionRecordsDtlTO;
    }

    public void setFixedAssetAqulisitionRecordsDtlTO(
            List<FixedAssetAqulisitionRecordsDtlTO> fixedAssetAqulisitionRecordsDtlTO) {
        this.fixedAssetAqulisitionRecordsDtlTO = fixedAssetAqulisitionRecordsDtlTO;
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
