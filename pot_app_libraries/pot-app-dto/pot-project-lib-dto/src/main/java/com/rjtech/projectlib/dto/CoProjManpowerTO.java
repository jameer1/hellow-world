package com.rjtech.projectlib.dto;

import java.util.Date;

import com.rjtech.centrallib.dto.EmpClassTO;
import com.rjtech.centrallib.dto.MeasureUnitTO;
import com.rjtech.common.dto.ProjectTO;

public class CoProjManpowerTO extends ProjectTO {

    private static final long serialVersionUID = 725069871190599664L;
    private Long id;
    private Long empClassId;
    private Long empCatgId;
    private Long measureId;
    private Double currentQty;
    private Double pendingQty;
    private Double revisedQty;
    private EmpClassTO empClassTO = new EmpClassTO();
    private MeasureUnitTO measureUnitTO = new MeasureUnitTO();
    private String notes;
    private String itemType;
    private Long projManpowerId;
    private Long coMstrId;
    private Long createdBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpClassId() {
        return empClassId;
    }

    public void setEmpClassId( Long empClassId ) {
        this.empClassId = empClassId;
    }

    public Long getEmpCatgId() {
        return empCatgId;
    }

    public void setEmpCatgId( Long empCatgId ) {
        this.empCatgId = empCatgId;
    }

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
    
    public EmpClassTO getEmpClassTO() {
        return empClassTO;
    }

    public void setEmpClassTO(EmpClassTO empClassTO) {
        this.empClassTO = empClassTO;
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
    
    public Long getProjManpowerId() {
        return projManpowerId;
    }

    public void setProjManpowerId( Long projManpowerId ) {
        this.projManpowerId = projManpowerId;
    }
    
    public Long getCoMstrId() {
        return coMstrId;
    }

    public void setCoMstrId( Long coMstrId ) {
        this.coMstrId = coMstrId;
    }
        
    public String toString() {
    	return "id:"+id+" empClassId:"+empClassId+" measureId:"+measureId+" currentQty:"+currentQty+" itemType:"+itemType+" revisedQty:"+revisedQty;
    }
}
