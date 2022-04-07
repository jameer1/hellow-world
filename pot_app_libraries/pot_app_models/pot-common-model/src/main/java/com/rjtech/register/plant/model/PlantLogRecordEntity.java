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

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;

@Entity
@Table(name = "plant_log_records")
public class PlantLogRecordEntity implements Serializable {

    private static final long serialVersionUID = -3172303638625702033L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLR_ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PLR_FROM_DATE")
    private Date fromDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PLR_TO_DATE")
    private Date toDate;

    @ManyToOne
    @JoinColumn(name = "PLR_PLRD_ID")
    private PlantRegisterDtlEntity plantId;

    @Column(name = "PLR_START_METER_READING")
    private BigDecimal startMeter;

    @Column(name = "PLR_END_METER_READING")
    private BigDecimal endMeter;

    @Column(name = "PLR_NET_UNITS")
    private BigDecimal netUnits;

    @Column(name = "PLR_COMMENTS")
    private String comments;

    @Column(name = "PLR_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PLR_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PLR_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PLR_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "PLR_UPDATED_ON")
    private Timestamp updatedOn;

    @Column(name = "PLR_IS_LATEST")
    private boolean latest;

    @ManyToOne
    @JoinColumn(name = "PLR_ERD_ID")
    private EmpRegisterDtlEntity empRegisterDtlEntity;

    @ManyToOne
    @JoinColumn(name = "PLR_PPJD_ID")
    private PlantRegProjEntity plantRegProjEntity;

    public PlantLogRecordEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public BigDecimal getStartMeter() {
        return startMeter;
    }

    public void setStartMeter(BigDecimal startMeter) {
        this.startMeter = startMeter;
    }

    public BigDecimal getEndMeter() {
        return endMeter;
    }

    public void setEndMeter(BigDecimal endMeter) {
        this.endMeter = endMeter;
    }

    public BigDecimal getNetUnits() {
        return netUnits;
    }

    public void setNetUnits(BigDecimal netUnits) {
        this.netUnits = netUnits;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
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

    public EmpRegisterDtlEntity getEmpRegisterDtlEntity() {
        return empRegisterDtlEntity;
    }

    public void setEmpRegisterDtlEntity(EmpRegisterDtlEntity empRegisterDtlEntity) {
        this.empRegisterDtlEntity = empRegisterDtlEntity;
    }

    public PlantRegProjEntity getPlantRegProjEntity() {
        return plantRegProjEntity;
    }

    public void setPlantRegProjEntity(PlantRegProjEntity plantRegProjEntity) {
        this.plantRegProjEntity = plantRegProjEntity;
    }

    public PlantRegisterDtlEntity getPlantId() {
        return plantId;
    }

    public void setPlantId(PlantRegisterDtlEntity plantId) {
        this.plantId = plantId;
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
