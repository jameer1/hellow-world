package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.projsettings.dto.ProjectTangibleTO;
import java.math.BigDecimal;

public class ProjectTangibleResp extends AppResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<ProjectTangibleTO> projectTangibleTOs = new ArrayList<ProjectTangibleTO>();

	public List<ProjectTangibleTO> getProjectTangibleTOs() {
		return projectTangibleTOs;
	}

	public void setProjectTangibleTOs(List<ProjectTangibleTO> projectTangibleTOs) {
		this.projectTangibleTOs = projectTangibleTOs;
	}
	
	public void upsert(ProjectTangibleTO projectTangibleTO) {
		boolean found = false;
		for (ProjectTangibleTO TO : projectTangibleTOs) {
			if (TO.getParentProjId().equals(projectTangibleTO.getParentProjId()) && TO.getProjId().equals(projectTangibleTO.getProjId()) 
					&& TO.getSowId().equals(projectTangibleTO.getSowId()) && TO.getTangibleItemId().equals(projectTangibleTO.getTangibleItemId()) && (TO.getDate() == projectTangibleTO.getDate())) {
				TO.setActualHours(TO.getActualHours().add(projectTangibleTO.getActualHours()));
				TO.setActualQuantity(TO.getActualQuantity().add(projectTangibleTO.getActualQuantity()));
				found = true;
			}
		}
		if (!found) {
			if(projectTangibleTO.getActualQuantity() != BigDecimal.valueOf(0)) {
			projectTangibleTOs.add(projectTangibleTO);
			}
		}
	}
}
