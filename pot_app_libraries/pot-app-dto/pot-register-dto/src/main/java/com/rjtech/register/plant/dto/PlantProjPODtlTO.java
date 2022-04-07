package com.rjtech.register.plant.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.dto.RegisterProjPurchaseOrderTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlantProjPODtlTO extends RegisterProjPurchaseOrderTO {

    /**
     * 
     */
    private static final long serialVersionUID = -1210735657272420352L;
    private Long id;
    private String commissionDate;
    private String deliveryStatus;
    private String receivedBy;
    private String docName;
    private Long docId;
    private BigDecimal odoMeter;
    private LabelKeyTO purchasePlantTypeLabelKeyTO;
    private PlantRegProjTO plantRegProjTO = new PlantRegProjTO();
    private List<PlantPODocketDtlTO> plantDocketDtlTOs = new ArrayList<>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public BigDecimal getOdoMeter() {
        return odoMeter;
    }

    public void setOdoMeter(BigDecimal odoMeter) {
        this.odoMeter = odoMeter;
    }

    public String getCommissionDate() {
        return commissionDate;
    }

    public void setCommissionDate(String commissionDate) {
        this.commissionDate = commissionDate;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public LabelKeyTO getPurchasePlantTypeLabelKeyTO() {
        return purchasePlantTypeLabelKeyTO;
    }

    public void setPurchasePlantTypeLabelKeyTO(LabelKeyTO purchasePlantTypeLabelKeyTO) {
        this.purchasePlantTypeLabelKeyTO = purchasePlantTypeLabelKeyTO;
    }

    public PlantRegProjTO getPlantRegProjTO() {
        return plantRegProjTO;
    }

    public void setPlantRegProjTO(PlantRegProjTO plantRegProjTO) {
        this.plantRegProjTO = plantRegProjTO;
    }

    public List<PlantPODocketDtlTO> getPlantDocketDtlTOs() {
        return plantDocketDtlTOs;
    }

    public void setPlantDocketDtlTOs(List<PlantPODocketDtlTO> plantDocketDtlTOs) {
        this.plantDocketDtlTOs = plantDocketDtlTOs;
    }
    
    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }
    
    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

}
