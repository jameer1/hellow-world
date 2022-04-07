package com.rjtech.centrallib.model;

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
@Table(name = "TANGIBLE_CLASSIFICATION_MSTR")
public class TangibleClassificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TCM_ID")
    private Long id;

    @Column(name = "TCM_CODE")
    private String code;

    @Column(name = "TCM_NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "TCM_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @Column(name = "TCM_IS_ITEM")
    private boolean item;

    @ManyToOne
    @JoinColumn(name = "TCM_MT_ID")
    private MeasurmentMstrEntity measurmentMstrEntity;
    
    @ManyToOne
    @JoinColumn(name = "TCM_PARENT_ID")
    private TangibleClassificationEntity tangibleClassificationEntity;

    @OneToMany(mappedBy = "tangibleClassificationEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TangibleClassificationEntity> childEntities = new ArrayList<TangibleClassificationEntity>();

    @Column(name = "TCM_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "TCM_CREATED_BY",  updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TCM_CREATED_ON" , updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "TCM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TCM_UPDATED_ON")
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

    public MeasurmentMstrEntity getMeasurmentMstrEntity() {
        return measurmentMstrEntity;
    }

    public void setMeasurmentMstrEntity(MeasurmentMstrEntity measurmentMstrEntity) {
        this.measurmentMstrEntity = measurmentMstrEntity;
    }
    
    public TangibleClassificationEntity getTangibleClassificationEntity() {
		return tangibleClassificationEntity;
	}

	public void setTangibleClassificationEntity(TangibleClassificationEntity tangibleClassificationEntity) {
		this.tangibleClassificationEntity = tangibleClassificationEntity;
	}

	public List<TangibleClassificationEntity> getChildEntities() {
		return childEntities;
	}

	public void setChildEntities(List<TangibleClassificationEntity> childEntities) {
		this.childEntities = childEntities;
	}

	public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
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

}
