package com.rjtech.centrallib.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;

public class AssetCategoryTO extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String code;
    private String name;
    private boolean item;
    private boolean expand = false;
    private Long parentId;
    private String parentName;
    private List<AssetCategoryTO> childAssetCategoryTOs = new ArrayList<AssetCategoryTO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isItem() {
        return item;
    }

    public void setItem(boolean item) {
        this.item = item;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public List<AssetCategoryTO> getChildAssetCategoryTOs() {
        return childAssetCategoryTOs;
    }

    public void setChildAssetCategoryTOs(List<AssetCategoryTO> childAssetCategoryTOs) {
        this.childAssetCategoryTOs = childAssetCategoryTOs;
    }

}
