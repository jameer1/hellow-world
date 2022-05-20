package com.rjtech.projectlib.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.dto.ChangeOrderTO;
import com.rjtech.projectlib.dto.CoMaterialTO;
import com.rjtech.projectlib.dto.CoProjCostTO;
import com.rjtech.projectlib.dto.CoProjManpowerTO;
import com.rjtech.projectlib.dto.CoProjPlantTO;
import com.rjtech.projectlib.dto.CoProjSOWTO;
import com.rjtech.projectlib.model.ChangeOrderMstrEntity;
import com.rjtech.projectlib.model.ChangeOrderSOWEntity;
import com.rjtech.projectlib.repository.COProjectPlantsRepository;
import com.rjtech.projectlib.repository.ChangeOrderMapRepository;
import com.rjtech.projectlib.repository.ChangeOrderRepository;
import com.rjtech.projectlib.repository.ChangeOrderSOWRepository;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.req.ChangeOrderReq;
import com.rjtech.projectlib.resp.ChangeOrderResp;
import com.rjtech.projectlib.service.ChangeOrderDtlService;
import com.rjtech.projectlib.service.handler.ProjLibServiceHandler;
import com.rjtech.projschedule.repository.ProjManpowerRepositoryCopy;
import com.rjtech.projsettings.model.COProjCostBudgetEntity;
import com.rjtech.projsettings.model.COProjManpowerEntity;
import com.rjtech.projsettings.model.COProjectMaterialBudgetEntity;
import com.rjtech.projsettings.model.COProjectPlantsDtlEntity;
import com.rjtech.projsettings.repository.COProjCostBudgetRepository;
import com.rjtech.projsettings.repository.COProjManpowerRepository;
import com.rjtech.projsettings.repository.COProjectMaterialRepository;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

@Service
public class ChangeOrderDtlServiceImpl implements ChangeOrderDtlService{

	
	
	@Autowired
	private ProjManpowerRepositoryCopy projManpowerRepositoryCopy;
	
	@Autowired
	private ChangeOrderRepository changeOrderMstrRepository;

	@Autowired
	private ChangeOrderMapRepository coMstrMapRepository;
	
	@Autowired
	private EPSProjRepository projRepository;
	
	@Autowired
	private ChangeOrderSOWRepository changeOrderSOWRepository;
	@Autowired
	private COProjCostBudgetRepository coProjCostBudgetRepository;
	@Autowired
	private COProjectMaterialRepository cpmRepository;
	
	@Autowired
	private COProjManpowerRepository pmpRepository;
	
	@Autowired
	private COProjectPlantsRepository coProjPlantsRepository;

	/*
	 * public ProjSOEItemResp projSOEApproval( ProjSOEItemGetReq projSOEItemGetReq )
	 * {
	 * 
	 * }
	 */
	// This function is to save the change order details
	@Override
	public ChangeOrderResp saveChangeOrderDetails(ChangeOrderReq changeOrderReq) {
		ChangeOrderResp changeOrderResp = new ChangeOrderResp();
		ChangeOrderMstrEntity changeOrderMstrEntity = null;
		System.out.println("saveChangeOrderDetails function from ProjLibServiceImpl class");
		for (ChangeOrderTO changeOrderTO : changeOrderReq.getChangeOrderTOs()) {
			System.out.println(changeOrderTO);
			changeOrderMstrEntity = new ChangeOrderMstrEntity();
			ProjMstrEntity projMstrEntity = new ProjMstrEntity();
			projMstrEntity.setProjectId(changeOrderTO.getProjId());
			UserMstrEntity userMstrEntity = new UserMstrEntity();
			userMstrEntity.setUserId(AppUserUtils.getUserId());
			changeOrderMstrEntity.setContractType(changeOrderTO.getContractType());
			changeOrderMstrEntity.setCode(generateChangeOrderCode(changeOrderTO.getProjId(), 0L));
			changeOrderMstrEntity.setProjectId(projMstrEntity);
			changeOrderMstrEntity.setDescription(changeOrderTO.getDescription());
			changeOrderMstrEntity.setCreatedBy(userMstrEntity);
			changeOrderMstrEntity.setApprovalStatus("DRAFT");
			changeOrderMstrEntity.setReasonForChange(changeOrderTO.getReasonForChange());
			changeOrderMstrEntity.setStatus(StatusCodes.ACTIVE.getValue());
			changeOrderMstrRepository.save(changeOrderMstrEntity);
			changeOrderTO.setId(changeOrderMstrEntity.getId());
			changeOrderResp.getChangeOrderTOs().add(changeOrderTO);
		}
		return changeOrderResp;
	}

	// This function is to fetch the change order details
	@Override
	public ChangeOrderResp getChangeOrderDetails(ChangeOrderReq changeOrderReq) {
		ChangeOrderResp changeOrderResp = new ChangeOrderResp();
		ChangeOrderTO changeOrderTO = null;
		ChangeOrderMstrEntity coMstrEntity = null;
		List<ChangeOrderMstrEntity> changeOrderMstrEntities = new ArrayList<ChangeOrderMstrEntity>();
		List<Long> projIds = new ArrayList<>();
		if (changeOrderReq.getChangeOrderTOs().size() != 0) {
			System.out.println("If condition");
			for (ChangeOrderTO coTo : changeOrderReq.getChangeOrderTOs()) {
				projIds.add(coTo.getProjId());
			}
			changeOrderMstrEntities = changeOrderMstrRepository.findCoDetailsByProjIds(projIds);
		} else {
			System.out.println("else condition");
			changeOrderMstrEntities = changeOrderMstrRepository.findAllProjsCoDetails();
		}
		System.out.println(projIds.size());
		System.out.println(changeOrderMstrEntities.size());
		if (changeOrderMstrEntities.size() > 0) {
			for (ChangeOrderMstrEntity changeOrderMstrEntity : changeOrderMstrEntities) {
				System.out.println(changeOrderMstrEntity);
				changeOrderResp.getChangeOrderTOs().add(ProjLibServiceHandler.convertChangeOrderEntityToPOJO(
						changeOrderMstrEntity, coMstrMapRepository, projManpowerRepositoryCopy));
			}
			System.out.println("changeorderresp size:" + changeOrderResp.getChangeOrderTOs().size());
			System.out.println("changeorderresp manpower size:" + changeOrderResp.getCoProjManpowerTOs().size());
		}
		return changeOrderResp;
	}

	@Override
	public ChangeOrderResp saveCoScopeOfWork(ChangeOrderReq changeOrderReq) {
		System.out.println("Started save change order sow (-)");
		ChangeOrderResp changeOrderResp = new ChangeOrderResp();
		for(CoProjSOWTO coProjSOWTo : changeOrderReq.getCoProjSOWTOs()) {
			ChangeOrderSOWEntity entity = new ChangeOrderSOWEntity();
			BeanUtils.copyProperties(coProjSOWTo, entity);
			entity.setStatus(1);
			UserMstrEntity updatedUserMstrEntity = new UserMstrEntity();
			updatedUserMstrEntity.setUserId(AppUserUtils.getUserId());
			entity.setUpdatedBy(updatedUserMstrEntity);
			entity.setCreatedBy(updatedUserMstrEntity);
			changeOrderSOWRepository.save(entity);
			coProjSOWTo.setId(entity.getId());
			changeOrderResp.getCoProjSOWTO().add(coProjSOWTo);
		}
		return changeOrderResp;
	}
	
	@Override
	public ChangeOrderResp saveCoManpowerDetails(ChangeOrderReq changeOrderReq) {
		System.out.println("Started save change order plant details(-)");
		ChangeOrderResp resp = new ChangeOrderResp();
		
		for(CoProjManpowerTO coProjManpowerTO : changeOrderReq.getCoProjManpowerTOs()) {
			COProjManpowerEntity entity = new COProjManpowerEntity();
			BeanUtils.copyProperties(coProjManpowerTO, entity);
			entity.setStatus(1);
			UserMstrEntity updatedUserMstrEntity = new UserMstrEntity();
			updatedUserMstrEntity.setUserId(AppUserUtils.getUserId());
			entity.setUpdatedBy(updatedUserMstrEntity);
			entity.setCreatedBy(updatedUserMstrEntity);
			pmpRepository.save(entity);
			coProjManpowerTO.setId(entity.getId());
			resp.getCoProjManpowerTOs().add(coProjManpowerTO);
		}
		
		return resp;
	}
	
	@Override
	public ChangeOrderResp saveCoPlantDetails(ChangeOrderReq changeOrderReq) {
		System.out.println("Started save change order plant details(-)");
		ChangeOrderResp resp = new ChangeOrderResp();
		for( CoProjPlantTO coProjPlantsTO : changeOrderReq.getCoProjPlantsTOs()) {
			COProjectPlantsDtlEntity entity= new COProjectPlantsDtlEntity();
			BeanUtils.copyProperties(coProjPlantsTO, entity);
			entity.setStatus(1);
			UserMstrEntity updatedUserMstrEntity = new UserMstrEntity();
			updatedUserMstrEntity.setUserId(AppUserUtils.getUserId());
			entity.setUpdatedBy(updatedUserMstrEntity);
			entity.setCreatedBy(updatedUserMstrEntity);
			coProjPlantsRepository.save(entity);
			coProjPlantsTO.setId(entity.getId());
			resp.getCoProjPlantsTOs().add(coProjPlantsTO);
		}
		return resp;
	}

	@Override
	public ChangeOrderResp getCoPlantDetails(ChangeOrderReq changeOrderReq) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ChangeOrderResp getCoManpowerDetails(ChangeOrderReq changeOrderReq) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//TODO:Commented this becoz New Version implemented above
	// This function is to save the manpower details for the Change Order module
	/*@Override
	public ChangeOrderResp saveCoManpowerDetails(ChangeOrderReq changeOrderReq) {
		ChangeOrderResp changeOrderResp = new ChangeOrderResp();
		ProjManpowerEntity projManpowerEntity = null;
		ProjMstrEntity projMstrEntity = null;
		ChangeOrderMstrEntity changeOrderMstrEntity = null;
		ChangeOrderMapEntity changeOrderMapEntity = null;
		List<Long> coProjManpowerIds = new ArrayList<Long>();
		UserMstrEntity createdUserMstrEntity = null;
		UserMstrEntity updatedUserMstrEntity = null;
		for (CoProjManpowerTO coProjManpowerTO : changeOrderReq.getCoProjManpowerTOs()) {
			System.out.println(coProjManpowerTO);
			projManpowerEntity = new ProjManpowerEntity();
			projMstrEntity = new ProjMstrEntity();
			changeOrderMstrEntity = new ChangeOrderMstrEntity();
			changeOrderMapEntity = new ChangeOrderMapEntity();

			projMstrEntity.setProjectId(coProjManpowerTO.getProjId());
			updatedUserMstrEntity = new UserMstrEntity();
			updatedUserMstrEntity.setUserId(AppUserUtils.getUserId());
			if (coProjManpowerTO.getItemType().equals("Existing Item")) {
				System.out.println("If condition");
				projManpowerEntity.setId(coProjManpowerTO.getProjManpowerId());
			} else {
				System.out.println("else condition");
				createdUserMstrEntity = new UserMstrEntity();
				createdUserMstrEntity.setUserId(AppUserUtils.getUserId());
				projManpowerEntity.setRequestedFrom("CO_MANPOWER");
				projManpowerEntity.setCreatedBy(createdUserMstrEntity);
				projManpowerEntity.setItemStatus("DRAFT");
			}

			projManpowerEntity.setUpdatedBy(updatedUserMstrEntity);
			projManpowerEntity.setRevisedQty(coProjManpowerTO.getCurrentQty());
			projManpowerEntity.setProjMstrEntity(projMstrEntity);
			if (CommonUtil.objectNotNull(coProjManpowerTO.getEmpClassTO())
					&& CommonUtil.isNonBlankLong(coProjManpowerTO.getEmpClassTO().getId())) {
				EmpClassMstrEntity classMstrEntity = empClassRepository
						.findOne(coProjManpowerTO.getEmpClassTO().getId());
				projManpowerEntity.setEmpClassMstrEntity(classMstrEntity);
			}

			if (CommonUtil.isNonBlankLong(coProjManpowerTO.getMeasureId())) {
				MeasurmentMstrEntity measurmentMstrEntity = measurementRepository
						.findOne(coProjManpowerTO.getMeasureId());
				projManpowerEntity.setMeasurmentMstrEntity(measurmentMstrEntity);
			}
			// projManpowerEntity.setNotes( coProjManpowerTO.getNotes() );
			projManpowerEntity.setStatus(StatusCodes.DEFAULT.getValue());
			projManpowerRepositoryCopy.save(projManpowerEntity);
			changeOrderResp.getCoProjManpowerTOs().add(coProjManpowerTO);
		}
		return changeOrderResp;
	}
	//TODO:Commented this becoz New Version implemented above
	// This function is to get the manpower details for the Change Order module
	@Override
	public ChangeOrderResp getCoManpowerDetails(ChangeOrderReq changeOrderReq) {
		ChangeOrderResp changeOrderResp = new ChangeOrderResp();
		CoProjManpowerTO coProjManpowerTO = null;
		List<ChangeOrderMapEntity> changeOrderMstrMapEntities = coMstrMapRepository
				.findCoManpowerDetails(changeOrderReq.getChangeOrderTOs().get(0).getId());

		for (ChangeOrderMapEntity changeOrderMstrMapEntity : changeOrderMstrMapEntities) {
			ProjManpowerEntity projManpowerEntity = changeOrderMstrMapEntity.getManpowerId();

			coProjManpowerTO.setId(projManpowerEntity.getId());
			coProjManpowerTO.setCurrentQty(projManpowerEntity.getOriginalQty());
			coProjManpowerTO.setRevisedQty(projManpowerEntity.getRevisedQty());
			coProjManpowerTO.setCoMstrId(changeOrderMstrMapEntity.getId());

			EmpClassMstrEntity empClass = projManpowerEntity.getEmpClassMstrEntity();
			if (CommonUtil.objectNotNull(empClass)) {
				coProjManpowerTO.setEmpClassId(empClass.getId());
				coProjManpowerTO.setEmpClassTO(ProjManpowerHandler.convertEmpClassEntityToPOJO(empClass));
			}

			if (CommonUtil.objectNotNull(projManpowerEntity.getMeasurmentMstrEntity())) {
				coProjManpowerTO.setMeasureId(projManpowerEntity.getMeasurmentMstrEntity().getId());
				coProjManpowerTO.setMeasureUnitTO(
						MeasurementHandler.convertMeasurePOJOFromEnity(projManpowerEntity.getMeasurmentMstrEntity()));
			}
			// coProjManpowerTO.setNotes( changeOrderMstrMapEntity.getId() );
			changeOrderResp.getCoProjManpowerTOs().add(coProjManpowerTO);
		}
		return changeOrderResp;
	}*/

	
	/*@Override
	public ChangeOrderResp saveCoPlantDetails(ChangeOrderReq changeOrderReq) {
		ChangeOrderResp changeOrderResp = new ChangeOrderResp();
		ProjectPlantsDtlEntity projPlantDtlEntityCopy = null;
		ProjMstrEntity projMstrEntity = null;
		ChangeOrderMstrEntity changeOrderMstrEntity = null;
		ChangeOrderMapEntity changeOrderMapEntity = null;
		List<Long> coProjPlantIds = new ArrayList<Long>();
		UserMstrEntity createdUserMstrEntity = null;
		UserMstrEntity updatedUserMstrEntity = null;
		for (CoProjPlantTO coProjPlantTO : changeOrderReq.getCoProjPlantsTOs()) {
			System.out.println(coProjPlantTO);
			projPlantDtlEntityCopy = new ProjectPlantsDtlEntity();
			projMstrEntity = new ProjMstrEntity();
			changeOrderMstrEntity = new ChangeOrderMstrEntity();
			changeOrderMapEntity = new ChangeOrderMapEntity();

			projMstrEntity.setProjectId(coProjPlantTO.getProjId());
			updatedUserMstrEntity = new UserMstrEntity();
			updatedUserMstrEntity.setUserId(AppUserUtils.getUserId());
			if (coProjPlantTO.getItemType().equals("Existing Item")) {
				System.out.println("If condition");
				projPlantDtlEntityCopy.setId(coProjPlantTO.getId());
				projPlantDtlEntityCopy.setOriginalQty(new BigDecimal(coProjPlantTO.getCurrentQty()));
			} else {
				System.out.println("else condition");
				createdUserMstrEntity = new UserMstrEntity();
				createdUserMstrEntity.setUserId(AppUserUtils.getUserId());
				projPlantDtlEntityCopy.setRequestedFrom("CO_PLANT");
				projPlantDtlEntityCopy.setCreatedBy(createdUserMstrEntity);
				projPlantDtlEntityCopy.setItemStatus("DRAFT");
				projPlantDtlEntityCopy.setRevisedQty(new BigDecimal(coProjPlantTO.getCurrentQty()));
			}

			projPlantDtlEntityCopy.setUpdatedBy(updatedUserMstrEntity);
			projPlantDtlEntityCopy.setProjMstrEntity(projMstrEntity);
			if (CommonUtil.objectNotNull(coProjPlantTO.getPlantClassTO())
					&& CommonUtil.isNonBlankLong(coProjPlantTO.getPlantClassTO().getId())) {
				PlantMstrEntity plantMstrEntity = plantClassRepository.findOne(coProjPlantTO.getPlantClassTO().getId());
				projPlantDtlEntityCopy.setPlantMstrEntity(plantMstrEntity);
			}

			if (CommonUtil.isNonBlankLong(coProjPlantTO.getMeasureId())) {
				MeasurmentMstrEntity measurmentMstrEntity = measurementRepository.findOne(coProjPlantTO.getMeasureId());
				projPlantDtlEntityCopy.setMeasurmentMstrEntity(measurmentMstrEntity);
			}
			projPlantDtlEntityCopy.setStatus(StatusCodes.DEFAULT.getValue());
			projectPlantsRepositoryCopy.save(projPlantDtlEntityCopy);
			changeOrderResp.getCoProjPlantsTOs().add(coProjPlantTO);
		}
		return changeOrderResp;
	}

	@Override
	public ChangeOrderResp getCoPlantDetails(ChangeOrderReq changeOrderReq) {
		ChangeOrderResp changeOrderResp = new ChangeOrderResp();
		CoProjPlantTO coProjPlantTO = null;
		List<ChangeOrderMapEntity> changeOrderMstrMapEntities = coMstrMapRepository
				.findCoPlantDetails(changeOrderReq.getChangeOrderTOs().get(0).getId());

		for (ChangeOrderMapEntity changeOrderMstrMapEntity : changeOrderMstrMapEntities) {
			ProjectPlantsDtlEntity projPlantEntityCopy = changeOrderMstrMapEntity.getPlantId();

			coProjPlantTO.setId(projPlantEntityCopy.getId());
			coProjPlantTO.setCurrentQty(projPlantEntityCopy.getOriginalQty().doubleValue());
			coProjPlantTO.setRevisedQty(projPlantEntityCopy.getRevisedQty().doubleValue());
			coProjPlantTO.setCoMstrId(changeOrderMstrMapEntity.getId());

			PlantMstrEntity plantClass = projPlantEntityCopy.getPlantMstrEntity();
			if (CommonUtil.objectNotNull(plantClass)) {
				coProjPlantTO.setPlantClassId(plantClass.getId());
				coProjPlantTO.setPlantClassTO(ProjectPlantsDtlHandler.converPlantClassEntityToPOJO(plantClass, false));
			}

			if (CommonUtil.objectNotNull(projPlantEntityCopy.getMeasurmentMstrEntity())) {
				coProjPlantTO.setMeasureId(projPlantEntityCopy.getMeasurmentMstrEntity().getId());
				coProjPlantTO.setMeasureUnitTO(
						MeasurementHandler.convertMeasurePOJOFromEnity(projPlantEntityCopy.getMeasurmentMstrEntity()));
			}

			changeOrderResp.getCoProjPlantsTOs().add(coProjPlantTO);
		}
		return changeOrderResp;
	}*/

	@Override
	public ChangeOrderResp updateCoApproverDetails(ChangeOrderReq changeOrderReq) {
		ChangeOrderResp changeOrderResp = new ChangeOrderResp();
		String reqApprovalType = changeOrderReq.getChangeOrderTOs().get(0).getReqApprovalType();
		Long loggedInUser = AppUserUtils.getUserId();
		if (reqApprovalType.equals("SUBMITTED_FOR_INTERNAL_APPROVAL") || reqApprovalType.equals("INTERNAL_APPROVE")) {
			System.out.println("if condition of internal approval");
			Long coId = changeOrderReq.getChangeOrderTOs().get(0).getId();
			String approvalStatus = (reqApprovalType.equals("SUBMITTED_FOR_INTERNAL_APPROVAL"))
					? "SUBMITTED_FOR_INTERNAL_APPROVAL"
					: "INTERNALLY_APPROVED";

			LabelKeyTO internalApproverDetails = changeOrderReq.getChangeOrderTOs().get(0)
					.getInternalApproverLabelKeyTO();
			if (approvalStatus.equals("SUBMITTED_FOR_INTERNAL_REQUEST")) {
				changeOrderMstrRepository.updateCoInternalApproverDetailsById(coId, approvalStatus,
						changeOrderReq.getChangeOrderTOs().get(0).getIsExternalApprovalRequired(),
						internalApproverDetails.getId(), loggedInUser);
			} else {
				changeOrderMstrRepository.updateCoApprovalDetailsById(coId, approvalStatus);
			}
		}
		if (reqApprovalType.equals("SUBMITTED_FOR_EXTERNAL_APPROVAL") || reqApprovalType.equals("EXTERNAL_APPROVE")) {
			System.out.println("if condition of external approval");
			Long coId = changeOrderReq.getChangeOrderTOs().get(0).getId();
			String approvalStatus = (reqApprovalType.equals("SUBMITTED_FOR_EXTERNAL_APPROVAL"))
					? "SUBMITTED_FOR_EXTERNAL_APPROVAL"
					: "APPROVED";
			System.out.println(approvalStatus);
			LabelKeyTO externalApproverDetails = changeOrderReq.getChangeOrderTOs().get(0)
					.getExternalApproverLabelKeyTO();
			if (approvalStatus.equals("SUBMITTED_FOR_EXTERNAL_APPROVAL")) {
				System.out.println("if condition:" + approvalStatus);
				changeOrderMstrRepository.updateCoExternalApproverDetailsById(coId, approvalStatus,
						externalApproverDetails.getId());
			} else {
				projManpowerRepositoryCopy.updateProjManpowerStatusById(coId, 1);
				changeOrderMstrRepository.updateCoApprovalDetailsById(coId, approvalStatus);
			}
		}
		return changeOrderResp;
	}

	// This function is to generate the code for the change order table
	public String generateChangeOrderCode(Long projectId, Long coId) {
		String co_code = "";
		ProjMstrEntity projDetails = projRepository.findOne(projectId);
		if (CommonUtil.isNonBlankLong(coId)) {
			co_code = "CO-" + projDetails.getCode() + "-" + coId;
		} else {
			co_code = "CO-" + projDetails.getCode();
		}
		return co_code;
	}

	@Override
	public ChangeOrderResp saveCoMaterialDetails(ChangeOrderReq changeOrderReq) {
		System.out.println("Started save change order material details(-)");
		ChangeOrderResp resp = new ChangeOrderResp();
		
		for(CoMaterialTO  coMaterialTO : changeOrderReq.getCoMaterialTOs()) {
			COProjectMaterialBudgetEntity entity = new COProjectMaterialBudgetEntity();
			BeanUtils.copyProperties(coMaterialTO, entity);
			entity.setStatus(1);
			UserMstrEntity updatedUserMstrEntity = new UserMstrEntity();
			updatedUserMstrEntity.setUserId(AppUserUtils.getUserId());
			entity.setUpdatedBy(updatedUserMstrEntity);
			entity.setCreatedBy(updatedUserMstrEntity);
			cpmRepository.save(entity);
			coMaterialTO.setId(entity.getId());
			resp.getCoMaterialTOs().add(coMaterialTO);
		}
		return resp;
		
	}

	@Override
	public ChangeOrderResp saveCoCostDetails(ChangeOrderReq changeOrderReq) {
		System.out.println("Started save change order cost details(-)");
		ChangeOrderResp resp = new ChangeOrderResp();
		for( CoProjCostTO coProjCostTO : changeOrderReq.getCoProjCostTOs()) {
			COProjCostBudgetEntity entity = new COProjCostBudgetEntity();
			BeanUtils.copyProperties(coProjCostTO, entity);
			entity.setStatus(1);
			UserMstrEntity updatedUserMstrEntity = new UserMstrEntity();
			updatedUserMstrEntity.setUserId(AppUserUtils.getUserId());
			entity.setUpdatedBy(updatedUserMstrEntity);
			entity.setCreatedBy(updatedUserMstrEntity);
			coProjCostBudgetRepository.save(entity);
			coProjCostTO.setId(entity.getId());
			resp.getCoProjCostTOs().add(coProjCostTO);
		}
		return resp;
	}

}
