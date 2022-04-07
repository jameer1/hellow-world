package com.rjtech.projsettings.model;

import java.io.Serializable;
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
import com.rjtech.notification.model.PlantNotificationsEntity;
import com.rjtech.notification.model.PlantNotificationsEntityCopy;
//import com.rjtech.notification.model.PlantNotificationsEntityCopy;

/**
 * @author Rajutech
 *
 */
@Entity
@Table(name = "proj_plant_transfer_approval")
public class PlantTransAddtionalTimeApprEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PTA_ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PTA_DATE")
    private Date requisitionDate;

    // Recheck if this business is correct
    @ManyToOne
    @JoinColumn(name = "PTA_NTF_ID")
    private PlantNotificationsEntityCopy plantNotificationsEntity;

    @Column(name = "PTA_IS_LATEST")
    private boolean latest;

    @Column(name = "PTA_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PTA_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PTA_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PTA_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PTA_UPDATED_ON")
    private Date updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getRequisitionDate() {
        return requisitionDate;
    }

    public void setRequisitionDate(Date requisitionDate) {
        this.requisitionDate = requisitionDate;
    }

    public PlantNotificationsEntityCopy getPlantNotificationsEntity() {
        return plantNotificationsEntity;
    }

    public void setPlantNotificationsEntity(PlantNotificationsEntityCopy plantNotificationsEntity) {
        this.plantNotificationsEntity = plantNotificationsEntity;
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

    public UserMstrEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserMstrEntity createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public UserMstrEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserMstrEntity updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

}
