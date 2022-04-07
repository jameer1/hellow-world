package com.rjtech.projectlib.model;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PROJ_MILESTONE")
public class ProjPMCMItemEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PM_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PM_CRM_ID", referencedColumnName = "CRM_ID", nullable = true)
    private ClientRegEntity clientRegEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PM_EPM_ID")
    private ProjMstrEntity projMstrEntity;

    @Column(name = "PM_GRP_ID")
    private String code;

    @Column(name = "PM_GRP_NAME")
    private String name;

    @Column(name = "PM_IS_ITEM")
    private Integer item;

    @ManyToOne
    @JoinColumn(name = "PM_PARENT_ID")
    private ProjPMCMItemEntity projPMCMItemEntity;

    @OneToMany(mappedBy = "projPMCMItemEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProjPMCMItemEntity> childEntities = new ArrayList<ProjPMCMItemEntity>();

    @Column(name = "PM_COMMENTS")
    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PM_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PM_UPDATED_ON")
    private Date updatedOn;

    public ProjPMCMItemEntity() {
    }

    @Column(name = "PM_COST_CODE_ID")
    private String pmCostCodeId;

    @Column(name = "PM_CURRENCY")
    private String pmCurrency;

    @Column(name = "PM_CONTRACT_AMOUNT")
    private Long pmContractAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PM_SCHEDULE_FINISH_DATE", updatable = false)
    private Date pmSchedFinishDate;

    @Column(name = "PM_PROGRESS_STATUS")
    private Integer pmProgressStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PM_ACTUAL_FINISH_DATE", updatable = false)
    private Date pmActualFinishDate;

    @Column(name = "PM_PREV_PROG_CLAIM")
    private Long pmPrevProgClaim;

    @Column(name = "PM_CLAIMED_AMOUNT")
    private Long pmClaimedAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PM_STATUS_DATE", updatable = false)
    private Date pmStatusDate;
    
    @Column(name ="PM_STATUS")
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientRegEntity getClientRegEntity() {
        return clientRegEntity;
    }

    public void setClientRegEntity(ClientRegEntity clientRegEntity) {
        this.clientRegEntity = clientRegEntity;
    }

    public ProjMstrEntity getProjMstrEntity() {
        return projMstrEntity;
    }

    public void setProjMstrEntity(ProjMstrEntity projMstrEntity) {
        this.projMstrEntity = projMstrEntity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

    public ProjPMCMItemEntity getProjPMCMItemEntity() {
        return projPMCMItemEntity;
    }

    public void setProjPMCMItemEntity(ProjPMCMItemEntity projPMCMItemEntity) {
        this.projPMCMItemEntity = projPMCMItemEntity;
    }

    public List<ProjPMCMItemEntity> getChildEntities() {
        return childEntities;
    }

    public void setChildEntities(List<ProjPMCMItemEntity> childEntities) {
        this.childEntities = childEntities;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public String getPmCostCodeId() {
        return pmCostCodeId;
    }

    public void setPmCostCodeId(String pmCostCodeId) {
        this.pmCostCodeId = pmCostCodeId;
    }

    public String getPmCurrency() {
        return pmCurrency;
    }

    public void setPmCurrency(String pmCurrency) {
        this.pmCurrency = pmCurrency;
    }

    public Long getPmContractAmount() {
        return pmContractAmount;
    }

    public void setPmContractAmount(Long pmContractAmount) {
        this.pmContractAmount = pmContractAmount;
    }

    public Date getPmSchedFinishDate() {
        return pmSchedFinishDate;
    }

    public void setPmSchedFinishDate(Date pmSchedFinishDate) {
        this.pmSchedFinishDate = pmSchedFinishDate;
    }

    public Integer getPmProgressStatus() {
        return pmProgressStatus;
    }

    public void setPmProgressStatus(Integer pmProgressStatus) {
        this.pmProgressStatus = pmProgressStatus;
    }

    public Date getPmActualFinishDate() {
        return pmActualFinishDate;
    }

    public void setPmActualFinishDate(Date pmActualFinishDate) {
        this.pmActualFinishDate = pmActualFinishDate;
    }

    public Long getPmClaimedAmount() {
        return pmClaimedAmount;
    }

    public void setPmClaimedAmount(Long pmClaimedAmount) {
        this.pmClaimedAmount = pmClaimedAmount;
    }

    public Date getPmStatusDate() {
        return pmStatusDate;
    }

    public void setPmStatusDate(Date pmStatusDate) {
        this.pmStatusDate = pmStatusDate;
    }

    public Long getPmPrevProgClaim() {
        return pmPrevProgClaim;
    }

    public void setPmPrevProgClaim(Long pmPrevProgClaim) {
        this.pmPrevProgClaim = pmPrevProgClaim;
    }
    
    public Integer getStatus() {
    	return status;
    }
    
    public void setStatus(Integer status) {
    	this.status = status;
    }
}