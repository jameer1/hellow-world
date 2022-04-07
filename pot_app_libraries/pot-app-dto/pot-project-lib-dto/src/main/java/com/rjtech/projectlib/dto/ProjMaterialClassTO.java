package com.rjtech.projectlib.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.MeasureUnitTO;
import com.rjtech.common.dto.ProjectTO;

public class ProjMaterialClassTO extends ProjectTO {
    private static final long serialVersionUID = 2950084862079755848L;
    private Long id;
    private String code;
    private String name;
    private Long uomId;
    private Boolean internalApproved;
    private Boolean externalApproved;
    private Long groupId;
    private Long materialGrpId;
    private Long measureId;
    private List<ProjMaterialClassTO> projMaterialClassTOs = new ArrayList<ProjMaterialClassTO>();
    private Long parentId;
    private boolean item;
    private MeasureUnitTO measureUnitTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
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

    public Long getUomId() {
        return uomId;
    }

    public void setUomId(Long uomId) {
        this.uomId = uomId;
    }

    public Boolean getInternalApproved() {
        return internalApproved;
    }

    public void setInternalApproved(Boolean internalApproved) {
        this.internalApproved = internalApproved;
    }

    public Boolean getExternalApproved() {
        return externalApproved;
    }

    public void setExternalApproved(Boolean externalApproved) {
        this.externalApproved = externalApproved;
    }

    public Long getMaterialGrpId() {
        return materialGrpId;
    }

    public void setMaterialGrpId(Long materialGrpId) {
        this.materialGrpId = materialGrpId;
    }

    public Long getMeasureId() {
        return measureId;
    }

    public void setMeasureId(Long measureId) {
        this.measureId = measureId;
    }

    public MeasureUnitTO getMeasureUnitTO() {
        return measureUnitTO;
    }

    public void setMeasureUnitTO(MeasureUnitTO measureUnitTO) {
        this.measureUnitTO = measureUnitTO;
    }

    public List<ProjMaterialClassTO> getProjMaterialClassTOs() {
        return projMaterialClassTOs;
    }

    public void setProjMaterialClassTOs(List<ProjMaterialClassTO> projMaterialClassTOs) {
        this.projMaterialClassTOs = projMaterialClassTOs;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public boolean isItem() {
        return item;
    }

    public void setItem(boolean item) {
        this.item = item;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProjMaterialClassTO other = (ProjMaterialClassTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
