package com.rjtech.register.material.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.material.dto.MaterialProjDtlTO;

public class MaterialProjSaveReq extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private List<MaterialProjDtlTO> materialProjDtlTOs = new ArrayList<MaterialProjDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Long purId;
    private Long materialId;
    private String folderCategory;
    private Long userId;
    private Long purOrderId;
    private Long schItemId;
    private String assetType;

    public List<MaterialProjDtlTO> getMaterialProjDtlTOs() {
        return materialProjDtlTOs;
    }

    public void setMaterialProjDtlTOs(List<MaterialProjDtlTO> materialProjDtlTOs) {
        this.materialProjDtlTOs = materialProjDtlTOs;
    }

    public Long getPurId() {
        return purId;
    }

    public void setPurId(Long purId) {
        this.purId = purId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getFolderCategory() {
        return folderCategory;
    }

    public void setFolderCategory(String folderCategory) {
        this.folderCategory = folderCategory;
    }
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getPurOrderId() {
        return purOrderId;
    }

    public void setPurOrderId( Long purOrderId ) {
        this.purOrderId = purOrderId;
    }
    
    public Long getSchItemId() {
        return schItemId;
    }

    public void setSchItemId( Long schItemId ) {
        this.schItemId = schItemId;
    }
    
    public void setAssetType(String assetType) {
    	this.assetType = assetType;
    }
    
    public String getAssetType() {
    	return assetType;
    }
    
}
