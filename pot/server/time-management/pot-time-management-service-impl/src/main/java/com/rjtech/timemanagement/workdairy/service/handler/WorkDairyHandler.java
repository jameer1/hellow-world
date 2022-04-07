package com.rjtech.timemanagement.workdairy.service.handler;

import com.rjtech.centrallib.model.WeatherMstrEntity;
import com.rjtech.centrallib.repository.WeatherRepository;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.procurement.model.PurchaseOrderEntity;
//import com.rjtech.procurement.model.PurchaseOrderEntityCopy;
import com.rjtech.procurement.repository.PurchaseOrderRepositoryCopy;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
//import com.rjtech.projectlib.model.ProjCrewMstrEntityCopy;
import com.rjtech.projectlib.model.ProjWorkShiftMstrEntity;
//import com.rjtech.projectlib.model.ProjWorkShiftMstrEntityCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjCrewRepositoryCopy;
import com.rjtech.projectlib.repository.ProjWorkShiftRepositoryCopy;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyTO;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEntity;
import com.rjtech.common.model.UserProjectsEntity;
import java.util.List;


public class WorkDairyHandler {

	public static WorkDairyTO convertEntityToPOJO1(WorkDairyEntity workDairyEntity,List<Long> userProjectsEntities) {
		WorkDairyTO workDairyTO = new WorkDairyTO();
		workDairyTO.setId(workDairyEntity.getId());
		workDairyTO.setWorkDiaryCode(generateCode(workDairyEntity));
		workDairyTO.setWorkDairyDate(CommonUtil.convertDateToString(workDairyEntity.getWorkDairyDate()));
		workDairyTO.setCreatedBy(workDairyEntity.getCreatedBy().getDisplayName());
		ProjMstrEntity projMstrEntity = workDairyEntity.getProjId();
		if (null != projMstrEntity) {
			workDairyTO.setProjId(projMstrEntity.getProjectId());
			workDairyTO.setCode(projMstrEntity.getCode());
			workDairyTO.setParentCode(projMstrEntity.getParentProjectMstrEntity().getCode());
			workDairyTO.setName(projMstrEntity.getProjName());
			workDairyTO.setParentName(projMstrEntity.getParentProjectMstrEntity().getProjName());
			workDairyTO.setParentProjId(projMstrEntity.getParentProjectMstrEntity().getProjectId());
		}
		ProjCrewMstrEntity crew = workDairyEntity.getCrewId();
		if (crew != null) {
			workDairyTO.setCrewId(crew.getId());
			workDairyTO.setCrewCode(crew.getCode());
		}
		if(userProjectsEntities != null) {
			for(int i=0;i<userProjectsEntities.size();i++) {
				if(userProjectsEntities.get(i) != workDairyEntity.getProjId().getProjectId()) {
					workDairyTO.setTimeFlag(true);
				}			
			}
		}
		if(userProjectsEntities != null) {
			for(int i=0;i<userProjectsEntities.size();i++) {
				if(userProjectsEntities.get(i).equals(workDairyEntity.getProjId().getProjectId())) {
					workDairyTO.setTimeFlag(false);
				}
			}
			
		}

		WeatherMstrEntity weather = workDairyEntity.getWeatherId();
		if (weather != null) {
			workDairyTO.setWeatherId(weather.getId());
			workDairyTO.setWeatherCode(weather.getCode());
		}
		ProjWorkShiftMstrEntity shift = workDairyEntity.getShiftId();
		if (shift != null) {
			workDairyTO.setShiftId(shift.getId());
			workDairyTO.setShiftCode(shift.getCode());
		}
		workDairyTO.setContractType(workDairyEntity.getContractType());
		workDairyTO.setContractNo(workDairyEntity.getPurCode());
		PurchaseOrderEntity purchseOrder = workDairyEntity.getPurId();
		if (purchseOrder != null)
			workDairyTO.setPurId(purchseOrder.getId());
		if(workDairyEntity.getApprStatus() != null) {
		  if(workDairyEntity.getApprStatus().equals("SubmittedForApproval")) {
				workDairyTO.setApprStatus("Submitted For Approval");
			}else
			if(workDairyEntity.getApprStatus().equals("SubmittedForClientApproval")) {
				workDairyTO.setApprStatus("Submitted For Client Approval");
			}else
				 workDairyTO.setApprStatus(workDairyEntity.getApprStatus());
		}
		
		UserMstrEntity requester = workDairyEntity.getReqUserId();
		if (requester != null)
			workDairyTO.setReqUserId(requester.getUserId());

		workDairyTO.setClientApproval(workDairyEntity.isClientApproval());
		UserMstrEntity internalAppr = workDairyEntity.getInternalApprUserId();
		if (internalAppr != null) {
			workDairyTO.setInternalApprUserId(internalAppr.getUserId());
			workDairyTO.setInternalApprBy(internalAppr.getDisplayName());
		}

		UserMstrEntity externalAppr = workDairyEntity.getClientApprUserId();
		if (externalAppr != null) {
			workDairyTO.setClientApprUserId(externalAppr.getUserId());
			workDairyTO.setClientApprBy(externalAppr.getDisplayName());
		}
		workDairyTO.setStatus(workDairyEntity.getStatus());

		return workDairyTO;
	}
	 
    
	
	public static WorkDairyTO convertEntityToPOJO(WorkDairyEntity workDairyEntity) {
		WorkDairyTO workDairyTO = new WorkDairyTO();
		workDairyTO.setId(workDairyEntity.getId());
		workDairyTO.setWorkDiaryCode(generateCode(workDairyEntity));
		workDairyTO.setWorkDairyDate(CommonUtil.convertDateToString(workDairyEntity.getWorkDairyDate()));
		workDairyTO.setCreatedBy(workDairyEntity.getCreatedBy().getDisplayName());
		ProjMstrEntity projMstrEntity = workDairyEntity.getProjId();
		if (null != projMstrEntity) {
			workDairyTO.setProjId(projMstrEntity.getProjectId());
			workDairyTO.setCode(projMstrEntity.getCode());
			workDairyTO.setParentCode(projMstrEntity.getParentProjectMstrEntity().getCode());
			workDairyTO.setName(projMstrEntity.getProjName());
			workDairyTO.setParentName(projMstrEntity.getParentProjectMstrEntity().getProjName());
			workDairyTO.setParentProjId(projMstrEntity.getParentProjectMstrEntity().getProjectId());
		}
		ProjCrewMstrEntity crew = workDairyEntity.getCrewId();
		if (crew != null) {
			workDairyTO.setCrewId(crew.getId());
			workDairyTO.setCrewCode(crew.getCode());
		}
		WeatherMstrEntity weather = workDairyEntity.getWeatherId();
		if (weather != null) {
			workDairyTO.setWeatherId(weather.getId());
			workDairyTO.setWeatherCode(weather.getCode());
		}
		ProjWorkShiftMstrEntity shift = workDairyEntity.getShiftId();
		if (shift != null) {
			workDairyTO.setShiftId(shift.getId());
			workDairyTO.setShiftCode(shift.getCode());
		}
		workDairyTO.setContractType(workDairyEntity.getContractType());
		workDairyTO.setContractNo(workDairyEntity.getPurCode());
		PurchaseOrderEntity purchseOrder = workDairyEntity.getPurId();
		if (purchseOrder != null)
			workDairyTO.setPurId(purchseOrder.getId());
		workDairyTO.setApprStatus(workDairyEntity.getApprStatus());
		UserMstrEntity requester = workDairyEntity.getReqUserId();
		if (requester != null)
			workDairyTO.setReqUserId(requester.getUserId());

		workDairyTO.setClientApproval(workDairyEntity.isClientApproval());
		UserMstrEntity internalAppr = workDairyEntity.getInternalApprUserId();
		if (internalAppr != null) {
			workDairyTO.setInternalApprUserId(internalAppr.getUserId());
			workDairyTO.setInternalApprBy(internalAppr.getDisplayName());
		}

		UserMstrEntity externalAppr = workDairyEntity.getClientApprUserId();
		if (externalAppr != null) {
			workDairyTO.setClientApprUserId(externalAppr.getUserId());
			workDairyTO.setClientApprBy(externalAppr.getDisplayName());
		}
		workDairyTO.setStatus(workDairyEntity.getStatus());

		return workDairyTO;
	}

	public static WorkDairyEntity convertPOJOToEntity(WorkDairyTO workDairyTO, EPSProjRepository epsProjRepository,
			ProjCrewRepositoryCopy projCrewRepository, ProjWorkShiftRepositoryCopy projWorkShiftRepository,
			WeatherRepository weatherRepository, PurchaseOrderRepositoryCopy purchaseOrderRepository,
			LoginRepository loginRepository) {
		WorkDairyEntity workDairyEntity = new WorkDairyEntity();
		if (CommonUtil.isNonBlankLong(workDairyTO.getId())) {
			workDairyEntity.setId(workDairyTO.getId());

		}
		workDairyEntity.setCode(workDairyTO.getCode());
		workDairyEntity.setWorkDairyDate(CommonUtil.convertStringToDate(workDairyTO.getWorkDairyDate()));
		workDairyEntity.setProjId(epsProjRepository.findOne(workDairyTO.getProjId()));
		workDairyEntity.setCrewId(projCrewRepository.findOne(workDairyTO.getCrewId()));
		workDairyEntity.setWeatherId(weatherRepository.findOne(workDairyTO.getWeatherId()));
		workDairyEntity.setShiftId(projWorkShiftRepository.findOne(workDairyTO.getShiftId()));
		workDairyEntity.setContractType(workDairyTO.getContractType());
		workDairyEntity.setPurCode(workDairyTO.getContractNo());
		if (workDairyTO.getPurId() != null)
			workDairyEntity.setPurId(purchaseOrderRepository.findOne(workDairyTO.getPurId()));
		workDairyEntity.setApprStatus(workDairyTO.getApprStatus());
		workDairyEntity.setReqUserId(loginRepository.findOne(AppUserUtils.getUserId()));
		workDairyEntity.setClientApproval(workDairyTO.isClientApproval());
		if (workDairyTO.isClientApproval() && workDairyTO.getClientApprUserId() != null)
			workDairyEntity.setClientApprUserId(loginRepository.findOne(workDairyTO.getClientApprUserId()));
		if (workDairyTO.getInternalApprUserId() != null)
			workDairyEntity.setInternalApprUserId(loginRepository.findOne(workDairyTO.getInternalApprUserId()));
		workDairyEntity.setStatus(workDairyTO.getStatus());

		return workDairyEntity;
	}

	public static String generateCode(WorkDairyEntity workDairyEntity) {
		return "WD-" + workDairyEntity.getProjId().getCode() + "-" + workDairyEntity.getCrewId().getCode() + "-"
				+ workDairyEntity.getId();
	}

}
