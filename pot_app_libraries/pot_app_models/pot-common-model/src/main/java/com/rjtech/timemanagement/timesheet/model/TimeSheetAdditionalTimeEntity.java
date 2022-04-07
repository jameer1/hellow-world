package com.rjtech.timemanagement.timesheet.model;

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
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
//import com.rjtech.projectlib.model.ProjCrewMstrEntityCopy;

@Entity
@Table(name = "TIME_SHEET_ADDT_TIME")
public class TimeSheetAdditionalTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAT_ID")
    private Long id;

    @Column(name = "TAT_FROM_DATE")
    private Date fromDate;

    @Column(name = "TAT_TO_DATE")
    private Date toDate;

    @ManyToOne
    @JoinColumn(name = "TAT_APPR_USER")
    private UserMstrEntity apprUser;

    @Column(name = "TAT_STATUS")
    private String status;

    @Column(name = "TAT_TYPE")
    private String type;

    @ManyToOne
    @JoinColumn(name = "TAT_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TAT_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "TAT_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TAT_UPDATED_ON")
    private Date updatedOn;

    @Column(name = "TAT_GRANT_HOURS")
    private Integer grantHrs;
    
    @ManyToOne
    @JoinColumn(name = "TAT_CRW_ID")
    private ProjCrewMstrEntity projCrewMstrEntity;
    
    @Column(name = "TAT_NOTIFICATION_STATUS")
    private String notificationStatus;

    @Column(name = "TAT_TSM_ID")
    private Long timeSheetId;
    
    @Column(name = "TAT_EPM_ID")
    private Long projId;
    
    @Column(name = "TAT_ERD_ID")
    private Long empId;
    
    @Column(name = "TAT_NOTIFICATION_MSG")
    private String notificationMsg;
    
    public String getNotificationMsg() {
		return notificationMsg;
	}

	public void setNotificationMsg(String notificationMsg) {
		this.notificationMsg = notificationMsg;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public Long getProjId() {
		return projId;
	}

	public void setProjId(Long projId) {
		this.projId = projId;
	}

	public String getNotificationStatus() {
		return notificationStatus;
	}

	public void setNotificationStatus(String notificationStatus) {
		this.notificationStatus = notificationStatus;
	}

	public Long getTimeSheetId() {
		return timeSheetId;
	}

	public void setTimeSheetId(Long timeSheetId) {
		this.timeSheetId = timeSheetId;
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

    public UserMstrEntity getApprUser() {
        return apprUser;
    }

    public void setApprUser(UserMstrEntity apprUser) {
        this.apprUser = apprUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Integer getGrantHrs() {
        return grantHrs;
    }

    public void setGrantHrs(Integer grantHrs) {
        this.grantHrs = grantHrs;
    }

    public ProjCrewMstrEntity getProjCrewMstrEntity() {
        return projCrewMstrEntity;
    }

    public void setProjCrewMstrEntity(ProjCrewMstrEntity projCrewMstrEntity) {
        this.projCrewMstrEntity = projCrewMstrEntity;
    }
    
}
