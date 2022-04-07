package com.rjtech.projschedule.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.rjtech.common.model.UserMstrEntity;

@Entity
@Table(name = "p6_predecessor_successor")
public class ScheduleActivityDataPredecessorSuccessorEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p6_ps_id")
    private Long id;
	
	@NotNull
	@Column(name = "p6_ps_type")
	private String type;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p6_created_by", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "p6_created_on", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p6_updated_by")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "p6_updated_on")
    private Date updatedOn;
	
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p6_ps_activity_id")
    private ScheduleActivityDataEntity scheduleActivityData;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "p6_ps_predecessor_successor_id")
    private ScheduleActivityDataEntity p6_ps_predecessor_successor_id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ScheduleActivityDataEntity getP6_ps_predecessor_successor_id() {
		return p6_ps_predecessor_successor_id;
	}

	public void setP6_ps_predecessor_successor_id(ScheduleActivityDataEntity p6_ps_predecessor_successor_id) {
		this.p6_ps_predecessor_successor_id = p6_ps_predecessor_successor_id;
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

	public ScheduleActivityDataEntity getScheduleActivityData() {
		return scheduleActivityData;
	}

	public void setScheduleActivityData(ScheduleActivityDataEntity scheduleActivityData) {
		this.scheduleActivityData = scheduleActivityData;
	}

	
}
