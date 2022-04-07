package com.rjtech.register.plant.model;

import java.io.Serializable;
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

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

@Entity
@Table(name = "plant_trans_req_approval_dtl")
public class PlantTransReqApprDtlEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PTRAD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PTRAD_PLRD_ID")
    private PlantRegisterDtlEntity plantId;

    @ManyToOne
    @JoinColumn(name = "PTRAD_EPM_ID")
    private ProjMstrEntity projId;

    @Column(name = "PTRAD_EXPECT_TRANS_DATE")
    private Date expectedTransDate;

    @Column(name = "PTRAD_TRANS_DATE")
    private Date transDate;

    @Column(name = "PTRAD_PLANT_TRANS_DATE")
    private Date plantTransDate;

    @Column(name = "PTRAD_ACTUAL_DELIVER_DATE")
    private Date actualDeliveryDate;

    @Column(name = "PTRAD_PLANT_TRANS_COMMENTS")
    private String plantTransComments;

    @ManyToOne
    @JoinColumn(name = "PTRAD_RECEIVED_BY")
    private UserMstrEntity receivedBy;

    @Column(name = "PTRAD_RECEIVER_COMMENTS")
    private String comments;

    @Column(name = "PTRAD_APPR_STATUS")
    private String apprStatus;

    @Column(name = "PTRAD_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PTRAD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PTRAD_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PTRAD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "PTRAD_UPDATED_ON")
    private Timestamp updatedOn;

    @ManyToOne
    @JoinColumn(name = "PTRAD_PTRA_ID")
    private PlantTransferReqApprEntity plantTransferReqApprEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public Date getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public void setActualDeliveryDate(Date actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }

    public PlantTransferReqApprEntity getPlantTransferReqApprEntity() {
        return plantTransferReqApprEntity;
    }

    public void setPlantTransferReqApprEntity(PlantTransferReqApprEntity plantTransferReqApprEntity) {
        this.plantTransferReqApprEntity = plantTransferReqApprEntity;
    }

    public Date getExpectedTransDate() {
        return expectedTransDate;
    }

    public void setExpectedTransDate(Date expectedTransDate) {
        this.expectedTransDate = expectedTransDate;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public Date getPlantTransDate() {
        return plantTransDate;
    }

    public void setPlantTransDate(Date plantTransDate) {
        this.plantTransDate = plantTransDate;
    }

    public String getPlantTransComments() {
        return plantTransComments;
    }

    public void setPlantTransComments(String plantTransComments) {
        this.plantTransComments = plantTransComments;
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

    public PlantRegisterDtlEntity getPlantId() {
        return plantId;
    }

    public void setPlantId(PlantRegisterDtlEntity plantId) {
        this.plantId = plantId;
    }

    public ProjMstrEntity getProjId() {
        return projId;
    }

    public void setProjId(ProjMstrEntity projId) {
        this.projId = projId;
    }

    public UserMstrEntity getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(UserMstrEntity receivedBy) {
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

}
