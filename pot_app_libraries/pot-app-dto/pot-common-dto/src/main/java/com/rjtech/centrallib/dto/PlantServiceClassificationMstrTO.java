package com.rjtech.centrallib.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;


public class PlantServiceClassificationMstrTO extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private String name;
    private Integer isSubCatg;
    private boolean item;
    private boolean itemParent = false;
    private boolean expand = false;
    private Long parentId;
    private String parentName;

    private List<PlantServiceClassificationMstrTO> plantServiceClassificationMstrTOs = new ArrayList<PlantServiceClassificationMstrTO>(
            5);

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getIsSubCatg() {
        return isSubCatg;
    }

    public void setIsSubCatg(Integer isSubCatg) {
        this.isSubCatg = isSubCatg;
    }

    public boolean isItem() {
        return item;
    }

    public void setItem(boolean item) {
        this.item = item;
    }

    public boolean isItemParent() {
        return itemParent;
    }

    public void setItemParent(boolean itemParent) {
        this.itemParent = itemParent;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public List<PlantServiceClassificationMstrTO> getPlantServiceClassificationMstrTOs() {
        return plantServiceClassificationMstrTOs;
    }

    public void setPlantServiceClassificationMstrTOs(
            List<PlantServiceClassificationMstrTO> plantServiceClassificationMstrTOs) {
        this.plantServiceClassificationMstrTOs = plantServiceClassificationMstrTOs;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
