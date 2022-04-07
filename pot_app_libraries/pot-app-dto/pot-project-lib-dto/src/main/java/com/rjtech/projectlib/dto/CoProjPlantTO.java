package com.rjtech.projectlib.dto;

import java.util.Date;

import com.rjtech.centrallib.dto.PlantClassTO;
import com.rjtech.centrallib.dto.MeasureUnitTO;
import com.rjtech.common.dto.ProjectTO;

public class CoProjPlantTO extends ProjectTO {

    private static final long serialVersionUID = 725069871190599664L;
    private Long id;
    private Long plantClassId;
    //private Long empCatgId;
    private Long measureId;
    private Double currentQty;
    private Double pendingQty;
    private Double revisedQty;
    private PlantClassTO plantClassTO = new PlantClassTO();
    private MeasureUnitTO measureUnitTO = new MeasureUnitTO();
    private String notes;
    private String itemType;
    private Long projPlantId;
    private Long coMstrId;
    private Long createdBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlantClassId() {
        return plantClassId;
    }

    public void setPlantClassId( Long plantClassId ) {
        this.plantClassId = plantClassId;
    }

    /*public Long getEmpCatgId() {
        return empCatgId;
    }

    public void setEmpCatgId( Long empCatgId ) {
        this.empCatgId = empCatgId;
    }*/

    public Double getCurrentQty() {
        return currentQty;
    }

    public void setCurrentQty( Double currentQty ) {
        this.currentQty = currentQty;
    }
    
    public Double getPendingQty() {
        return pendingQty;
    }

    public void setPendingQty( Double pendingQty ) {
        this.pendingQty = pendingQty;
    }

    public Double getRevisedQty() {
        return revisedQty;
    }

    public void setRevisedQty(Double revisedQty) {
        this.revisedQty = revisedQty;
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
    
    public PlantClassTO getPlantClassTO() {
        return plantClassTO;
    }

    public void setPlantClassTO( PlantClassTO plantClassTO ) {
        this.plantClassTO = plantClassTO;
    }
    
    public String getNotes() {
        return notes;
    }

    public void setNotes( String notes ) {
        this.notes = notes;
    }
    
    public String getItemType() {
        return itemType;
    }

    public void setItemType( String itemType ) {
        this.itemType = itemType;
    }
    
    public Long getProjPlantId() {
        return projPlantId;
    }

    public void setProjPlantId( Long projPlantId ) {
        this.projPlantId = projPlantId;
    }
    
    public Long getCoMstrId() {
        return coMstrId;
    }

    public void setCoMstrId( Long coMstrId ) {
        this.coMstrId = coMstrId;
    }
        
    public String toString() {
    	return "id:"+id+" plantClassId:"+plantClassId+" measureId:"+measureId+" currentQty:"+currentQty+" itemType:"+itemType+" revisedQty:"+revisedQty;
    }
}
