package com.rjtech.projschedule.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.repository.ResourceCurveRepository;
import com.rjtech.projschedule.dto.ResourceCurveMappingTO;
import com.rjtech.projschedule.model.ResourceCurveMappingEntity;

public class ResourceCurveMappingHandler {

	public static List<ResourceCurveMappingEntity> toEntities(List<ResourceCurveMappingTO> resourceCurveMappingTOs, ResourceCurveRepository resourceCurveRepository){
		List<ResourceCurveMappingEntity> resourceCurveMappingEntities = new ArrayList<ResourceCurveMappingEntity>();
		
		for (ResourceCurveMappingTO ResourceCurveMappingTO : resourceCurveMappingTOs) {
			if (ResourceCurveMappingTO.getId() == null)
				resourceCurveMappingEntities.add(new ResourceCurveMappingEntity(resourceCurveRepository.findOne(ResourceCurveMappingTO.getCurveId()), ResourceCurveMappingTO.getResourceReferenceId(), ResourceCurveMappingTO.getResourceReferenceType()));
			else
				resourceCurveMappingEntities.add(new ResourceCurveMappingEntity(ResourceCurveMappingTO.getId(), resourceCurveRepository.findOne(ResourceCurveMappingTO.getCurveId()), ResourceCurveMappingTO.getResourceReferenceId(), ResourceCurveMappingTO.getResourceReferenceType()));
		}
		return resourceCurveMappingEntities;
	}
}
