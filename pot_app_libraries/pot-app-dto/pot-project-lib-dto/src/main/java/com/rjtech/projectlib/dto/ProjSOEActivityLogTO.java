package com.rjtech.projectlib.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.rjtech.common.dto.ProjectTO;

public class ProjSOEActivityLogTO extends ProjectTO {
    private static final long serialVersionUID = 2950084862079755848L;

    private Long activityId;
    private Long fromUserId;
    private String fromUserDisplayName;
    private Long toUserId;
    private String toUserDisplayName;
    private String description;
    private String activityType;
    private Date createdOn;
	private Long soeId;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }
    
    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId( Long fromUserId ) {
        this.fromUserId = fromUserId;
    }
    
    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId( Long toUserId ) {
        this.toUserId = toUserId;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }
    
    public String getActivityType() {
        return activityType;
    }

    public void setActivityType( String activityType ) {
        this.activityType = activityType;
    }
    
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn( Date createdOn ) {
        this.createdOn = createdOn;
    }
    
    public String getFromUserDisplayName() {
        return fromUserDisplayName;
    }

    public void setFromUserDisplayName( String fromUserDisplayName ) {
        this.fromUserDisplayName = fromUserDisplayName;
    }
    
    public String getToUserDisplayName() {
        return toUserDisplayName;
    }

    public void setToUserDisplayName( String toUserDisplayName ) {
        this.toUserDisplayName = toUserDisplayName;
    }
	
	 public Long getSoeId() {
        return soeId;
    }

    public void setSoeId(Long soeId) {
        this.soeId = soeId;
    }
    
    public String toString() {
    	return "fromUserId:"+fromUserId+" toUserId:"+toUserId+" activityType:"+activityType;
    }
}
