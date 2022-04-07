package com.rjtech.register.service.impl.emp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.service.impl.CommonEmailServiceImpl;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.service.EPSProjService;
import com.rjtech.register.constans.RegisterConstants;
import com.rjtech.register.dto.NotificationsGetReq;
import com.rjtech.register.emp.dto.EmpTransReqApprDetailTO;
import com.rjtech.register.emp.dto.EmpTransferReqApprTO;
import com.rjtech.register.emp.model.EmpNotificationsEntity;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.emp.model.EmpTransReqApprDetailEntity;
import com.rjtech.register.emp.model.EmpTransferReqApprEntity;
import com.rjtech.register.emp.req.EmpTransReq;
import com.rjtech.register.emp.req.EmpTransSaveReq;
import com.rjtech.register.emp.resp.EmpNotificationsResp;
import com.rjtech.register.emp.resp.EmpTransResp;
import com.rjtech.register.proc.emp.EmpTransferReqApprProcRepository;
import com.rjtech.register.repository.emp.EmpNotificationsRepository;
import com.rjtech.register.repository.emp.EmpProjRegisterRepository;
import com.rjtech.register.repository.emp.EmpRegisterRepository;
import com.rjtech.register.repository.emp.EmpTransferReqAprRepository;
import com.rjtech.register.service.emp.EmpTransferReqApprService;
import com.rjtech.register.service.handler.emp.EmpNotificationsHandler;
import com.rjtech.register.service.handler.emp.EmpTransReqApprDetailHandler;
import com.rjtech.register.service.handler.emp.EmpTransReqApprHandler;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.user.dto.UserProjDetailsTO;
import com.rjtech.user.req.UserProjGetReq;
import com.rjtech.user.resp.UserProjResp;

@Service(value = "empTransferReqApprService")
@RJSService(modulecode = "empTransferReqApprService")
@Transactional
public class EmpTransferReqApprServiceImpl implements EmpTransferReqApprService {
	
	private static final Logger log = LoggerFactory.getLogger(EmpTransferReqApprServiceImpl.class);

	private static String pot = "\"Project on Track\"";
	
    @Autowired
    private CommonEmailServiceImpl commonEmail;

    @Autowired
    private EmpTransferReqAprRepository empTransferReqAprRepository;

    @Autowired
    private EmpProjRegisterRepository empProjRegisterRepository;

    @Autowired
    private EmpNotificationsRepository empNotificationsRepository;

    @Autowired
    private EmpTransferReqApprProcRepository empTransferReqApprProcRepository;

    @Autowired
    private EPSProjService epsProjService;

    @Autowired
    private EPSProjRepository epsProjRepository;

    @Autowired
    private EmpRegisterRepository empRegisterRepository;

    @Autowired
    private LoginRepository loginRepository;

    public EmpTransResp getEmpTranfers(EmpTransReq empTransReq) {
    	log.info("public EmpTransResp getEmpTranfers");
        EmpTransResp empReqForTransResp = new EmpTransResp();
        Date fromDate = null;
        Date toDate = null;
        if (CommonUtil.isNotBlankStr(empTransReq.getFromDate()) && CommonUtil.isNotBlankStr(empTransReq.getToDate())) {
            fromDate = CommonUtil.convertStringToDate(empTransReq.getFromDate());
            toDate = CommonUtil.convertStringToDate(empTransReq.getToDate());
        } else {
            toDate = new Date();
            fromDate = CommonUtil.substarctInputMonths(toDate, -1);
        }
        toDate = CommonUtil.addNumberOfDaysInputDate(toDate, 1);
        List<EmpTransferReqApprEntity> empReqTransEntities = null;
        List<Long> projIds = epsProjService.getUserProjIds();
        if (projIds.isEmpty())
            return empReqForTransResp;

        Long userId = null;
        if (empTransReq.isLoginUser()) {
            userId = AppUserUtils.getUserId();
        }
        log.info("empTransReq.isTransType() " + empTransReq.isTransType());
        if (empTransReq.isTransType()) {
        	log.info("empTransReq.isTransType() - TRUE");
            empReqTransEntities = empTransferReqAprRepository.findEmpReqTranfers(projIds, userId, fromDate, toDate);
        } else {
        	log.info("empTransReq.isTransType() - FALSE");
            if (RegisterConstants.ALL.equalsIgnoreCase(empTransReq.getApprStatus())) {
                empReqTransEntities = empTransferReqAprRepository.findEmpAllTranfers(projIds, userId, fromDate, toDate);
            } else {
                empReqTransEntities = empTransferReqAprRepository.findEmpApprTranfers(projIds, userId,
                        empTransReq.getApprStatus(), fromDate, toDate);
            }
        }
        if (CommonUtil.isListHasData(empReqTransEntities)) {
            for (EmpTransferReqApprEntity empReqTransEntity : empReqTransEntities) {
                EmpTransferReqApprTO empTransferReqApprTO = EmpTransReqApprHandler
                        .convertEntityToPOJO(empReqTransEntity);
                for (EmpTransReqApprDetailEntity empTransReqApprDetailEntity : empReqTransEntity
                        .getEmpTransReqApprDetailEntities()) {
                    empTransferReqApprTO.getEmpTransReqApprDetailTOs()
                            .add(EmpTransReqApprDetailHandler.convertEntityToPOJO(empTransReqApprDetailEntity));
                }
                empReqForTransResp.getEmpReqTransTOs().add(empTransferReqApprTO);
            }
        }
        return empReqForTransResp;
    }

    public EmpTransResp getEmpTranferReqDetails(EmpTransReq empTransReq) {
    	log.info("public EmpTransResp getEmpTranferReqDetails");
        EmpTransResp empReqForTransResp = new EmpTransResp();
        EmpTransferReqApprEntity transferReqApprEntity = empTransferReqAprRepository.findOne(empTransReq.getId());
        EmpTransferReqApprTO empTransferReqApprTO = EmpTransReqApprHandler.convertEntityToPOJO(transferReqApprEntity);
        for (EmpTransReqApprDetailEntity transReqApprDetialEntity : transferReqApprEntity
                .getEmpTransReqApprDetailEntities()) {
            empTransferReqApprTO.getEmpTransReqApprDetailTOs()
                    .add(EmpTransReqApprDetailHandler.convertEntityToPOJO(transReqApprDetialEntity));
        }
        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        List<LabelKeyTO> labelKeyTOs = empTransferReqApprProcRepository.getEmpTransferReqDetails(empTransReq.getId());
        labelKeyTOResp.setLabelKeyTOs(labelKeyTOs);
        for (LabelKeyTO labelKeyTO : labelKeyTOs) {
            empReqForTransResp.getTransferEmpMap().put(labelKeyTO.getId(), labelKeyTO);
        }
        empReqForTransResp.getEmpReqTransTOs().add(empTransferReqApprTO);
        return empReqForTransResp;
    }
    // This function will be called to save the request details under the Request for Transfer tab or Approval for Transfer
    public EmpTransResp saveEmpTranfers(EmpTransSaveReq empTransSaveReq) {
    	log.info("public EmpTransResp saveEmpTranfers");
        List<EmpTransferReqApprEntity> empTransferReqEntities = new ArrayList<>();
        if (CommonUtil.isListHasData(empTransSaveReq.getEmpReqTransTOs())) {
            for (EmpTransferReqApprTO empTransferReqTO : empTransSaveReq.getEmpReqTransTOs()) {
                // Transfer request details
                EmpTransferReqApprEntity empTransferReqApprEntity = EmpTransReqApprHandler
                        .convertPOJOToEntity(empTransferReqTO, epsProjRepository, loginRepository);
                empTransferReqApprEntity
                        .setNotificationStatus(empTransSaveReq.getEmpTransReq().getNotificationStatus());

                EmpNotificationsEntity empNotificationsEntity = populateNotificationEntity(empTransferReqApprEntity);
                EmpNotificationsEntity notificationsEntity = empNotificationsRepository.save(empNotificationsEntity);
                empTransferReqApprEntity.setEmpNotificationsEntity(notificationsEntity);
                if (CommonUtil.isListHasData(empTransferReqTO.getEmpTransReqApprDetailTOs())) {
                    // empTransferReqTO.getEmpTransReqApprDetailTOs() is number of employees under this request
                    for (EmpTransReqApprDetailTO empTransReqApprDetailTO : empTransferReqTO
                            .getEmpTransReqApprDetailTOs()) {
                        EmpTransReqApprDetailEntity empTransReqApprDetailEntity = EmpTransReqApprDetailHandler
                                .convertPOJOToEntity(empTransReqApprDetailTO, empRegisterRepository);
                        empTransReqApprDetailEntity.setEmpTransferReqApprEntity(empTransferReqApprEntity);
                        empTransferReqApprEntity.getEmpTransReqApprDetailEntities().add(empTransReqApprDetailEntity);
                        if (RegisterConstants.APPROVED.equalsIgnoreCase(empTransferReqTO.getApprStatus())) {
							 EmpProjRigisterEntity empProjRigisterEntity = empProjRegisterRepository.findLatestEmployeDeployment(empTransReqApprDetailTO.getEmpRegId());
							 
                            //calling the list of empprojregisterEntity lastest, setting the status as relived and IS_LATEST as N 
                            List<EmpProjRigisterEntity> empProjRigisterEntities = empProjRegisterRepository.findProjEmpRigisters(empTransReqApprDetailTO.getEmpRegId());
                            for(EmpProjRigisterEntity empProjRigisterEntitiy: empProjRigisterEntities ) {
                            	empProjRigisterEntitiy.setIsLatest(RegisterConstants.UPDATE_IS_LATEST);
                        //        empProjRigisterEntitiy.setDemobilizationStatus("Relieved"); not required this line ignore this.
                            }
                            // Set info to latest
                            cloneEmpToNewProj(empProjRigisterEntity, empTransReqApprDetailTO,
                                    empTransferReqTO.getToProjId());
                        }
                    }
                }
                // TODO make mail notification as single call. Remove mailing in populateNotificationEntity, and reuse this template on approve as well.
                if (CommonUtil.isBlankLong(empTransferReqTO.getId()))
                    sendEmailNotification(notificationsEntity);
                empTransferReqEntities.add(empTransferReqApprEntity);
            }
            // Generate and save transfer codes
            empTransferReqEntities = empTransferReqAprRepository.save(empTransferReqEntities);
            boolean transType = empTransSaveReq.getEmpTransReq().isTransType();
            for (EmpTransferReqApprEntity entity : empTransferReqEntities) {
                setTransferCode(entity, transType);
            }
        }
        EmpTransResp empTransResp = new EmpTransResp();
        if (CommonUtil.isListHasData(empTransferReqEntities)) {
            for (EmpTransferReqApprEntity empReqTransEntity : empTransferReqEntities) {
                EmpTransferReqApprTO empTransferReqApprTO = EmpTransReqApprHandler
                        .convertEntityToPOJO(empReqTransEntity);
                for (EmpTransReqApprDetailEntity empTransReqApprDetailEntity : empReqTransEntity
                        .getEmpTransReqApprDetailEntities()) {
                    empTransferReqApprTO.getEmpTransReqApprDetailTOs()
                            .add(EmpTransReqApprDetailHandler.convertEntityToPOJO(empTransReqApprDetailEntity));
                }
                empTransResp.getEmpReqTransTOs().add(empTransferReqApprTO);
            }
        }
        return empTransResp;
    }

    private EmpNotificationsEntity populateNotificationEntity(EmpTransferReqApprEntity empTransferReqEntity) {
    	log.info("private EmpNotificationsEntity populateNotificationEntity");
        EmpNotificationsEntity empNotificationsEntity = null;
        String notificationMsg = "Request for Approval";
        if (CommonUtil.isBlankLong(empTransferReqEntity.getId())) {
            empNotificationsEntity = new EmpNotificationsEntity();

            if (RegisterConstants.PENDING_FOR_APPROVAL.equalsIgnoreCase(empTransferReqEntity.getApprStatus())) {
                empNotificationsEntity.setNotificationStatus(CommonConstants.APPR_STATUS_PENDING);
                empNotificationsEntity.setNotificationMsg(notificationMsg);
                String toprojName = null;
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
                    if (empTransferReqEntity.getToProjMstrEntity().getProjectId().equals(entry.getKey())) {
                        toprojName = entry.getValue().getName();
                    }

                }

                empNotificationsEntity.setType("Request for employee transfer to Project - " + toprojName);

            }
            empNotificationsEntity.setProjMstrEntity(
                    epsProjRepository.findOne(empTransferReqEntity.getFromProjMstrEntity().getProjectId()));
            if (CommonUtil.objectNotNull(empTransferReqEntity.getFromProjMstrEntity())) {
                empNotificationsEntity.setForProject(empTransferReqEntity.getToProjMstrEntity().getProjectId());
            }
            empNotificationsEntity.setDate(new Date());
            empNotificationsEntity.setReqUserId(empTransferReqEntity.getReqUserMstrEntity());
            empNotificationsEntity.setApprUserId(empTransferReqEntity.getApprUserMstrEntity());
            empNotificationsEntity.setStatus(StatusCodes.ACTIVE.getValue());
            empNotificationsEntity.setEmpStatus(RegisterConstants.EMP_TRANSFER);
        } else {
            empNotificationsEntity = empNotificationsRepository
                    .findOne(empTransferReqEntity.getEmpNotificationsEntity().getId());
            
            
            if (RegisterConstants.APPROVED.equalsIgnoreCase(empTransferReqEntity.getApprStatus())) {
                empNotificationsEntity.setNotificationStatus(CommonConstants.APPR_STATUS_APPROVED);
            } else if (RegisterConstants.REJECTED.equalsIgnoreCase(empTransferReqEntity.getApprStatus())) {
                empNotificationsEntity.setNotificationStatus(CommonConstants.APPR_STATUS_REJECTED);
            }
            // normal approved Email notification for emptransfer

            String epsName = null;
            String projName = null;
            String ccEmail;
            String toEmail;
            String toSubject;
            String text;
            String toepsName = null;
            String toprojName = null;
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
            	if (empNotificationsEntity.getForProject().equals(entry.getKey())) {
                    toepsName = entry.getValue().getCode();
                    toprojName = entry.getValue().getName();
                }

            }

            UserMstrEntity userMstr = empNotificationsEntity.getReqUserId();
            UserMstrEntity userMstr1 = empNotificationsEntity.getApprUserId();
            toEmail = userMstr.getEmail();
            ccEmail = userMstr1.getEmail();
            
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yy");
        	reqDate = dateFormat1.format(empNotificationsEntity.getDate());

            String code = EmpTransReqApprHandler.generateCode(empNotificationsEntity) + " dated " + reqDate;
            String reqCode = EmpTransReqApprHandler.generateReqCode(empNotificationsEntity) + " dated " + reqDate;
            String apprDec = empNotificationsEntity.getNotificationStatus();
            String apprDecision =apprDec.substring(0, 1).toUpperCase() + apprDec.substring(1).toLowerCase();
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String apprDate = dateFormat.format(empTransferReqEntity.getApprDate());
            
            empNotificationsEntity.setType("Employee transfer to Project - " + toprojName + " has been " + apprDecision);

            toSubject = "Approver  Decision for Employee Transfer to the Project -  " + toprojName;
            text = "<html><body><p>" + userMstr.getDisplayName() + ",</p>"
                    + "<p>Reference Notification ID " + code + "</p>"
                    + "<p>I have transmitted my decision for Employee Transfer through " + pot + ", as per details mentioned here below.</p>"
                    + "<table border='1'>"
                    + "<tr><td>From EPS </td><td>" + epsName + "</td></tr>"
                    + "<tr><td>From Project </td><td>" + projName + "</td></tr>"
                    + "<tr><td>To EPS </td><td>" + toepsName + "</td></tr>"
                    + "<tr><td>To Project </td><td>" + toprojName + "</td></tr>"
                    + "<tr><td>Requisition ID</td><td>" + reqCode + "</td></tr>"
                    + "<tr><td>Notification ID</td><td>" + code + "</td></tr>"
                    + "<tr><td>Approver Decision</td><td>" + apprDecision + "</td></tr>"
                    + "</table><p>This is for your information please.</p>" + "<p>Regards,</p>" + "<p>"
                    + AppUserUtils.getName() + "<br/>" + AppUserUtils.getDisplayRole() + " <br/>" + apprDate + " </p>"
                    + "</body></html>";

            commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);

            

            
        }
        return empNotificationsEntity;
    }

    public LabelKeyTOResp getEmpTransferReqDetails(EmpTransReq empTransReq) {
    	log.info("public LabelKeyTOResp getEmpTransferReqDetails");
        List<LabelKeyTO> labelKeyTOs = new ArrayList<>();
        List<EmpRegisterDtlEntity> empRegisterDtlEntities = empRegisterRepository
                .findNewRequestTransferEmployees(AppUserUtils.getClientId(), empTransReq.getProjId());
        for (EmpRegisterDtlEntity empRegisterDtlEntity : empRegisterDtlEntities) {
            LabelKeyTO labelKeyTO = new LabelKeyTO();
            labelKeyTO.setId(empRegisterDtlEntity.getId());
            labelKeyTO.setCode(empRegisterDtlEntity.getCode());
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.FIRST_NAME, empRegisterDtlEntity.getFirstName());
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.LAST_NAME, empRegisterDtlEntity.getLastName());
            EmpClassMstrEntity empClass = empRegisterDtlEntity.getEmpClassMstrEntity();
            if (CommonUtil.objectNotNull(empClass))
                labelKeyTO.getDisplayNamesMap().put(CommonConstants.CLASS_TYPE, empClass.getName());
            CompanyMstrEntity companyMstr = empRegisterDtlEntity.getCompanyMstrEntity();
            if (CommonUtil.objectNotNull(companyMstr))
                labelKeyTO.getDisplayNamesMap().put(CommonConstants.CMP_NAME, companyMstr.getName());
            for (EmpProjRigisterEntity empRegister : empRegisterDtlEntity.getProjEmpRigisterEntities()) {
                if (empRegister.getIsLatest().equalsIgnoreCase("Y")) {
                    String demobDate = CommonUtil.getDDMMMYYYFormat(empRegister.getDeMobilaizationDate());
                    labelKeyTO.getDisplayNamesMap().put(CommonConstants.EXPECTED_TRANS_DATE, demobDate);
                    break;
                }
            }
            labelKeyTOs.add(labelKeyTO);
        }
        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        labelKeyTOResp.setLabelKeyTOs(labelKeyTOs);
        return labelKeyTOResp;
    }

    public EmpNotificationsResp getEmpNotifications(NotificationsGetReq notificationsGetReq) {
    	log.info("public EmpNotificationsResp getEmpNotifications");
        EmpNotificationsResp empNotificationsResp = new EmpNotificationsResp();
        List<EmpNotificationsEntity> empNotificationsEntities = empNotificationsRepository.findEmpNotifications(
                notificationsGetReq.getId(), notificationsGetReq.getProjId(), notificationsGetReq.getStatus());

        for (EmpNotificationsEntity empNotificationsEntity : empNotificationsEntities) {
            empNotificationsResp.getEmpNotificationsTOs()
                    .add(EmpNotificationsHandler.convertEntityToPOJO(empNotificationsEntity));
        }
        return empNotificationsResp;
    }

    private void sendEmailNotification(EmpNotificationsEntity empNotificationsEntity) {
    	log.info("private void sendEmailNotification");
        String epsName = null;
        String projName = null;
        String ccEmail;
        String toEmail;
        String toSubject;
        String text;
        String toepsName = null;
        String toprojName = null;
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
        	if (empNotificationsEntity.getForProject().equals(entry.getKey())) {
                toepsName = entry.getValue().getCode();
                toprojName = entry.getValue().getName();
            }

        }
        

        UserMstrEntity userMstr = empNotificationsEntity.getReqUserId();
        UserMstrEntity userMstr1 = empNotificationsEntity.getApprUserId();

        toEmail = userMstr1.getEmail();
        ccEmail = userMstr.getEmail();
        
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yy");
    	reqDate = dateFormat1.format(empNotificationsEntity.getDate());
    	
        String code = EmpTransReqApprHandler.generateCode(empNotificationsEntity) + " dated " + reqDate;
        String reqCode = EmpTransReqApprHandler.generateReqCode(empNotificationsEntity) + " dated " + reqDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String apprDate = dateFormat.format(empNotificationsEntity.getDate());

        toSubject = "Request for Employee Transfer to the Project  -  " + toprojName;
        text = "<html><body><p>" + userMstr.getDisplayName() + ",</p>"
                + "<p>I have submitted my request for Employee Transfer through " + pot + ", as per details mentioned here below.</p>"
                + "<table border='1'>"
                + "<tr><td>From EPS </td><td>" + epsName + "</td></tr>"
                + "<tr><td>From Project </td><td>" + projName + "</td></tr>"
                + "<tr><td>To EPS </td><td>" + toepsName + "</td></tr>"
                + "<tr><td>To Project </td><td>" + toprojName + "</td></tr>"
                + "<tr><td>Requisition ID</td><td>" + reqCode + "</td></tr>"
                + "<tr><td>Notification ID</td><td>" + code + "</td></tr>"
                + "</table><p>This is for your approval please.</p>" + "<p>Regards,</p>" 
                + "<p>" + AppUserUtils.getName() + "<br/>" + AppUserUtils.getDisplayRole() + " <br/>" + apprDate + " </p>"
                + "</body></html>";

        commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
    }

    private void setTransferCode(EmpTransferReqApprEntity entity, boolean transType) {
    	log.info("private void setTransferCode");
        if (transType) {
            if (CommonUtil.isBlankStr(entity.getReqCode())) {
                String reqCode = ModuleCodesPrefixes.EMP_REG_PREFIX.getDesc().concat("-")
                        .concat(ModuleCodesPrefixes.REQUEST_PREFIX.getDesc()).concat("-")
                        .concat(entity.getToProjMstrEntity().getCode()).concat("-").concat(entity.getId().toString());
                entity.setReqCode(reqCode);
            }
        } else {
            if (CommonUtil.isBlankStr(entity.getApprCode())) {
                String apprcode = ModuleCodesPrefixes.EMP_REG_PREFIX.getDesc().concat("-")
                        .concat(ModuleCodesPrefixes.APPROVE_PREFIX.getDesc()).concat("-")
                        .concat(entity.getToProjMstrEntity().getCode()).concat("-").concat(entity.getId().toString());
                entity.setApprCode(apprcode);
            }
        }

    }

    private void cloneEmpToNewProj(EmpProjRigisterEntity oldEntity, EmpTransReqApprDetailTO empTransReqApprDetailTO,
            Long projId) {
    	log.info("private void cloneEmpToNewProj");
    	Long temp = 0L;
    	List<EmpProjRigisterEntity> empProjRigisterEntitiy = empProjRegisterRepository.findProjEmpRigisters(empTransReqApprDetailTO.getEmpRegId());
    	Long count = 0L;
    	for(EmpProjRigisterEntity empProjRigisterEntities : empProjRigisterEntitiy) {
    		 count = count+1; //for every iteration count increases 
    		 temp = count;
    	}
    	EmpProjRigisterEntity EmpProjRigisters = empProjRegisterRepository.findEmpClassNames(temp, oldEntity.getEmpRegisterDtlEntity().getId());
        EmpProjRigisterEntity newEntity = new EmpProjRigisterEntity();
        EmpRegisterDtlEntity dtlEntity = empRegisterRepository.findOne(empTransReqApprDetailTO.getEmpRegId());
        newEntity.setIsLatest(RegisterConstants.SET_IS_LATEST);
        newEntity.setDeploymentId(oldEntity.getDeploymentId() + count); //for new record count + 1 
        newEntity.setEmpRegisterDtlEntity(dtlEntity);
        
        newEntity.setEmpClassMstrEntity(EmpProjRigisters.getEmpClassMstrEntity());
  
        newEntity.setTaxFileNum(oldEntity.getTaxFileNum());
        newEntity.setStatus(StatusCodes.ACTIVE.getValue());
        newEntity.setMobilaizationDate(CommonUtil.convertStringToDate(empTransReqApprDetailTO.getTransDate()));
        newEntity.setDemobilizationStatus("On Transfer"); //hardcoding because we need to change status from on transfer to relieved
        // Assign employee to new Project
        if (CommonUtil.isNonBlankLong(projId)) {
            ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projId);
            dtlEntity.setProjMstrEntity(projMstrEntity);
            newEntity.setProjMstrEntity(projMstrEntity);
        }
        empProjRegisterRepository.save(newEntity);
    }
    
    //Rename the below function with the above in case of employee record need to be created for correct implementation instead of updating the employee project with project id on Employee Transfer
    private void cloneEmpBkupToNewProj(EmpProjRigisterEntity oldEntity, EmpTransReqApprDetailTO empTransReqApprDetailTO,
            Long projId) {
    	log.info("private void cloneEmpToNewProj");
        EmpProjRigisterEntity newEntity = new EmpProjRigisterEntity();
        EmpRegisterDtlEntity existingEmpRegDtlEntity = empRegisterRepository.findOne(empTransReqApprDetailTO.getEmpRegId());
        EmpRegisterDtlEntity newEmpRegDtlEntity = cloneEmpRegisterDetails( existingEmpRegDtlEntity ); // creating new employee record to keep the existing record history 
        
        //newEntity.setEmpRegisterDtlEntity(existingEmpRegDtlEntity);
        newEntity.setEmpClassMstrEntity(oldEntity.getEmpClassMstrEntity());
        newEntity.setTaxFileNum(oldEntity.getTaxFileNum());
        newEntity.setDeploymentId(oldEntity.getDeploymentId() + 1);
        newEntity.setStatus(StatusCodes.ACTIVE.getValue());
        newEntity.setIsLatest(RegisterConstants.SET_IS_LATEST);
        newEntity.setMobilaizationDate(CommonUtil.convertStringToDate(empTransReqApprDetailTO.getTransDate()));
        
        existingEmpRegDtlEntity.setStatus( StatusCodes.DEFAULT.getValue() );
        
        // Assign employee to new Project
        if (CommonUtil.isNonBlankLong(projId)) {
            ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projId);
            newEmpRegDtlEntity.setProjMstrEntity(projMstrEntity);
            newEntity.setProjMstrEntity(projMstrEntity);
        }
        //empRegisterRepository.save( existingEmpRegDtlEntity );
        log.info("before save");        
        EmpRegisterDtlEntity resRegisterDtlEntity = empRegisterRepository.save( newEmpRegDtlEntity );
        System.out.println("emp proj register entity primary key id:"+oldEntity.getId()+" new register dtl entity id:"+resRegisterDtlEntity.getId()+" old register dtl entity id:"+empTransReqApprDetailTO.getEmpRegId());
        newEntity.setEmpRegisterDtlEntity( resRegisterDtlEntity );
        empProjRegisterRepository.save(newEntity);
        empProjRegisterRepository.updateExistingEmpProjStatus( oldEntity.getId() );
    }
    
    //This is to insert new row in case of employee transfer
    private EmpRegisterDtlEntity cloneEmpRegisterDetails( EmpRegisterDtlEntity existingEmpRegDtlEntity ){
    	EmpRegisterDtlEntity newEmpRegisterDtlEntity = new EmpRegisterDtlEntity();
    	newEmpRegisterDtlEntity.setFirstName( existingEmpRegDtlEntity.getFirstName() );
    	newEmpRegisterDtlEntity.setLastName( existingEmpRegDtlEntity.getLastName() );
    	newEmpRegisterDtlEntity.setGender( existingEmpRegDtlEntity.getGender() );
    	newEmpRegisterDtlEntity.setEmpStatus( existingEmpRegDtlEntity.getEmpStatus() );
    	newEmpRegisterDtlEntity.setLocation( existingEmpRegDtlEntity.getLocation() );
    	newEmpRegisterDtlEntity.setClientId( existingEmpRegDtlEntity.getClientId() );
    	newEmpRegisterDtlEntity.setCode( existingEmpRegDtlEntity.getCode() );
    	newEmpRegisterDtlEntity.setCompanyMstrEntity( existingEmpRegDtlEntity.getCompanyMstrEntity() );
    	newEmpRegisterDtlEntity.setDob( existingEmpRegDtlEntity.getDob() );
    	newEmpRegisterDtlEntity.setContractDate( existingEmpRegDtlEntity.getContractDate() );
    	newEmpRegisterDtlEntity.setContractNumber( existingEmpRegDtlEntity.getContractNumber() );
        newEmpRegisterDtlEntity.setEmpClassMstrEntity( existingEmpRegDtlEntity.getEmpClassMstrEntity() );
        newEmpRegisterDtlEntity.setProcureCatgDtlEntity( existingEmpRegDtlEntity.getProcureCatgDtlEntity() );
        newEmpRegisterDtlEntity.setStatus( StatusCodes.ACTIVE.getValue() );
        return newEmpRegisterDtlEntity;
    }
}
