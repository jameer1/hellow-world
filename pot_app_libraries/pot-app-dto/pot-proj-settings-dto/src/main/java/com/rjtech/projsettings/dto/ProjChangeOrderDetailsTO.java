package com.rjtech.projsettings.dto;

import java.sql.Time;

import com.rjtech.common.dto.ProjectTO;

public class ProjChangeOrderDetailsTO  extends ProjectTO{
	
	  private static final long serialVersionUID = -2524052864474680149L;
	    private Long id;
	    private Integer cutOffDays;
	    private Time cutOffTime;
	    private Integer cutOffHours;
	    private Integer cutOffMinutes;
	    private String type;
	    private String defaultStatus;
	    private Integer status;
	    private Long apprTypeId;
	  
		public String getDefaultStatus() {
			return defaultStatus;
		}
		public void setDefaultStatus(String defaultStatus) {
			this.defaultStatus = defaultStatus;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public Long getApprTypeId() {
			return apprTypeId;
		}
		public void setApprTypeId(Long apprTypeId) {
			this.apprTypeId = apprTypeId;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Integer getCutOffDays() {
			return cutOffDays;
		}
		public void setCutOffDays(Integer cutOffDays) {
			this.cutOffDays = cutOffDays;
		}
		public Time getCutOffTime() {
			return cutOffTime;
		}
		public void setCutOffTime(Time cutOffTime) {
			this.cutOffTime = cutOffTime;
		}
		public Integer getCutOffHours() {
			return cutOffHours;
		}
		public void setCutOffHours(Integer cutOffHours) {
			this.cutOffHours = cutOffHours;
		}
		public Integer getCutOffMinutes() {
			return cutOffMinutes;
		}
		public void setCutOffMinutes(Integer cutOffMinutes) {
			this.cutOffMinutes = cutOffMinutes;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	
	

}
