package com.rjtech.projsettings.req;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rjtech.projsettings.dto.ProjectTangibleTO;

public class ProjectTangibleReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ProjectTangibleTO> projectTangibleTOs = new ArrayList<ProjectTangibleTO>();
	private List<Long> projectIds = new ArrayList<Long>();
	private Date fromDate;
	private Date toDate;
	private boolean showOnlyActuals = false;
	
	public List<ProjectTangibleTO> getProjectTangibleTOs() {
		return projectTangibleTOs;
	}
	public void setProjectTangibleTOs(List<ProjectTangibleTO> projectTangibleTOs) {
		this.projectTangibleTOs = projectTangibleTOs;
	}
	public List<Long> getProjectIds() {
		return projectIds;
	}
	public void setProjectIds(List<Long> projectIds) {
		this.projectIds = projectIds;
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
	public boolean isShowOnlyActuals() {
		return showOnlyActuals;
	}
	public void setShowOnlyActuals(boolean showOnlyActuals) {
		this.showOnlyActuals = showOnlyActuals;
	}

}
