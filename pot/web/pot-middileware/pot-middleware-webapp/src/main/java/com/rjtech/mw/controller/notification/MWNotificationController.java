package com.rjtech.mw.controller.notification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.req.NotificationStatusReq;
import com.rjtech.common.resp.AppResp;
import com.rjtech.common.resp.NotificationStatusOnLoadResp;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.common.utils.StatusType;
import com.rjtech.mw.service.notification.MWNotificationService;
import com.rjtech.mw.service.projlib.MWProjLibService;
import com.rjtech.mw.service.register.MWEmpRegisterService;
import com.rjtech.mw.service.user.MWUserService;
import com.rjtech.notification.constans.NotificationURLConstants;
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
import com.rjtech.register.emp.dto.EmpRegisterDtlTO;
import com.rjtech.register.emp.req.EmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpRegResp;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.user.dto.UserProjDetailsTO;
import com.rjtech.user.dto.UserTO;
import com.rjtech.user.req.ClientReq;
import com.rjtech.user.req.UserProjGetReq;
import com.rjtech.user.resp.UserProjResp;
import com.rjtech.user.resp.UserResp;
import com.rjtech.notification.resp.SoeNotificationsResp;
import com.rjtech.notification.req.NotificationsSoeGetReq;
import com.rjtech.notification.req.SoeNotificationsSaveReq;
@RestController
@RequestMapping(NotificationURLConstants.NOTIFICATION_PARH_URL)
public class MWNotificationController {

	private static final Logger log = LoggerFactory.getLogger(MWNotificationController.class);
	
    @Autowired
    private MWNotificationService mwNotificationService;

    @Autowired
    private MWProjLibService mwProjLibService;

    @Autowired
    private MWUserService mwUserService;

    @Autowired
    private MWEmpRegisterService mwRegisterService;

    @RequestMapping(value = NotificationURLConstants.GET_TIME_SHEET_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetNotificationsResp> getTimeSheetNotifications(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        TimeSheetNotificationsResp timeSheetNotificationsResp = mwNotificationService
                .getTimeSheetNotifications(notificationsGetReq);
        //timeSheetNotificationsResp.setUserProjMap(getUserProjMap());
        //timeSheetNotificationsResp.setUsersMap(getUsersMap());
        return new ResponseEntity<TimeSheetNotificationsResp>(timeSheetNotificationsResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_TIME_SHEET_NOTIFICATIONS_BY_PROJID, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetNotificationsResp> getTimeSheetNotificationsByProjId(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        TimeSheetNotificationsResp timeSheetNotificationsResp = mwNotificationService
                .getTimeSheetNotificationsByProjId(notificationsGetReq);
        timeSheetNotificationsResp.setUsersMap(getUsersMap());
        return new ResponseEntity<TimeSheetNotificationsResp>(timeSheetNotificationsResp, HttpStatus.OK);
    }
    
    @RequestMapping(value = NotificationURLConstants.GET_ADDL_TIME_ATTENDANCE_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<AttendenceNotificationsResp> getAttendenceAddlTimeNotifications(
            @RequestBody NotificationsGetReq notificationsGetReq) {
        return new ResponseEntity<AttendenceNotificationsResp>(
        		mwNotificationService.getAttendenceAddlTimeNotifications(notificationsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_ATTENDANCE_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<AttendenceNotificationsResp> getAttendanceNotification(
            @RequestBody NotificationsGetReq notificationsGetReq) {
        AttendenceNotificationsResp attendenceNotificationsResp = mwNotificationService
                .getAttendanceNotification(notificationsGetReq);
        //attendenceNotificationsResp.setUserProjMap(getUserProjMap());
        //attendenceNotificationsResp.setUsersMap(getUsersMap());
        return new ResponseEntity<AttendenceNotificationsResp>(attendenceNotificationsResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.SAVE_TIMESHEET_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveTimeSheetNotifications(
            @RequestBody TimeSheetNotificationsSaveReq timeSheetNotificationsSaveReq) {

        return new ResponseEntity<AppResp>(
                mwNotificationService.saveTimeSheetNotifications(timeSheetNotificationsSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.SAVE_ATTENDANCE_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveAttendenceNotifications(
            @RequestBody AttendenceNotificationsSaveReq attendenceNotificationsSaveReq) {

        return new ResponseEntity<AppResp>(
                mwNotificationService.saveAttendenceNotifications(attendenceNotificationsSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_WORK_DIARY_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyNotificationResp> getWorkDiaryNotifications(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        WorkDairyNotificationResp workDairyNotificationResp = mwNotificationService
                .getWorkDiaryNotifications(notificationsGetReq);
        //	workDairyNotificationResp.setUserProjMap(getUserProjMap());
        //workDairyNotificationResp.setUsersMap(getUsersMap());
        return new ResponseEntity<WorkDairyNotificationResp>(workDairyNotificationResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_WORK_DIARY_NOTIFICATIONS_BY_PROJECT, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyNotificationResp> getWorkDiaryNotificationsByProjId(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        WorkDairyNotificationResp workDairyNotificationResp = mwNotificationService
                .getWorkDiaryNotificationsByProjId(notificationsGetReq);
        workDairyNotificationResp.setUsersMap(getUsersMap());
        return new ResponseEntity<WorkDairyNotificationResp>(workDairyNotificationResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.SAVE_WORK_DIARY_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveWorkDiaryNotifications(
            @RequestBody WorkDairyNotificationsSaveReq workDairyNotificationsSaveReq) {

        return new ResponseEntity<AppResp>(
                mwNotificationService.saveWorkDiaryNotifications(workDairyNotificationsSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_EMP_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<EmployeeNotificationsResp> getEmpNotifications(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        EmployeeNotificationsResp employeeNotificationsResp = mwNotificationService
                .getEmployeeNotifications(notificationsGetReq);
        //employeeNotificationsResp.setUserProjMap(getUserProjMap());
        //employeeNotificationsResp.setUsersMap(getUsersMap());
        return new ResponseEntity<EmployeeNotificationsResp>(employeeNotificationsResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_EMP_NOTIFICATIONS_BY_PROJECT, method = RequestMethod.POST)
    public ResponseEntity<EmployeeNotificationsResp> getEmployeeNotificationsByProjId(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        EmployeeNotificationsResp employeeNotificationsResp = mwNotificationService
                .getEmployeeNotificationsByProjId(notificationsGetReq);
        return new ResponseEntity<EmployeeNotificationsResp>(employeeNotificationsResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.SAVE_EMP_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveEmpNotifications(
            @RequestBody EmployeeNotificationsSaveReq employeeNotificationsSaveReq) {

        return new ResponseEntity<AppResp>(
                mwNotificationService.saveEmployeeNotifications(employeeNotificationsSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_PLANT_NOTIFICATION, method = RequestMethod.POST)
    public ResponseEntity<PlantNotificationResp> getPlantNotification(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        PlantNotificationResp plantNotificationResp = mwNotificationService.getPlantNotifications(notificationsGetReq);
        //	plantNotificationResp.setUserProjMap(getUserProjMap());
        //plantNotificationResp.setUsersMap(getUsersMap());
        return new ResponseEntity<PlantNotificationResp>(plantNotificationResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_PLANT_NOTIFICATION_BY_PROJECT, method = RequestMethod.POST)
    public ResponseEntity<PlantNotificationResp> getPlantNotificationsByProjId(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        PlantNotificationResp plantNotificationResp = mwNotificationService
                .getPlantNotificationsByProjId(notificationsGetReq);
        return new ResponseEntity<PlantNotificationResp>(plantNotificationResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.SAVE_PLANT_NOTIFICATION, method = RequestMethod.POST)
    public ResponseEntity<AppResp> savePlantNotification(
            @RequestBody PlantNotificationsSaveReq plantNotificationsSaveReq) {

        return new ResponseEntity<AppResp>(mwNotificationService.savePlantNotifications(plantNotificationsSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_MATERIAL_NOTIFICATION, method = RequestMethod.POST)
    public ResponseEntity<MaterialNotificationResp> getMaterialNotification(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        MaterialNotificationResp materialNotificationResp = mwNotificationService
                .getMaterialNotifications(notificationsGetReq);
        //materialNotificationResp.setUserProjMap(getUserProjMap());
        //materialNotificationResp.setUsersMap(getUsersMap());

        return new ResponseEntity<MaterialNotificationResp>(materialNotificationResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_MATERIAL_NOTIFICATION_BY_PROJECT, method = RequestMethod.POST)
    public ResponseEntity<MaterialNotificationResp> getMaterialNotificationsByProjId(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        MaterialNotificationResp materialNotificationResp = mwNotificationService
                .getMaterialNotificationsByProjId(notificationsGetReq);
        return new ResponseEntity<MaterialNotificationResp>(materialNotificationResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.SAVE_MATERIAL_NOTIFICATION, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveMaterialNotification(
            @RequestBody MaterialNotificationsSaveReq materialNotificationsSaveReq) {

        return new ResponseEntity<AppResp>(
                mwNotificationService.saveMaterialNotifications(materialNotificationsSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_PROCUREMENT_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<ProcurementNotificationResp> getProcurementNotifications(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        ProcurementNotificationResp procurementNotificationResp = mwNotificationService
                .getProcureNotifications(notificationsGetReq);
        //procurementNotificationResp.setUserProjMap(getUserProjMap());
        //procurementNotificationResp.setUsersMap(getUsersMap());

        return new ResponseEntity<ProcurementNotificationResp>(procurementNotificationResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_PROCUREMENT_NOTIFICATIONS_BY_PROJECT, method = RequestMethod.POST)
    public ResponseEntity<ProcurementNotificationResp> getProcurementNotificationsByProjId(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        ProcurementNotificationResp procurementNotificationResp = mwNotificationService
                .getProcurementNotificationsByProjId(notificationsGetReq);
        return new ResponseEntity<ProcurementNotificationResp>(procurementNotificationResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.SAVE_PROCUREMENT_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveProcurementNotifications(
            @RequestBody ProcureNotificationsSaveReq procureNotificationsSaveReq) {
    	
        return new ResponseEntity<AppResp>(mwNotificationService.saveProcurementNotifications(procureNotificationsSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.ONLOAD_DATA_FOR_NOTIFICATION_STATUS, method = RequestMethod.POST)
    public ResponseEntity<NotificationStatusOnLoadResp> notificationStatusOnLoad(
            @RequestBody NotificationStatusReq notificationStatusReq) {
        NotificationStatusOnLoadResp notificationStatusOnLoadResp = new NotificationStatusOnLoadResp();
        List<String> statuslist = new ArrayList<String>();
        for (StatusType statusType : StatusType.values()) {
            statuslist.add(statusType.getName());
        }
        notificationStatusOnLoadResp.setStatuslist(statuslist);
        notificationStatusOnLoadResp.setUserProjMap(getUserProjMap());
        notificationStatusOnLoadResp.setUsersMap(getUsersMap());
        return new ResponseEntity<NotificationStatusOnLoadResp>(notificationStatusOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.UPDATE_MATERIAL_NOTIFICATION_STATUS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> updateMaterialNotificationStatus(
            @RequestBody NotificationsSaveReq notificationsSaveReq) {
        return new ResponseEntity<AppResp>(mwNotificationService.updateMaterialNotificationStatus(notificationsSaveReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = NotificationURLConstants.UPDATE_PLANT_NOTIFICATION_STATUS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> updatePlantNotificationStatus(
            @RequestBody NotificationsSaveReq notificationsSaveReq) {
        return new ResponseEntity<AppResp>(mwNotificationService.updatePlantNotificationStatus(notificationsSaveReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = NotificationURLConstants.UPDATE_EMP_NOTIFICATION_STATUS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> updateEmpNotificationStatus(@RequestBody NotificationsSaveReq notificationsSaveReq) {
        return new ResponseEntity<AppResp>(mwNotificationService.updateEmpNotificationStatus(notificationsSaveReq),
                HttpStatus.OK);

    }

    private Map<Long, LabelKeyTO> getUserProjMap() {
        UserProjGetReq userProjGetReq = new UserProjGetReq();
        userProjGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        userProjGetReq.setClientId(AppUserUtils.getClientId());

        UserProjResp userProjResp = mwProjLibService.getUserProjects(userProjGetReq);
        Map<Long, LabelKeyTO> userProjMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO userProjLabelKeyTO = null;
        for (UserProjDetailsTO userProjDetailsTO : userProjResp.getUserProjDetailsTOs()) {

            userProjLabelKeyTO = new LabelKeyTO();
            userProjLabelKeyTO.setId(userProjDetailsTO.getProjId());
            userProjLabelKeyTO.setCode(userProjDetailsTO.getParentName());
            userProjLabelKeyTO.setName(userProjDetailsTO.getProjName());
            userProjMap.put(userProjDetailsTO.getProjId(), userProjLabelKeyTO);
        }
        return userProjMap;
    }

    private Map<Long, LabelKeyTO> getUsersMap() {
        ClientReq clientReq = new ClientReq();
        clientReq.setStatus(StatusCodes.ACTIVE.getValue());
        clientReq.setClientId(AppUserUtils.getClientId());

        UserResp userResp = mwUserService.getUsers(clientReq);
        Map<Long, LabelKeyTO> usersMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO usersLabelKeyTO = null;
        for (UserTO userTO : userResp.getUsers()) {

            usersLabelKeyTO = new LabelKeyTO();
            usersLabelKeyTO.setId(userTO.getUserId());
            usersLabelKeyTO.setCode(userTO.getUserName());
            usersMap.put(userTO.getUserId(), usersLabelKeyTO);
        }
        return usersMap;
    }

    @RequestMapping(value = NotificationURLConstants.GET_EMP_LEAVE_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<EmployeeLeaveNotificationsResp> getEmployeeLeaveNotifications(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        EmployeeLeaveNotificationsResp employeeLeaveNotificationsResp = mwNotificationService
                .getEmployeeLeaveNotifications(notificationsGetReq);
        employeeLeaveNotificationsResp.setEmpRegMap(getRegEmpMap());
        //employeeNotificationsResp.setUserProjMap(getUserProjMap());
        //employeeNotificationsResp.setUsersMap(getUsersMap());
        return new ResponseEntity<EmployeeLeaveNotificationsResp>(employeeLeaveNotificationsResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_EMP_LEAVE_NOTIFICATIONS_BY_PROJID, method = RequestMethod.POST)
    public ResponseEntity<EmployeeLeaveNotificationsResp> getEmployeeLeaveNotificationsByProjId(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        EmployeeLeaveNotificationsResp employeeLeaveNotificationsResp = mwNotificationService
                .getEmployeeLeaveNotificationsByProjId(notificationsGetReq);
        employeeLeaveNotificationsResp.setEmpRegMap(getRegEmpMap());
        return new ResponseEntity<EmployeeLeaveNotificationsResp>(employeeLeaveNotificationsResp, HttpStatus.OK);
    }

    private Map<Long, LabelKeyTO> getRegEmpMap() {
        EmpRegisterGetReq empRegisterGetReq = new EmpRegisterGetReq();
        empRegisterGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        empRegisterGetReq.setClientId(AppUserUtils.getClientId());

        EmpRegResp empRegResp = mwRegisterService.getAllRegEmp(empRegisterGetReq);
        Map<Long, LabelKeyTO> regEmpMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO regEmpLabelKeyTO = null;
        for (EmpRegisterDtlTO empRegisterDtlTO : empRegResp.getEmpRegisterDtlTOs()) {

            regEmpLabelKeyTO = new LabelKeyTO();
            regEmpLabelKeyTO.setId(empRegisterDtlTO.getId());
            regEmpLabelKeyTO.setCode(empRegisterDtlTO.getCode());
            regEmpLabelKeyTO.setName(empRegisterDtlTO.getFirstName());
            regEmpLabelKeyTO.setReferenceId(empRegisterDtlTO.getCmpId());
            regEmpMap.put(empRegisterDtlTO.getId(), regEmpLabelKeyTO);
        }
        return regEmpMap;
    }

    @RequestMapping(value = NotificationURLConstants.SAVE_EMP_LEAVE_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveEmployeeLeaveNotifications(
            @RequestBody EmployeeNotificationsSaveReq employeeNotificationsSaveReq) {

        return new ResponseEntity<AppResp>(
                mwNotificationService.saveEmployeeLeaveNotifications(employeeNotificationsSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.UPDATE_EMP_LEAVE_NOTIFICATION_STATUS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> updateEmployeeNotificationStatus(
            @RequestBody NotificationsSaveReq notificationsSaveReq) {
        return new ResponseEntity<AppResp>(
                mwNotificationService.updateEmployeeLeaveNotificationStatus(notificationsSaveReq), HttpStatus.OK);

    }

    @RequestMapping(value = NotificationURLConstants.GET_COUNT_NOTIFICATION, method = RequestMethod.POST)
    public ResponseEntity<NotificationCountResp> getCountNotifications(
            @RequestBody NotificationCountGetReq notificationCountGetReq) {

        return new ResponseEntity<NotificationCountResp>(
                mwNotificationService.getCountNotification(notificationCountGetReq), HttpStatus.OK);

    }
    
    @RequestMapping(value = NotificationURLConstants.GET_SOE_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<SoeNotificationsResp> getSoeNotifications(
            @RequestBody NotificationsSoeGetReq notificationsSoeGetReq) {

    	SoeNotificationsResp soeNotificationsResp = mwNotificationService
                .getSoeNotifications(notificationsSoeGetReq);
        //timeSheetNotificationsResp.setUserProjMap(getUserProjMap());
        //timeSheetNotificationsResp.setUsersMap(getUsersMap());
        return new ResponseEntity<SoeNotificationsResp>(soeNotificationsResp, HttpStatus.OK);
    }
    
    @RequestMapping(value = NotificationURLConstants.SAVE_SOE_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveSoeNotifications(
            @RequestBody SoeNotificationsSaveReq soeNotificationsSaveReq) {

        return new ResponseEntity<AppResp>(
                mwNotificationService.saveSoeNotifications(soeNotificationsSaveReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = NotificationURLConstants.GET_SOE_NOTIFICATION_BY_PROJECT, method = RequestMethod.POST)
    public ResponseEntity<SoeNotificationsResp> getSoeNotificationsByProjId(
            @RequestBody NotificationsSoeGetReq notificationsSoeGetReq) {

    	SoeNotificationsResp soeNotificationsResp = mwNotificationService
                .getSoeNotificationsByProjId(notificationsSoeGetReq);
        return new ResponseEntity<SoeNotificationsResp>(soeNotificationsResp, HttpStatus.OK);
    }

}
