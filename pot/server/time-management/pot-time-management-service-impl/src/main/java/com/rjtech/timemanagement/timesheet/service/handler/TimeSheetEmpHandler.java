package com.rjtech.timemanagement.timesheet.service.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.rjtech.centrallib.model.EmpWageMstrEntity;
import com.rjtech.centrallib.repository.EmpWageRepository;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.projectlib.model.ProjCostItemEntity;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
import com.rjtech.projectlib.repository.ProjCostItemRepositoryCopy;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.repository.emp.EmpRegisterRepositoryCopy;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetEmpDtlTO;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetEmpExpenseTO;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetEmpTaskTO;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetEmpWorkDtlTO;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetTO;
import com.rjtech.timemanagement.timesheet.model.TimeSheetEmpDtlEntity;
import com.rjtech.timemanagement.timesheet.model.TimeSheetEmpExpenseEntity;
import com.rjtech.timemanagement.timesheet.model.TimeSheetEmpTaskEntity;
import com.rjtech.timemanagement.timesheet.model.TimeSheetEmpWorkDtlEntity;
import com.rjtech.timemanagement.timesheet.model.TimeSheetEntity;
import com.rjtech.timemanagement.timesheet.repository.TimeSheetEmpDtlRepository;
import com.rjtech.timemanagement.timesheet.repository.TimeSheetEmpExpenseRepository;
import com.rjtech.timemanagement.timesheet.repository.TimeSheetEmpRepository;
import com.rjtech.timemanagement.timesheet.repository.TimeSheetEmpTaskRepository;
import com.rjtech.timemanagement.util.TimeManagementUtil;

public class TimeSheetEmpHandler {
    
	public static TimeSheetTO convertEntityToPOJO1(TimeSheetEntity timeSheetEntity,List<Long> userProjectsEntities) {
        TimeSheetTO timeSheetTO = new TimeSheetTO();
        timeSheetTO.setId(timeSheetEntity.getId());
        timeSheetTO.setTimeSheetCode(generateTimeSheetCode(timeSheetEntity));
        timeSheetTO.setWeekStartDate(timeSheetEntity.getWeekStartDate());
        timeSheetTO.setWeekEndDate(timeSheetEntity.getWeekEndDate());
        if (CommonUtil.objectNotNull(timeSheetEntity.getProjCrewMstrEntity())) {
            timeSheetTO.setCrewId(timeSheetEntity.getProjCrewMstrEntity().getId());
            timeSheetTO.setCrewName(timeSheetEntity.getProjCrewMstrEntity().getCode());
        }
        if (CommonUtil.objectNotNull(timeSheetEntity.getEmpRegisterDtlEntity())) {
            timeSheetTO.setEmpId(timeSheetEntity.getEmpRegisterDtlEntity().getId());
        }
        if(userProjectsEntities != null) {
			for(int i=0;i<userProjectsEntities.size();i++) {
				if(userProjectsEntities.get(i) != timeSheetEntity.getProjMstrEntity().getProjectId()) {
					timeSheetTO.setDisableFlag(true);
				}			
			}
		}
		if(userProjectsEntities != null) {
			for(int i=0;i<userProjectsEntities.size();i++) {
				if(userProjectsEntities.get(i).equals(timeSheetEntity.getProjMstrEntity().getProjectId())) {
					timeSheetTO.setDisableFlag(false);
				}
			}
			
		}
        UserMstrEntity requester = timeSheetEntity.getReqUserMstrEntity();
        if (CommonUtil.objectNotNull(requester)) {
            timeSheetTO.setReqUserId(requester.getUserId());
            timeSheetTO.setReqUserName(requester.getEmpCode());
            timeSheetTO.setReqUserDisplayName(requester.getDisplayName());
        }
        UserMstrEntity approver = timeSheetEntity.getApprUserMstrEntity();
        if (CommonUtil.objectNotNull(approver)) {
            timeSheetTO.setApprUserId(approver.getUserId());
            timeSheetTO.setApprUserName(approver.getEmpCode());
            timeSheetTO.setApprUserDisplayName(approver.getDisplayName());
        }
        if (CommonUtil.objectNotNull(timeSheetEntity.getProjMstrEntity())) {
        	timeSheetTO.setProjId(timeSheetEntity.getProjMstrEntity().getProjectId());
        	timeSheetTO.setCode(timeSheetEntity.getProjMstrEntity().getCode());
        	timeSheetTO.setParentCode(timeSheetEntity.getProjMstrEntity().getParentProjectMstrEntity().getCode());
        	timeSheetTO.setName(timeSheetEntity.getProjMstrEntity().getProjName());
        	timeSheetTO.setParentName(timeSheetEntity.getProjMstrEntity().getParentProjectMstrEntity().getProjName());
        	timeSheetTO.setParentProjId(timeSheetEntity.getProjMstrEntity().getParentProjectMstrEntity().getProjectId());
        }
        timeSheetTO.setAdditional(timeSheetEntity.getAdditional());
        timeSheetTO.setReqComments(timeSheetEntity.getReqComments());
        timeSheetTO.setApprStatus(timeSheetEntity.getApprStatus());
        timeSheetTO.setApprComments(timeSheetEntity.getApprComments());
        timeSheetTO.setStatus(timeSheetEntity.getStatus());

        return timeSheetTO;
    }
	
    public static TimeSheetTO convertEntityToPOJO(TimeSheetEntity timeSheetEntity) {
        TimeSheetTO timeSheetTO = new TimeSheetTO();
        timeSheetTO.setId(timeSheetEntity.getId());
        timeSheetTO.setTimeSheetCode(generateTimeSheetCode(timeSheetEntity));
        timeSheetTO.setWeekStartDate(timeSheetEntity.getWeekStartDate());
        timeSheetTO.setWeekEndDate(timeSheetEntity.getWeekEndDate());
        if (CommonUtil.objectNotNull(timeSheetEntity.getProjCrewMstrEntity())) {
            timeSheetTO.setCrewId(timeSheetEntity.getProjCrewMstrEntity().getId());
            timeSheetTO.setCrewName(timeSheetEntity.getProjCrewMstrEntity().getCode());
        }
        if (CommonUtil.objectNotNull(timeSheetEntity.getEmpRegisterDtlEntity())) {
            timeSheetTO.setEmpId(timeSheetEntity.getEmpRegisterDtlEntity().getId());
        }
        UserMstrEntity requester = timeSheetEntity.getReqUserMstrEntity();
        if (CommonUtil.objectNotNull(requester)) {
            timeSheetTO.setReqUserId(requester.getUserId());
            timeSheetTO.setReqUserName(requester.getEmpCode());
            timeSheetTO.setReqUserDisplayName(requester.getDisplayName());
        }
        UserMstrEntity approver = timeSheetEntity.getApprUserMstrEntity();
        if (CommonUtil.objectNotNull(approver)) {
            timeSheetTO.setApprUserId(approver.getUserId());
            timeSheetTO.setApprUserName(approver.getEmpCode());
            timeSheetTO.setApprUserDisplayName(approver.getDisplayName());
        }
        if (CommonUtil.objectNotNull(timeSheetEntity.getProjMstrEntity())) {
        	timeSheetTO.setProjId(timeSheetEntity.getProjMstrEntity().getProjectId());
        	timeSheetTO.setCode(timeSheetEntity.getProjMstrEntity().getCode());
        	timeSheetTO.setParentCode(timeSheetEntity.getProjMstrEntity().getParentProjectMstrEntity().getCode());
        	timeSheetTO.setName(timeSheetEntity.getProjMstrEntity().getProjName());
        	timeSheetTO.setParentName(timeSheetEntity.getProjMstrEntity().getParentProjectMstrEntity().getProjName());
        	timeSheetTO.setParentProjId(timeSheetEntity.getProjMstrEntity().getParentProjectMstrEntity().getProjectId());
        }
        timeSheetTO.setAdditional(timeSheetEntity.getAdditional());
        timeSheetTO.setReqComments(timeSheetEntity.getReqComments());
        timeSheetTO.setApprStatus(timeSheetEntity.getApprStatus());
        timeSheetTO.setApprComments(timeSheetEntity.getApprComments());
        timeSheetTO.setStatus(timeSheetEntity.getStatus());

        return timeSheetTO;
    }

    public static TimeSheetEmpDtlTO convertEmpDtlEntityToPOJO(TimeSheetEmpDtlEntity timeSheetEmpDtlEntity) {
        TimeSheetEmpDtlTO timeSheetEmpDtlTO = new TimeSheetEmpDtlTO();
        timeSheetEmpDtlTO.setId(timeSheetEmpDtlEntity.getId());
        LabelKeyTO empDetailLabelKeyTO = new LabelKeyTO();
        EmpRegisterDtlEntity empReg = timeSheetEmpDtlEntity.getEmpRegisterDtlEntity();
        if (empReg != null) {
            timeSheetEmpDtlTO.setEmpRegId(empReg.getId());
            empDetailLabelKeyTO.setCode(empReg.getCode());
            Map<String, String> displayNamesMap = new HashMap<>();
            displayNamesMap.put(CommonConstants.FIRST_NAME, empReg.getFirstName());
            displayNamesMap.put(CommonConstants.LAST_NAME, empReg.getLastName());
            displayNamesMap.put(CommonConstants.GENDER, empReg.getGender());
            displayNamesMap.put(CommonConstants.CLASS_TYPE, empReg.getEmpClassMstrEntity().getCode());
            displayNamesMap.put(CommonConstants.CLASS_NAME, empReg.getEmpClassMstrEntity().getName());
            displayNamesMap.put(CommonConstants.COMPANY_CATG_NAME, empReg.getCompanyMstrEntity().getName());
            empDetailLabelKeyTO.setDisplayNamesMap(displayNamesMap);
            timeSheetEmpDtlTO.setEmpDetailLabelKeyTO(empDetailLabelKeyTO);
        }
        if (CommonUtil.objectNotNull(timeSheetEmpDtlEntity.getTimeSheetEntity())) {
            timeSheetEmpDtlTO.setTimeSheetId(timeSheetEmpDtlEntity.getTimeSheetEntity().getId());
        }
        timeSheetEmpDtlTO.setStatus(timeSheetEmpDtlEntity.getStatus());
        return timeSheetEmpDtlTO;
    }

    public static TimeSheetEmpDtlEntity convertEmpDtlPOJOToEntity(TimeSheetEmpDtlTO timeSheetEmpDtlTO,
            EmpRegisterRepositoryCopy empRegisterRepository) {
        TimeSheetEmpDtlEntity timeSheetEmpDtlEntity = new TimeSheetEmpDtlEntity();
        if (CommonUtil.isNonBlankLong(timeSheetEmpDtlTO.getId())) {
            timeSheetEmpDtlEntity.setId(timeSheetEmpDtlTO.getId());
        }
        if (CommonUtil.isNonBlankLong(timeSheetEmpDtlTO.getEmpRegId())) {
            EmpRegisterDtlEntity empRegisterDtlEntity = empRegisterRepository
                    .findOne(timeSheetEmpDtlTO.getEmpRegId());
            timeSheetEmpDtlEntity.setEmpRegisterDtlEntity(empRegisterDtlEntity);
        }
        if (CommonUtil.isNonBlankLong(timeSheetEmpDtlTO.getTimeSheetId())) {
            TimeSheetEntity timeSheetEntity = new TimeSheetEntity();
            timeSheetEntity.setId(timeSheetEmpDtlTO.getTimeSheetId());
            timeSheetEmpDtlEntity.setTimeSheetEntity(timeSheetEntity);
        }

        timeSheetEmpDtlEntity.setStatus(timeSheetEmpDtlTO.getStatus());

        return timeSheetEmpDtlEntity;
    }

    public static TimeSheetEmpWorkDtlTO convertEmpWorkDtlEntityToPOJO(
            TimeSheetEmpWorkDtlEntity timeSheetEmpWorkDtlEntity, boolean forReports) {

        TimeSheetEmpWorkDtlTO timeSheetEmpWorkDtlTO = new TimeSheetEmpWorkDtlTO();
        timeSheetEmpWorkDtlTO.setId(timeSheetEmpWorkDtlEntity.getId());

        ProjCostItemEntity cost = timeSheetEmpWorkDtlEntity.getProjCostItemEntity();
        if (CommonUtil.objectNotNull(cost)) {
            timeSheetEmpWorkDtlTO.setCostId(cost.getId());
            timeSheetEmpWorkDtlTO.setCostCode(cost.getCode());
            if (forReports) {
                timeSheetEmpWorkDtlTO.setCostCodeName(cost.getName());
                ProjCostItemEntity parent = cost.getProjCostItemEntity();
                if (parent != null) {
                    timeSheetEmpWorkDtlTO.setCostCodeParent(parent.getCode());
                    timeSheetEmpWorkDtlTO.setCostCodeParentName(parent.getName());
                }
            }

        }
        EmpWageMstrEntity wage = timeSheetEmpWorkDtlEntity.getEmpWageMstrEntity();
        if (CommonUtil.objectNotNull(wage)) {
            timeSheetEmpWorkDtlTO.setEmpWageId(wage.getId());
            timeSheetEmpWorkDtlTO.setWageCode(wage.getCode());
        }
        timeSheetEmpWorkDtlTO.setEmpDtlId(timeSheetEmpWorkDtlEntity.getTimeSheetEmpDtlEntity().getId());

        timeSheetEmpWorkDtlTO.setDay1Hrs(timeSheetEmpWorkDtlEntity.getDay1());
        timeSheetEmpWorkDtlTO.setDay2Hrs(timeSheetEmpWorkDtlEntity.getDay2());
        timeSheetEmpWorkDtlTO.setDay3Hrs(timeSheetEmpWorkDtlEntity.getDay3());
        timeSheetEmpWorkDtlTO.setDay4Hrs(timeSheetEmpWorkDtlEntity.getDay4());
        timeSheetEmpWorkDtlTO.setDay5Hrs(timeSheetEmpWorkDtlEntity.getDay5());
        timeSheetEmpWorkDtlTO.setDay6Hrs(timeSheetEmpWorkDtlEntity.getDay6());
        timeSheetEmpWorkDtlTO.setDay7Hrs(timeSheetEmpWorkDtlEntity.getDay7());

        timeSheetEmpWorkDtlTO.setApprStatus(timeSheetEmpWorkDtlEntity.getApprStatus());
        UserMstrEntity apprUser = timeSheetEmpWorkDtlEntity.getApprUserMstrEntity();
        if (apprUser != null)
            timeSheetEmpWorkDtlTO.setApprUsrId(apprUser.getUserId());
        timeSheetEmpWorkDtlTO.setApprComments(timeSheetEmpWorkDtlEntity.getApprComments());

        if (CommonUtil.objectNotNull(timeSheetEmpWorkDtlEntity.getParentTimeSheetEmpWorkDtlEntity()))
            timeSheetEmpWorkDtlTO.setParentId(timeSheetEmpWorkDtlEntity.getParentTimeSheetEmpWorkDtlEntity().getId());
        timeSheetEmpWorkDtlTO.setStatus(timeSheetEmpWorkDtlEntity.getStatus());

        return timeSheetEmpWorkDtlTO;
    }

    public static TimeSheetEmpWorkDtlEntity convertEmpWorkDtlPOJOToEntity(TimeSheetEmpWorkDtlTO timeSheetEmpWorkDtlTO,
            ProjCostItemRepositoryCopy projCostItemRepository, EmpWageRepository empWageRepository,
            TimeSheetEmpDtlRepository timeSheetEmpDtlRepository, LoginRepository loginRepository) {
        TimeSheetEmpWorkDtlEntity timeSheetEmpWorkDtlEntity = new TimeSheetEmpWorkDtlEntity();
        if (CommonUtil.isNonBlankLong(timeSheetEmpWorkDtlTO.getId())) {
            timeSheetEmpWorkDtlEntity.setId(timeSheetEmpWorkDtlTO.getId());
        }
        if (CommonUtil.isNonBlankLong(timeSheetEmpWorkDtlTO.getCostId())) {
            ProjCostItemEntity projCostItemEntity = projCostItemRepository
                    .findOne(timeSheetEmpWorkDtlTO.getCostId());
            timeSheetEmpWorkDtlEntity.setProjCostItemEntity(projCostItemEntity);
        }
        if (CommonUtil.isNonBlankLong(timeSheetEmpWorkDtlTO.getEmpWageId())) {
            EmpWageMstrEntity empWageMstrEntity = empWageRepository.findOne(timeSheetEmpWorkDtlTO.getEmpWageId());
            timeSheetEmpWorkDtlEntity.setEmpWageMstrEntity(empWageMstrEntity);
        }
        if (CommonUtil.isNonBlankLong(timeSheetEmpWorkDtlTO.getApprUsrId())) {
            UserMstrEntity apprUsr = loginRepository.findOne(timeSheetEmpWorkDtlTO.getApprUsrId());
            timeSheetEmpWorkDtlEntity.setApprUserMstrEntity(apprUsr);
        }
        timeSheetEmpWorkDtlEntity.setDay1(timeSheetEmpWorkDtlTO.getDay1Hrs());
        timeSheetEmpWorkDtlEntity.setDay2(timeSheetEmpWorkDtlTO.getDay2Hrs());
        timeSheetEmpWorkDtlEntity.setDay3(timeSheetEmpWorkDtlTO.getDay3Hrs());
        timeSheetEmpWorkDtlEntity.setDay4(timeSheetEmpWorkDtlTO.getDay4Hrs());
        timeSheetEmpWorkDtlEntity.setDay5(timeSheetEmpWorkDtlTO.getDay5Hrs());
        timeSheetEmpWorkDtlEntity.setDay6(timeSheetEmpWorkDtlTO.getDay6Hrs());
        timeSheetEmpWorkDtlEntity.setDay7(timeSheetEmpWorkDtlTO.getDay7Hrs());
        timeSheetEmpWorkDtlEntity.setLatest(true);
        timeSheetEmpWorkDtlEntity.setStatus(1);
        timeSheetEmpWorkDtlEntity.setApprStatus(timeSheetEmpWorkDtlTO.getApprStatus());
        timeSheetEmpWorkDtlEntity.setApprComments(timeSheetEmpWorkDtlTO.getApprComments());

        if (timeSheetEmpWorkDtlTO.getEmpDtlId() != null) {
            TimeSheetEmpDtlEntity timeSheetEmpDtlEntity = new TimeSheetEmpDtlEntity();
            timeSheetEmpDtlEntity.setId(timeSheetEmpWorkDtlTO.getEmpDtlId());
            timeSheetEmpWorkDtlEntity.setTimeSheetEmpDtlEntity(timeSheetEmpDtlEntity);
        }
        if (CommonUtil.isNonBlankLong(timeSheetEmpWorkDtlTO.getParentId())) {
            TimeSheetEmpWorkDtlEntity parent = timeSheetEmpDtlRepository.findOne(timeSheetEmpWorkDtlTO.getParentId());
            timeSheetEmpWorkDtlEntity.setParentTimeSheetEmpWorkDtlEntity(parent);
        }
        timeSheetEmpWorkDtlEntity.setStatus(timeSheetEmpWorkDtlEntity.getStatus());

        return timeSheetEmpWorkDtlEntity;
    }

    public static TimeSheetEmpTaskTO convertEmpTaskEntityToPOJO(TimeSheetEmpTaskEntity timeSheetEmpTaskEntity) {
        TimeSheetEmpTaskTO timeSheetEmpTaskTO = new TimeSheetEmpTaskTO();
        timeSheetEmpTaskTO.setId(timeSheetEmpTaskEntity.getId());
        timeSheetEmpTaskTO.setTaskName(timeSheetEmpTaskEntity.getTaskName());
        if (CommonUtil.isNotBlankDate(timeSheetEmpTaskEntity.getDate())) {
            timeSheetEmpTaskTO.setDate(TimeManagementUtil.getDateWithDDMMMYYYYFormat(timeSheetEmpTaskEntity.getDate()));
        }
        timeSheetEmpTaskTO.setApprStatus(timeSheetEmpTaskEntity.getApprStatus());
        if (CommonUtil.objectNotNull(timeSheetEmpTaskEntity.getApprUserMstrEntity()))
            timeSheetEmpTaskTO.setApprUsrId(timeSheetEmpTaskEntity.getApprUserMstrEntity().getUserId());
        timeSheetEmpTaskTO.setEmpDtlId(timeSheetEmpTaskEntity.getTimeSheetEmpDtlEntity().getId());
        if (CommonUtil.objectNotNull(timeSheetEmpTaskEntity.getParentTimeSheetEmpTaskEntity()))
            timeSheetEmpTaskTO.setParentId(timeSheetEmpTaskEntity.getParentTimeSheetEmpTaskEntity().getId());
        timeSheetEmpTaskTO.setStatus(timeSheetEmpTaskEntity.getStatus());

        return timeSheetEmpTaskTO;
    }

    public static TimeSheetEmpTaskEntity convertEmpTaskPOJOToEntity(TimeSheetEmpTaskTO timeSheetEmpTaskTO,
            LoginRepository loginRepository, TimeSheetEmpTaskRepository timeSheetEmpTaskRepository,
            TimeSheetEmpRepository timeSheetEmpRepository) {
        TimeSheetEmpTaskEntity timeSheetEmpTaskEntity = new TimeSheetEmpTaskEntity();
        if (CommonUtil.isNonBlankLong(timeSheetEmpTaskTO.getId())) {
            timeSheetEmpTaskEntity.setId(timeSheetEmpTaskTO.getId());
        }
        timeSheetEmpTaskEntity.setTaskName(timeSheetEmpTaskTO.getTaskName());
        if (CommonUtil.isNotBlankStr(timeSheetEmpTaskTO.getDate())) {
            timeSheetEmpTaskEntity.setDate(TimeManagementUtil.getStringDDMMMYYYYFormat(timeSheetEmpTaskTO.getDate()));
        }
        timeSheetEmpTaskEntity.setApprStatus(timeSheetEmpTaskTO.getApprStatus());
        if (CommonUtil.isNonBlankLong(timeSheetEmpTaskTO.getApprUsrId())) {
            UserMstrEntity apprUserMstrEntity = loginRepository.findOne(timeSheetEmpTaskTO.getApprUsrId());
            timeSheetEmpTaskEntity.setApprUserMstrEntity(apprUserMstrEntity);
        }
        if (CommonUtil.isNonBlankLong(timeSheetEmpTaskTO.getEmpDtlId())) {
            TimeSheetEmpDtlEntity timeSheetEmpDtlEntity = timeSheetEmpRepository
                    .findOne(timeSheetEmpTaskTO.getEmpDtlId());
            timeSheetEmpTaskEntity.setTimeSheetEmpDtlEntity(timeSheetEmpDtlEntity);
        }
        if (CommonUtil.isNonBlankLong(timeSheetEmpTaskTO.getParentId())) {
            TimeSheetEmpTaskEntity parentEmpTaskEntity = timeSheetEmpTaskRepository
                    .findOne(timeSheetEmpTaskTO.getParentId());
            timeSheetEmpTaskEntity.setParentTimeSheetEmpTaskEntity(parentEmpTaskEntity);
        }
        timeSheetEmpTaskEntity.setStatus(timeSheetEmpTaskTO.getStatus());

        return timeSheetEmpTaskEntity;
    }

    public static TimeSheetEmpExpenseTO convertEmpExpenseEntityToPOJO(
            TimeSheetEmpExpenseEntity timeSheetEmpExpenseEntity) {
        TimeSheetEmpExpenseTO timeSheetEmpExpenseTO = new TimeSheetEmpExpenseTO();
        timeSheetEmpExpenseTO.setId(timeSheetEmpExpenseEntity.getId());

        timeSheetEmpExpenseTO.setAmount(timeSheetEmpExpenseEntity.getAmount());
        timeSheetEmpExpenseTO.setComments(timeSheetEmpExpenseEntity.getComments());

        if (CommonUtil.isNotBlankDate(timeSheetEmpExpenseEntity.getDate())) {
            timeSheetEmpExpenseTO.setDate(CommonUtil.convertDateToString(timeSheetEmpExpenseEntity.getDate()));
        }
        timeSheetEmpExpenseTO.setApprStatus(timeSheetEmpExpenseEntity.getApprStatus());

        if (CommonUtil.objectNotNull(timeSheetEmpExpenseEntity.getApprUserMstrEntity())) {
            timeSheetEmpExpenseTO.setApprUsrId(timeSheetEmpExpenseEntity.getApprUserMstrEntity().getUserId());
        }
        if (CommonUtil.objectNotNull(timeSheetEmpExpenseEntity.getTimeSheetEmpDtlEntity())) {
            timeSheetEmpExpenseTO.setEmpDtlId(timeSheetEmpExpenseEntity.getTimeSheetEmpDtlEntity().getId());
        }
        if (CommonUtil.objectNotNull(timeSheetEmpExpenseEntity.getProjCostItemEntity())) {
            timeSheetEmpExpenseTO.setCostId(timeSheetEmpExpenseEntity.getProjCostItemEntity().getId());
        }
        if (CommonUtil.objectNotNull(timeSheetEmpExpenseEntity.getParentTimeSheetEmpExpenseEntity())) {
            timeSheetEmpExpenseTO.setParentId(timeSheetEmpExpenseEntity.getParentTimeSheetEmpExpenseEntity().getId());
        }
        timeSheetEmpExpenseTO.setStatus(timeSheetEmpExpenseEntity.getStatus());

        return timeSheetEmpExpenseTO;
    }

    public static TimeSheetEmpExpenseEntity convertEmpExpensePOJOToEntity(TimeSheetEmpExpenseTO timeSheetEmpExpenseTO,
            ProjCostItemRepositoryCopy projCostItemRepository,
            TimeSheetEmpExpenseRepository timeSheetEmpExpenseRepository, LoginRepository loginRepository) {
        TimeSheetEmpExpenseEntity timeSheetEmpExpenseEntity = new TimeSheetEmpExpenseEntity();
        if (CommonUtil.isNonBlankLong(timeSheetEmpExpenseTO.getId())) {
            timeSheetEmpExpenseEntity.setId(timeSheetEmpExpenseTO.getId());
        }
        if (CommonUtil.isNonBlankLong(timeSheetEmpExpenseTO.getCostId())) {
            ProjCostItemEntity projCostItemEntity = projCostItemRepository
                    .findOne(timeSheetEmpExpenseTO.getCostId());
            timeSheetEmpExpenseEntity.setProjCostItemEntity(projCostItemEntity);
        }
        if (CommonUtil.isNonBlankLong(timeSheetEmpExpenseTO.getApprUsrId())) {
            UserMstrEntity userEntity = loginRepository.findOne(timeSheetEmpExpenseTO.getApprUsrId());
            timeSheetEmpExpenseEntity.setApprUserMstrEntity(userEntity);
        }
        timeSheetEmpExpenseEntity.setAmount(timeSheetEmpExpenseTO.getAmount());
        timeSheetEmpExpenseEntity.setComments(timeSheetEmpExpenseTO.getComments());

        if (CommonUtil.isNotBlankStr(timeSheetEmpExpenseTO.getDate())) {
            timeSheetEmpExpenseEntity.setDate(CommonUtil.convertStringToDate(timeSheetEmpExpenseTO.getDate()));
        }
        timeSheetEmpExpenseEntity.setApprStatus(timeSheetEmpExpenseTO.getApprStatus());
        TimeSheetEmpDtlEntity timeSheetEmpDtlEntity = new TimeSheetEmpDtlEntity();
        timeSheetEmpDtlEntity.setId(timeSheetEmpExpenseTO.getEmpDtlId());
        timeSheetEmpExpenseEntity.setTimeSheetEmpDtlEntity(timeSheetEmpDtlEntity);

        if (CommonUtil.isNonBlankLong(timeSheetEmpExpenseTO.getParentId())) {
            TimeSheetEmpExpenseEntity parentEntity = timeSheetEmpExpenseRepository
                    .findOne(timeSheetEmpExpenseTO.getParentId());
            timeSheetEmpExpenseEntity.setParentTimeSheetEmpExpenseEntity(parentEntity);
        }

        timeSheetEmpExpenseEntity.setStatus(timeSheetEmpExpenseTO.getStatus());

        return timeSheetEmpExpenseEntity;
    }

    public static void enableTimeSheet(Map<String, List<Long>> attendanceDayMap,
            TimeSheetEmpWorkDtlTO timeSheetEmpWorkDtlTO, Long empId) {
        if (attendanceDayMap.get(CommonConstants.DAY1_BOOKED_HRS) != null
                && attendanceDayMap.get(CommonConstants.DAY1_BOOKED_HRS).contains(empId)) {
            timeSheetEmpWorkDtlTO.setDay1Flag(true);
        }
        if (attendanceDayMap.get(CommonConstants.DAY2_BOOKED_HRS) != null
                && attendanceDayMap.get(CommonConstants.DAY2_BOOKED_HRS).contains(empId)) {
            timeSheetEmpWorkDtlTO.setDay2Flag(true);
        }

        if (attendanceDayMap.get(CommonConstants.DAY3_BOOKED_HRS) != null
                && attendanceDayMap.get(CommonConstants.DAY3_BOOKED_HRS).contains(empId)) {
            timeSheetEmpWorkDtlTO.setDay3Flag(true);
        }
        if (attendanceDayMap.get(CommonConstants.DAY4_BOOKED_HRS) != null
                && attendanceDayMap.get(CommonConstants.DAY4_BOOKED_HRS).contains(empId)) {
            timeSheetEmpWorkDtlTO.setDay4Flag(true);
        }

        if (attendanceDayMap.get(CommonConstants.DAY5_BOOKED_HRS) != null
                && attendanceDayMap.get(CommonConstants.DAY5_BOOKED_HRS).contains(empId)) {
            timeSheetEmpWorkDtlTO.setDay5Flag(true);
        }

        if (attendanceDayMap.get(CommonConstants.DAY6_BOOKED_HRS) != null
                && attendanceDayMap.get(CommonConstants.DAY6_BOOKED_HRS).contains(empId)) {
            timeSheetEmpWorkDtlTO.setDay6Flag(true);
        }
        if (attendanceDayMap.get(CommonConstants.DAY7_BOOKED_HRS) != null
                && attendanceDayMap.get(CommonConstants.DAY7_BOOKED_HRS).contains(empId)) {
            timeSheetEmpWorkDtlTO.setDay7Flag(true);
        }
    }
    
    public static void enableTimeSheet1(Map<String, List<Long>> attendanceDayMap,
            TimeSheetEmpWorkDtlTO timeSheetEmpWorkDtlTO, Long empId) { 	
    	timeSheetEmpWorkDtlTO.setDay1Flag(true);
    	timeSheetEmpWorkDtlTO.setDay2Flag(true);
    	timeSheetEmpWorkDtlTO.setDay3Flag(true);
    	timeSheetEmpWorkDtlTO.setDay4Flag(true);
    	timeSheetEmpWorkDtlTO.setDay5Flag(true);
    	timeSheetEmpWorkDtlTO.setDay6Flag(true);
    	timeSheetEmpWorkDtlTO.setDay7Flag(true);
    }
    
    public static void populateBookedHrs(Map<String, Double> dayWiseBookedHrsMap, LabelKeyTO labelKeyTO) {
        double day1TotalHrs = 0;
        double day2TotalHrs = 0;
        double day3TotalHrs = 0;
        double day4TotalHrs = 0;
        double day5TotalHrs = 0;
        double day6TotalHrs = 0;
        double day7TotalHrs = 0;

        if (!CommonUtil.isBlankDouble(dayWiseBookedHrsMap.get(CommonConstants.DAY1_BOOKED_HRS))) {
            day1TotalHrs = day1TotalHrs + dayWiseBookedHrsMap.get(CommonConstants.DAY1_BOOKED_HRS);
        }
        if (CommonUtil.isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(CommonConstants.DAY1_BOOKED_HRS))) {
            day1TotalHrs = day1TotalHrs
                    + Double.valueOf(labelKeyTO.getDisplayNamesMap().get(CommonConstants.DAY1_BOOKED_HRS));
        }
        dayWiseBookedHrsMap.put(CommonConstants.DAY1_BOOKED_HRS, day1TotalHrs);

        if (!CommonUtil.isBlankDouble(dayWiseBookedHrsMap.get(CommonConstants.DAY2_BOOKED_HRS))) {
            day2TotalHrs = day2TotalHrs + dayWiseBookedHrsMap.get(CommonConstants.DAY2_BOOKED_HRS);
        }
        if (CommonUtil.isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(CommonConstants.DAY2_BOOKED_HRS))) {
            day2TotalHrs = day3TotalHrs
                    + Double.valueOf(labelKeyTO.getDisplayNamesMap().get(CommonConstants.DAY2_BOOKED_HRS));
        }
        dayWiseBookedHrsMap.put(CommonConstants.DAY2_BOOKED_HRS, day2TotalHrs);

        if (!CommonUtil.isBlankDouble(dayWiseBookedHrsMap.get(CommonConstants.DAY3_BOOKED_HRS))) {
            day3TotalHrs = day3TotalHrs + dayWiseBookedHrsMap.get(CommonConstants.DAY3_BOOKED_HRS);
        }
        if (CommonUtil.isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(CommonConstants.DAY3_BOOKED_HRS))) {
            day3TotalHrs = day3TotalHrs
                    + Double.valueOf(labelKeyTO.getDisplayNamesMap().get(CommonConstants.DAY3_BOOKED_HRS));
        }
        dayWiseBookedHrsMap.put(CommonConstants.DAY3_BOOKED_HRS, day3TotalHrs);

        if (!CommonUtil.isBlankDouble(dayWiseBookedHrsMap.get(CommonConstants.DAY4_BOOKED_HRS))) {
            day4TotalHrs = day4TotalHrs + dayWiseBookedHrsMap.get(CommonConstants.DAY4_BOOKED_HRS);
        }
        if (CommonUtil.isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(CommonConstants.DAY4_BOOKED_HRS))) {
            day4TotalHrs = day4TotalHrs
                    + Double.valueOf(labelKeyTO.getDisplayNamesMap().get(CommonConstants.DAY4_BOOKED_HRS));
        }
        dayWiseBookedHrsMap.put(CommonConstants.DAY4_BOOKED_HRS, day4TotalHrs);

        if (!CommonUtil.isBlankDouble(dayWiseBookedHrsMap.get(CommonConstants.DAY5_BOOKED_HRS))) {
            day5TotalHrs = day5TotalHrs + dayWiseBookedHrsMap.get(CommonConstants.DAY5_BOOKED_HRS);
        }
        if (CommonUtil.isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(CommonConstants.DAY5_BOOKED_HRS))) {
            day5TotalHrs = day5TotalHrs
                    + Double.valueOf(labelKeyTO.getDisplayNamesMap().get(CommonConstants.DAY5_BOOKED_HRS));
        }
        dayWiseBookedHrsMap.put(CommonConstants.DAY5_BOOKED_HRS, day5TotalHrs);

        if (!CommonUtil.isBlankDouble(dayWiseBookedHrsMap.get(CommonConstants.DAY6_BOOKED_HRS))) {
            day6TotalHrs = day6TotalHrs + dayWiseBookedHrsMap.get(CommonConstants.DAY6_BOOKED_HRS);
        }
        if (CommonUtil.isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(CommonConstants.DAY6_BOOKED_HRS))) {
            day6TotalHrs = day6TotalHrs
                    + Double.valueOf(labelKeyTO.getDisplayNamesMap().get(CommonConstants.DAY6_BOOKED_HRS));
        }
        dayWiseBookedHrsMap.put(CommonConstants.DAY6_BOOKED_HRS, day6TotalHrs);

        if (!CommonUtil.isBlankDouble(dayWiseBookedHrsMap.get(CommonConstants.DAY7_BOOKED_HRS))) {
            day7TotalHrs = day7TotalHrs + dayWiseBookedHrsMap.get(CommonConstants.DAY7_BOOKED_HRS);
        }
        if (CommonUtil.isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(CommonConstants.DAY7_BOOKED_HRS))) {
            day7TotalHrs = day7TotalHrs
                    + Double.valueOf(labelKeyTO.getDisplayNamesMap().get(CommonConstants.DAY7_BOOKED_HRS));
        }
        dayWiseBookedHrsMap.put(CommonConstants.DAY7_BOOKED_HRS, day7TotalHrs);
    }

    public static void populateCurrentBookedHrs(Map<String, Double> bookedHrsMap, TimeSheetEmpDtlTO timeSheetEmpDtlTO) {
        for (TimeSheetEmpWorkDtlTO timeSheetEmpWorkDtlTO : timeSheetEmpDtlTO.getTimeSheetEmpWorkDtlTOs()) {
            if (bookedHrsMap.get(CommonConstants.DAY1_BOOKED_HRS) != null
                    && CommonUtil.isNonBlankDouble(timeSheetEmpWorkDtlTO.getDay1Hrs())) {
                bookedHrsMap.put(CommonConstants.DAY1_BOOKED_HRS,
                        bookedHrsMap.get(CommonConstants.DAY1_BOOKED_HRS) + timeSheetEmpWorkDtlTO.getDay1Hrs());
            } else {
                if (CommonUtil.isNonBlankDouble(timeSheetEmpWorkDtlTO.getDay1Hrs())) {
                    bookedHrsMap.put(CommonConstants.DAY1_BOOKED_HRS, timeSheetEmpWorkDtlTO.getDay1Hrs());
                }
            }
            if (bookedHrsMap.get(CommonConstants.DAY2_BOOKED_HRS) != null
                    && CommonUtil.isNonBlankDouble(timeSheetEmpWorkDtlTO.getDay2Hrs())) {
                bookedHrsMap.put(CommonConstants.DAY2_BOOKED_HRS,
                        bookedHrsMap.get(CommonConstants.DAY2_BOOKED_HRS) + timeSheetEmpWorkDtlTO.getDay2Hrs());
            } else {
                if (CommonUtil.isNonBlankDouble(timeSheetEmpWorkDtlTO.getDay2Hrs())) {
                    bookedHrsMap.put(CommonConstants.DAY2_BOOKED_HRS, timeSheetEmpWorkDtlTO.getDay2Hrs());
                }
            }
            if (bookedHrsMap.get(CommonConstants.DAY3_BOOKED_HRS) != null
                    && CommonUtil.isNonBlankDouble(timeSheetEmpWorkDtlTO.getDay3Hrs())) {
                bookedHrsMap.put(CommonConstants.DAY3_BOOKED_HRS,
                        bookedHrsMap.get(CommonConstants.DAY3_BOOKED_HRS) + timeSheetEmpWorkDtlTO.getDay3Hrs());
            } else {
                if (CommonUtil.isNonBlankDouble(timeSheetEmpWorkDtlTO.getDay3Hrs())) {
                    bookedHrsMap.put(CommonConstants.DAY3_BOOKED_HRS, timeSheetEmpWorkDtlTO.getDay3Hrs());
                }
            }
            if (bookedHrsMap.get(CommonConstants.DAY4_BOOKED_HRS) != null
                    && CommonUtil.isNonBlankDouble(timeSheetEmpWorkDtlTO.getDay4Hrs())) {
                bookedHrsMap.put(CommonConstants.DAY4_BOOKED_HRS,
                        bookedHrsMap.get(CommonConstants.DAY4_BOOKED_HRS) + timeSheetEmpWorkDtlTO.getDay4Hrs());
            } else {
                if (CommonUtil.isNonBlankDouble(timeSheetEmpWorkDtlTO.getDay4Hrs())) {
                    bookedHrsMap.put(CommonConstants.DAY4_BOOKED_HRS, timeSheetEmpWorkDtlTO.getDay4Hrs());
                }
            }

            if (bookedHrsMap.get(CommonConstants.DAY5_BOOKED_HRS) != null
                    && CommonUtil.isNonBlankDouble(timeSheetEmpWorkDtlTO.getDay5Hrs())) {
                bookedHrsMap.put(CommonConstants.DAY5_BOOKED_HRS,
                        bookedHrsMap.get(CommonConstants.DAY5_BOOKED_HRS) + timeSheetEmpWorkDtlTO.getDay5Hrs());
            } else {
                if (CommonUtil.isNonBlankDouble(timeSheetEmpWorkDtlTO.getDay5Hrs())) {
                    bookedHrsMap.put(CommonConstants.DAY5_BOOKED_HRS, timeSheetEmpWorkDtlTO.getDay5Hrs());
                }
            }

            if (bookedHrsMap.get(CommonConstants.DAY6_BOOKED_HRS) != null
                    && CommonUtil.isNonBlankDouble(timeSheetEmpWorkDtlTO.getDay6Hrs())) {
                bookedHrsMap.put(CommonConstants.DAY6_BOOKED_HRS,
                        bookedHrsMap.get(CommonConstants.DAY6_BOOKED_HRS) + timeSheetEmpWorkDtlTO.getDay6Hrs());
            } else {
                if (CommonUtil.isNonBlankDouble(timeSheetEmpWorkDtlTO.getDay6Hrs())) {
                    bookedHrsMap.put(CommonConstants.DAY6_BOOKED_HRS, timeSheetEmpWorkDtlTO.getDay6Hrs());
                }
            }

            if (bookedHrsMap.get(CommonConstants.DAY7_BOOKED_HRS) != null
                    && CommonUtil.isNonBlankDouble(timeSheetEmpWorkDtlTO.getDay7Hrs())) {
                bookedHrsMap.put(CommonConstants.DAY7_BOOKED_HRS,
                        bookedHrsMap.get(CommonConstants.DAY7_BOOKED_HRS) + timeSheetEmpWorkDtlTO.getDay7Hrs());
            } else {
                if (CommonUtil.isNonBlankDouble(timeSheetEmpWorkDtlTO.getDay7Hrs())) {
                    bookedHrsMap.put(CommonConstants.DAY7_BOOKED_HRS, timeSheetEmpWorkDtlTO.getDay7Hrs());
                }
            }
        }
    }

    public static boolean validateTimeSheetHrs(Integer maxHrs, Map<String, Double> currentBookedHrsMap,
            Map<String, Double> empBookedHrs) {

        final Integer maxHrsPerWeek = maxHrs * 7;

        double day1BookedHrs = 0.0;
        double day2BookedHrs = 0.0;
        double day3BookedHrs = 0.0;
        double day4BookedHrs = 0.0;
        double day5BookedHrs = 0.0;
        double day6BookedHrs = 0.0;
        double day7BookedHrs = 0.0;

        double day1 = 0.0;
        double day2 = 0.0;
        double day3 = 0.0;
        double day4 = 0.0;
        double day5 = 0.0;
        double day6 = 0.0;
        double day7 = 0.0;

        if (CommonUtil.isMapHasData(empBookedHrs)) {
            // Replace null with 0.0
            empBookedHrs = empBookedHrs.entrySet().stream().map(entry -> {
                if (entry.getValue() == null) {
                    entry.setValue(0.0);
                }
                return entry;
            }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            day1BookedHrs = empBookedHrs.getOrDefault(CommonConstants.DAY1_BOOKED_HRS, 0.0);
            day2BookedHrs = empBookedHrs.getOrDefault(CommonConstants.DAY2_BOOKED_HRS, 0.0);
            day3BookedHrs = empBookedHrs.getOrDefault(CommonConstants.DAY3_BOOKED_HRS, 0.0);
            day4BookedHrs = empBookedHrs.getOrDefault(CommonConstants.DAY4_BOOKED_HRS, 0.0);
            day5BookedHrs = empBookedHrs.getOrDefault(CommonConstants.DAY5_BOOKED_HRS, 0.0);
            day6BookedHrs = empBookedHrs.getOrDefault(CommonConstants.DAY6_BOOKED_HRS, 0.0);
            day7BookedHrs = empBookedHrs.getOrDefault(CommonConstants.DAY7_BOOKED_HRS, 0.0);
        }
        if (CommonUtil.isMapHasData(currentBookedHrsMap)) {

            // Replace null with 0.0
            currentBookedHrsMap = currentBookedHrsMap.entrySet().stream().map(entry -> {
                if (entry.getValue() == null) {
                    entry.setValue(0.0);
                }
                return entry;
            }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            day1 = currentBookedHrsMap.getOrDefault(CommonConstants.DAY1_BOOKED_HRS, 0.0);
            day2 = currentBookedHrsMap.getOrDefault(CommonConstants.DAY2_BOOKED_HRS, 0.0);
            day3 = currentBookedHrsMap.getOrDefault(CommonConstants.DAY3_BOOKED_HRS, 0.0);
            day4 = currentBookedHrsMap.getOrDefault(CommonConstants.DAY4_BOOKED_HRS, 0.0);
            day5 = currentBookedHrsMap.getOrDefault(CommonConstants.DAY5_BOOKED_HRS, 0.0);
            day6 = currentBookedHrsMap.getOrDefault(CommonConstants.DAY6_BOOKED_HRS, 0.0);
            day7 = currentBookedHrsMap.getOrDefault(CommonConstants.DAY7_BOOKED_HRS, 0.0);
        }

        return (((day1BookedHrs + day1) > maxHrsPerWeek) || ((day2BookedHrs + day2) > maxHrsPerWeek)
                || ((day3BookedHrs + day3) > maxHrsPerWeek) || ((day4BookedHrs + day4) > maxHrsPerWeek)
                || ((day5BookedHrs + day5) > maxHrsPerWeek) || ((day6BookedHrs + day6) > maxHrsPerWeek)
                || ((day7BookedHrs + day7) > maxHrsPerWeek));

    }

    public static String generateTimeSheetCode(TimeSheetEntity timeSheetEntity) {
        Integer additional = timeSheetEntity.getAdditional();
        EmpRegisterDtlEntity emp = timeSheetEntity.getEmpRegisterDtlEntity();
        StringBuilder code = new StringBuilder();
        if (emp == null && additional != null && additional > 0) {
            code.append("A");
        }
        code.append("TS-");
        code.append(timeSheetEntity.getProjMstrEntity().getCode());
        code.append("-");
        if (emp == null) {
            ProjCrewMstrEntity crew = timeSheetEntity.getProjCrewMstrEntity();
            if (crew != null)
                code.append(crew.getCode());
        } else {
            code.append(emp.getCode());
        }
        code.append("-");
        code.append(AppUtils.formatNumberToString(timeSheetEntity.getId()));
        return code.toString();
    }
}
