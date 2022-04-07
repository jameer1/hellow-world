package com.rjtech.projectlib.dto;

import com.rjtech.centrallib.dto.MeasureUnitTO;
import com.rjtech.centrallib.dto.PlantClassTO;
import com.rjtech.common.dto.ClientTO;

public class ProjPlantClassTO extends ClientTO {
    private static final long serialVersionUID = 2950084862079755848L;
    private Long id;
    private String plantContrName;
    private Long plantClassId;
    private Long measureId;
    private Long projId;
    private PlantClassTO plantClassTO = new PlantClassTO();
    private MeasureUnitTO measureUnitTO = new MeasureUnitTO();

    public String getPlantContrName() {
        return plantContrName;
    }

    public void setPlantContrName(String plantContrName) {
        this.plantContrName = plantContrName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlantClassId() {
        return plantClassId;
    }

    public void setPlantClassId(Long plantClassId) {
        this.plantClassId = plantClassId;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public Long getMeasureId() {
        return measureId;
    }

    public void setMeasureId(Long measureId) {
        this.measureId = measureId;
    }

    public PlantClassTO getPlantClassTO() {
        return plantClassTO;
    }

    public void setPlantClassTO(PlantClassTO plantClassTO) {
        this.plantClassTO = plantClassTO;
    }

    public MeasureUnitTO getMeasureUnitTO() {
        return measureUnitTO;
    }

    public void setMeasureUnitTO(MeasureUnitTO measureUnitTO) {
        this.measureUnitTO = measureUnitTO;
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
        ProjPlantClassTO other = (ProjPlantClassTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
