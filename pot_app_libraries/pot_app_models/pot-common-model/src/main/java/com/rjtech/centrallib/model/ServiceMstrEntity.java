package com.rjtech.centrallib.model;

import java.io.Serializable;
import javax.persistence.*;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;
import java.util.Date;

/**
 * The persistent class for the service_classification_mstr database table.
 * 
 */
@Entity
@Table(name = "service_classification_mstr")
public class ServiceMstrEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCM_ID")
    private Long id;

    @Column(name = "SCM_CODE")
    private String code;

    @ManyToOne
    @JoinColumn(name = "SCM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SCM_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "SCM_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @Column(name = "SCM_DESC")
    private String name;

    @Column(name = "SCM_STATUS")
    private Integer status;


    @ManyToOne
    @JoinColumn(name = "SCM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SCM_UPDATED_ON")
    private Date updatedOn;
    
    @Column(name = "SCM_PROC_SUBCAT_CODE")
    private String procSubcatCode;
    
    @Column(name = "SCM_PROC_SUBCAT_NAME")
    private String procSubcatName;
    
    @Column(name = "SCM_PROC_SUBCAT_ID")
    private Integer procSubcatId;

    public ServiceMstrEntity() {
    }

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

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
    }

    public UserMstrEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserMstrEntity updatedBy) {
        this.updatedBy = updatedBy;
    }

	public String getProcSubcatCode() {
		return procSubcatCode;
	}

	public void setProcSubcatCode(String procSubcatCode) {
		this.procSubcatCode = procSubcatCode;
	}

	public String getProcSubcatName() {
		return procSubcatName;
	}

	public void setProcSubcatName(String procSubcatName) {
		this.procSubcatName = procSubcatName;
	}

	public Integer getProcSubcatId() {
		return procSubcatId;
	}

	public void setProcSubcatId(Integer procSubcatId) {
		this.procSubcatId = procSubcatId;
	}
    
    

}