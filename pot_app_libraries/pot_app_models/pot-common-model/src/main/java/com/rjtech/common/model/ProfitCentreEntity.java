package com.rjtech.common.model;

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

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;

@Entity
@Table(name = "finance_profit_centre")
public class ProfitCentreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FPC_ID")
    private Long id;

    @Column(name = "FPC_CODE")
    private String code;

    @Column(name = "FPC_NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "FPC_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @Column(name = "FPC_IS_ITEM")
    private boolean item;

    @Column(name = "FPC_IS_ITEM_PARENT")
    private boolean itemParent = false;

    @ManyToOne
    @JoinColumn(name = "FPC_PARENT_ID")
    private ProfitCentreEntity profitCentreEntity;

    @OneToMany(mappedBy = "profitCentreEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProfitCentreEntity> childEntities = new ArrayList<ProfitCentreEntity>();

    @Column(name = "FPC_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "FPC_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FPC_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "FPC_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FPC_UPDATED_ON")
    private Date updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isItem() {
        return item;
    }

    public void setItem(boolean item) {
        this.item = item;
    }

    public boolean isItemParent() {
        return itemParent;
    }

    public void setItemParent(boolean itemParent) {
        this.itemParent = itemParent;
    }

    public ProfitCentreEntity getProfitCentreEntity() {
        return profitCentreEntity;
    }

    public void setProfitCentreEntity(ProfitCentreEntity profitCentreEntity) {
        this.profitCentreEntity = profitCentreEntity;
    }

    public List<ProfitCentreEntity> getChildEntities() {
        return childEntities;
    }

    public void setChildEntities(List<ProfitCentreEntity> childEntities) {
        this.childEntities = childEntities;
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

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
    }
}
