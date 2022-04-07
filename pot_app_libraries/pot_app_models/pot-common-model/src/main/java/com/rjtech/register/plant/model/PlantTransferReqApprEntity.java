package com.rjtech.register.plant.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

@Entity
@Table(name = "plant_trans_req_approval")
public class PlantTransferReqApprEntity implements Serializable {

    private static final long serialVersionUID = -3042326684865353187L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PTRA_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PTRA_APPR_USR_ID")
    private UserMstrEntity apprUserMstrEntity;

    @ManyToOne
    @JoinColumn(name = "PTRA_REQ_USR_ID")
    private UserMstrEntity reqUserMstrEnitty;

    @ManyToOne
    @JoinColumn(name = "PTRA_TO_EPM_ID")
    private ProjMstrEntity toProjId;

    @ManyToOne
    @JoinColumn(name = "PTRA_FROM_EPM_ID")
    private ProjMstrEntity fromProjId;

    @ManyToOne
    @JoinColumn(name = "PTRA_NOTIFY_ID")
    private PlantNotificationsEntity plantNotificationsEntity;

    @Column(name = "PTRA_APPR_CODE")
    private String apprCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PTRA_REQ_DATE")
    private Date reqDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PTRA_APPR_DATE")
    private Date apprDate;

    @Column(name = "PTRA_APPR_STATUS")
    private String apprStatus;

    @Column(name = "PTRA_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PTRA_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PTRA_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PTRA_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "PTRA_UPDATED_ON")
    private Timestamp updatedOn;

    @Column(name = "PTRA_REQ_CUR_PROJECT")
    private String reqCurrentProj;

    @Column(name = "PTRA_APPR_CUR_PROJECT")
    private String apprCurrentProj;

    @Column(name = "PTRA_NOTIFICATION_STATUS")
    private String notificationStatus;

    @OneToMany(mappedBy = "plantTransferReqApprEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PlantTransReqApprDtlEntity> plantTransReqApprDtlEntities = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getReqDate() {
        return reqDate;
    }

    public void setReqDate(Date reqDate) {
        this.reqDate = reqDate;
    }

    public Date getApprDate() {
        return apprDate;
    }

    public void setApprDate(Date apprDate) {
        this.apprDate = apprDate;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public String getApprCode() {
        return apprCode;
    }

    public void setApprCode(String apprCode) {
        this.apprCode = apprCode;
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

    public UserMstrEntity getApprUserMstrEntity() {
        return apprUserMstrEntity;
    }

    public void setApprUserMstrEntity(UserMstrEntity apprUserMstrEntity) {
        this.apprUserMstrEntity = apprUserMstrEntity;
    }

    public UserMstrEntity getReqUserMstrEnitty() {
        return reqUserMstrEnitty;
    }

    public void setReqUserMstrEnitty(UserMstrEntity reqUserMstrEnitty) {
        this.reqUserMstrEnitty = reqUserMstrEnitty;
    }

    public PlantNotificationsEntity getPlantNotificationsEntity() {
        return plantNotificationsEntity;
    }

    public void setPlantNotificationsEntity(PlantNotificationsEntity plantNotificationsEntity) {
        this.plantNotificationsEntity = plantNotificationsEntity;
    }

    public List<PlantTransReqApprDtlEntity> getPlantTransReqApprDtlEntities() {
        return plantTransReqApprDtlEntities;
    }

    public void setPlantTransReqApprDtlEntities(List<PlantTransReqApprDtlEntity> plantTransReqApprDtlEntities) {
        this.plantTransReqApprDtlEntities = plantTransReqApprDtlEntities;
    }

    public String getReqCurrentProj() {
        return reqCurrentProj;
    }

    public void setReqCurrentProj(String reqCurrentProj) {
        this.reqCurrentProj = reqCurrentProj;
    }

    public String getApprCurrentProj() {
        return apprCurrentProj;
    }

    public void setApprCurrentProj(String apprCurrentProj) {
        this.apprCurrentProj = apprCurrentProj;
    }

    public ProjMstrEntity getToProjId() {
        return toProjId;
    }

    public void setToProjId(ProjMstrEntity toProjId) {
        this.toProjId = toProjId;
    }

    public ProjMstrEntity getFromProjId() {
        return fromProjId;
    }

    public void setFromProjId(ProjMstrEntity fromProjId) {
        this.fromProjId = fromProjId;
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

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }
}
