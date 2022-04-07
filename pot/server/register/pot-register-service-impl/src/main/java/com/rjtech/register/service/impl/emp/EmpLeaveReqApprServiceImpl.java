package com.rjtech.register.service.impl.emp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.service.impl.CommonEmailServiceImpl;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.service.EPSProjService;
import com.rjtech.register.constans.RegisterConstants;
import com.rjtech.register.emp.dto.EmpLeaveReqApprTO;
import com.rjtech.register.emp.model.EmpLeaveReqApprDetailEntity;
import com.rjtech.register.emp.model.EmpLeaveReqApprEntity;
import com.rjtech.register.emp.model.EmpNotificationsEntity;
import com.rjtech.register.emp.model.EmpPublicHolidayEntity;
import com.rjtech.register.emp.req.EmpLeaveReq;
import com.rjtech.register.emp.req.EmpLeaveReqApprSaveReq;
import com.rjtech.register.emp.resp.EmpLeaveReqApprResp;
import com.rjtech.register.plant.model.PlantNotificationsEntity;
import com.rjtech.register.repository.emp.EmpLeaveReqApprRepository;
import com.rjtech.register.repository.emp.EmpNotificationsRepository;
import com.rjtech.register.repository.emp.EmpRegisterRepository;
import com.rjtech.register.repository.emp.ProjSettingsEmpLeaveProcRepository;
import com.rjtech.register.service.emp.EmpLeaveReqApprService;
import com.rjtech.register.service.handler.emp.EmpLeaveReqApprDetailHandler;
import com.rjtech.register.service.handler.emp.EmpLeaveReqApprHandler;
import com.rjtech.register.service.handler.emp.EmpPublicHolidayHandler;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.timemanagement.attendence.repository.EmpAttendanceDtlRepositoryCopy;
import com.rjtech.user.dto.UserProjDetailsTO;
import com.rjtech.user.model.UserEntity;
import com.rjtech.user.repository.UserRepository;
import com.rjtech.user.req.UserProjGetReq;
import com.rjtech.user.resp.UserProjResp;


@Service(value = "empLeaveReqApprService")
@RJSService(modulecode = "empLeaveReqApprService")
@Transactional
public class EmpLeaveReqApprServiceImpl implements EmpLeaveReqApprService {
	
	private static final Logger log = LoggerFactory.getLogger(EmpLeaveReqApprServiceImpl.class);
	
	private static String pot = "\"Project on Track\"";
	
	@Autowired
    private CommonEmailServiceImpl commonEmail;

    @Autowired
    private EmpLeaveReqApprRepository empLeaveReqApprRepository;

    @Autowired
    private EmpNotificationsRepository empNotificationsRepository;

    @Autowired
    private ProjSettingsEmpLeaveProcRepository projSettingsEmpLeaveProcRepository;

    @Autowired
    private EmpRegisterRepository empRegisterRepository;

    @Autowired
    private EPSProjRepository epsProjRepository;

    @Autowired
    private EmpAttendanceDtlRepositoryCopy empAttendanceDtlRepository;
    
    @Autowired
    private EPSProjService epsProjService;
    
    @Autowired
	private UserRepository userRepository;

    public EmpLeaveReqApprResp getEmpLeaveReqApprovals(EmpLeaveReq empLeaveReq) {
        EmpLeaveReqApprResp empLeaveReqApprResp = new EmpLeaveReqApprResp();
        List<EmpLeaveReqApprEntity> empLeaveReqApprEntities;
        Date fromDate = null;
        Date toDate = null;

        if (CommonUtil.isNotBlankStr(empLeaveReq.getFromDate()) && CommonUtil.isNotBlankStr(empLeaveReq.getToDate())) {
            fromDate = CommonUtil.convertStringToDate(empLeaveReq.getFromDate());
            toDate = CommonUtil.convertStringToDate(empLeaveReq.getToDate());
        } else {
            toDate = new Date();
            fromDate = CommonUtil.substarctInputMonths(toDate, -1);
        }
        String apprStatus = null;

        if (CommonUtil.isBlankStr(empLeaveReq.getApprStatus())
                || RegisterConstants.ALL.equalsIgnoreCase(empLeaveReq.getApprStatus())) {
        	empLeaveReqApprEntities = empLeaveReqApprRepository.findAllEmpLeaveApprs(fromDate, toDate, empLeaveReq.getProjId(), empLeaveReq.getId());            
        } else {
            empLeaveReqApprEntities = empLeaveReqApprRepository.findEmpLeaveReqs(empLeaveReq.getId(),
                    empLeaveReq.getStatus(), apprStatus, fromDate, toDate);
        }
        EmpLeaveReqApprTO empLeaveReqApprTO = null;

        for (EmpLeaveReqApprEntity empLeaveReqApprEntity : empLeaveReqApprEntities) {
            empLeaveReqApprTO = EmpLeaveReqApprHandler.convertEntityToPOJO(empLeaveReqApprEntity);
            empLeaveReqApprResp.getEmpLeaveReqApprTOs().add(empLeaveReqApprTO);
            int count = empAttendanceDtlRepository.getEmpAttendanceCount(empLeaveReqApprTO.getEmpRegId(),
                    empLeaveReqApprTO.getStartDate(), empLeaveReqApprTO.getEndDate());
            boolean flag = (count >= 0);
            empLeaveReqApprTO.setLeaveAvailed(flag);
        }
        return empLeaveReqApprResp;
    }

    public EmpLeaveReqApprResp getEmpLeaveApprovals(EmpLeaveReq empLeaveReq) {
        EmpLeaveReqApprResp empLeaveReqApprResp = new EmpLeaveReqApprResp();
        AppUserUtils appUserUtils = new AppUserUtils();
        Date fromDate = null;
        Date toDate = null;

        if (CommonUtil.isNotBlankStr(empLeaveReq.getFromDate()) && CommonUtil.isNotBlankStr(empLeaveReq.getToDate())) {
            fromDate = CommonUtil.convertStringToDate(empLeaveReq.getFromDate());
            toDate = CommonUtil.convertStringToDate(empLeaveReq.getToDate());
        } else {
            toDate = new Date();
            fromDate = CommonUtil.substarctInputMonths(toDate, -1);
        }
        toDate = CommonUtil.addNumberOfDaysInputDate(toDate, 1);
        List<EmpLeaveReqApprEntity> empLeaveReqApprEntities;
        if (CommonUtil.isBlankStr(empLeaveReq.getApprStatus())
                || RegisterConstants.ALL.equalsIgnoreCase(empLeaveReq.getApprStatus())) {
           // changing the findAllEmpLeaveApprs name as it has same name  and same type for both leave request and approval 
        	empLeaveReqApprEntities = empLeaveReqApprRepository.findAllEmpLeaveApprs(fromDate, toDate,empLeaveReq.getProjId(), appUserUtils.getUserId(), empLeaveReq.getId());
        } else {
            empLeaveReqApprEntities = empLeaveReqApprRepository.findEmpLeaveApprs(empLeaveReq.getApprStatus().toUpperCase(), fromDate,
                    toDate,empLeaveReq.getProjId(), empLeaveReq.getId());
        }
        for (EmpLeaveReqApprEntity empLeaveReqApprEntity : empLeaveReqApprEntities) {
            EmpLeaveReqApprTO empLeaveReqApprTO = EmpLeaveReqApprHandler.convertEntityToPOJO(empLeaveReqApprEntity);
            empLeaveReqApprResp.getEmpLeaveReqApprTOs().add(empLeaveReqApprTO);
            int count = empAttendanceDtlRepository.getEmpAttendanceCount(empLeaveReqApprTO.getEmpRegId(),
                    empLeaveReqApprTO.getStartDate(), empLeaveReqApprTO.getEndDate());
            boolean flag = (count >= 0);
            empLeaveReqApprTO.setLeaveAvailed(flag);
        }
        return empLeaveReqApprResp;
    }

    public EmpLeaveReqApprResp getEmpLeaveReqApprovalDetails(EmpLeaveReq empLeaveReq) {
        EmpLeaveReqApprResp empLeaveReqApprResp = new EmpLeaveReqApprResp();
        EmpLeaveReqApprEntity empLeaveReqApprEntity = empLeaveReqApprRepository.findOne(empLeaveReq.getId());
        EmpLeaveReqApprTO empLeaveReqApprTO = EmpLeaveReqApprHandler.convertEntityToPOJO(empLeaveReqApprEntity);
        for (EmpLeaveReqApprDetailEntity empLeaveReqApprDetailEntity : empLeaveReqApprEntity
                .getEmpLeaveReqApprDetailEntities()) {
            empLeaveReqApprTO.getEmpLeaveReqApprDetailTOs()
                    .add(EmpLeaveReqApprDetailHandler.convertEntityToPOJO(empLeaveReqApprDetailEntity));
        }
        for (EmpPublicHolidayEntity empPublicHolidayEntity : empLeaveReqApprEntity.getEmpPublicHolidayEntities()) {
            if (RegisterConstants.PUBLIC_HILDAY.equalsIgnoreCase(empPublicHolidayEntity.getType())) {
                empLeaveReqApprTO.getEmpPublicHolidayTOs()
                        .add(EmpPublicHolidayHandler.convertEntityToPOJO(empPublicHolidayEntity));
            } else if (RegisterConstants.ROSTER_DAY.equalsIgnoreCase(empPublicHolidayEntity.getType())) {
                empLeaveReqApprTO.getEmpRosterDays()
                        .add(EmpPublicHolidayHandler.convertEntityToPOJO(empPublicHolidayEntity));
            }
        }
        empLeaveReqApprResp.getEmpLeaveReqApprTOs().add(empLeaveReqApprTO);
        return empLeaveReqApprResp;
    }

    public EmpLeaveReqApprResp saveEmpLeaveReqApprovals(EmpLeaveReqApprSaveReq empLeaveReqApprSaveReq) {
    	log.info("public EmpLeaveReqApprResp saveEmpLeaveReqApprovals");
        List<EmpLeaveReqApprEntity> empLeaveReqApprEntities = new ArrayList<>();
        EmpLeaveReqApprResp appResp = new EmpLeaveReqApprResp();
        EmpLeaveReqApprEntity empLeaveReqApprEntity = null;
        EmpNotificationsEntity empNotificationsEntity = null;
        EmpLeaveReqApprEntity parentEmpLeaveReqApprEntity = null;
        for (EmpLeaveReqApprTO empLeaveReqApprTO : empLeaveReqApprSaveReq.getEmpLeaveReqApprTOs()) {
        	log.info("empLeaveReqApprTO.getEmpRegId() " + empLeaveReqApprTO.getEmpRegId());
            List<EmpLeaveReqApprEntity> leaveReqApprEntity = empLeaveReqApprRepository
                    .findLatestEmpLeaveRequest(empLeaveReqApprTO.getEmpRegId());
            if (CommonUtil.objectNotNull(leaveReqApprEntity)) {
                Date startDate = CommonUtil.convertStringToDate(empLeaveReqApprTO.getStartDate());
                for (EmpLeaveReqApprEntity entity : leaveReqApprEntity) {
                    if (entity.getEndDate().before(startDate)) {
                        appResp.cloneAppResp(CommonUtil.getWarningAppResp());
                        return appResp;
                    }
                }
            }
            empLeaveReqApprEntity = EmpLeaveReqApprHandler.populateEmpLeaveReApprEntity(empLeaveReqApprTO,
                    epsProjRepository, empRegisterRepository);
            if (CommonUtil.isBlankStr(empLeaveReqApprTO.getApprCode())) {
                empLeaveReqApprEntity.setNotifyCode(empLeaveReqApprTO.getNotifyCode());
            }
            empLeaveReqApprEntities.add(empLeaveReqApprEntity);
            empLeaveReqApprEntity.setApprStatus(empLeaveReqApprTO.getApprStatus());
            empNotificationsEntity = populateNotificationEntity(empLeaveReqApprEntity);
            log.info("empLeaveReqApprTO.getId() blank or not " + CommonUtil.isBlankLong(empLeaveReqApprTO.getId()));
            // While Approve goes here....
            if (CommonUtil.isBlankLong(empLeaveReqApprTO.getId())) {
            /*    parentEmpLeaveReqApprEntity = EmpLeaveReqApprHandler
                        .populateEmpLeaveReApprEntity(empLeaveReqApprTO, epsProjRepository, empRegisterRepository); */
                empLeaveReqApprEntity.setNotifyCode(empNotificationsEntity.getCode());
          //      empLeaveReqApprEntity.setParentEmpLeaveReqApprEntity(parentEmpLeaveReqApprEntity);
                empLeaveReqApprEntity.setEmpNotificationsEntity(empNotificationsEntity);
                empLeaveReqApprEntity.setApprStatus(RegisterConstants.PENDING_FOR_APPROVAL);
                empNotificationsEntity.setNotificationStatus(CommonConstants.APPR_STATUS_PENDING);
         //       empLeaveReqApprEntities.add(parentEmpLeaveReqApprEntity);
                empNotificationsRepository.save(empNotificationsEntity);
            }
        }
        empLeaveReqApprRepository.save(empLeaveReqApprEntities);
        sendEmailNotification(empNotificationsEntity);
        return appResp;
    }
    
    private void sendEmailNotification(EmpNotificationsEntity empNotificationsEntity) {
    	String epsName = null;
		String projName = null;
		String leaveStartDate = null;
		String noOfDays = null;
		String ccEmail;
		String toEmail;
		String toSubject;
		String text;
		String reqDate = null;
		
		UserProjGetReq userProjGetReq = new UserProjGetReq();
        UserProjResp userProjResp = epsProjService.getAllUserProjects(userProjGetReq);
        Map<Long, LabelKeyTO> userProjMap = new HashMap<>();
        LabelKeyTO userProjLabelKeyTO = null;
        for (UserProjDetailsTO userProjDetailsTO : userProjResp.getUserProjDetailsTOs()) {
            userProjLabelKeyTO = new LabelKeyTO();
            userProjLabelKeyTO.setId(userProjDetailsTO.getProjId());
            userProjLabelKeyTO.setCode(userProjDetailsTO.getParentName());
            userProjLabelKeyTO.setName(userProjDetailsTO.getProjName());
            userProjMap.put(userProjDetailsTO.getProjId(), userProjLabelKeyTO);
        }
        for (Map.Entry<Long, LabelKeyTO> entry : userProjMap.entrySet()) {
        	if (CommonUtil.objectNotNull(empNotificationsEntity.getProjMstrEntity())
                    && empNotificationsEntity.getProjMstrEntity().getProjectId().equals(entry.getKey())) {
                epsName = entry.getValue().getCode();
                projName = entry.getValue().getName();
            }
        }

        UserEntity reqUserId = userRepository.findOne(empNotificationsEntity.getReqUserId().getUserId());
        UserEntity apprUserId = userRepository.findOne(empNotificationsEntity.getApprUserId().getUserId());

        toEmail = apprUserId.getEmail();
        ccEmail = reqUserId.getEmail();

    	EmpLeaveReqApprEntity leaveReqApprEntity = empLeaveReqApprRepository.findEmpLeaveReqApprRecord(empNotificationsEntity.getId());
		
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    	SimpleDateFormat dateFormatStartDate = new SimpleDateFormat("dd-MMM-yyyy");
		if(leaveReqApprEntity.getStartDate() != null) {
			leaveStartDate = dateFormatStartDate.format(leaveReqApprEntity.getStartDate());
		}
		if(leaveReqApprEntity.getTotalDays() != null) {
			noOfDays = leaveReqApprEntity.getTotalDays().toString();
		}
        
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yy");
    	reqDate = dateFormat1.format(empNotificationsEntity.getDate());
    	
        String reqCode = ModuleCodesPrefixes.EMP_REG_PREFIX.getDesc().concat("-" + ModuleCodesPrefixes.REQUEST_PREFIX.getDesc())
                .concat("-" + leaveReqApprEntity.getProjMstrEntity().getCode()).concat("-" + leaveReqApprEntity.getId()) + " dated " + reqDate;;
        
        String apprCode = ModuleCodesPrefixes.EMP_REG_PREFIX.getDesc().concat("-" + ModuleCodesPrefixes.APPROVE_PREFIX.getDesc())
                .concat("-" + leaveReqApprEntity.getProjMstrEntity().getCode()).concat("-" + leaveReqApprEntity.getId());
        
        String notifyCode = ModuleCodesPrefixes.EMP_REG_PREFIX.getDesc()
                .concat("-" + ModuleCodesPrefixes.NOTIFICATION_PREFIX.getDesc())
                .concat("-" + leaveReqApprEntity.getProjMstrEntity().getCode()).concat("-" + leaveReqApprEntity.getId()) + " dated " + reqDate;;
    	
    	
        String apprDate = dateFormat.format(empNotificationsEntity.getDate());
        
        String apprDec = empNotificationsEntity.getNotificationStatus();
        String apprDecision =apprDec.substring(0, 1).toUpperCase() + apprDec.substring(1).toLowerCase();
        
        if (empNotificationsEntity.getNotificationStatus().equalsIgnoreCase("Pending")) {
	    	toSubject = "Request for Leave";
	    	text = "<html><body><p>" + apprUserId.getDisplayName() + ",</p>"
	                + "<p>I have submitted my request for Leave approval through " + pot + ", as per details mentioned here below.</p>"
	                + "<table border='1'>"
	                + "<tr><td>Company </td><td>" + epsName + "</td></tr>"
	                + "<tr><td>Project </td><td>"+ projName + "</td></tr>"
	                + "<tr><td>Employee ID </td><td>" + leaveReqApprEntity.getEmpRegisterDtlEntity().getCode() + "</td></tr>"
	                + "<tr><td>Employee Name</td><td>" + leaveReqApprEntity.getEmpRegisterDtlEntity().getFirstName() + " " + leaveReqApprEntity.getEmpRegisterDtlEntity().getLastName() + "</td></tr>"
	                + "<tr><td>Requisition ID</td><td>" + reqCode + "</td></tr>"
	                + "<tr><td>Notification ID</td><td>"+ notifyCode +"</td></tr>"
	                + "<tr><td>Leave Start Date</td><td>"+ leaveStartDate +"</td></tr>"
	                + "<tr><td>Number of Days</td><td>"+ noOfDays +"</td></tr>"
	                + "</table>"
	                + "<p>This is for your approval please.</p>" 
	                + "<p>Regards,</p>" 
	                + "<p>" + AppUserUtils.getName() + "<br/>" + AppUserUtils.getDisplayRole() + "<br/>" + apprDate + "</p>"
	                + "</body></html>";
	    	
	    	commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
        } else {
	    	toSubject = "Response to Leave Request";
	    	text = "<html><body><p>" + reqUserId.getDisplayName() + ",</p>"
	                + "<p>Reference Notification ID " + notifyCode + "</p>"
	                + "<p>I have transmitted my decision for Leave approval through " + pot + ", as per details mentioned here below.</p>"
	                + "<table border='1'>"
	                + "<tr><td>Company </td><td>" + epsName + "</td></tr>"
	                + "<tr><td>Project </td><td>"+ projName + "</td></tr>"
	                + "<tr><td>Employee ID </td><td>" + leaveReqApprEntity.getEmpRegisterDtlEntity().getCode() + "</td></tr>"
	                + "<tr><td>Employee Name</td><td>" + leaveReqApprEntity.getEmpRegisterDtlEntity().getFirstName() + " " + leaveReqApprEntity.getEmpRegisterDtlEntity().getLastName() + "</td></tr>"
	                + "<tr><td>Requisition ID</td><td>" + reqCode + "</td></tr>"
	                + "<tr><td>Notification ID</td><td>"+ notifyCode +"</td></tr>"
	                + "<tr><td>Leave Start Date</td><td>"+ leaveStartDate +"</td></tr>"
	                + "<tr><td>Number of Days</td><td>"+ noOfDays +"</td></tr>"
	                + "<tr><td>Approver Decision</td><td>"+ apprDecision +"</td></tr>"
	                + "</table>"
	                + "<p>This is for your information please.</p>" 
	                + "<p>Regards,</p>" 
	                + "<p>" + AppUserUtils.getName() + "<br/>" + AppUserUtils.getDisplayRole() + "<br/>" + apprDate + "</p>"
	                + "</body></html>";
	    	
	    	commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
        }
    	
    }
    
    public EmpNotificationsEntity populateNotificationEntity(EmpLeaveReqApprEntity empLeaveReqApprEntity) {
        EmpNotificationsEntity empNotificationsEntity = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        
        if (CommonUtil.isBlankLong(empLeaveReqApprEntity.getId())) {
            empNotificationsEntity = new EmpNotificationsEntity();
            if (RegisterConstants.PENDING_FOR_APPROVAL.equalsIgnoreCase(empLeaveReqApprEntity.getApprStatus())) {
                empNotificationsEntity.setNotificationStatus(CommonConstants.APPR_STATUS_PENDING);
            }
            if (CommonUtil.objectNotNull(empLeaveReqApprEntity.getProjMstrEntity())) {
                ProjMstrEntity projMstrEntity = empLeaveReqApprEntity.getProjMstrEntity();
                empNotificationsEntity.setProjMstrEntity(projMstrEntity);
            }
            empNotificationsEntity.setDate(new Date());
            empNotificationsEntity.setApprUserId(empLeaveReqApprEntity.getApprUserEntity());
            empNotificationsEntity.setReqUserId(empLeaveReqApprEntity.getReqUserEntity());
            String startDate = dateFormat.format(empLeaveReqApprEntity.getStartDate());
            empNotificationsEntity.setType("Request for Leave from " + startDate + " for " + empLeaveReqApprEntity.getTotalDays() + " days");
            //Request for  Leave  from   --------- (auto fill in " Start date  as per Leave request form") for  ----------- days (  auto filll in  net  leave days as per  leave request form) 
            empNotificationsEntity.setStatus(StatusCodes.ACTIVE.getValue());
            empLeaveReqApprEntity.setEmpNotificationsEntity(empNotificationsEntity);
            empNotificationsEntity.setReqCode(empLeaveReqApprEntity.getReqCode());
            empNotificationsEntity.setEmpStatus(RegisterConstants.EMP_LEAVE);
        } else {
            empNotificationsEntity = empNotificationsRepository
                    .findOne(empLeaveReqApprEntity.getEmpNotificationsEntity().getId());
            if (RegisterConstants.APPROVED.equalsIgnoreCase(empLeaveReqApprEntity.getApprStatus())) {
                empNotificationsEntity.setNotificationStatus(CommonConstants.APPR_STATUS_APPROVED);
            } else if (RegisterConstants.REJECTED.equalsIgnoreCase(empLeaveReqApprEntity.getApprStatus())) {
                empNotificationsEntity.setNotificationStatus(CommonConstants.APPR_STATUS_REJECTED);
            }
            String startDate = dateFormat.format(empLeaveReqApprEntity.getStartDate());
        	empNotificationsEntity.setType("Approver decision notification For leave Request from " + startDate + " for " + empLeaveReqApprEntity.getTotalDays() + " days");
        }
        return empNotificationsEntity;
    }

    public LabelKeyTOResp getProjSettingsEmpLeaveCheck(EmpLeaveReq empLeaveReq) {
        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        if (CommonUtil.isNonBlankLong(empLeaveReq.getProjId())
                && CommonUtil.isNotBlankStr(empLeaveReq.getApprStatus())) {
            List<LabelKeyTO> labelKeyTOs = projSettingsEmpLeaveProcRepository.getProSettingsForEmpLeaveCheck(
                    empLeaveReq.getProjId(), empLeaveReq.getId(), empLeaveReq.getApprStatus());
            labelKeyTOResp.getLabelKeyTOs().addAll(labelKeyTOs);

        }
        return labelKeyTOResp;
    }

}
