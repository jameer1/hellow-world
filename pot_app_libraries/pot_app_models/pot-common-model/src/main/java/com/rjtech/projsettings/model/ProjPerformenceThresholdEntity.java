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
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

@Entity
@Table(name = "proj_performance_threshold")
public class ProjPerformenceThresholdEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PPT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PPT_EPM_ID")
    private ProjMstrEntity projMstrEntity;

    @Column(name = "PPT_CATEGORY")
    private String category;

    @Column(name = "PPT_SV_LOWER_LIMIT")
    private String svLowerLimit;

    @Column(name = "PPT_SV_UPPER_LIMIT")
    private String svUpperLimit;

    @Column(name = "PPT_CV_LOWER_LIMIT")
    private String cvLowerLimit;

    @Column(name = "PPT_CV_UPPER_LIMIT")
    private String cvUpperLimit;

    @Column(name = "PPT_SPI_LOWER_LIMIT")
    private String spiLowerLimit;

    @Column(name = "PPT_SPI_UPPER_LIMIT")
    private String spiUpperLimit;

    @Column(name = "PPT_CPI_LOWER_LIMIT")
    private String cpiLowerLimit;

    @Column(name = "PPT_CPI_UPPER_LIMIT")
    private String cpiUpperLimit;

    @Column(name = "PPT_TCPI_LOWER_LIMIT")
    private String tcpiLowerLimit;

    @Column(name = "PPT_TCPI_UPPER_LIMIT")
    private String tcpiUpperLimit;

    @Column(name = "PPT_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PPT_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PPT_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PPT_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PPT_UPDATED_ON")
    private Date updatedOn;

    @Column(name = "PPT_IS_DEFAULT")
    private String isDefault;

    public ProjPerformenceThresholdEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjMstrEntity getProjMstrEntity() {
        return projMstrEntity;
    }

    public void setProjMstrEntity(ProjMstrEntity projMstrEntity) {
        this.projMstrEntity = projMstrEntity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSvLowerLimit() {
        return svLowerLimit;
    }

    public void setSvLowerLimit(String svLowerLimit) {
        this.svLowerLimit = svLowerLimit;
    }

    public String getSvUpperLimit() {
        return svUpperLimit;
    }

    public void setSvUpperLimit(String svUpperLimit) {
        this.svUpperLimit = svUpperLimit;
    }

    public String getCvLowerLimit() {
        return cvLowerLimit;
    }

    public void setCvLowerLimit(String cvLowerLimit) {
        this.cvLowerLimit = cvLowerLimit;
    }

    public String getCvUpperLimit() {
        return cvUpperLimit;
    }

    public void setCvUpperLimit(String cvUpperLimit) {
        this.cvUpperLimit = cvUpperLimit;
    }

    public String getSpiLowerLimit() {
        return spiLowerLimit;
    }

    public void setSpiLowerLimit(String spiLowerLimit) {
        this.spiLowerLimit = spiLowerLimit;
    }

    public String getSpiUpperLimit() {
        return spiUpperLimit;
    }

    public void setSpiUpperLimit(String spiUpperLimit) {
        this.spiUpperLimit = spiUpperLimit;
    }

    public String getCpiLowerLimit() {
        return cpiLowerLimit;
    }

    public void setCpiLowerLimit(String cpiLowerLimit) {
        this.cpiLowerLimit = cpiLowerLimit;
    }

    public String getCpiUpperLimit() {
        return cpiUpperLimit;
    }

    public void setCpiUpperLimit(String cpiUpperLimit) {
        this.cpiUpperLimit = cpiUpperLimit;
    }

    public String getTcpiLowerLimit() {
        return tcpiLowerLimit;
    }

    public void setTcpiLowerLimit(String tcpiLowerLimit) {
        this.tcpiLowerLimit = tcpiLowerLimit;
    }

    public String getTcpiUpperLimit() {
        return tcpiUpperLimit;
    }

    public void setTcpiUpperLimit(String tcpiUpperLimit) {
        this.tcpiUpperLimit = tcpiUpperLimit;
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

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

}
