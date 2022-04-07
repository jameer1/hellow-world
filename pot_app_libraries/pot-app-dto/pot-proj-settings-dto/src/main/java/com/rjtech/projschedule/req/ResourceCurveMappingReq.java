package com.rjtech.projschedule.req;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rjtech.projschedule.dto.ResourceCurveMappingTO;


public class ResourceCurveMappingReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<ResourceCurveMappingTO> resourceCurveMappingTOs = new ArrayList<ResourceCurveMappingTO>();

	public List<ResourceCurveMappingTO> getResourceCurveMappingTOs() {
		return resourceCurveMappingTOs;
	}

	public void setResourceCurveMappingTOs(List<ResourceCurveMappingTO> resourceCurveMappingTOs) {
		this.resourceCurveMappingTOs = resourceCurveMappingTOs;
	}
}
