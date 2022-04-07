package com.rjtech.projschedule.service.handler;

import com.rjtech.projschedule.dto.ResourceAssignmentDataValueTO;
import com.rjtech.projschedule.model.ResourceAssignmentDataEntity;
import com.rjtech.projschedule.model.ResourceAssignmentDataValueEntity;

public class ResourceAssignmentDataValueHandler {

	public static ResourceAssignmentDataValueTO convertEntityToPOJO(ResourceAssignmentDataValueEntity resourceAssignmentDataValueEntity) {
		ResourceAssignmentDataValueTO resourceAssignmentDataValueTO = new ResourceAssignmentDataValueTO();
		resourceAssignmentDataValueTO.setId(resourceAssignmentDataValueEntity.getId());
		resourceAssignmentDataValueTO.setBudget(resourceAssignmentDataValueEntity.getBudgetUnits());
		resourceAssignmentDataValueTO.setForecastDate(resourceAssignmentDataValueEntity.getForecastDate());
		return resourceAssignmentDataValueTO;
	}
	
	public static ResourceAssignmentDataValueEntity convertPOJOToEntity(ResourceAssignmentDataValueTO resourceAssignmentDataValueTO, ResourceAssignmentDataEntity resourceAssignmentDataEntity) {
		ResourceAssignmentDataValueEntity resourceAssignmentDataValueEntity = new ResourceAssignmentDataValueEntity();
		resourceAssignmentDataValueEntity.setId(resourceAssignmentDataValueTO.getId());
		resourceAssignmentDataValueEntity.setBudgetUnits(resourceAssignmentDataValueTO.getBudget());
		resourceAssignmentDataValueEntity.setForecastDate(resourceAssignmentDataValueTO.getForecastDate());
		resourceAssignmentDataValueEntity.setResourceAssignmentDataEntity(resourceAssignmentDataEntity);
		return resourceAssignmentDataValueEntity;
	}
}
