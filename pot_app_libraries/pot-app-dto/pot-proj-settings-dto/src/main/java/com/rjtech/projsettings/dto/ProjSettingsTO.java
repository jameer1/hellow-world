package com.rjtech.projsettings.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class ProjSettingsTO extends ProjectTO {

    private static final long serialVersionUID = -6304719164679626529L;
    private Long id;
    private String name;
    private String urlValue;
    private Integer dispOrder;
    private Long parentId;

    private List<ProjSettingsTO> childTabs = new ArrayList<ProjSettingsTO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlValue() {
        return urlValue;
    }

    public void setUrlValue(String urlValue) {
        this.urlValue = urlValue;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<ProjSettingsTO> getChildTabs() {
        return childTabs;
    }

    public void setChildTabs(List<ProjSettingsTO> childTabs) {
        this.childTabs = childTabs;
    }

}
