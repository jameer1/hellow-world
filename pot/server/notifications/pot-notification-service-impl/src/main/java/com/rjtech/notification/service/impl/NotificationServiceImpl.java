package com.rjtech.notification.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.model.EmailSettingEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.ClientRegRepository;
import com.rjtech.common.repository.CommonRepository;
import com.rjtech.common.repository.EmailSettingRepository;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.repository.POTSequenceGeneratorProcRepository;
import com.rjtech.common.service.impl.CommonEmailServiceImpl;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodes;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.common.utils.ProcurmentStageStatus;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.notification.dto.AttendenceNotificationsTO;
import com.rjtech.notification.dto.EmployeeNotificationsTO;
import com.rjtech.notification.dto.MaterialNotificationsTO;
import com.rjtech.notification.dto.PlantNotificationsTO;
import com.rjtech.notification.dto.ProcurementNotificationsTO;
import com.rjtech.notification.dto.TimeSheetNotificationsTO;
import com.rjtech.notification.dto.WorkDairyNotificationsTO;
import com.rjtech.notification.model.AttendenceNotificationsEntity;
import com.rjtech.notification.model.EmpLeaveReqApprEntity;
import com.rjtech.notification.model.EmployeeNotificationsEntity;
import com.rjtech.notification.model.EmpRegisterDtlEntityCopy;
import com.rjtech.notification.model.MaterialNotificationsEntity;
import com.rjtech.notification.model.PlantNotificationsEntity;
import com.rjtech.notification.model.ProcurementAddtionalTimeApprEntity;
import com.rjtech.notification.model.ProcurementNormalTimeEntity;
import com.rjtech.notification.model.ProcurementNotificationsEntity;
//import com.rjtech.notification.model.ProjCrewMstrEntity;
import com.rjtech.notification.model.ReqApprNotificationEntity;
import com.rjtech.notification.model.TimeSheetAdditionalTimeEntity;
import com.rjtech.notification.model.TimeSheetNotificationsEntity;
import com.rjtech.notification.model.WorkDairyAdditionalTimeEntity;
import com.rjtech.notification.model.WorkDairyNotificationsEntity;
import com.rjtech.notification.repository.AttendenceNotificationsRepository;
import com.rjtech.notification.repository.EmpLeaveReqApprRepository;
import com.rjtech.notification.repository.EmployeeLeaveNotificationsRepository;
import com.rjtech.notification.repository.EmployeeNotificationsRepository;
import com.rjtech.notification.repository.EntpsProjRepository;
import com.rjtech.notification.repository.MaterialNotificationsRepository;
import com.rjtech.notification.repository.PlantNotificationsRepository;
import com.rjtech.notification.repository.ProcurementNotificationsRepository;
import com.rjtech.notification.repository.ReqApprNotificationRepository;
import com.rjtech.notification.repository.TimeSheetAdditionalTimeRepository;
//import com.rjtech.notification.repository.PrecontractRepository;
import com.rjtech.notification.repository.ProcurementAddltionalTimeRepository;
import com.rjtech.notification.repository.ProcurementNormalTimeRepository;
import com.rjtech.notification.repository.ProjectCrewMstrRepository;
import com.rjtech.notification.repository.NotificationEmpDtlRepository;
import com.rjtech.notification.repository.TimeSheetNotificationsRepository;
import com.rjtech.notification.repository.TimeSheetRepositoryCopy;
import com.rjtech.notification.repository.WorkDairyAdditionalTimeRepository;
import com.rjtech.notification.repository.WorkDairyNotificationRepository;
import com.rjtech.notification.req.AttendenceNotificationsSaveReq;
import com.rjtech.notification.req.EmployeeNotificationsSaveReq;
import com.rjtech.notification.req.MaterialNotificationsSaveReq;
import com.rjtech.notification.req.NotificationCountGetReq;
import com.rjtech.notification.req.NotificationsGetReq;
import com.rjtech.notification.req.NotificationsSaveReq;
import com.rjtech.notification.req.PlantNotificationsSaveReq;
import com.rjtech.notification.req.ProcureNotificationsSaveReq;
import com.rjtech.notification.req.TimeSheetNotificationsSaveReq;
import com.rjtech.notification.req.WorkDairyNotificationsSaveReq;
import com.rjtech.notification.resp.AttendenceNotificationsResp;
import com.rjtech.notification.resp.EmployeeLeaveNotificationsResp;
import com.rjtech.notification.resp.EmployeeNotificationsResp;
import com.rjtech.notification.resp.MaterialNotificationResp;
import com.rjtech.notification.resp.NotificationCountResp;
import com.rjtech.notification.resp.PlantNotificationResp;
import com.rjtech.notification.resp.ProcurementNotificationResp;
import com.rjtech.notification.resp.TimeSheetNotificationsResp;
import com.rjtech.notification.resp.WorkDairyNotificationResp;
import com.rjtech.notification.service.NotificationService;
import com.rjtech.notification.service.handler.AttendenceNotificationsHandler;
import com.rjtech.notification.service.handler.EmployeeLeaveNotificationsHandler;
import com.rjtech.notification.service.handler.EmployeeNotificationsHandler;
import com.rjtech.notification.service.handler.MaterialNotificationsHandler;
import com.rjtech.notification.service.handler.PlantNotificationsHandler;
import com.rjtech.notification.service.handler.ProcurementNotificationsHandler;
import com.rjtech.notification.service.handler.TimeSheetNotificationsHandler;
import com.rjtech.notification.service.handler.WorkDairyNotificationsHandler;
import com.rjtech.procurement.repository.PrecontractRepository;
//import com.rjtech.notification.timemanagement.timesheet.model.TimeSheetEntityCopy;
//import com.rjtech.notification.timemanagement.workdairy.repository.ProjCrewMstrRepositoryCopy;
import com.rjtech.notification.repository.WorkDairyRepository;
import com.rjtech.notification.repository.ProjCrewMstrRepositoryCopy;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
//import com.rjtech.notification.timemanagement.workdairy.repository.WorkDairyRepository;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.service.EPSProjService;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.timemanagement.timesheet.model.TimeSheetEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEntity;
import com.rjtech.user.dto.UserProjDetailsTO;
import com.rjtech.user.model.UserEntity;
import com.rjtech.user.repository.UserRepository;
import com.rjtech.user.req.UserProjGetReq;
import com.rjtech.user.resp.UserProjResp;
//import com.rjtech.notification.model.WorkDairyEntity;
import com.rjtech.notification.resp.SoeNotificationsResp;
import com.rjtech.notification.dto.SoeNotificationsTO;
import com.rjtech.notification.model.WorkDairyNotificationsEntity;
import com.rjtech.notification.req.NotificationsSoeGetReq;
import com.rjtech.notification.model.SoeNotificationsEntity;
import com.rjtech.notification.req.SoeNotificationsSaveReq;
import com.rjtech.notification.repository.SoeNotificationRepository;
import com.rjtech.notification.service.handler.SoeNotificationsHandler;
import com.rjtech.notification.model.SoeAddtionalTimeApprEntity;
import com.rjtech.notification.repository.SoeAddltionalTimeRepository;

@Service(value = "notificationService")
@RJSService(modulecode = "notificationService")
@Transactional
public class NotificationServiceImpl implements NotificationService {

	private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);
	
	private static String pot = "\"Project on Track\"";

	@Autowired
	private EPSProjService epsProjService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommonEmailServiceImpl commonEmail;

	@Autowired
	private EmailSettingRepository emailSettingRepository;

	@Autowired
	private TimeSheetNotificationsRepository timeSheetNotificationsRepository;

	@Autowired
	private AttendenceNotificationsRepository attendenceNotificationsRepository;

	@Autowired
	private POTSequenceGeneratorProcRepository potSequenceGeneratorProcRepository;

	@Autowired
	private WorkDairyNotificationRepository workDairyNotificationRepository;

	@Autowired
	private ProcurementNotificationsRepository procurementNotificationsRepository;

	@Autowired
	private ReqApprNotificationRepository reqApprNotificationsRepository;

	@Autowired
	private EmployeeNotificationsRepository employeeNotificationsRepository;

	@Autowired
	private PlantNotificationsRepository plantNotificationsRepository;

	@Autowired
	private MaterialNotificationsRepository materialNotificationsRepository;

	@Autowired
	private EmployeeLeaveNotificationsRepository employeeLeaveNotificationsRepository;

	@Autowired
	private EPSProjRepository epsProjRepository;

	@Autowired
	private LoginRepository loginRepository;

	@Autowired
	private ClientRegRepository clientRegRepository;

	@Autowired
	private ProjCrewMstrRepositoryCopy projCrewMstrRepository;

	@Autowired
	private WorkDairyRepository workDairyRepository;

	@Autowired
	private WorkDairyAdditionalTimeRepository workDairyAdditionalTimeRepository;

	@Autowired
	private CommonRepository commonRepository;

	@Autowired
	private TimeSheetRepositoryCopy timeSheetRepository;

	@Autowired
	private TimeSheetAdditionalTimeRepository timeSheetAdditionalTimeRepository;
	
	@Autowired
	private ProjectCrewMstrRepository projectCrewMstrRepository;
	
	@Autowired
	private NotificationEmpDtlRepository notificationEmpDtlRepository;
	
	@Autowired
	private EntpsProjRepository entpsProjRepository;
	
	@Autowired
	private ProcurementAddltionalTimeRepository procurementAddltionalTimeRepository;
	
	@Autowired
	private ProcurementNormalTimeRepository procurementNormalTimeRepository;
	
	@Autowired
	private PrecontractRepository precontractRepository;

	@Autowired
	private EmpLeaveReqApprRepository empLeaveReqApprRepository;
	
	@Autowired
	private SoeNotificationRepository soeNotificationRepository;
	
	@Autowired
	private SoeAddltionalTimeRepository soeAddltionalTimeRepository;
	
	public TimeSheetNotificationsResp getTimeSheetNotifications(NotificationsGetReq notificationsGetReq) {

		TimeSheetNotificationsResp timeSheetNotificationsResp = new TimeSheetNotificationsResp();
		Date fromDate = null;
		Date toDate = null;
		if (CommonUtil.isNotBlankStr(notificationsGetReq.getFromDate())
				&& CommonUtil.isNotBlankStr(notificationsGetReq.getToDate())) {
			fromDate = CommonUtil.convertStringToDate(notificationsGetReq.getFromDate());
			toDate = CommonUtil.convertStringToDate(notificationsGetReq.getToDate());
		} else {
			toDate = new Date();
			fromDate = CommonUtil.substarctInputMonths(toDate, -1);
		}
		toDate = CommonUtil.addNumberOfDaysInputDate(toDate, 1);
		
		String notificationStatus = null;
		String notificationPendingStatus = null;
		String notificationApprovedStatus = null;
		String notificationAddlTimeMsg = null;
		String notificationSubmitTimeMsg = null;
		
		List<TimeSheetNotificationsEntity> timeSheetNotificationsEntites = new ArrayList<>();
		if (notificationsGetReq.getNotifyStatus().equalsIgnoreCase("All")) {
			
			String notification = "\"Pending\" OR \"Approved\"";
			notificationPendingStatus = "Pending";
			notificationPendingStatus = "Pending"; // "\"Pending\" OR \"Pending\"";
			notificationApprovedStatus = "Approved";
			notificationAddlTimeMsg = "Request for Additional Time";
			notificationSubmitTimeMsg = "Request for Approval";
			
			if (notificationsGetReq.getProjId() == null) {
				timeSheetNotificationsEntites = timeSheetNotificationsRepository.findTimeSheetNotificationsAll(
						fromDate, toDate, notificationPendingStatus, notificationApprovedStatus, notificationAddlTimeMsg,
						notificationSubmitTimeMsg);
			} else {
				timeSheetNotificationsEntites = timeSheetNotificationsRepository.findTimeSheetNotificationsAll(
						notificationsGetReq.getProjId(), fromDate, toDate, notificationPendingStatus, notificationApprovedStatus, 
						notificationAddlTimeMsg, notificationSubmitTimeMsg);
			}
		} else {			
			if ("Pending for Approval".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())
					|| "Approved".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())) {
				
				if ("Pending for Approval".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())) {
					notificationStatus = "Pending";
				} else {
					notificationStatus = "Approved";
				}

				if (notificationsGetReq.getProjId() == null) {
					timeSheetNotificationsEntites = timeSheetNotificationsRepository.findTimeSheetNotifications(
							fromDate, toDate, notificationStatus, "Request for Approval");
				} else {
					timeSheetNotificationsEntites = timeSheetNotificationsRepository.findTimeSheetNotifications(
							notificationsGetReq.getProjId(), fromDate, toDate, notificationStatus, "Request for Approval");
				}
			} else if ("Additional Time Request".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())
					|| "Additional Time Approved".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())) {
				if ("Additional Time Request".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())) {
					notificationStatus = "Pending";
				} else {
					notificationStatus = "Approved";
				}

				if (notificationsGetReq.getProjId() == null) {
					timeSheetNotificationsEntites = timeSheetNotificationsRepository.findTimeSheetNotifications(
						fromDate, toDate, notificationStatus,"Request for Additional Time");
				} else {
					timeSheetNotificationsEntites = timeSheetNotificationsRepository.findTimeSheetNotifications(
						notificationsGetReq.getProjId(), fromDate, toDate, notificationStatus,
						"Request for Additional Time");
				}
			} else {
				log.info("NO CRITERIA AVAILABLE");
			}
		}
		
		if (CommonUtil.isListHasData(timeSheetNotificationsEntites)) {
			for (TimeSheetNotificationsEntity timeSheetNotificationsEntity : timeSheetNotificationsEntites) {
				timeSheetNotificationsResp.getTimeSheetNotificationsTOs()
						.add(TimeSheetNotificationsHandler.convertEntityToPOJO(timeSheetNotificationsEntity));
			}
		} 
		return timeSheetNotificationsResp;
	}

	public TimeSheetNotificationsResp getTimeSheetNotificationsByProjId(NotificationsGetReq notificationsGetReq) {

		TimeSheetNotificationsResp timeSheetNotificationsResp = new TimeSheetNotificationsResp();
		
		List<TimeSheetAdditionalTimeEntity> timeSheetAdditionalTimeEntities = new ArrayList<>();
		List<ProjCrewMstrEntity> projCrewMstrEntities = new ArrayList<>();
		List<EmpRegisterDtlEntityCopy> empRegisterDtlEntities = new ArrayList<>();

		if ("Submitted".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())) {
			timeSheetAdditionalTimeEntities = timeSheetAdditionalTimeRepository.findTimeSheetNotificationsByStatus(
					notificationsGetReq.getProjId(), "Pending", "Request for Additional Time",
					notificationsGetReq.getStatus());
		}
		String ProjCode = "";
		if (CommonUtil.isListHasData(timeSheetAdditionalTimeEntities)) {
			for (TimeSheetAdditionalTimeEntity timeSheetAdditionalTimeEntity : timeSheetAdditionalTimeEntities) {
				TimeSheetNotificationsTO timeSheetNotificationsTO = new TimeSheetNotificationsTO();
				if (timeSheetAdditionalTimeEntity != null) {
					List<ProjMstrEntity> projEntityCode =  entpsProjRepository.getProjCode(timeSheetAdditionalTimeEntity.getProjId());
					if (CommonUtil.objectNotNull(projEntityCode)) {
						for (ProjMstrEntity projCode : projEntityCode) {
							ProjCode = projCode.getCode();
							log.info("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = " + ProjCode);
						}
					}
					if (null != timeSheetAdditionalTimeEntity.getCrewId()) {
						projCrewMstrEntities = projectCrewMstrRepository.findCrewName(timeSheetAdditionalTimeEntity.getProjId(),timeSheetAdditionalTimeEntity.getCrewId());
						for (ProjCrewMstrEntity projCrewMstrEntity : projCrewMstrEntities) {
							timeSheetNotificationsTO.setCrewName( projCrewMstrEntity.getCode());
						} 
					} else {
						empRegisterDtlEntities = notificationEmpDtlRepository.findEmpName(timeSheetAdditionalTimeEntity.getEmpId());
						for (EmpRegisterDtlEntityCopy empRegisterDtlEntity : empRegisterDtlEntities)
						timeSheetNotificationsTO.setCrewName(empRegisterDtlEntity.getFirstName() + " " + empRegisterDtlEntity.getLastName());
					}
					if (CommonUtil.isNotBlankDate(timeSheetAdditionalTimeEntity.getFromDate())) {
						timeSheetNotificationsTO.setFromDate(
								CommonUtil.convertDateToString(timeSheetAdditionalTimeEntity.getFromDate()));
						log.info("============= CommonUtil.convertDateToString(timeSheetAdditionalTimeEntity.getFromDate()) " + CommonUtil.convertDateToString(timeSheetAdditionalTimeEntity.getFromDate()));
					}
					if (CommonUtil.isNotBlankDate(timeSheetAdditionalTimeEntity.getToDate())) {
						timeSheetNotificationsTO
								.setToDate(CommonUtil.convertDateToString(timeSheetAdditionalTimeEntity.getToDate()));
						log.info("============= CommonUtil.convertDateToString(timeSheetAdditionalTimeEntity.getToDate()) " + CommonUtil.convertDateToString(timeSheetAdditionalTimeEntity.getToDate()));
					}
					if (null != timeSheetAdditionalTimeEntity.getApprUser()) {
						timeSheetNotificationsTO.setToUserId(timeSheetAdditionalTimeEntity.getApprUser().getUserId());
						log.info("============= timeSheetAdditionalTimeEntity.getApprUser().getUserId()) " + timeSheetAdditionalTimeEntity.getApprUser().getUserId());
					}
					if (null != timeSheetAdditionalTimeEntity.getApprUser()) {
						timeSheetNotificationsTO.setToUserName(timeSheetAdditionalTimeEntity.getApprUser().getUserName());
						log.info("============= timeSheetAdditionalTimeEntity.getApprUser().getUserName()) " + timeSheetAdditionalTimeEntity.getApprUser().getUserName());
					}
					timeSheetNotificationsTO.setType(timeSheetAdditionalTimeEntity.getType());
					timeSheetNotificationsTO
							.setCode(TimeSheetNotificationsHandler.generateCode(timeSheetAdditionalTimeEntity, ProjCode));
//					log.info("============= TimeSheetNotificationsHandler.generateCode(timeSheetAdditionalTimeEntity) " + TimeSheetNotificationsHandler.generateCode(timeSheetAdditionalTimeEntity, ProjCode));
					timeSheetNotificationsTO.setId(timeSheetAdditionalTimeEntity.getId());
					log.info("============= timeSheetAdditionalTimeEntity.getId() " + timeSheetAdditionalTimeEntity.getId());
					if (null != timeSheetAdditionalTimeEntity.getCrewId()) {
						timeSheetNotificationsTO.setCrewType("Team");
						log.info("============= timeSheetNotificationsTO.setCrewType(\"Team\") ");
					} else {
						timeSheetNotificationsTO.setCrewType("Individual");
						log.info("============= timeSheetNotificationsTO.setCrewType(\"Individual\") ");
					}
					timeSheetNotificationsTO.setId(timeSheetAdditionalTimeEntity.getId());
					log.info("============= timeSheetAdditionalTimeEntity.getId() " + timeSheetAdditionalTimeEntity.getId());
					timeSheetNotificationsResp.getTimeSheetNotificationsTOs().add(timeSheetNotificationsTO);
				}

			}
		}

		return timeSheetNotificationsResp;
	}

	public void saveTimeSheetNotifications(TimeSheetNotificationsSaveReq timeSheetNotificationsSaveReq) {
		List<TimeSheetNotificationsEntity> timeSheetNotificationsEntities = new ArrayList<>();
		
		for (TimeSheetNotificationsTO timeSheetNotificationsTO : timeSheetNotificationsSaveReq
				.getTimeSheetNotificationsTOs()) {
			TimeSheetNotificationsEntity timeSheetNotificationsEntity = TimeSheetNotificationsHandler
					.convertPOJOToEntity(timeSheetNotificationsTO, timeSheetRepository);
			log.info("timeSheetNotificationsEntity " + timeSheetNotificationsEntity.toString().length());
			timeSheetNotificationsEntities.add(timeSheetNotificationsEntity);
			timeSheetNotificationsRepository.save(timeSheetNotificationsEntities);
				
			// for Additional Time Notification creation
			log.info("timeSheetNotificationsTO.getProjId() " + timeSheetNotificationsTO.getProjId());
			TimeSheetAdditionalTimeEntity timeSheetAdditionalTimeEntity = new TimeSheetAdditionalTimeEntity();
			if (timeSheetNotificationsTO.getCrewId() != null) {
				//timeSheetAdditionalTimeEntity
				//		.setProjCrewMstrEntity(projCrewMstrRepository.findOne(timeSheetNotificationsTO.getCrewId()));
				timeSheetAdditionalTimeEntity.setCrewId(timeSheetNotificationsTO.getCrewId());
			}
			timeSheetAdditionalTimeEntity
					.setFromDate(CommonUtil.convertStringToDate(timeSheetNotificationsTO.getFromDate()));
			timeSheetAdditionalTimeEntity
					.setToDate(CommonUtil.convertStringToDate(timeSheetNotificationsTO.getToDate()));
			timeSheetAdditionalTimeEntity.setApprUser(commonRepository.findOne(timeSheetNotificationsTO.getToUserId()));
			timeSheetAdditionalTimeEntity.setNotificationStatus(timeSheetNotificationsTO.getNotificationStatus());
			timeSheetAdditionalTimeEntity.setNotificationMsg(timeSheetNotificationsTO.getNotificationMsg());
			timeSheetAdditionalTimeEntity.setStatus(timeSheetNotificationsTO.getStatus());
			timeSheetAdditionalTimeEntity.setType(timeSheetNotificationsTO.getType());
			timeSheetAdditionalTimeEntity.setProjId(timeSheetNotificationsTO.getProjId());
			if (timeSheetNotificationsTO.getId() != null) {
				timeSheetAdditionalTimeEntity.setEmpId(timeSheetNotificationsTO.getId());
			}
			timeSheetAdditionalTimeEntity.setTimeSheetId(timeSheetNotificationsTO.getTimeSheetId());
			TimeSheetAdditionalTimeEntity savedEntity = timeSheetAdditionalTimeRepository
					.save(timeSheetAdditionalTimeEntity);
			sendTimeSheetMail(savedEntity);
			
		}
	}

	public AttendenceNotificationsResp getAttendenceAddlTimeNotifications(NotificationsGetReq notificationsGetReq) {

		AttendenceNotificationsResp attendenceNotificationsResp = new AttendenceNotificationsResp();
		Date fromDate = null;
		Date toDate = null;
		if (CommonUtil.isNotBlankStr(notificationsGetReq.getFromDate())
				&& CommonUtil.isNotBlankStr(notificationsGetReq.getToDate())) {
			fromDate = CommonUtil.convertStringToDate(notificationsGetReq.getFromDate());
			toDate = CommonUtil.convertStringToDate(notificationsGetReq.getToDate());
		} else {
			toDate = new Date();
			fromDate = CommonUtil.substarctInputMonths(toDate, -1);
		}
		toDate = CommonUtil.addNumberOfDaysInputDate(toDate, 1);

		if (notificationsGetReq.getCrewId() == null) {
			List<AttendenceNotificationsEntity> attendenceNotificationsEntites = attendenceNotificationsRepository
					.findAttendenceAddlTimeNotifications(notificationsGetReq.getProjId(), notificationsGetReq.getNotifyStatus(),
							fromDate, toDate);
			if (CommonUtil.isListHasData(attendenceNotificationsEntites)) {
				for (AttendenceNotificationsEntity attendenceNotificationsEntity : attendenceNotificationsEntites) {
					attendenceNotificationsResp.getAttendenceNotificationsTOs()
							.add(AttendenceNotificationsHandler.convertEntityToPOJO(attendenceNotificationsEntity));
				}
			}
		} else {
			List<AttendenceNotificationsEntity> attendenceNotificationsEntites = attendenceNotificationsRepository
					.findAll(notificationsGetReq.getProjId(), notificationsGetReq.getCrewId(),
							fromDate, toDate, notificationsGetReq.getType());
			if (CommonUtil.isListHasData(attendenceNotificationsEntites)) {
				for (AttendenceNotificationsEntity attendenceNotificationsEntity : attendenceNotificationsEntites) {
					attendenceNotificationsResp.getAttendenceNotificationsTOs()
							.add(AttendenceNotificationsHandler.convertEntityToPOJO(attendenceNotificationsEntity));
				}
			}
		}

		return attendenceNotificationsResp;

	}
	
	public AttendenceNotificationsResp getAttendenceNotifications(NotificationsGetReq notificationsGetReq) {
		log.info("public AttendenceNotificationsResp getAttendenceNotifications(NotificationsGetReq notificationsGetReq) {");
		AttendenceNotificationsResp attendenceNotificationsResp = new AttendenceNotificationsResp();
		Date fromDate = null;
		Date toDate = null;
		if (CommonUtil.isNotBlankStr(notificationsGetReq.getFromDate())
				&& CommonUtil.isNotBlankStr(notificationsGetReq.getToDate())) {
			fromDate = CommonUtil.convertStringToDate(notificationsGetReq.getFromDate());
			toDate = CommonUtil.convertStringToDate(notificationsGetReq.getToDate());
		} else {
			toDate = new Date();
			fromDate = CommonUtil.substarctInputMonths(toDate, -1);
		}
		toDate = CommonUtil.addNumberOfDaysInputDate(toDate, 1);
		String notificationPendingStatus = null;
		String notificationApprovedStatus = null;
		if (notificationsGetReq.getNotifyStatus().equalsIgnoreCase("All")) {
			notificationPendingStatus = "Pending"; 
			notificationApprovedStatus = "Approved";
			if (notificationsGetReq.getProjId() == null) {
				log.info("All > Project ID Null");

				List<AttendenceNotificationsEntity> attendenceNotificationsEntites = attendenceNotificationsRepository
						.findAttendenceNotificationsAll(notificationPendingStatus, notificationApprovedStatus,
								fromDate, toDate);
				if (CommonUtil.isListHasData(attendenceNotificationsEntites)) {
					for (AttendenceNotificationsEntity attendenceNotificationsEntity : attendenceNotificationsEntites) {
						attendenceNotificationsResp.getAttendenceNotificationsTOs()
								.add(AttendenceNotificationsHandler.convertEntityToPOJO(attendenceNotificationsEntity));
					}
				}
			} else {
				log.info("All > Project ID NOT Null");
				
				List<AttendenceNotificationsEntity> attendenceNotificationsEntites = attendenceNotificationsRepository
						.findAttendenceNotificationsAllByProjId(notificationsGetReq.getProjId(), notificationPendingStatus, notificationApprovedStatus,
								fromDate, toDate);
				if (CommonUtil.isListHasData(attendenceNotificationsEntites)) {
					for (AttendenceNotificationsEntity attendenceNotificationsEntity : attendenceNotificationsEntites) {
						attendenceNotificationsResp.getAttendenceNotificationsTOs()
								.add(AttendenceNotificationsHandler.convertEntityToPOJO(attendenceNotificationsEntity));
					}
				}
			}
		} else {
			if (notificationsGetReq.getProjId() == null) {
				log.info("PROJECT ID IS NULL");
				List<AttendenceNotificationsEntity> attendenceNotificationsEntites = attendenceNotificationsRepository
						.findAttendenceNotifications(notificationsGetReq.getNotifyStatus(),
								fromDate, toDate);
				if (CommonUtil.isListHasData(attendenceNotificationsEntites)) {
					for (AttendenceNotificationsEntity attendenceNotificationsEntity : attendenceNotificationsEntites) {
						attendenceNotificationsResp.getAttendenceNotificationsTOs()
								.add(AttendenceNotificationsHandler.convertEntityToPOJO(attendenceNotificationsEntity));
					}
				}
			} else {
				log.info("PROJECT ID IS NOT NULL");
				log.info("notificationsGetReq.getProjId() " + notificationsGetReq.getProjId());
				List<AttendenceNotificationsEntity> attendenceNotificationsEntites = attendenceNotificationsRepository
						.findAttendenceNotificationsByProjId(notificationsGetReq.getProjId(), notificationsGetReq.getNotifyStatus(),
								fromDate, toDate);
				if (CommonUtil.isListHasData(attendenceNotificationsEntites)) {
					for (AttendenceNotificationsEntity attendenceNotificationsEntity : attendenceNotificationsEntites) {
						attendenceNotificationsResp.getAttendenceNotificationsTOs()
								.add(AttendenceNotificationsHandler.convertEntityToPOJO(attendenceNotificationsEntity));
					}
				}
				/*
				log.info("CREW ID IS NOT NULL");
				List<AttendenceNotificationsEntity> attendenceNotificationsEntites = attendenceNotificationsRepository
						.findAll(notificationsGetReq.getProjId(), notificationsGetReq.getCrewId(),
								fromDate, toDate, notificationsGetReq.getType());
				if (CommonUtil.isListHasData(attendenceNotificationsEntites)) {
					for (AttendenceNotificationsEntity attendenceNotificationsEntity : attendenceNotificationsEntites) {
						attendenceNotificationsResp.getAttendenceNotificationsTOs()
								.add(AttendenceNotificationsHandler.convertEntityToPOJO(attendenceNotificationsEntity));
					}
				}
				*/
			}
		}
		

		return attendenceNotificationsResp;

	}

	private void sendTimeSheetMail(TimeSheetAdditionalTimeEntity timeSheetAdditionalTimeEntity) {
		// TODO weekStartDate is removed and set to new Date
		String apprName = "";
		String epsName = "";
		String projName = "";
		String code = "";
		Date weekStartDate = new Date();
		String ccEmail = "";
		String toEmail = "";
		String generatedCode = "";
		if (null != timeSheetAdditionalTimeEntity) {
			List<ProjMstrEntity> projEntityCode =  entpsProjRepository.getProjCode(timeSheetAdditionalTimeEntity.getProjId());
			if (CommonUtil.objectNotNull(projEntityCode)) {
				for (ProjMstrEntity projMstr : projEntityCode) {
					code = projMstr.getCode();
					projName = projMstr.getProjName();
					epsName = projMstr.getParentProjectMstrEntity().getProjName();
					log.info("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = " + code);
				}
			}

			TimeSheetEntity timeSheetEntityCopy = timeSheetRepository.findOne(timeSheetAdditionalTimeEntity.getTimeSheetId());
			weekStartDate = timeSheetEntityCopy.getWeekStartDate();

			if (timeSheetAdditionalTimeEntity.getApprUser() != null) {
				apprName = timeSheetAdditionalTimeEntity.getApprUser().getDisplayName();
				log.info("apprName " + apprName);
			}
			
			generatedCode = TimeSheetNotificationsHandler.generateCode(timeSheetAdditionalTimeEntity,code);
			toEmail = timeSheetAdditionalTimeEntity.getApprUser().getEmail();
			
			String toSubject = "Request for Addtional Time For Approval of Time Sheet - " + code
					+ " for Week Commencing " + weekStartDate;
			String text = "<html><body><p>" + apprName + ",</p>"
					+ "<p>I have granted   additional time for approval of time sheet through " + pot + ", as per details mentioned here below.</p>"
					+ "<table border='1'><tr><td>EPS </td><td>" + epsName + "</td></tr><tr><td> Project </td><td>"
					+ projName + "</td></tr><tr><td>Week Commencing </td><td>" + weekStartDate
					+ "</td></tr><tr><td>Time Sheet Number</td><td>" + generatedCode
					+ "</td></tr></table><br>This is for your information and approval of the said time sheet please."
					+ "<p>Regards,</p>" + "<p>" + AppUserUtils.getName() + "<br/>"
					+ AppUserUtils.getDisplayRole() + "</p></body></html>";
			log.info("text " + text);
			commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
		}

	}

	public void saveAttendenceNotifications(AttendenceNotificationsSaveReq attendenceNotificationsSaveReq) {
		List<AttendenceNotificationsEntity> attendenceNotificationsEntities = new ArrayList<>();
		for (AttendenceNotificationsTO attendenceNotificationsTO : attendenceNotificationsSaveReq
				.getAttendenceNotificationsTOs()) {
			AttendenceNotificationsEntity attendenceNotificationsEntity = AttendenceNotificationsHandler
					.convertPOJOToEntity(attendenceNotificationsTO, loginRepository, projCrewMstrRepository);
			attendenceNotificationsEntities.add(attendenceNotificationsEntity);
		}
		List<AttendenceNotificationsEntity> savedEntities = attendenceNotificationsRepository
				.save(attendenceNotificationsEntities);
		for (AttendenceNotificationsEntity savedEntity : savedEntities) {
			sendAttdNotifyMail(savedEntity);
		}
	}

	private void sendAttdNotifyMail(AttendenceNotificationsEntity attendenceNotificationsEntity) {
		String projName = "";
		String epsName = "";
		String crewName = "";
		String month = "";
		String generatedCode = "";
		String fromDate = "";
		String toDate = "";
		String toEmail = "";
        String ccEmail = "";
        String userName = "";
        String forMonthYear = ""; 
        
		
		if (null != attendenceNotificationsEntity) {
			if (null != attendenceNotificationsEntity.getProjCrewMstrEntity()) {
                if (null != attendenceNotificationsEntity.getProjCrewMstrEntity().getProjId()) {
                    projName = attendenceNotificationsEntity.getProjCrewMstrEntity().getProjId().getProjName();
                    crewName = attendenceNotificationsEntity.getProjCrewMstrEntity().getDesc();
                    epsName = attendenceNotificationsEntity.getProjCrewMstrEntity().getProjId()
                            .getParentProjectMstrEntity().getProjName();
                }
            }
			toEmail = attendenceNotificationsEntity.getToUserId().getEmail();
	        userName = attendenceNotificationsEntity.getToUserId().getDisplayName();
	        generatedCode = AttendenceNotificationsHandler.generateNotificationNumber(attendenceNotificationsEntity);
	        month = CommonUtil.getMonth(attendenceNotificationsEntity.getCreatedOn());
	        fromDate = CommonUtil.convertDateToString(attendenceNotificationsEntity.getFromDate());
	        toDate = CommonUtil.convertDateToString(attendenceNotificationsEntity.getToDate());
	        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy");
	        forMonthYear = dateFormat.format(attendenceNotificationsEntity.getFromDate());
	        
		}
		
		String toSubject = "Request for Approval of Attendance Records Data entry for " + crewName +" Crew for " + forMonthYear;
		
		String text = "<html><body><p>" + userName + ",</p>"
				+ "<p>I have submitted my request for time elapsed past daily attendance records through " + pot 
				+ " as per the details mentioned here below.</p>"
				+ "<table border='1' bordercolor='#000' cellspacing='0' cellpadding='5'>"
				+ "<tr><td>EPS 		</td><td>" + epsName + "</td></tr>"
				+ "<tr><td>Project </td><td>" + projName + "</td></tr>"
				+ "<tr><td>Crew Name </td><td>" + crewName + "</td></tr>"
				+ "<tr><td>Month </td><td> "+ forMonthYear + "</td></tr>"
				+ "<tr><td>Affected </td><td> " + fromDate + ", " + toDate + "</td></tr>"
				+ "<tr><td>Notification number </td><td>  " + generatedCode + "</td></tr></table>"
				+ "<p>This is for granting your approval please." + "</p>"
				+ "<p>Regards,</p>" 
				+ "<p>" + AppUserUtils.getName() + "<br/>"
				+ AppUserUtils.getDisplayRole() + "</p></body></html>";
		
		commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
	}

	@Override
	public WorkDairyNotificationResp getWorkDairyNotifications(NotificationsGetReq notificationsGetReq) {
		WorkDairyNotificationResp workDairyNotificationResp = new WorkDairyNotificationResp();
		Date fromDate = null;
		Date toDate = null;
		if (CommonUtil.isNotBlankStr(notificationsGetReq.getFromDate())
				&& CommonUtil.isNotBlankStr(notificationsGetReq.getToDate())) {
			fromDate = CommonUtil.convertStringToDate(notificationsGetReq.getFromDate());
			toDate = CommonUtil.convertStringToDate(notificationsGetReq.getToDate());
		} else {
			toDate = new Date();
			fromDate = CommonUtil.substarctInputMonths(toDate, -1);
		}
		toDate = CommonUtil.addNumberOfDaysInputDate(toDate, 1);
		
		String notificationPendingStatus = null;
		String notificationApprIntlStatus = null;
		String notificationApprClntStatus = null;
		String notificationReqAddlTimeStatus = null;
		String notificationAddlTimeApprStatus = null;
		String notificationAddlTimeMsg = null;
		String notificationSubmitTimeMsg = null;
		List<WorkDairyNotificationsEntity> workDairyNotificationsEntities = new ArrayList<>();
		if (notificationsGetReq.getNotifyStatus().equalsIgnoreCase("All")) {
			log.info("All Search");
			notificationPendingStatus = "Pending";
			notificationApprIntlStatus = "Approved Internally";
			notificationApprClntStatus = "Approved by Client";
			notificationReqAddlTimeStatus = "Request for Additional Time";
			notificationAddlTimeApprStatus = "Approved";
			notificationAddlTimeMsg = "Request for Additional Time";
			notificationSubmitTimeMsg = "Approved";
			
			if (notificationsGetReq.getProjId() == null) {
				workDairyNotificationsEntities = workDairyNotificationRepository.findWorkDairyNotificationsAll(
						notificationAddlTimeMsg, notificationSubmitTimeMsg, notificationPendingStatus, notificationApprIntlStatus, 
						notificationApprClntStatus, notificationAddlTimeApprStatus, fromDate, toDate);
			//	System.out.println("call reached in the if block and size is : "+workDairyNotificationsEntities.size());
			
			} else {
				workDairyNotificationsEntities = workDairyNotificationRepository.findWorkDairyNotificationsAllByProjId(
						notificationsGetReq.getProjId(), notificationAddlTimeMsg, notificationSubmitTimeMsg, notificationPendingStatus, 
						notificationApprIntlStatus, notificationApprClntStatus, notificationAddlTimeApprStatus, fromDate, toDate);
			//	System.out.println("call reached in the else block and size is: "+workDairyNotificationsEntities.size());
			}
		} else {
			log.info("Search by STATUS");
			String value = notificationsGetReq.getCode();
			if (CommonUtil.isNotBlankStr(value)) {
				String[] newValue = value.split("-");
				WorkDairyNotificationsEntity workDairyNotificationsEntity = workDairyNotificationRepository
						.findOne(Long.parseLong(newValue[newValue.length - 1]));
				if (workDairyNotificationsEntity != null) {
					workDairyNotificationsEntities.add(workDairyNotificationsEntity);
				}
			} else if ("Pending".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())) {
				workDairyNotificationsEntities = workDairyNotificationRepository.findWorkDairyNotifications(
						notificationsGetReq.getProjId(), "Request for Additional Time",
						notificationsGetReq.getNotifyStatus(), fromDate, toDate);
				//if no projId is selected ?
				if(notificationsGetReq.getProjId() == null && notificationsGetReq.getNotifyStatus() != null) {
					workDairyNotificationsEntities = workDairyNotificationRepository.findWorkDairyNotificationsNoProjId(
							 notificationsGetReq.getNotifyStatus(), "Request for Additional Time", fromDate, toDate);
					//System.out.println("when status is pending and the size is: "+workDairyNotificationsEntities.size());
				}
			} else if ("Approved Internally".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())
					|| "Approved by Client".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())) {
				workDairyNotificationsEntities = workDairyNotificationRepository
						.findWorkDairyNotificationsByInternalApproval(notificationsGetReq.getProjId(), "Approved",
								notificationsGetReq.getNotifyStatus(), fromDate, toDate);
				//if no projId is selected ?
				if(notificationsGetReq.getProjId() == null && notificationsGetReq.getNotifyStatus() != null) {
					workDairyNotificationsEntities = workDairyNotificationRepository.findWorkDairyNotificationsNoProjId(
							  "Approved", notificationsGetReq.getNotifyStatus(), fromDate, toDate);
					//System.out.println("when status is Approved and the size is: "+workDairyNotificationsEntities.size());
				}
			} else {
				String notificationStatus = null;
				if ("Request for Additional Time".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())) {
					notificationStatus = "Pending";
				} else {
					notificationStatus = "Approved";
				}
				workDairyNotificationsEntities = workDairyNotificationRepository
						.findWorkDairyNotificationsByInternalApproval(notificationsGetReq.getProjId(), notificationStatus,
								"Request for Additional Time", fromDate, toDate);
				//is no projId is selected ?
				if(notificationsGetReq.getProjId() == null && notificationsGetReq.getNotifyStatus() != null) {
					workDairyNotificationsEntities = workDairyNotificationRepository.findWorkDairyNotificationsNoProjId(
							notificationStatus, "Request for Additional Time", fromDate, toDate);
					//System.out.println("when status is request for additional time and  the size is: "+workDairyNotificationsEntities.size());
				}
			}	
		}
		log.info("Setting POJO " + workDairyNotificationsEntities.size());
		if (CommonUtil.isListHasData(workDairyNotificationsEntities)) {
			for (WorkDairyNotificationsEntity workDairyNotificationsEntity1 : workDairyNotificationsEntities) {
				workDairyNotificationResp.getWorkDairyNotificationsTOs()
						.add(WorkDairyNotificationsHandler.convertEntityToPOJO(workDairyNotificationsEntity1));
				
				log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++ ADDED");
			}
		}
		log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++ RETURNING");
		
		return workDairyNotificationResp;

	}

	public WorkDairyNotificationResp getWorkDairyNotificationsByProjId(NotificationsGetReq notificationsGetReq) {
		WorkDairyNotificationResp workDairyNotificationResp = new WorkDairyNotificationResp();
		List<WorkDairyAdditionalTimeEntity> workDairyAdditionalTimeEntities = workDairyAdditionalTimeRepository
				.findWorkDairyByStatus(notificationsGetReq.getProjId(), "Pending");

		if (CommonUtil.isListHasData(workDairyAdditionalTimeEntities)) {
			for (WorkDairyAdditionalTimeEntity workDairyAdditionalTimeEntity : workDairyAdditionalTimeEntities) {
				WorkDairyNotificationsTO workDairyNotificationsTO = new WorkDairyNotificationsTO();
				workDairyNotificationsTO.setCrewName(workDairyAdditionalTimeEntity.getCrewId().getDesc());
				if (CommonUtil.isNotBlankDate(workDairyAdditionalTimeEntity.getFromDate()))
					workDairyNotificationsTO
							.setFromDate(CommonUtil.convertDateToString(workDairyAdditionalTimeEntity.getFromDate()));
				if (CommonUtil.isNotBlankDate(workDairyAdditionalTimeEntity.getToDate()))
					workDairyNotificationsTO
							.setToDate(CommonUtil.convertDateToString(workDairyAdditionalTimeEntity.getToDate()));
				workDairyNotificationsTO.setType(workDairyAdditionalTimeEntity.getType());
				workDairyNotificationsTO.setId(workDairyAdditionalTimeEntity.getId());
				workDairyNotificationsTO.setWorkDairyNumber(
						WorkDairyNotificationsHandler.generateAdditionalTimeCode(workDairyAdditionalTimeEntity));
				workDairyNotificationsTO.setReqUser(workDairyAdditionalTimeEntity.getApprUser().getUserName());
				if (null != workDairyAdditionalTimeEntity.getWorkDairyEntity()) {
					workDairyNotificationsTO.setWdmId(workDairyAdditionalTimeEntity.getWorkDairyEntity().getId());
				}
				workDairyNotificationResp.getWorkDairyNotificationsTOs().add(workDairyNotificationsTO);
			}
		}
		return workDairyNotificationResp;
	}

	public void saveWorkDairyNotifications(WorkDairyNotificationsSaveReq workDairyNotificationsSaveReq) {
		WorkDairyAdditionalTimeEntity workDairyAdditionalTimeEntity = new WorkDairyAdditionalTimeEntity();
		List<WorkDairyNotificationsEntity> workDairyNotificationsEntities = new ArrayList<>();
		for (WorkDairyNotificationsTO workDairyNotificationsTO : workDairyNotificationsSaveReq
				.getWorkDairyNotificationsTOs()) {
			WorkDairyNotificationsEntity workDairyNotificationsEntity = WorkDairyNotificationsHandler
					.convertPOJOToEntity(workDairyNotificationsTO, workDairyRepository);
			workDairyNotificationsEntities.add(workDairyNotificationsEntity);

			// setting to Create-Additional time Entity
			String type = null;
			if (workDairyNotificationsTO.getType() != null) {
				type = workDairyNotificationsTO.getType();
			} else {
				type = "Original";
			}
			workDairyAdditionalTimeEntity
					.setFromDate(CommonUtil.convertStringToDate(workDairyNotificationsTO.getFromDate()));
			workDairyAdditionalTimeEntity
					.setToDate(CommonUtil.convertStringToDate(workDairyNotificationsTO.getToDate()));
			workDairyAdditionalTimeEntity.setApprUser(commonRepository.findOne(workDairyNotificationsTO.getToUserId()));
			workDairyAdditionalTimeEntity.setStatus("Pending");
			workDairyAdditionalTimeEntity.setType(type);
			workDairyAdditionalTimeEntity
					.setCrewId(projCrewMstrRepository.findOne(workDairyNotificationsTO.getCrewId()));
			workDairyAdditionalTimeEntity
					.setWorkDairyEntity(workDairyRepository.findOne(workDairyNotificationsTO.getWdmId()));
			// saving AdditionalTime Entity
			WorkDairyAdditionalTimeEntity savedEntity = workDairyAdditionalTimeRepository
					.save(workDairyAdditionalTimeEntity);
			sendMailForAdditionalTimeRequest(savedEntity);
		}
		// saving Notification Entity
		workDairyNotificationRepository.save(workDairyNotificationsEntities);

	}

	private void sendMailForAdditionalTimeRequest(WorkDairyAdditionalTimeEntity workDairyAdditionalTimeEntity) {
		String type = null;
		String wdGeneratedCode = generateWorkDairyCode(workDairyAdditionalTimeEntity.getWorkDairyEntity() );
		System.out.println("generate wdm code:"+wdGeneratedCode);
		if ("Original".equalsIgnoreCase(workDairyAdditionalTimeEntity.getType())
				|| workDairyAdditionalTimeEntity.getType() == null) {
			type = "Request for Additional Time Work Diary Original - ";
		} else if ("Internal".equalsIgnoreCase(workDairyAdditionalTimeEntity.getType())) {
			type = "Request for Additional Time Work Diary Internal - ";
		} else {
			type = "Request for Additional Time Work Diary External - ";
		}
		String crewName = null;
		String apprName = null;
		String code = null;
		String epsName = null;
		String projName = null;
		String ccEmail = "";
		String toEmail = null;

		if (null != workDairyAdditionalTimeEntity) {
			ProjCrewMstrEntity projCrewMstrEntity = workDairyAdditionalTimeEntity.getCrewId();
			if (projCrewMstrEntity != null) {
				if (null != projCrewMstrEntity.getDesc())
					crewName = projCrewMstrEntity.getDesc();
				code = projCrewMstrEntity.getCode();
				if (projCrewMstrEntity.getProjId() != null)
					epsName = projCrewMstrEntity.getProjId().getParentProjectMstrEntity().getProjName();
				if (projCrewMstrEntity.getProjId() != null)
					projName = projCrewMstrEntity.getProjId().getProjName();
			}
			apprName = workDairyAdditionalTimeEntity.getApprUser().getDisplayName();
			toEmail = workDairyAdditionalTimeEntity.getApprUser().getEmail();
			String date = CommonUtil.convertDateToString(workDairyAdditionalTimeEntity.getCreatedOn());

			String toSubject = type + wdGeneratedCode + " dated " + date;
			String text = "<html><body><p>" + apprName + ",</p>"
					+ "<p>I have granted additional time for your approval on Employee through " + pot + ", as per details mentioned here below. </p>"
					+ "<table border='1'><tr><td>EPS </td><td>" + epsName + "</td></tr><tr><td> Project </td><td>"
					+ projName + "</td></tr><tr><td>Crew </td><td>" + crewName
					+ "</td></tr><tr><td>Work Diary Number</td><td>" + wdGeneratedCode + "</td></tr><tr><td>Date</td><td>"
					+ new Date() + "</td></tr></table><br/>This is for your Information and Further Action Please."
					+ "<p>Regards,</p>" + "<p>" + AppUserUtils.getName() + "<br/>"
					+ AppUserUtils.getDisplayRole() + "</p></body></html>";

			commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
		}
	}

	public void saveWorkDiaryAddTimeNotifications(WorkDairyNotificationsSaveReq workDairyNotificationsSaveReq) {
		List<WorkDairyNotificationsEntity> workDairyNotificationsEntities = new ArrayList<WorkDairyNotificationsEntity>();
		WorkDairyNotificationsEntity workDairyNotificationsEntity = null;
		for (WorkDairyNotificationsTO workDairyNotificationsTO : workDairyNotificationsSaveReq
				.getWorkDairyNotificationsTOs()) {
			workDairyNotificationsEntity = WorkDairyNotificationsHandler.convertPOJOToEntity(workDairyNotificationsTO,
					workDairyRepository);
			// workDairyNotificationsEntity.setType(workDairyNotificationsTO.getType());
			workDairyNotificationsEntities.add(workDairyNotificationsEntity);
		}
		workDairyNotificationRepository.save(workDairyNotificationsEntities);
	}

	public EmployeeNotificationsResp getEmployeeNotifications(NotificationsGetReq notificationsGetReq) {

		EmployeeNotificationsResp employeeNotificationsResp = new EmployeeNotificationsResp();
		Date fromDate = null;
		Date toDate = null;
		if (CommonUtil.isNotBlankStr(notificationsGetReq.getFromDate())
				&& CommonUtil.isNotBlankStr(notificationsGetReq.getToDate())) {
			fromDate = CommonUtil.convertStringToDate(notificationsGetReq.getFromDate());
			toDate = CommonUtil.convertStringToDate(notificationsGetReq.getToDate());
		} else {
			toDate = new Date();
			fromDate = CommonUtil.substarctInputMonths(toDate, -1);
		}
		toDate = CommonUtil.addNumberOfDaysInputDate(toDate, 1);
		
		String notificationPendingStatus = null;
		String notificationApprStatus = null;
		String notificationRejtStatus = null;
		String notificationAddlTimeReqStatus = null;
		String notificationAddlTimeApprStatus = null;
		String notificationAddlTimeMsg = null;
		String notificationSubmitTimeMsg = null;
		
		List<EmployeeNotificationsEntity> employeeNotificationsEntites = null;
		
		if (notificationsGetReq.getNotifyStatus().equalsIgnoreCase("All")) {
			notificationPendingStatus = "Pending";
			notificationApprStatus = "APPROVED";
			notificationRejtStatus = "REJECTED";
			
			notificationAddlTimeReqStatus = "Additional Time for Request";
			
			
			if (notificationsGetReq.getProjId() == null) {
				employeeNotificationsEntites = employeeNotificationsRepository.findEmployeeNotificationsAll(
						notificationPendingStatus, notificationApprStatus,  notificationRejtStatus, 
						fromDate, toDate);
			} else {
				employeeNotificationsEntites = employeeNotificationsRepository.findEmployeeNotificationsAllByProjId(
						notificationsGetReq.getProjId(), notificationPendingStatus, notificationApprStatus,  notificationRejtStatus, 
						notificationAddlTimeReqStatus, fromDate, toDate);
				
			}
		} else {
			if (notificationsGetReq.getProjId() == null) {
				if ("Pending".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())
						|| "APPROVED".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())
						|| "REJECTED".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())) {

					employeeNotificationsEntites = employeeNotificationsRepository.findEmployeeNotifications(
							notificationsGetReq.getNotifyStatus(), "Additional Time for Request", fromDate, toDate);
				} else if ("Additional Time for Submit".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())
						|| "Additional Time for Approved".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())) {
					String notificationStatus = null;
					if ("Additional Time for Submit".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())) {
						notificationStatus = "Pending";
					} else {
						notificationStatus = "APPROVED";
					}
					employeeNotificationsEntites = employeeNotificationsRepository.findEmployeeNotificationsForAdditionalTime1(
							notificationsGetReq.getProjId(), notificationStatus, "Additional Time for Request", fromDate,
							toDate);

				}
			} else {
				if ("Pending".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())
						|| "APPROVED".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())
						|| "REJECTED".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())) {

					employeeNotificationsEntites = employeeNotificationsRepository.findEmployeeNotificationsByProjectId(
							notificationsGetReq.getProjId(), notificationsGetReq.getNotifyStatus(),
							"Additional Time for Request", fromDate, toDate);
				} else if ("Additional Time for Submit".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())
						|| "Additional Time for Approved".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())) {
					String notificationStatus = null;
					if ("Additional Time for Submit".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())) {
						notificationStatus = "Pending";
					} else {
						notificationStatus = "APPROVED";
					}
					employeeNotificationsEntites = employeeNotificationsRepository.findEmployeeNotificationsForAdditionalTime(
							notificationsGetReq.getProjId(), notificationStatus, "Additional Time for Request", fromDate,
							toDate);

				}
			}
		}
		
		
		if (CommonUtil.isListHasData(employeeNotificationsEntites)) {
			for (EmployeeNotificationsEntity employeeNotificationsEntity : employeeNotificationsEntites) {
				employeeNotificationsResp.getEmployeeNotificationsTOs()
						.add(EmployeeNotificationsHandler.convertEntityToPOJO(employeeNotificationsEntity));
			}
		}
		return employeeNotificationsResp;

	}

	public EmployeeNotificationsResp getEmployeeNotificationsByProjId(NotificationsGetReq notificationsGetReq) {
		// type is not reuired
		EmployeeNotificationsResp employeeNotificationsResp = new EmployeeNotificationsResp();
		List<EmployeeNotificationsEntity> employeeNotificationEntities = employeeNotificationsRepository
				.findEmployeeNotificationsByProjId(notificationsGetReq.getProjId(),
						notificationsGetReq.getNotifyStatus(), notificationsGetReq.getType());
		if (CommonUtil.isListHasData(employeeNotificationEntities)) {
			for (EmployeeNotificationsEntity employeeNotificationsEntity : employeeNotificationEntities) {
				EmployeeNotificationsTO employeeNotificationsTO = new EmployeeNotificationsTO();
				employeeNotificationsTO
						.setDate(CommonUtil.convertDateToString(employeeNotificationsEntity.getUpdatedOn()));
				employeeNotificationsTO
						.setCode(EmployeeLeaveNotificationsHandler.generateReqCode(employeeNotificationsEntity));
				employeeNotificationsTO.setNotifyRefId(employeeNotificationsEntity.getId());
				employeeNotificationsResp.getEmployeeNotificationsTOs().add(employeeNotificationsTO);
			}
		}
		return employeeNotificationsResp;
	}

	public void saveEmployeeNotifications(EmployeeNotificationsSaveReq employeeNotificationsSaveReq) {
		List<EmployeeNotificationsEntity> employeeNotificationsEntities = new ArrayList<>();
		
		for (EmployeeNotificationsTO employeeNotificationsTO : employeeNotificationsSaveReq
				.getEmployeeNotificationsTOs()) {
			log.info("employeeNotificationsTO.getNotifyId() -------- " + employeeNotificationsTO.getNotifyId());
			log.info("Start Inserting.....");
			
			EmployeeNotificationsEntity employeeNotificationsEntity = employeeNotificationsRepository
					.findOne(employeeNotificationsTO.getNotifyId());
			
			if(employeeNotificationsEntity != null) {
				employeeNotificationsEntity.setNotificationMsg("Additional Time for Request");
				
				if (CommonUtil.isNonBlankLong(employeeNotificationsTO.getApprUserId())) {
		            UserMstrEntity userMstrEntity = loginRepository.findOne(employeeNotificationsTO.getApprUserId());
		            employeeNotificationsEntity.setApprUserId(userMstrEntity);
		        }
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
		        	if (employeeNotificationsEntity.getForProject().equals(entry.getKey())) {
		                toprojName = entry.getValue().getName();
		            }

		        }
				employeeNotificationsEntity.setType("Request for additional time for approval of employee transfer to Project - " + toprojName);
				
				sendEmpNotifyMail(employeeNotificationsEntity);
			}
		}
	}
	
	private void sendEmpNotifyMail(EmployeeNotificationsEntity employeeNotificationsEntity) {
		String epsName = null;
		String projName = null;
		String ccEmail;
		String toEmail;
		String toSubject;
		String text;
		String toepsName = null;
		String toprojName = null;
		String reqDate = null;
		
		log.info("Employee Notification Id is " + employeeNotificationsEntity.getId());
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
        	if (CommonUtil.objectNotNull(employeeNotificationsEntity.getProjMstrEntity())
                    && employeeNotificationsEntity.getProjMstrEntity().getProjectId().equals(entry.getKey())) {
                epsName = entry.getValue().getCode();
                projName = entry.getValue().getName();
            }
        	if (employeeNotificationsEntity.getForProject().equals(entry.getKey())) {
                toepsName = entry.getValue().getCode();
                toprojName = entry.getValue().getName();
            }

        }
        
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yy");
    	reqDate = dateFormat1.format(employeeNotificationsEntity.getDate());

		UserMstrEntity userMstr = employeeNotificationsEntity.getReqUserId();
		UserMstrEntity userMstr1 = employeeNotificationsEntity.getApprUserId();
		toEmail = userMstr1.getEmail();
		ccEmail = userMstr.getEmail();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String apprDate = dateFormat.format(employeeNotificationsEntity.getDate());
        
		toSubject = "Request for additional time for approval of employee transfer to Project  - " + toprojName;
		text = "<html><body><p>" + userMstr.getDisplayName() + ",</p>"
				+ "<p>Reference Notification ID  " + EmployeeLeaveNotificationsHandler.generateCode(employeeNotificationsEntity) + " dated " + reqDate + "</p>"
				+ "<p>I have submitted my request to grant additional time for approval on Employee through " + pot + ", as per details mentioned here below. </p>"
				+ "<table border='1'>"
				+ "<tr><td>From EPS </td><td>" + epsName + "</td></tr>"
				+ "<tr><td>From Project </td><td>" + projName + "</td></tr>"
				+ "<tr><td>To EPS </td><td>" + toepsName + "</td></tr>"
				+ "<tr><td>To Project </td><td>" + toprojName + "</td></tr>"
				+ "<tr><td>Requisition ID</td><td>" + EmployeeLeaveNotificationsHandler.generateReqCode(employeeNotificationsEntity) + " dated " + reqDate + "</td></tr>"
				+ "<tr><td>Notification ID</td><td>" + EmployeeLeaveNotificationsHandler.generateCode(employeeNotificationsEntity) + " dated " + reqDate + "</td></tr>"
				+ "</table>"
				+ "<p>This is for your information and further action please.</p>"
				+ "<p>Regards,</p>" + "<p>" + AppUserUtils.getName() + "<br/>" + AppUserUtils.getDisplayRole() + " <br/>" + apprDate + "</p>"
				+ "</body></html>";
		commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);

	}

	public ProcurementNotificationResp getProcurementNotifications(NotificationsGetReq notificationsGetReq) {

		ProcurementNotificationResp procurementNotificationResp = new ProcurementNotificationResp();
		Date fromDate = null;
		Date toDate = null;
		if (CommonUtil.isNotBlankStr(notificationsGetReq.getFromDate())
				&& CommonUtil.isNotBlankStr(notificationsGetReq.getToDate())) {
			fromDate = CommonUtil.convertStringToDate(notificationsGetReq.getFromDate());
			toDate = CommonUtil.convertStringToDate(notificationsGetReq.getToDate());
		} else {
			toDate = new Date();
			fromDate = CommonUtil.substarctInputMonths(toDate, -1);
		}
		toDate = CommonUtil.addNumberOfDaysInputDate(toDate, 1);
		log.info("notificationsGetReq.getProjId() " + notificationsGetReq.getProjId());
		log.info("notificationsGetReq.getCode() " + notificationsGetReq.getCode());
		log.info("notificationsGetReq.getNotifyStatus() " + notificationsGetReq.getNotifyStatus());
		log.info("notificationsGetReq.getStatus() " + notificationsGetReq.getStatus());
		log.info("notificationsGetReq fromDate " + fromDate);
		log.info("notificationsGetReq toDate " + toDate);
		
		/*
		List<ReqApprNotificationEntity> reqApprNotificationEntities = reqApprNotificationsRepository
				.findProcureNotifications(notificationsGetReq.getProjId(), notificationsGetReq.getCode(),
						notificationsGetReq.getNotifyStatus(), notificationsGetReq.getStatus(), fromDate, toDate);

		if (CommonUtil.isListHasData(reqApprNotificationEntities)) {
			for (ReqApprNotificationEntity reqApprNotificationEntity : reqApprNotificationEntities) {
				procurementNotificationResp.getReqApprNotificationsTOs()
						.add(ProcurementNotificationsHandler.convertEntityToPOJO(reqApprNotificationEntity));
			}
		}
		*/
		List<ProcurementNotificationsEntity> procurementNotificationsEntity = new ArrayList<>();
		if (notificationsGetReq.getProjId() != null) {
			procurementNotificationsEntity = procurementNotificationsRepository
					.findProcureNotifications1(notificationsGetReq.getProjId(), notificationsGetReq.getCode(),
							notificationsGetReq.getNotifyStatus(), notificationsGetReq.getStatus(), fromDate, toDate);
		} else {
			procurementNotificationsEntity = procurementNotificationsRepository
					.findProcureNotificationsForAll(notificationsGetReq.getCode(),
							notificationsGetReq.getNotifyStatus(), notificationsGetReq.getStatus(), fromDate, toDate);
		}
		if (CommonUtil.isListHasData(procurementNotificationsEntity)) {
			for (ProcurementNotificationsEntity procurementNotificationsEntities : procurementNotificationsEntity) {
				procurementNotificationResp.getProcurementNotificationsTOs()
				.add(ProcurementNotificationsHandler.convertEntityToPOJO(procurementNotificationsEntities));
			}
		}
		return procurementNotificationResp;

	}

	public void saveProcurementNotifications(ProcureNotificationsSaveReq procureNotificationsSaveReq) {
	
		//List<ProcurementNotificationsEntity> procurementNotificationsEntities = new ArrayList<>();
		String contractStageStatus = null;
		for (ProcurementNotificationsTO procurementNotificationsTO : procureNotificationsSaveReq
				.getProcurementNotificationsTOs()) {
			log.info("The Pre Contractor ID is " + procurementNotificationsTO.getId());
			ProcurementNotificationsEntity procNotEntity = procurementNotificationsRepository
					.findPreContractNotificationDetails(procurementNotificationsTO.getId(), procurementNotificationsTO.getStatus());
			if(procNotEntity != null) {
				log.info("procNotEntity.getProcureStage() " + procNotEntity.getProcureStage());
				if (procNotEntity.getProcureStage().equalsIgnoreCase("Stage 1 Request") ) {
					procNotEntity.setNotificationStatus("Request for Additional Time for Stage 1 Procurement Approval");
				} else if  (procNotEntity.getProcureStage().equalsIgnoreCase("Stage 2 Request") ){
					procNotEntity.setNotificationStatus("Request for Additional Time for Stage 2 Procurement Approval");
				}
				procNotEntity.setReqComments(procurementNotificationsTO.getReqComments());
				procNotEntity.setToUserId(loginRepository.findOne(procurementNotificationsTO.getToUserId()));
				procNotEntity.setApprStatus(7);
				//procNotEntity.setReqComments(procurementSaveReq.getPreContractTO().getPreContractReqApprTO().getReqComments());
				
				//sendEmail(procNotEntity);
			} 
			// for Additional Time Notification creation
			
			if(procNotEntity.getProcureStage().equalsIgnoreCase("Stage 1 Request") || procNotEntity.getProcureStage().equalsIgnoreCase("Stage 1 Approval")) {
				contractStageStatus = "Stage 1 Internal Approval";
			} else if(procNotEntity.getProcureStage().equalsIgnoreCase("Stage 2 Request") || procNotEntity.getProcureStage().equalsIgnoreCase("Stage 2 Approval")) {
				contractStageStatus = "Stage 2 Internal Approval";
			}
			ProcurementNormalTimeEntity procurementNormalTime = procurementNormalTimeRepository
        			.findCutOffDaysForProject(procNotEntity.getProjId().getProjectId(), contractStageStatus);
			
			
			ProcurementAddtionalTimeApprEntity procAddlTimeApprEntity = procurementAddltionalTimeRepository
					.findPreContractAddlTimeDetails(procurementNotificationsTO.getPreContractId(), procurementNotificationsTO.getStatus());
		
			ProcurementAddtionalTimeApprEntity savedEntity = null;
			if (procAddlTimeApprEntity == null) {
				ProcurementAddtionalTimeApprEntity procAddtionalTimeApprEntity = new ProcurementAddtionalTimeApprEntity();
				
				procAddtionalTimeApprEntity.setRequisitionDate(new Date());
				procAddtionalTimeApprEntity.setLatest(true);
				procAddtionalTimeApprEntity.setStatus(procurementNotificationsTO.getStatus());
				procAddtionalTimeApprEntity.setNotificationMessage("Request for Additional Time");
				procAddtionalTimeApprEntity.setNotificationStatus("Pending");
				procAddtionalTimeApprEntity.setProjProcurementEntity(procurementNormalTimeRepository.findOne( procurementNormalTime.getId()));
				procAddtionalTimeApprEntity.setProcureStage(contractStageStatus);
				procAddtionalTimeApprEntity.setApprUser(commonRepository.findOne(procurementNotificationsTO.getToUserId()));
				procAddtionalTimeApprEntity.setProjId(procNotEntity.getProjId().getProjectId());
				procAddtionalTimeApprEntity.setPreContractEntity(precontractRepository.findOne(procNotEntity.getPreContractEntity().getId()));
				procAddtionalTimeApprEntity.setProcurementNotificationsEntity(procurementNotificationsRepository.findOne(procNotEntity.getId()));
				
				savedEntity = procurementAddltionalTimeRepository.save(procAddtionalTimeApprEntity);
			} else {
				procAddlTimeApprEntity.setProjProcurementEntity(procurementNormalTimeRepository.findOne( procurementNormalTime.getId()));
				procAddlTimeApprEntity.setNotificationStatus("Pending");
				procAddlTimeApprEntity.setProcureStage(contractStageStatus);
				
				savedEntity = procurementAddltionalTimeRepository.save(procAddlTimeApprEntity);
			}
			
	
			sendProcurementMail(savedEntity);//Send mail here...
		}
		
	}
	
	private void sendProcurementMail(ProcurementAddtionalTimeApprEntity procurementAddtionalTimeApprEntity) {
				String apprName = "";
		        String epsName = "";
		        String projName = "";
		        String toEmail = null;
		        String toSubject = null;
		        String stage = null;
		        String procCateg = null;
		        String apprStage = null;
		        String approvalStage = null;
		        String reqId = null;
		        String notId = null;
		        String prString = null;
		        String text = null;
		        String reqDate = null;
		        String updateOn = null;
				if (null != procurementAddtionalTimeApprEntity) {
					
					Long notofyId = procurementAddtionalTimeApprEntity.getProcurementNotificationsEntity().getId();
					
					ProcurementNotificationsEntity procurementNotificationsEntity1 = procurementNotificationsRepository.findOne(notofyId);
					if (null != procurementNotificationsEntity1) {
						if(procurementNotificationsEntity1.getProcureStage().equalsIgnoreCase("Stage 1 Request") || 
			        			procurementNotificationsEntity1.getProcureStage().equalsIgnoreCase("Stage 1 Approval")) {
			        		approvalStage = "1";
			        	} else {
			        		approvalStage = "2";
			        	}

			        	if(procurementNotificationsEntity1.getProcureStage().equalsIgnoreCase("Stage 1 Request")) { 
			            	prString = "PRS1";
			            } else if (procurementNotificationsEntity1.getProcureStage().equalsIgnoreCase("Stage 1 Approval")) {
			            	prString = "PAS1";
			            } else if (procurementNotificationsEntity1.getProcureStage().equalsIgnoreCase("Stage 2 Request")) {
			            	prString = "PRS2";
			            } else if (procurementNotificationsEntity1.getProcureStage().equalsIgnoreCase("Stage 2 Approval")) {
			            	prString = "PAS2";
			            }
			        	
			        	epsName = procurementNotificationsEntity1.getProjId().getParentProjectMstrEntity().getProjName();
			        	projName = procurementNotificationsEntity1.getProjId().getProjName();
			        	procCateg = procurementNotificationsEntity1.getProcureCatg();
			        	apprName = procurementNotificationsEntity1.getToUserId().getFirstName() 
			        			+ " " + procurementNotificationsEntity1.getToUserId().getLastName();
			        	apprStage = approvalStage;
			        	SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yy");
			        	reqDate = dateFormat.format(procurementNotificationsEntity1.getDate());
			        	reqId = procurementNotificationsEntity1.getModuleCode(); //+ " dated " + reqDate;
			        	notId = prString + "-" + procurementNotificationsEntity1.getId(); //+ " dated " + reqDate;
			        	toEmail = procurementNotificationsEntity1.getToUserId().getEmail();
			        	updateOn = dateFormat.format(procurementNotificationsEntity1.getUpdatedOn());
			        	stage = procurementNotificationsEntity1.getProcureStage();
			        	
			        	if (procurementNotificationsEntity1.getApprStatus() == 7) {
			        		toSubject = "Request for additional time - Procurement Stage " + apprStage + " Approval" ;
				        	text = "<html><body><p>Hi</p>"
				        			+"<p>" + apprName + ",</p>"
				                    + "<p>I have Request for additional time  - Procurement Stage " + apprStage + " Approval through " + pot + ", as per details mentioned here below.</p>"
				                    + "<table border='1'>"
				                    + "<tr><td>EPS </td><td>" + epsName + "</td></tr>"
				                    + "<tr><td>Project </td><td>"+ projName + "</td></tr>"
				                    + "<tr><td>Procurement Category </td><td>" + procCateg + "</td></tr>"
				                    + "<tr><td>Approval  Stage</td><td>" + apprStage + "</td></tr>"
				                    + "<tr><td>Requisition ID</td><td>" + reqId + "</td></tr>"
				                    + "<tr><td>Notification ID</td><td>"+ notId +"</td></tr>"
				                    + "<tr><td>Request Date</td><td>"+ reqDate +"</td></tr>"
				                    + "</table><br>This is for your information & further action please." + "<p>Regards,</p>" + "<p>"
			                    + AppUserUtils.getName() + "<br/>" + AppUserUtils.getDisplayRole() + "</p></body></html>";
				        	
				        	log.info("text " + text);
							commonEmail.sendEmailNotification(toEmail, "", toSubject, text);
			        	}
					}
				}

	}

	public ProcurementNotificationResp getProcurementNotificationsByProjId(NotificationsGetReq notificationsGetReq) {
		log.info("getProcurementNotificationsByProjId method to get notification info...");
		Long notId = null;
		ProcurementNotificationResp procurementNotificationResp = new ProcurementNotificationResp();
		List<ProcurementAddtionalTimeApprEntity>  procAddtionalTimeApprEntity  = new ArrayList<>();
		
		log.info("Notify Status is " + notificationsGetReq.getNotifyStatus());
		log.info("notificationsGetReq.getProjId() " + notificationsGetReq.getProjId());
		log.info("notificationsGetReq.getStatus() " + notificationsGetReq.getStatus());
		if ("Pending".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())) {
			
			procAddtionalTimeApprEntity = procurementAddltionalTimeRepository.findProcNotificationsByStatus(
					notificationsGetReq.getProjId(), "Pending", "Request for Additional Time",
					notificationsGetReq.getStatus());
			
		}
		
		if (CommonUtil.isListHasData(procAddtionalTimeApprEntity)) {
			
			log.info("procAddtionalTimeApprEntity has data");
			for (ProcurementAddtionalTimeApprEntity procAddlTimeEntity : procAddtionalTimeApprEntity) {
				ProcurementNotificationsTO procurementNotificationsTO = new ProcurementNotificationsTO();
				if(procAddlTimeEntity != null) {
					log.info("procAddtionalTimeApprEntity NOT EMPTY");
					procurementNotificationsTO.setId(procAddlTimeEntity.getId());
					procurementNotificationsTO.setCode("PREQ-"+procAddlTimeEntity.getId());
					procurementNotificationsTO.setRequisitionDate(CommonUtil.convertDateToString(procAddlTimeEntity.getRequisitionDate()));
					procurementNotificationsTO.setStatus(procAddlTimeEntity.getStatus());
					procurementNotificationsTO.setNotifyRefId(procAddlTimeEntity.getProjProcurementEntity().getId());
					procurementNotificationsTO.setStage(procAddlTimeEntity.getProcureStage());
					procurementNotificationsTO.setAddlTimeNotificationStatus(procAddlTimeEntity.getNotificationStatus());
					procurementNotificationsTO.setNotificationMessage(procAddlTimeEntity.getNotificationMessage());
					procurementNotificationsTO.setToUserId(procAddlTimeEntity.getApprUser().getUserId());
					procurementNotificationsTO.setToUserName(procAddlTimeEntity.getApprUser().getUserName());
					procurementNotificationsTO.setPreContractId(procAddlTimeEntity.getPreContractEntity().getId());
					procurementNotificationsTO.setNotificationId(procAddlTimeEntity.getProcurementNotificationsEntity().getId());
					procurementNotificationResp.getProcurementNotificationsTOs().add(procurementNotificationsTO);
				}
			}
		}
		return procurementNotificationResp;
	}

	public PlantNotificationResp getPlantNotifications(NotificationsGetReq notificationsGetReq) {

		PlantNotificationResp plantNotificationResp = new PlantNotificationResp();
		Date fromDate = null;
		Date toDate = null;
		if (CommonUtil.isNotBlankStr(notificationsGetReq.getFromDate())
				&& CommonUtil.isNotBlankStr(notificationsGetReq.getToDate())) {
			fromDate = CommonUtil.convertStringToDate(notificationsGetReq.getFromDate());
			toDate = CommonUtil.convertStringToDate(notificationsGetReq.getToDate());
		} else {
			toDate = new Date();
			fromDate = CommonUtil.substarctInputMonths(toDate, -1);
		}
		toDate = CommonUtil.addNumberOfDaysInputDate(toDate, 1);
		List<PlantNotificationsEntity> plantNotificationsEntities = null;

		String notificationPendingStatus = null;
		String notificationApprStatus = null;
		String notificationRejtStatus = null;
		String notificationAddlTimeReqStatus = null;
		
		if (notificationsGetReq.getNotifyStatus().equalsIgnoreCase("All")) {
			log.info("notificationsGetReq.getNotifyStatus() is ALL ");
			
			notificationPendingStatus = "Pending";
			notificationApprStatus = "APPROVED";
			notificationRejtStatus = "REJECTED";
			
			log.info("fromDate " + fromDate);
			log.info("toDate " + toDate);
			
			notificationAddlTimeReqStatus = "Additional Time for Request";
			if (notificationsGetReq.getProjId() == null) {
				log.info("notificationsGetReq.getNotifyStatus() is ALL & Project Id is NULL ");
				plantNotificationsEntities = plantNotificationsRepository.findPlantNotificationsAll(
						notificationPendingStatus, notificationApprStatus, notificationRejtStatus, 
						fromDate, toDate);
			} else {
				log.info("notificationsGetReq.getNotifyStatus() is ALL & Project Id is NOT NULL ");
				plantNotificationsEntities = plantNotificationsRepository.findEmployeeNotificationsAllByProjId(
						notificationsGetReq.getProjId(), notificationPendingStatus, notificationApprStatus, notificationRejtStatus, 
						notificationAddlTimeReqStatus, fromDate, toDate);
			}
			
		} else {
			log.info("notificationsGetReq.getNotifyStatus() is NOT ALL ");
			if (notificationsGetReq.getProjId() == null) {
				log.info("notificationsGetReq.getNotifyStatus() is NOR ALL & Project Id is NULL ");
				if ("Pending".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())
						|| "APPROVED".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())
						|| "REJECTED".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())) {
					plantNotificationsEntities = plantNotificationsRepository.findPlantNotifications(
							notificationsGetReq.getNotifyStatus(),
							"Additional Time for Request", fromDate, toDate);
				} else if ("Additional Time for Submit".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())
						|| "Additional Time for Approved".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())) {
					String notificationStatus = null;
					if ("Additional Time for Submit".equalsIgnoreCase(notificationsGetReq.getNotifyStatus()))
						notificationStatus = "Pending";
					else
						notificationStatus = "APPROVED";
					
					plantNotificationsEntities = plantNotificationsRepository.findPlantNotificationsForAdditionalTime(
							notificationStatus, "Additional Time for Request", fromDate,
							toDate);
				}
			} else {
				log.info("notificationsGetReq.getNotifyStatus() is NOR ALL & Project Id is NOT NULL ");
				if ("Pending".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())
						|| "APPROVED".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())
						|| "REJECTED".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())) {
					plantNotificationsEntities = plantNotificationsRepository.findPlantNotificationsByProjectId(
							notificationsGetReq.getProjId(), notificationsGetReq.getNotifyStatus(),
							"Additional Time for Request", fromDate, toDate);
				} else if ("Additional Time for Submit".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())
						|| "Additional Time for Approved".equalsIgnoreCase(notificationsGetReq.getNotifyStatus())) {
					String notificationStatus = null;
					if ("Additional Time for Submit".equalsIgnoreCase(notificationsGetReq.getNotifyStatus()))
						notificationStatus = "Pending";
					else
						notificationStatus = "APPROVED";

					plantNotificationsEntities = plantNotificationsRepository.findPlantNotificationsForAdditionalTimeByProjectId(
							notificationsGetReq.getProjId(), notificationStatus, "Additional Time for Request", fromDate,
							toDate);
				}
			}
		}
		
		

		if (CommonUtil.isListHasData(plantNotificationsEntities)) {
			for (PlantNotificationsEntity plantNotificationsEntity : plantNotificationsEntities) {
				plantNotificationResp.getPlantNotificationsTOs()
						.add(PlantNotificationsHandler.convertEntityToPOJO(plantNotificationsEntity));
			}
		}
		return plantNotificationResp;

	}

	public PlantNotificationResp getPlantNotificationsByProjId(NotificationsGetReq notificationsGetReq) {

		PlantNotificationResp plantNotificationResp = new PlantNotificationResp();
		List<PlantNotificationsEntity> plantNotificationsEntities = plantNotificationsRepository
				.findPlantNotificationsByProjId(notificationsGetReq.getProjId(), notificationsGetReq.getNotifyStatus(),
						"Additional Time for Request");

		if (CommonUtil.isListHasData(plantNotificationsEntities)) {
			for (PlantNotificationsEntity plantNotificationsEntity : plantNotificationsEntities) {
				plantNotificationResp.getPlantNotificationsTOs()
						.add(PlantNotificationsHandler.convertEntityToPOJO(plantNotificationsEntity));
			}
		}
		return plantNotificationResp;

	}

	public void savePlantNotifications(PlantNotificationsSaveReq plantNotificationsSaveReq) {
		List<PlantNotificationsEntity> plantNotificationsEntities = new ArrayList<>();
		for (PlantNotificationsTO plantNotificationsTO : plantNotificationsSaveReq.getPlantNotificationsTOs()) {
			log.info("employeeNotificationsTO.getNotifyId() -------- " + plantNotificationsTO.getNotifyId());
			
			PlantNotificationsEntity plantNotificationsEntity = plantNotificationsRepository
					.findOne(plantNotificationsTO.getNotifyId());
			
			if (plantNotificationsEntity != null) {
				plantNotificationsEntity.setNotificationMsg("Additional Time for Request");
				
				if (CommonUtil.isNonBlankLong(plantNotificationsTO.getApprUserId())) {
					UserMstrEntity userMstrEntity = loginRepository.findOne(plantNotificationsTO.getApprUserId());
					plantNotificationsEntity.setApprUserId(userMstrEntity);
				}
				
				String toprojName = null;
				ProjMstrEntity forProjEntity = plantNotificationsEntity.getProjMstrEntity();
				if (CommonUtil.objectNotNull(forProjEntity)) {
					toprojName = forProjEntity.getProjName();
				}
				plantNotificationsEntity.setType("Request for additional time for approval of plant transfer to Project - " + toprojName);
				
				sendPlantNotifyMail(plantNotificationsEntity);
			}
			/*
			PlantNotificationsEntity plantNotificationsEntity = PlantNotificationsHandler
					.convertPOJOToEntity(plantNotificationsTO, epsProjRepository, loginRepository);

			String toprojName = null;
			ProjMstrEntity forProjEntity = plantNotificationsEntity.getProjMstrEntity();
			if (CommonUtil.objectNotNull(forProjEntity)) {
				toprojName = forProjEntity.getProjName();
			}
				
			plantNotificationsEntity.setType("Additional time granted for plant transfer to Project" + toprojName);
			plantNotificationsEntities.add(plantNotificationsEntity);
			*/
		}
		List<PlantNotificationsEntity> savedEntites = plantNotificationsRepository.save(plantNotificationsEntities);
		for (PlantNotificationsEntity savedEntity : savedEntites) {
			sendPlantNotifyMail(savedEntity);
		}
	}

	private void sendPlantNotifyMail(PlantNotificationsEntity plantNotificationsEntity) {
		String epsName = "";
		String projName = "";
		String toepsName = "";
		String toprojName = "";
		String userName = "";
		String toEmail = "";
		String ccEmail = "";
		String code = "";
		String reqCode = "";

		if (null != plantNotificationsEntity) {
			
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
	        	if (CommonUtil.objectNotNull(plantNotificationsEntity.getProjMstrEntity())
	                    && plantNotificationsEntity.getProjMstrEntity().getProjectId().equals(entry.getKey())) {
	                epsName = entry.getValue().getCode();
	                projName = entry.getValue().getName();
	            }
	        	if (plantNotificationsEntity.getForProject().equals(entry.getKey())) {
	                toepsName = entry.getValue().getCode();
	                toprojName = entry.getValue().getName();
	            }

	        }
			if (null != plantNotificationsEntity.getReqUserId()) {
				userName = plantNotificationsEntity.getReqUserId().getDisplayName();
				ccEmail = plantNotificationsEntity.getReqUserId().getEmail();
			}
			if (null != plantNotificationsEntity.getApprUserId()) {
				toEmail = plantNotificationsEntity.getApprUserId().getEmail();
			}
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String apprDate = dateFormat.format(new Date());
            
			code = PlantNotificationsHandler.generateNotifyCode(plantNotificationsEntity);
			reqCode = PlantNotificationsHandler.generateNotifyReqCode(plantNotificationsEntity);
			String toSubject = "Request for additional time for approval of Plant transfer to Project - " + toprojName;
			String text = "<html><body>"
					+ "<p>" + userName + ",</p>"
					+ "<p>Reference Notification ID " + code + "</p>"
					+ "<p>I have submitted my request to grant additional time for approval on Plant transfer through " + pot + ", as per details mentioned here below.</p>"
					+ "<table border='1'>"
					+ "<tr><td>From EPS </td><td>" + epsName + "</td></tr>"
					+ "<tr><td>From Project </td><td>" + projName + "</td></tr>"
					+ "<tr><td>To EPS </td><td>" + toepsName + "</td></tr>"
					+ "<tr><td>To Project </td><td>" + toprojName + "</td></tr>"
					+ "<tr><td>Requisition ID</td><td>" + reqCode + "</td></tr>"
					+ "<tr><td>Notification ID</td><td>" + code + "</td></tr>"
					+ "</table>"
					+ "<p>This is for your information and further action please.</p>" 
					+ "<p>Regards,</p>" 
					+ "<p>"+ AppUserUtils.getName() + "<br/>" + AppUserUtils.getDisplayRole() + "<br/>" + apprDate + "</p>"
					+ "</body></html>";
			commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
		}
	}

	public MaterialNotificationResp getMaterialNotifications(NotificationsGetReq notificationsGetReq) {
		MaterialNotificationResp materialNotificationResp = new MaterialNotificationResp();
		Date fromDate = null;
		Date toDate = null;
		if (CommonUtil.isNotBlankStr(notificationsGetReq.getFromDate())
				&& CommonUtil.isNotBlankStr(notificationsGetReq.getToDate())) {
			fromDate = CommonUtil.convertStringToDate(notificationsGetReq.getFromDate());
			toDate = CommonUtil.convertStringToDate(notificationsGetReq.getToDate());
		} else {
			toDate = new Date();
			fromDate = CommonUtil.substarctInputMonths(toDate, -1);
		}
		toDate = CommonUtil.addNumberOfDaysInputDate(toDate, 1);
		
		String notificationPendingStatus = null;
		String notificationApprStatus = null;
		String notificationRejtStatus = null;
		List<MaterialNotificationsEntity> materialNotificationsEntities = null;
		
		if (notificationsGetReq.getNotifyStatus().equalsIgnoreCase("All")) {
			notificationPendingStatus = "Pending";
			notificationApprStatus = "APPROVED";
			notificationRejtStatus = "REJECTED";
			log.info("___________ AppUserUtils.getClientId() " + AppUserUtils.getClientId());
			log.info("___________ notificationsGetReq.getProjId() " + notificationsGetReq.getProjId());
			log.info("___________ notificationsGetReq.getCode() " + notificationsGetReq.getCode());
			log.info("___________ notificationsGetReq.getNotifyStatus() " + notificationsGetReq.getNotifyStatus());
			log.info("___________ notificationsGetReq.getStatus() " + notificationsGetReq.getStatus());
			log.info("___________ notificationsGetReq.getStatus() " + notificationsGetReq.getStatus());
			log.info("___________ fromDate " + fromDate);
			log.info("___________ toDate " + toDate);
			if (notificationsGetReq.getProjId() == null) {
				materialNotificationsEntities = materialNotificationsRepository.findMaterialNotificationsAll(
						notificationPendingStatus, notificationApprStatus, notificationRejtStatus, 
						notificationsGetReq.getStatus(), fromDate, toDate);
			}
		} else {
			if (notificationsGetReq.getProjId() == null) {
				
			} else {
				materialNotificationsEntities = materialNotificationsRepository.findMaterialNotifications(
						AppUserUtils.getClientId(), notificationsGetReq.getProjId(), notificationsGetReq.getCode(),
						notificationsGetReq.getNotifyStatus(), notificationsGetReq.getStatus(), fromDate, toDate);
			}
			
		}

		if (CommonUtil.isListHasData(materialNotificationsEntities)) {
			for (MaterialNotificationsEntity materialNotificationsEntity : materialNotificationsEntities) {
				materialNotificationResp.getMaterialNotificationsTOs()
						.add(MaterialNotificationsHandler.convertEntityToPOJO(materialNotificationsEntity));
			}
		}
		return materialNotificationResp;

	}

	public MaterialNotificationResp getMaterialNotificationsByProjId(NotificationsGetReq notificationsGetReq) {
		MaterialNotificationResp materialNotificationResp = new MaterialNotificationResp();
		List<MaterialNotificationsEntity> materialNotificationsEntities = null;

		materialNotificationsEntities = materialNotificationsRepository.findMaterialNotificationsByProjId(
				notificationsGetReq.getProjId(), notificationsGetReq.getNotifyStatus(),
				notificationsGetReq.getStatus(), "Additional Time for Request");
		log.info("notificationsGetReq.getProjId() " + notificationsGetReq.getProjId());
		log.info("notificationsGetReq.getNotifyStatus() " + notificationsGetReq.getNotifyStatus());
		log.info("notificationsGetReq.getStatus() " + notificationsGetReq.getStatus());
		log.info("materialNotificationsEntities size is " + materialNotificationsEntities.size());
		
		if (CommonUtil.isListHasData(materialNotificationsEntities)) {
			for (MaterialNotificationsEntity materialNotificationsEntity : materialNotificationsEntities) {
				log.info("materialNotificationsEntity ID is  " + materialNotificationsEntity.getId());
				materialNotificationResp.getMaterialNotificationsTOs()
						.add(MaterialNotificationsHandler.convertEntityToPOJO(materialNotificationsEntity));
			}
		}
		return materialNotificationResp;

	}

	public void saveMaterialNotifications(MaterialNotificationsSaveReq materialNotificationsSaveReq) {

		List<MaterialNotificationsEntity> materialNotificationsEntities = new ArrayList<MaterialNotificationsEntity>();
		MaterialNotificationsEntity materialNotificationsEntity = null;
		for (MaterialNotificationsTO materialNotificationsTO : materialNotificationsSaveReq
				.getMaterialNotificationsTOs()) {
			
			MaterialNotificationsEntity matNotificationsEntity = materialNotificationsRepository
					.findOne(materialNotificationsTO.getNotifyId());
			
			if (matNotificationsEntity != null) {
				matNotificationsEntity.setNotificationMsg("Additional Time for Request");
				if (CommonUtil.isNonBlankLong(materialNotificationsTO.getApprUserId())) {
					UserMstrEntity userMstrEntity = loginRepository.findOne(materialNotificationsTO.getApprUserId());
					matNotificationsEntity.setApprUserId(userMstrEntity);
				}
				if (matNotificationsEntity.getFromProjId().equals(matNotificationsEntity.getToProjId())) {
					matNotificationsEntity.setType("Request for Additional time for approval of internal project material transfer");
				} else {
					matNotificationsEntity.setType("Request for Additional time for approval of external project material transfer");
				}
				sendMatNotifyMail(matNotificationsEntity);
			}
			
			/*
			materialNotificationsEntity = MaterialNotificationsHandler.convertPOJOToEntity(materialNotificationsTO, epsProjRepository, loginRepository, clientRegRepository);
			
			if (CommonUtil.isNotBlankStr(materialNotificationsTO.getCode())) {
				materialNotificationsEntity.setCode(materialNotificationsTO.getCode());
			} else {
				materialNotificationsEntity.setCode(potSequenceGeneratorProcRepository.generatePOTSeqCode(
						AppUserUtils.getClientId(), materialNotificationsTO.getProjId(),
						ModuleCodesPrefixes.MATERIAL_REG_PREFIX.getDesc()
								.concat("-" + ModuleCodesPrefixes.NOTIFICATION_PREFIX.getDesc()),
						ModuleCodes.MATERIAL_TRANS_NOTIFICATION.getDesc()));

			}
			if (CommonUtil.isNotBlankStr(materialNotificationsTO.getRequistionCode())) {
				materialNotificationsEntity.setCode(materialNotificationsTO.getCode());
			} else {
				materialNotificationsEntity.setRequistionCode(potSequenceGeneratorProcRepository.generatePOTSeqCode(
						AppUserUtils.getClientId(), materialNotificationsTO.getProjId(),
						ModuleCodesPrefixes.MATERIAL_REG_PREFIX.getDesc()
								.concat("-" + ModuleCodesPrefixes.REQUEST_PREFIX.getDesc()),
						ModuleCodes.MATERIAL_REQ_FOR_TRANS.getDesc()));
			}
			*/
			/*
			// addtional time request material transfer email notification
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
			Map<Long, LabelKeyTO> userProjMap = new HashMap<Long, LabelKeyTO>();
			LabelKeyTO userProjLabelKeyTO = null;
			for (UserProjDetailsTO userProjDetailsTO : userProjResp.getUserProjDetailsTOs()) {
				userProjLabelKeyTO = new LabelKeyTO();
				userProjLabelKeyTO.setId(userProjDetailsTO.getProjId());
				userProjLabelKeyTO.setCode(userProjDetailsTO.getParentName());
				userProjLabelKeyTO.setName(userProjDetailsTO.getProjName());
				userProjMap.put(userProjDetailsTO.getProjId(), userProjLabelKeyTO);
			}
			for (Map.Entry<Long, LabelKeyTO> entry : userProjMap.entrySet()) {
				if (materialNotificationsEntity.getFromProjId().getProjectId().equals(entry.getKey())) {
					epsName = entry.getValue().getCode();
					projName = entry.getValue().getName();
				}
				if (materialNotificationsEntity.getProjId().getProjectId().equals(entry.getKey())) {
					toepsName = entry.getValue().getCode();
					toprojName = entry.getValue().getName();
				}

			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String apprDate = dateFormat.format(new Date());
            
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yy");
        	reqDate = dateFormat1.format(materialNotificationsEntity.getDate());
            
            String requestCode = ModuleCodesPrefixes.MATERIAL_REG_PREFIX.getDesc() + "-"
                    + ModuleCodesPrefixes.APPROVE_PREFIX.getDesc() + "-" + ModuleCodes.MATERIAL_TRANSFER.getDesc() + "-"
                    + materialNotificationsEntity.getFromProjId().getCode() + "-" + materialNotificationsEntity.getId() + " dated " + reqDate;
            
            String notifyCode = ModuleCodesPrefixes.MATERIAL_REG_PREFIX.getDesc() + "-"
                    + ModuleCodesPrefixes.NOTIFICATION_PREFIX.getDesc() + "-"
                    + materialNotificationsEntity.getFromProjId().getCode() + "-" + materialNotificationsEntity.getId() + " dated " + reqDate;
            
			if (materialNotificationsEntity.getFromProjId().equals(materialNotificationsEntity.getProjId())) {
				UserEntity userMstr = userRepository.findOne(materialNotificationsEntity.getReqUserId().getUserId());
				UserEntity userMstr1 = userRepository.findOne(materialNotificationsEntity.getApprUserId().getUserId());
				toEmail = userMstr1.getEmail();
				ccEmail = userMstr.getEmail();
				
				toSubject = "Request for additional time for approval of \"Internal Project Material transfer\" in the  Project " + epsName;
				text = "<html><body><p>" + userMstr.getDisplayName() + ",</p>"
						+ "<p>Reference Notification ID - " + notifyCode + "</p>"
						+ "<p>I have submitted my request to grant additional time for approval on \"Internal Project Material Transfer\" through " + pot + ", as per details mentioned here below.</p>"
						+ "<table border='1'>"
						+ "<tr><td>From EPS </td><td>" + epsName + "</td></tr>"
						+ "<tr><td>From Project </td><td>" + projName + "</td></tr>"
						//+ "<tr><td>To EPS </td><td>" + toepsName + "</td></tr>"
						//+ "<tr><td>To Project </td><td>" + toprojName + "</td></tr>"
						+ "<tr><td>Requisition ID</td><td>" + requestCode + "</td></tr>"
						+ "<tr><td>Notification ID</td><td>" + notifyCode + "</td></tr>"
						+ "</table>"
						+ "<p>This is for your information and further action please.</p>" 
						+ "<p>Regards,</p>" 
						+ "<p>" + AppUserUtils.getName() + "<br/>" + AppUserUtils.getDisplayRole() + " <br/> " + apprDate + "</p></body></html>";

				commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
			} else {
				UserEntity userMstr = userRepository.findOne(materialNotificationsEntity.getReqUserId().getUserId());
				UserEntity userMstr1 = userRepository.findOne(materialNotificationsEntity.getApprUserId().getUserId());
				toEmail = userMstr1.getEmail();
				ccEmail = userMstr.getEmail();
				toSubject = "Request for Additional time for external project material transfer to the project - " + toprojName;
				text = "<html><body>"
						+ "<p>" + userMstr.getDisplayName() + ",</p>"
						+ "<p>Reference Notification ID - " + notifyCode + "</p>"
						+ "<p>I have submitted my request for granting of additional time for approval of \"External Project Material Transfer\" through " + pot + ", as per details mentioned here below.</p>"
						+ "<table border='1'>"
						+ "<tr><td>From EPS </td><td>" + epsName + "</td></tr>"
						+ "<tr><td>From Project </td><td>" + projName + "</td></tr>"
						+ "<tr><td>To EPS </td><td>" + toepsName + "</td></tr>"
						+ "<tr><td>To Project </td><td>" + toprojName + "</td></tr>"
						+ "<tr><td>Requisition ID</td><td>" + requestCode + "</td></tr>"
						+ "<tr><td>Notification ID</td><td>" + notifyCode + "</td></tr>"
						+ "</table>"
						+ "<p>This is for your information and further action please..</p>" 
						+ "<p>Regards,</p>" 
						+ "<p>" + AppUserUtils.getName() + "<br/>" + AppUserUtils.getDisplayRole() + " <br/> " + apprDate + "</p></body></html>";

				commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
				
			}
			 
			if (materialNotificationsTO.getProjId().equals(materialNotificationsTO.getForProjId())) {
				materialNotificationsEntity
						.setType("Additional time granted for for internal project material transfer");

			} else {
				materialNotificationsEntity
						.setType("Additional time granted for for external  project material transfer");
			}

			materialNotificationsEntities.add(materialNotificationsEntity);
			*/
		}
		//List<MaterialNotificationsEntity> savedEnities = materialNotificationsRepository.save(materialNotificationsEntities);
		//generateMatNotifyCodeSendMail(savedEnities);
	}
	
	private void generateMatNotifyCodeSendMail(List<MaterialNotificationsEntity> savedEnities) {
		for (MaterialNotificationsEntity entity : savedEnities) {
			sendMatNotifyMail(entity);
		}
	}
	
	private void sendMatNotifyMail(MaterialNotificationsEntity materialNotificationsEntity) {
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
		Map<Long, LabelKeyTO> userProjMap = new HashMap<Long, LabelKeyTO>();
		LabelKeyTO userProjLabelKeyTO = null;
		for (UserProjDetailsTO userProjDetailsTO : userProjResp.getUserProjDetailsTOs()) {
			userProjLabelKeyTO = new LabelKeyTO();
			userProjLabelKeyTO.setId(userProjDetailsTO.getProjId());
			userProjLabelKeyTO.setCode(userProjDetailsTO.getParentName());
			userProjLabelKeyTO.setName(userProjDetailsTO.getProjName());
			userProjMap.put(userProjDetailsTO.getProjId(), userProjLabelKeyTO);
		}
		for (Map.Entry<Long, LabelKeyTO> entry : userProjMap.entrySet()) {
			if (materialNotificationsEntity.getFromProjId().getProjectId().equals(entry.getKey())) {
				epsName = entry.getValue().getCode();
				projName = entry.getValue().getName();
			}
			if (materialNotificationsEntity.getProjId().getProjectId().equals(entry.getKey())) {
				toepsName = entry.getValue().getCode();
				toprojName = entry.getValue().getName();
			}

		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String apprDate = dateFormat.format(new Date());
        
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yy");
    	reqDate = dateFormat1.format(materialNotificationsEntity.getDate());
        
        String requestCode = ModuleCodesPrefixes.MATERIAL_REG_PREFIX.getDesc() + "-"
                + ModuleCodesPrefixes.APPROVE_PREFIX.getDesc() + "-" + ModuleCodes.MATERIAL_TRANSFER.getDesc() + "-"
                + materialNotificationsEntity.getFromProjId().getCode() + "-" + materialNotificationsEntity.getId() + " dated " + reqDate;
        
        String notifyCode = ModuleCodesPrefixes.MATERIAL_REG_PREFIX.getDesc() + "-"
                + ModuleCodesPrefixes.NOTIFICATION_PREFIX.getDesc() + "-"
                + materialNotificationsEntity.getFromProjId().getCode() + "-" + materialNotificationsEntity.getId() + " dated " + reqDate;
        
		if (materialNotificationsEntity.getFromProjId().equals(materialNotificationsEntity.getProjId())) {
			UserEntity userMstr = userRepository.findOne(materialNotificationsEntity.getReqUserId().getUserId());
			UserEntity userMstr1 = userRepository.findOne(materialNotificationsEntity.getApprUserId().getUserId());
			toEmail = userMstr1.getEmail();
			ccEmail = userMstr.getEmail();
			
			toSubject = "Request for additional time for approval of \"Internal Project Material transfer\" in the  Project " + epsName;
			text = "<html><body><p>" + userMstr.getDisplayName() + ",</p>"
					+ "<p>Reference Notification ID - " + notifyCode + "</p>"
					+ "<p>I have submitted my request to grant additional time for approval on \"Internal Project Material Transfer\" through " + pot + ", as per details mentioned here below.</p>"
					+ "<table border='1'>"
					+ "<tr><td>From EPS </td><td>" + epsName + "</td></tr>"
					+ "<tr><td>From Project </td><td>" + projName + "</td></tr>"
					//+ "<tr><td>To EPS </td><td>" + toepsName + "</td></tr>"
					//+ "<tr><td>To Project </td><td>" + toprojName + "</td></tr>"
					+ "<tr><td>Requisition ID</td><td>" + requestCode + "</td></tr>"
					+ "<tr><td>Notification ID</td><td>" + notifyCode + "</td></tr>"
					+ "</table>"
					+ "<p>This is for your information and further action please.</p>" 
					+ "<p>Regards,</p>" 
					+ "<p>" + AppUserUtils.getName() + "<br/>" + AppUserUtils.getDisplayRole() + " <br/> " + apprDate + "</p></body></html>";

			commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
		} else {
			UserEntity userMstr = userRepository.findOne(materialNotificationsEntity.getReqUserId().getUserId());
			UserEntity userMstr1 = userRepository.findOne(materialNotificationsEntity.getApprUserId().getUserId());
			toEmail = userMstr1.getEmail();
			ccEmail = userMstr.getEmail();
			toSubject = "Request for Additional time for external project material transfer to the project - " + toprojName;
			text = "<html><body>"
					+ "<p>" + userMstr.getDisplayName() + ",</p>"
					+ "<p>Reference Notification ID - " + notifyCode + "</p>"
					+ "<p>I have submitted my request for granting of additional time for approval of \"External Project Material Transfer\" through " + pot + ", as per details mentioned here below.</p>"
					+ "<table border='1'>"
					+ "<tr><td>From EPS </td><td>" + epsName + "</td></tr>"
					+ "<tr><td>From Project </td><td>" + projName + "</td></tr>"
					+ "<tr><td>To EPS </td><td>" + toepsName + "</td></tr>"
					+ "<tr><td>To Project </td><td>" + toprojName + "</td></tr>"
					+ "<tr><td>Requisition ID</td><td>" + requestCode + "</td></tr>"
					+ "<tr><td>Notification ID</td><td>" + notifyCode + "</td></tr>"
					+ "</table>"
					+ "<p>This is for your information and further action please..</p>" 
					+ "<p>Regards,</p>" 
					+ "<p>" + AppUserUtils.getName() + "<br/>" + AppUserUtils.getDisplayRole() + " <br/> " + apprDate + "</p></body></html>";

			commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
			
		}
	}

	public void updateMaterialNotificationStatus(NotificationsSaveReq notificationsSaveReq) {
		materialNotificationsRepository.updateNotificationStatus(notificationsSaveReq.getId(),
				notificationsSaveReq.getNotifyStatus());

	}

	public void updatePlantNotificationStatus(NotificationsSaveReq notificationsSaveReq) {
		plantNotificationsRepository.updateNotificationStatus(notificationsSaveReq.getId(),
				notificationsSaveReq.getNotifyStatus());

	}

	public void updateEmpNotificationStatus(NotificationsSaveReq notificationsSaveReq) {
		String empStatus = "TRANSFER";
		employeeNotificationsRepository.updateNotificationStatus(notificationsSaveReq.getId(),
				notificationsSaveReq.getNotifyStatus(), empStatus);

	}

	public EmployeeLeaveNotificationsResp getEmployeeLeaveNotifications(NotificationsGetReq notificationsGetReq) {

		EmployeeLeaveNotificationsResp employeeLeaveNotificationsResp = new EmployeeLeaveNotificationsResp();
		Date fromDate = null;
		Date toDate = null;
		if (CommonUtil.isNotBlankStr(notificationsGetReq.getFromDate())
				&& CommonUtil.isNotBlankStr(notificationsGetReq.getToDate())) {
			fromDate = CommonUtil.convertStringToDate(notificationsGetReq.getFromDate());
			toDate = CommonUtil.convertStringToDate(notificationsGetReq.getToDate());
		} else {
			toDate = new Date();
			fromDate = CommonUtil.substarctInputMonths(toDate, -1);
		}
		toDate = CommonUtil.addNumberOfDaysInputDate(toDate, 1);
		List<EmployeeNotificationsEntity> employeeNotificationsEntities = null;
		
		String notificationPendingStatus = null;
		String notificationApprStatus = null;
		String notificationRejtStatus = null;
		String empStatus = null;
		
		if (notificationsGetReq.getNotifyStatus().equalsIgnoreCase("All")) {
			notificationPendingStatus = "Pending";
			notificationApprStatus = "APPROVED";
			notificationRejtStatus = "REJECTED";
			empStatus = "LEAVE";
			
			if (notificationsGetReq.getProjId() == null) {
			employeeNotificationsEntities = employeeLeaveNotificationsRepository.findEmployeeLeaveNotificationsAll(
					notificationPendingStatus, notificationApprStatus, notificationRejtStatus , empStatus,
					notificationsGetReq.getStatus(), fromDate, toDate);
			} else {
				employeeNotificationsEntities = employeeLeaveNotificationsRepository.findEmployeeLeaveNotificationsAllByProjId(
						notificationsGetReq.getProjId(), notificationsGetReq.getNotifyStatus(), empStatus,
						notificationsGetReq.getStatus(), fromDate, toDate);
			}
		} else {
			employeeNotificationsEntities = employeeLeaveNotificationsRepository.findEmployeeLeaveNotifications(
					notificationsGetReq.getProjId(), notificationsGetReq.getNotifyStatus(), "LEAVE",
					notificationsGetReq.getStatus(), fromDate, toDate);
		}
		

		if (CommonUtil.isListHasData(employeeNotificationsEntities)) {
			for (EmployeeNotificationsEntity employeeNotificationsEntity : employeeNotificationsEntities) {
				employeeLeaveNotificationsResp.getEmployeeLeaveNotificationsTOs()
						.add(EmployeeLeaveNotificationsHandler.convertEntityToPOJO(employeeNotificationsEntity));
			}
		}
		return employeeLeaveNotificationsResp;

	}

	public EmployeeLeaveNotificationsResp getEmployeeLeaveNotificationsByProjId(
			NotificationsGetReq notificationsGetReq) {

		EmployeeLeaveNotificationsResp employeeLeaveNotificationsResp = new EmployeeLeaveNotificationsResp();
		List<EmployeeNotificationsEntity> employeeNotificationsEntities = null;
		String empStatus = "LEAVE";
		employeeNotificationsEntities = employeeLeaveNotificationsRepository.findEmployeeLeaveNotificationsByProjId(
				notificationsGetReq.getProjId(), notificationsGetReq.getNotifyStatus(), empStatus,
				notificationsGetReq.getType(), notificationsGetReq.getStatus());

		if (CommonUtil.isListHasData(employeeNotificationsEntities)) {
			for (EmployeeNotificationsEntity employeeNotificationsEntity : employeeNotificationsEntities) {
				employeeLeaveNotificationsResp.getEmployeeLeaveNotificationsTOs()
						.add(EmployeeLeaveNotificationsHandler.convertEntityToPOJO(employeeNotificationsEntity));
			}
		}
		return employeeLeaveNotificationsResp;

	}

	public void saveEmployeeLeaveNotifications(EmployeeNotificationsSaveReq employeeNotificationsSaveReq) {

		List<EmployeeNotificationsEntity> employeeNotificationsEntities = new ArrayList<>();
		EmployeeNotificationsEntity employeeNotificationsEntity = null;
		for (EmployeeNotificationsTO employeeNotificationsTO : employeeNotificationsSaveReq
				.getEmployeeNotificationsTOs()) {
			
			employeeNotificationsEntity = employeeNotificationsRepository.findOne(employeeNotificationsTO.getNotifyId());
			
			String AddlTimeLeaveType = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			String apprDate = null;
			EmpLeaveReqApprEntity leaveReqApprEntity = empLeaveReqApprRepository.findEmpLeaveReqApprRecord(employeeNotificationsTO.getNotifyId());
			if (leaveReqApprEntity != null) {
				if (leaveReqApprEntity.getStartDate() != null) {
					
			        apprDate = dateFormat.format(leaveReqApprEntity.getStartDate());
				}
				AddlTimeLeaveType = "Request for additional time for approval of Leave from " + apprDate + " for "  + leaveReqApprEntity.getTotalDays()+ " days";
			}
			if(employeeNotificationsEntity != null) {
				employeeNotificationsEntity.setNotificationMsg("Additional Time for Request");
				
				if (CommonUtil.isNonBlankLong(employeeNotificationsTO.getApprUserId())) {
		            UserMstrEntity userMstrEntity = loginRepository.findOne(employeeNotificationsTO.getApprUserId());
		            employeeNotificationsEntity.setApprUserId(userMstrEntity);
		        }
				
				employeeNotificationsEntity.setType(AddlTimeLeaveType);
				
				sendEmpLeaveNotifyMail(employeeNotificationsEntity);
			}
		}
	}

	public void updateEmployeeLeaveNotificationStatus(NotificationsSaveReq notificationsSaveReq) {
		String empStatus = "LEAVE";
		employeeLeaveNotificationsRepository.updateNotificationStatus(notificationsSaveReq.getId(),
				notificationsSaveReq.getNotifyStatus(), empStatus);

	}
	
	private void sendEmpLeaveNotifyMail(EmployeeNotificationsEntity employeeNotificationsEntity) {
		String epsName = null;
		String projName = null;
		String ccEmail;
		String toEmail;
		String toSubject;
		String text;
		String toepsName = null;
		String toprojName = null;
		String leaveStartDate = null;
		String noOfDays = null;
		String reqDate = null;
		
		EmpLeaveReqApprEntity leaveReqApprEntity = empLeaveReqApprRepository.findEmpLeaveReqApprRecord(employeeNotificationsEntity.getId());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat dateFormatStartDate = new SimpleDateFormat("dd-MMM-yyyy");
		if(leaveReqApprEntity.getStartDate() != null) {
			leaveStartDate = dateFormatStartDate.format(leaveReqApprEntity.getStartDate());
		}
		if(leaveReqApprEntity.getTotalDays() != null) {
			noOfDays = leaveReqApprEntity.getTotalDays().toString();
		}
		
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yy");
    	reqDate = dateFormat1.format(employeeNotificationsEntity.getDate());
        
        String reqCode = ModuleCodesPrefixes.EMP_REG_PREFIX.getDesc().concat("-" + ModuleCodesPrefixes.REQUEST_PREFIX.getDesc())
                .concat("-" + leaveReqApprEntity.getProjMstrEntity().getCode()).concat("-" + leaveReqApprEntity.getId()) + " dated " + reqDate;
        
        String notifyCode = ModuleCodesPrefixes.EMP_REG_PREFIX.getDesc()
                .concat("-" + ModuleCodesPrefixes.NOTIFICATION_PREFIX.getDesc())
                .concat("-" + leaveReqApprEntity.getProjMstrEntity().getCode()).concat("-" + leaveReqApprEntity.getId()) + " dated " + reqDate;
        String apprName = leaveReqApprEntity.getEmpRegisterDtlEntity().getFirstName();
        System.out.println(apprName+"apprnameeeeeeeeeeeeeeee");
		System.out.println(leaveReqApprEntity.getEmpRegisterDtlEntity().getCompanyMstrEntity().getName()+" company");
		log.info("Employee Notification Id is " + employeeNotificationsEntity.getId());		
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
        	if (CommonUtil.objectNotNull(employeeNotificationsEntity.getProjMstrEntity())
                    && employeeNotificationsEntity.getProjMstrEntity().getProjectId().equals(entry.getKey())) {
                epsName = entry.getValue().getCode();
                projName = entry.getValue().getName();
            }


        }

		UserMstrEntity userMstr = employeeNotificationsEntity.getReqUserId();
		UserMstrEntity userMstr1 = employeeNotificationsEntity.getApprUserId();
		toEmail = userMstr1.getEmail();
		ccEmail = userMstr.getEmail();
		
		
        String apprDate = dateFormat.format(employeeNotificationsEntity.getDate());
        
        System.out.println(leaveReqApprEntity.getEmpRegisterDtlEntity()+" detailssssss");
		toSubject = "Request for additional time for approval of employee Leave";
		text = "<html><body><p>" + userMstr.getDisplayName() + ",</p>"
                + "<p>I have submitted my request to grant additional time for Leave approval through " + pot + ", as per details mentioned here below.</p>"
                + "<table border='1'>"
                + "<tr><td>Company </td><td>" + leaveReqApprEntity.getEmpRegisterDtlEntity().getCompanyMstrEntity().getName() + "</td></tr>"
                + "<tr><td>Project </td><td>"+ projName + "</td></tr>"
                + "<tr><td>Employee ID </td><td>" + leaveReqApprEntity.getEmpRegisterDtlEntity().getCode() + "</td></tr>"
                + "<tr><td>Employee Name</td><td>" + leaveReqApprEntity.getEmpRegisterDtlEntity().getFirstName() +" "+leaveReqApprEntity.getEmpRegisterDtlEntity().getLastName()+ "</td></tr>"
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
	}

	public NotificationCountResp getCountNotification(NotificationCountGetReq notificationCountGetReq) {

		NotificationCountResp notificationCountResp = new NotificationCountResp();
		Date fromDate = null;
		Date toDate = null;
		Integer timeSheetCount, count, countWorkDairy, countAttendance, countProcurment, countEmployeeTransfer, countSoe, countEmployeeLeave;
		if (CommonUtil.isNotBlankStr(notificationCountGetReq.getFromDate())
				&& CommonUtil.isNotBlankStr(notificationCountGetReq.getToDate())) {
			fromDate = CommonUtil.convertStringToDate(notificationCountGetReq.getFromDate());
			toDate = CommonUtil.convertStringToDate(notificationCountGetReq.getToDate());
		} else {
			toDate = new Date();
			fromDate = CommonUtil.substarctInputMonths(toDate, -1);
		}
		toDate = CommonUtil.addNumberOfDaysInputDate(toDate, 1);
		timeSheetCount = timeSheetNotificationsRepository.countTimeSheet(AppUserUtils.getClientId(), fromDate, toDate);
		countWorkDairy = workDairyNotificationRepository.countWorkDairy(AppUserUtils.getClientId(), fromDate, toDate);
		countAttendance = attendenceNotificationsRepository.countAttendance(AppUserUtils.getUserId(), fromDate, toDate);
		countProcurment = procurementNotificationsRepository.countProcurment(AppUserUtils.getClientId(), fromDate,
				toDate);
		countEmployeeTransfer = employeeNotificationsRepository.countEmployeeTransfer(AppUserUtils.getClientId(),
				AppUserUtils.getUserId(), fromDate, toDate);
        countSoe =  soeNotificationRepository.countSoe(AppUserUtils.getClientId(),
				AppUserUtils.getUserId(), fromDate, toDate);
		count = timeSheetCount + countWorkDairy + countAttendance + countProcurment + countEmployeeTransfer + countSoe;
		notificationCountResp.setCount(count);

		return notificationCountResp;
	}
	
	public static String generateWorkDairyCode( WorkDairyEntity workDairyEntity ) {
        return  "WD-" + workDairyEntity.getProjId().getCode() + "-" + workDairyEntity.getCrewId().getCode() + "-"
                + workDairyEntity.getId();
    }

	public SoeNotificationsResp getSoeNotifications(NotificationsSoeGetReq notificationsSoeGetReq) {
		String code=null;
		SoeNotificationsResp soeNotificationsResp = new SoeNotificationsResp();
		Date fromDate = null;
		Date toDate = null;
		if (CommonUtil.isNotBlankStr(notificationsSoeGetReq.getFromDate())
				&& CommonUtil.isNotBlankStr(notificationsSoeGetReq.getToDate())) {
			fromDate = CommonUtil.convertStringToDate(notificationsSoeGetReq.getFromDate());
			toDate = CommonUtil.convertStringToDate(notificationsSoeGetReq.getToDate());
		} else {
			toDate = new Date();
			fromDate = CommonUtil.substarctInputMonths(toDate, -1);
		}
		toDate = CommonUtil.addNumberOfDaysInputDate(toDate, 1);
		List<SoeNotificationsEntity> soeNotificationsEntites = new ArrayList<>();
		soeNotificationsEntites = soeNotificationRepository.findSoeNotificationsAll(
					fromDate, toDate);
	
		if(notificationsSoeGetReq.getProjIds().size() > 0) {
		soeNotificationsEntites = soeNotificationRepository.findSoeNotificationsByProjId(notificationsSoeGetReq.getProjIds(),
				fromDate, toDate);
		}
		
        if(notificationsSoeGetReq.getNotifyStatus().equals("Additional Time for Request")) {
        	soeNotificationsEntites = soeNotificationRepository.findSoeNotificationsByNotifyStatus(notificationsSoeGetReq.getProjId(),
        			notificationsSoeGetReq.getNotifyStatus());
		}
     //   System.out.println("notificationsGetReqqqqq"+notificationsSoeGetReq.getProjIds());
		if (CommonUtil.isListHasData(soeNotificationsEntites)) {
			for (SoeNotificationsEntity soeNotificationsEntity : soeNotificationsEntites) {
				soeNotificationsResp.getSoeNotificationsTOs()
						.add(SoeNotificationsHandler.convertEntityToPOJO(soeNotificationsEntity));
			}
		} 
         return soeNotificationsResp;
	}

	public void saveSoeNotifications(SoeNotificationsSaveReq soeNotificationsSaveReq) {	
		List <SoeNotificationsEntity> soeNotificationsEntites = new ArrayList<>();
		System.out.println("SoeNotificationsEntites"+soeNotificationsEntites);
		System.out.println("soeNotificationsSaveReq"+soeNotificationsSaveReq.getSoeNotificationsTOs());
		
		
		for (SoeNotificationsTO soeNotificationsTO : soeNotificationsSaveReq.getSoeNotificationsTOs()) {
	
			System.out.println("soeNotificationsTO.getNotificationStatus()"+soeNotificationsTO.getNotificationStatus());
			System.out.println("soeNotificationsTO.getNotificationStatus()"+soeNotificationsTO.getNotificationStatus());
			System.out.println("soeNotificationsTO.getNotifyRefId()"+soeNotificationsTO.getNotifyRefId());
			System.out.println("soeNotificationsTO.getToUserId()"+soeNotificationsTO.getToUserId());
			soeNotificationsEntites = soeNotificationRepository.findSoe(soeNotificationsTO.getNotifyRefId());
			
			System.out.println("SoeNotificationsEntites"+soeNotificationsEntites);
			for (SoeNotificationsEntity soeNotificationsEntity : soeNotificationsEntites) {
				if (soeNotificationsEntity != null) {
					soeNotificationsEntity.setNotificationMsg("Additional Time for Request");
				
				  if (CommonUtil.isNonBlankLong(soeNotificationsTO.getToUserId())) {
				  UserMstrEntity userMstrEntity =
				  loginRepository.findOne(soeNotificationsTO.getToUserId());
				  System.out.println("userMstrEntity"+userMstrEntity.getEmail());
				  }
		
						String toprojName = null;
						ProjMstrEntity forProjEntity = soeNotificationsEntity.getProjSOEItemEntity().getProjMstrEntity();
						if (CommonUtil.objectNotNull(forProjEntity)) {
							toprojName = forProjEntity.getProjName();
						}
						soeNotificationsEntity.setNotificationStatus("Request for additional time for approval " + toprojName);
						
					}
				SoeAddtionalTimeApprEntity soeAddlTimeApprEntity  = soeAddltionalTimeRepository
						.findProjId(soeNotificationsTO.getProjId());
				SoeAddtionalTimeApprEntity savedEntity = null;
				String type = null;
				if (soeNotificationsTO.getType() != null) {
					type = soeNotificationsTO.getType();
				} else {
					type = "Original";
				}
				if (soeAddlTimeApprEntity == null) {
					SoeAddtionalTimeApprEntity soeAddtionalTimeApprEntity = new SoeAddtionalTimeApprEntity();
					
					soeAddtionalTimeApprEntity.setRequisitionDate(new Date());
					soeAddtionalTimeApprEntity.setLatest(true);
					soeAddtionalTimeApprEntity.setNotificationMessage("Request for Additional Time");
					soeAddtionalTimeApprEntity.setNotificationStatus("Pending");
					soeAddtionalTimeApprEntity.setStatus(type);
					soeAddtionalTimeApprEntity.setApprUser(commonRepository.findOne(soeNotificationsTO.getToUserId()));
					soeAddtionalTimeApprEntity.setProjId(soeNotificationsEntity.getProjSOEItemEntity().getProjMstrEntity().getProjectId());
					soeAddtionalTimeApprEntity.setSoeNotificationsEntity(soeNotificationRepository.findOne(soeNotificationsEntity.getId()));
				//	soeAddtionalTimeApprEntity.setSchofEstimateNormalTimeEntity(soeNotificationRepository.findSoe(soeNotificationsTO.getNotifyRefId()));
					savedEntity = soeAddltionalTimeRepository.save(soeAddtionalTimeApprEntity);
					sendSoeNotifyMail(soeNotificationsEntity,soeNotificationsTO);
				} else {
					soeAddlTimeApprEntity.setNotificationStatus("Pending");
					soeAddlTimeApprEntity.setStatus(type);
					savedEntity = soeAddltionalTimeRepository.save(soeAddlTimeApprEntity);
					sendSoeNotifyMail(soeNotificationsEntity,soeNotificationsTO);
				}
				
				}
			
			
			
			}
				
	}
			
	public SoeNotificationsResp getSoeNotificationsByProjId(NotificationsSoeGetReq notificationsSoeGetReq) {
		SoeNotificationsResp soeNotificationsResp = new SoeNotificationsResp();
		List<SoeAddtionalTimeApprEntity> soeAddtionalTimeApprEntities = soeAddltionalTimeRepository
				.findSoeAddlTimeDetails(notificationsSoeGetReq.getProjId(), "Pending");

		if (CommonUtil.isListHasData(soeAddtionalTimeApprEntities)) {
			for (SoeAddtionalTimeApprEntity soeAddtionalTimeApprEntity : soeAddtionalTimeApprEntities) {
				SoeNotificationsTO soeNotificationsTO = new SoeNotificationsTO();
				soeNotificationsTO.setType(soeAddtionalTimeApprEntity.getStatus());
				soeNotificationsTO.setId(soeAddtionalTimeApprEntity.getId());
				soeNotificationsTO.setProjName(soeAddtionalTimeApprEntity.getSoeNotificationsEntity().getProjSOEItemEntity().getProjMstrEntity().getParentProjectMstrEntity().getProjName());
				soeNotificationsTO.setFromUserName(soeAddtionalTimeApprEntity.getApprUser().getDisplayName());
		//		soeNotificationsTO.setFromUserName(soeAddtionalTimeApprEntity.getApprUser().getUserName());
				soeNotificationsTO.setSoeNumber(ModuleCodesPrefixes.SOE_PREFIX.getDesc().concat("-")
  					  .concat(soeAddtionalTimeApprEntity.getSoeNotificationsEntity().getProjSOEItemEntity().getProjMstrEntity().getCode()).concat("-")
  					  .concat(String.valueOf(soeAddtionalTimeApprEntity.getId())));
				if (null != soeAddtionalTimeApprEntity.getSoeNotificationsEntity().getProjSOEItemEntity().getId()) {
					soeNotificationsTO.setSoeId(soeAddtionalTimeApprEntity.getSoeNotificationsEntity().getProjSOEItemEntity().getId());
				}
				soeNotificationsResp.getSoeNotificationsTOs().add(soeNotificationsTO);
			}
		}
		return soeNotificationsResp;
	}
	
	
	
	private void sendSoeNotifyMail(SoeNotificationsEntity soeNotificationsEntity,SoeNotificationsTO soeNotificationsTO) {
		String epsName = null;
		String projName = null;
		String ccEmail ="";
		String toEmail;
		String toSubject;
		String text;
		String toepsName = null;
		String toprojName = null;
		String leaveStartDate = null;
		String noOfDays = null;
		String reqDate = null;
		
		UserProjGetReq userProjGetReq = new UserProjGetReq();
		UserProjResp userProjResp = epsProjService.getAllUserProjects(userProjGetReq);
		Map<Long, LabelKeyTO> userProjMap = new HashMap<Long, LabelKeyTO>();
		LabelKeyTO userProjLabelKeyTO = null;
		for (UserProjDetailsTO userProjDetailsTO : userProjResp.getUserProjDetailsTOs()) {
			userProjLabelKeyTO = new LabelKeyTO();
			userProjLabelKeyTO.setId(userProjDetailsTO.getProjId());
			userProjLabelKeyTO.setCode(userProjDetailsTO.getParentName());
			userProjLabelKeyTO.setName(userProjDetailsTO.getProjName());
			userProjMap.put(userProjDetailsTO.getProjId(), userProjLabelKeyTO);
		}
		for (Map.Entry<Long, LabelKeyTO> entry : userProjMap.entrySet()) {
			if (soeNotificationsEntity.getProjSOEItemEntity().getProjMstrEntity().getProjectId().equals(entry.getKey())) {
				epsName = entry.getValue().getCode();
				projName = entry.getValue().getName();
			}
			if (soeNotificationsEntity.getProjSOEItemEntity().getProjMstrEntity().getProjectId().equals(entry.getKey())) {
				toepsName = entry.getValue().getCode();
				toprojName = entry.getValue().getName();
			}

		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String apprDate = dateFormat.format(new Date());
        
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yy");
    	reqDate = dateFormat1.format(soeNotificationsEntity.getDate());
    	UserEntity userMstr = userRepository.findOne(soeNotificationsTO.getToUserId());
    	System.out.println("userMstr.getEmail()"+userMstr.getEmail());
	
    	toEmail = userMstr.getEmail();
        toSubject = "Request for additional time for approval of soe";
		text = "<html><body><p>" + userMstr.getDisplayName() + ",</p>"
                + "<p>I have submitted my request to grant additional time for soe through " + pot + ", as per details mentioned here below.</p>"
                + "<table border='1'>"
                +"<tr><td>EPS </td><td>" + epsName + "</td></tr>"
                + "<tr><td>Project</td><td>"+ projName + "</td></tr>"
                + "<tr><td>Notification ID</td><td>"+"SOE-"+ epsName +"-"+soeNotificationsEntity.getProjSOEItemEntity().getId()+"</td></tr>"
                + "<tr><td>Date</td><td>"+ new Date() + "</td></tr>"
                + "</table>"
                + "<p>This is for your approval please.</p>" 
                + "<p>Regards,</p>" 
                + "<p>" + AppUserUtils.getName() + "<br/>" + AppUserUtils.getDisplayRole() + "<br/>" + apprDate + "</p>"
                + "</body></html>";
		commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);		
        		
		
	}

}
