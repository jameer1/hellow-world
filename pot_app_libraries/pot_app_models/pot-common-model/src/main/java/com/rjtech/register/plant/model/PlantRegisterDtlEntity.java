package com.rjtech.register.plant.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.centrallib.model.PlantMstrEntity;
import com.rjtech.centrallib.model.ProcureCatgDtlEntity;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

@Entity
@Table(name = "plant_register_dtl")
public class PlantRegisterDtlEntity implements Serializable {

    private static final long serialVersionUID = 6471821724912392639L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLRD_ID")
    private Long id;

    @Column(name = "PLRD_DEPLOYMENT_ID")
    private Long deploymentId;

    @ManyToOne
    @JoinColumn(name = "PLRD_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @ManyToOne
    @JoinColumn(name = "PLRD_EPM_ID")
    private ProjMstrEntity projMstrEntity;

    @ManyToOne
    @JoinColumn(name = "PLRD_PCM_ID")
    private PlantMstrEntity plantClassMstrId;

    @ManyToOne
    @JoinColumn(name = "PLRD_CMP_ID")
    private CompanyMstrEntity cmpId;

    @ManyToOne
    @JoinColumn(name = "PLRD_OWNER_CMP_ID")
    private CompanyMstrEntity ownerCmpId;

    @Column(name = "PLRD_DESC")
    private String desc;

    @ManyToOne
    @JoinColumn(name = "PLRD_PCD_ID")
    private ProcureCatgDtlEntity procurecatgId;

    @Column(name = "PLRD_ASSET_ID")
    private String assertId;

    @Column(name = "PLRD_REG_NUM")
    private String regNumber;

    @Column(name = "PLRD_MAKE")
    private String manfacture;

    @Column(name = "PLRD_MODEL")
    private String model;

    @Column(name = "PLRD_ASSET_TYPE")
    private String assetType;

    @Column(name = "PLRD_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PLRD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PLRD_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PLRD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PLRD_UPDATED_ON")
    private Date updatedOn;

    @Column(name = "PLRD_PLANT_STATUS")
    private String plantStatus;

    @OneToMany(mappedBy = "plantRegisterDtlEntity")
    private List<PlantRegProjEntity> plantRegProjEntities = new ArrayList<PlantRegProjEntity>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAssertId() {
        return assertId;
    }

    public void setAssertId(String assertId) {
        this.assertId = assertId;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getManfacture() {
        return manfacture;
    }

    public void setManfacture(String manfacture) {
        this.manfacture = manfacture;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
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

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(Long deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getPlantStatus() {
        return plantStatus;
    }

    public void setPlantStatus(String plantStatus) {
        this.plantStatus = plantStatus;
    }

    public List<PlantRegProjEntity> getPlantRegProjEntities() {
        return plantRegProjEntities;
    }

    public void setPlantRegProjEntities(List<PlantRegProjEntity> plantRegProjEntities) {
        this.plantRegProjEntities = plantRegProjEntities;
    }

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
    }

    public ProjMstrEntity getProjMstrEntity() {
        return projMstrEntity;
    }

    public void setProjMstrEntity(ProjMstrEntity projMstrEntity) {
        this.projMstrEntity = projMstrEntity;
    }

    public PlantMstrEntity getPlantClassMstrId() {
        return plantClassMstrId;
    }

    public void setPlantClassMstrId(PlantMstrEntity plantClassMstrId) {
        this.plantClassMstrId = plantClassMstrId;
    }

    public CompanyMstrEntity getCmpId() {
        return cmpId;
    }

    public void setCmpId(CompanyMstrEntity cmpId) {
        this.cmpId = cmpId;
    }

    public CompanyMstrEntity getOwnerCmpId() {
        return ownerCmpId;
    }

    public void setOwnerCmpId(CompanyMstrEntity ownerCmpId) {
        this.ownerCmpId = ownerCmpId;
    }

    public ProcureCatgDtlEntity getProcurecatgId() {
        return procurecatgId;
    }

    public void setProcurecatgId(ProcureCatgDtlEntity procurecatgId) {
        this.procurecatgId = procurecatgId;
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
