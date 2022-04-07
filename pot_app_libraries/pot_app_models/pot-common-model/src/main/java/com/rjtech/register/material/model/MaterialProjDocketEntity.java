package com.rjtech.register.material.model;

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

import com.rjtech.centrallib.model.StockMstrEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;
//import com.rjtech.projectlib.model.ProjStoreStockMstrEntityCopy;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;

@Entity
@Table(name = "material_proj_docket")
public class MaterialProjDocketEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MAPD_ID")
    private Long id;

    @Column(name = "MAPD_DOCT_NUM")
    private String docketNum;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MAPD_DOCT_DATE")
    private Date docketDate;

    @ManyToOne
    @JoinColumn(name = "MAPD_FROM_EPM_ID")
    private ProjMstrEntity fromProjId;

    @ManyToOne
    @JoinColumn(name = "MAPD_TO_EPM_ID")
    private ProjMstrEntity toProjId;

    @ManyToOne
    @JoinColumn(name = "MAPD_FROM_SM_ID")
    private StockMstrEntity originStockId;

    @Column(name = "MAPD_FROM_SM_CODE")
    private String originStockCode;

    @ManyToOne
    @JoinColumn(name = "MAPD_FROM_SPM_ID")
    private ProjStoreStockMstrEntity originProjStockId;

    @Column(name = "MAPD_FROM_SPM_CODE")
    private String originProjStockCode;

    @Column(name = "MAPD_FROM_SM_CODE_CATEGORY")
    private String originStockCodeCategory;

    @Column(name = "MAPD_FROM_SPM_CODE_CATEGORY")
    private String originProjStockCodeCategory;

    @ManyToOne
    @JoinColumn(name = "MAPD_TO_SM_ID")
    private StockMstrEntity toStockId;

    @Column(name = "MAPD_TO_SM_CODE")
    private String toStockCode;

    @ManyToOne
    @JoinColumn(name = "MAPD_TO_SPM_ID")
    private ProjStoreStockMstrEntity toProjStockId;

    @Column(name = "MAPD_TO_SPM_CODE")
    private String toProjStockCode;

    @Column(name = "MAPD_TO_SM_CODE_CATEGORY")
    private String toStockCodeCategory;

    @Column(name = "MAPD_TO_SPM_CODE_CATEGORY")
    private String toProjStockCodeCategory;

    @Column(name = "MAPD_IS_COMPLETE")
    private String docketStatus;

    @Column(name = "MAPD_SOURCE_TYPE")
    private String sourceType;

    @Column(name = "MAPD_NOTIFY_ID")
    private Long notifyId;

    @Column(name = "MAPD_NOTIFY_CODE")
    private String notifyCode;

    @Column(name = "MAPD_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "MAPD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MAPD_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "MAPD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "MAPD_UPDATED_ON")
    private Timestamp updatedOn;

    @Column(name = "MAPD_APPR_STATUS")
    private String apprStatus;

    @OneToMany(mappedBy = "materialProjDocketEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MaterialProjDocketSchItemsEntity> materialProjDocketSchItemsEntities = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "MAPD_ISSUE_ERD_ID")
    private EmpRegisterDtlEntity issuedEmpEntity;

    @ManyToOne
    @JoinColumn(name = "MAPD_RECEIVED_ERD_ID")
    private EmpRegisterDtlEntity receivedEmpEntity;

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

    public Date getDocketDate() {
        return docketDate;
    }

    public void setDocketDate(Date docketDate) {
        this.docketDate = docketDate;
    }

    public String getOriginStockCode() {
        return originStockCode;
    }

    public String getToStockCode() {
        return toStockCode;
    }

    public String getToProjStockCode() {
        return toProjStockCode;
    }

    public void setOriginStockCode(String originStockCode) {
        this.originStockCode = originStockCode;
    }

    public void setOriginProjStockCode(String originProjStockCode) {
        this.originProjStockCode = originProjStockCode;
    }

    public void setToStockCode(String toStockCode) {
        this.toStockCode = toStockCode;
    }

    public void setToProjStockCode(String toProjStockCode) {
        this.toProjStockCode = toProjStockCode;
    }

    public Long getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(Long notifyId) {
        this.notifyId = notifyId;
    }

    public String getNotifyCode() {
        return notifyCode;
    }

    public void setNotifyCode(String notifyCode) {
        this.notifyCode = notifyCode;
    }

    public List<MaterialProjDocketSchItemsEntity> getMaterialProjDocketSchItemsEntities() {
        return materialProjDocketSchItemsEntities;
    }

    public void setMaterialProjDocketSchItemsEntities(
            List<MaterialProjDocketSchItemsEntity> materialProjDocketSchItemsEntities) {
        this.materialProjDocketSchItemsEntities = materialProjDocketSchItemsEntities;
    }

    public String getDocketStatus() {
        return docketStatus;
    }

    public void setDocketStatus(String docketStatus) {
        this.docketStatus = docketStatus;
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

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public EmpRegisterDtlEntity getIssuedEmpEntity() {
        return issuedEmpEntity;
    }

    public void setIssuedEmpEntity(EmpRegisterDtlEntity issuedEmpEntity) {
        this.issuedEmpEntity = issuedEmpEntity;
    }

    public EmpRegisterDtlEntity getReceivedEmpEntity() {
        return receivedEmpEntity;
    }

    public void setReceivedEmpEntity(EmpRegisterDtlEntity receivedEmpEntity) {
        this.receivedEmpEntity = receivedEmpEntity;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getOriginStockCodeCategory() {
        return originStockCodeCategory;
    }

    public String getOriginProjStockCodeCategory() {
        return originProjStockCodeCategory;
    }

    public String getToStockCodeCategory() {
        return toStockCodeCategory;
    }

    public String getToProjStockCodeCategory() {
        return toProjStockCodeCategory;
    }

    public void setOriginStockCodeCategory(String originStockCodeCategory) {
        this.originStockCodeCategory = originStockCodeCategory;
    }

    public void setOriginProjStockCodeCategory(String originProjStockCodeCategory) {
        this.originProjStockCodeCategory = originProjStockCodeCategory;
    }

    public void setToStockCodeCategory(String toStockCodeCategory) {
        this.toStockCodeCategory = toStockCodeCategory;
    }

    public void setToProjStockCodeCategory(String toProjStockCodeCategory) {
        this.toProjStockCodeCategory = toProjStockCodeCategory;
    }

    public ProjMstrEntity getFromProjId() {
        return fromProjId;
    }

    public void setFromProjId(ProjMstrEntity fromProjId) {
        this.fromProjId = fromProjId;
    }

    public ProjMstrEntity getToProjId() {
        return toProjId;
    }

    public void setToProjId(ProjMstrEntity toProjId) {
        this.toProjId = toProjId;
    }

    public StockMstrEntity getOriginStockId() {
        return originStockId;
    }

    public void setOriginStockId(StockMstrEntity originStockId) {
        this.originStockId = originStockId;
    }

    public ProjStoreStockMstrEntity getOriginProjStockId() {
        return originProjStockId;
    }

    public void setOriginProjStockId(ProjStoreStockMstrEntity originProjStockId) {
        this.originProjStockId = originProjStockId;
    }

    public StockMstrEntity getToStockId() {
        return toStockId;
    }

    public void setToStockId(StockMstrEntity toStockId) {
        this.toStockId = toStockId;
    }

    public ProjStoreStockMstrEntity getToProjStockId() {
        return toProjStockId;
    }

    public void setToProjStockId(ProjStoreStockMstrEntity toProjStockId) {
        this.toProjStockId = toProjStockId;
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

    public String getOriginProjStockCode() {
        return originProjStockCode;
    }

}
