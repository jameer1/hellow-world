package com.rjtech.register.plant.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.OneToOne;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.document.model.ProjDocFileEntity;

@Entity
@Table(name = "plant_docket_dtl")
public class PlantPODocketDtlEntity implements Serializable {

    private static final long serialVersionUID = 4351525147029155408L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PDD_ID")
    private Long id;

    @Column(name = "PDD_DOCKET_NUM")
    private String docketNum;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PDD_DOCKET_START_DATE")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PDD_DOCKET_END_DATE")
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "PDD_RECEIVED_BY")
    private EmpRegisterDtlEntity receivedBy;

    @Column(name = "PDD_RECEIVER_COMMENTS")
    private String receiverComments;

    @Column(name = "PDD_ODO_METER_READING")
    private BigDecimal odoMeter;

    @Column(name = "PDD_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PDD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PDD_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PDD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "PDD_UPDATED_ON")
    private Timestamp updatedOn;

    @Column(name = "PDD_DELIVERY_LOCATION")
    private String deliveryLocation;

    @Column(name = "PDD_RECEIVED_QTY")
    private BigDecimal receivedQty;

    @Column(name = "PDD_DELIVERY_TYPE")
    private String deliveryType;

    @ManyToOne
    @JoinColumn(name = "PDD_PPP_ID")
    private PlantProjPODtlEntity plantProjPODtlEntity;

    @Column(name = "PDD_DOC_KEY")
    private String docKey;

    @Column(name = "PDD_DOC_FILE_NAME")
    private String documentFileName;

    @Column(name = "PDD_DOC_FILE_TYPE")
    private String documentFileType;
    
    @OneToOne
    @JoinColumn(name = "PDFL_ID_FK")
    private ProjDocFileEntity projDocFile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocketNum() {
        return docketNum;
    }

    public void setDocketNum(String docketNum) {
        this.docketNum = docketNum;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public String getReceiverComments() {
        return receiverComments;
    }

    public void setReceiverComments(String receiverComments) {
        this.receiverComments = receiverComments;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    public BigDecimal getOdoMeter() {
        return odoMeter;
    }

    public void setOdoMeter(BigDecimal odoMeter) {
        this.odoMeter = odoMeter;
    }

    public BigDecimal getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(BigDecimal receivedQty) {
        this.receivedQty = receivedQty;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public PlantProjPODtlEntity getPlantProjPODtlEntity() {
        return plantProjPODtlEntity;
    }

    public void setPlantProjPODtlEntity(PlantProjPODtlEntity plantProjPODtlEntity) {
        this.plantProjPODtlEntity = plantProjPODtlEntity;
    }

    public EmpRegisterDtlEntity getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(EmpRegisterDtlEntity receivedBy) {
        this.receivedBy = receivedBy;
    }

    public UserMstrEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserMstrEntity createdBy) {
        this.createdBy = createdBy;
    }

    public UserMstrEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserMstrEntity updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getDocKey() {
        return docKey;
    }

    public void setDocKey(String docKey) {
        this.docKey = docKey;
    }

    public String getDocumentFileName() {
        return documentFileName;
    }

    public void setDocumentFileName(String documentFileName) {
        this.documentFileName = documentFileName;
    }

    public String getDocumentFileType() {
        return documentFileType;
    }

    public void setDocumentFileType(String documentFileType) {
        this.documentFileType = documentFileType;
    }

    public ProjDocFileEntity getProjDocFile() {
        return projDocFile;
    }

    public void setProjDocFile( ProjDocFileEntity projDocFile ) {
        this.projDocFile = projDocFile;
    }
}
