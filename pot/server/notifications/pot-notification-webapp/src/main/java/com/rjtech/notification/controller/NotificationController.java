package com.rjtech.notification.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.req.NotificationStatusReq;
import com.rjtech.common.resp.AppResp;
import com.rjtech.common.resp.NotificationStatusOnLoadResp;
import com.rjtech.common.service.CentralLibService;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusType;
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
import com.rjtech.notification.service.NotificationService;
import com.rjtech.notification.resp.SoeNotificationsResp;
import com.rjtech.notification.req.NotificationsSoeGetReq;
import com.rjtech.notification.req.SoeNotificationsSaveReq;
@RestController
@RequestMapping(NotificationURLConstants.NOTIFICATION_PARH_URL)
public class NotificationController {

	private static final Logger log = LoggerFactory.getLogger(NotificationController.class);
	
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private CentralLibService centralLibService;

    @RequestMapping(value = NotificationURLConstants.GET_TIME_SHEET_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetNotificationsResp> getTimeSheetNotifications(
            @RequestBody NotificationsGetReq notificationsGetReq) {
        System.out.println("GET_TIME_SHEET_NOTIFICATIONS"+notificationsGetReq);
        return new ResponseEntity<TimeSheetNotificationsResp>(
                notificationService.getTimeSheetNotifications(notificationsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_TIME_SHEET_NOTIFICATIONS_BY_PROJID, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetNotificationsResp> getTimeSheetNotificationsByProjId(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        return new ResponseEntity<TimeSheetNotificationsResp>(
                notificationService.getTimeSheetNotificationsByProjId(notificationsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.SAVE_TIMESHEET_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveTimeSheetNotifications(
            @RequestBody TimeSheetNotificationsSaveReq timeSheetNotificationsSaveReq) {

        notificationService.saveTimeSheetNotifications(timeSheetNotificationsSaveReq);
        AppResp appResp = new AppResp();
        appResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }
    
    @RequestMapping(value = NotificationURLConstants.SAVE_PROCUREMENT_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveProcurementNotifications(
            @RequestBody ProcureNotificationsSaveReq procureNotificationsSaveReq) {
    	notificationService.saveProcurementNotifications(procureNotificationsSaveReq);
    	log.info("--------------------NotificationController.java ");
    	AppResp appResp = new AppResp();
    	appResp.cloneAppResp(CommonUtil.getSaveAppResp());
    	return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_ADDL_TIME_ATTENDANCE_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<AttendenceNotificationsResp> getAttendenceAddlTimeNotifications(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        return new ResponseEntity<AttendenceNotificationsResp>(
                notificationService.getAttendenceAddlTimeNotifications(notificationsGetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = NotificationURLConstants.GET_ATTENDANCE_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<AttendenceNotificationsResp> getAttendenceNotifications(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        return new ResponseEntity<AttendenceNotificationsResp>(
                notificationService.getAttendenceNotifications(notificationsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.SAVE_ATTENDANCE_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveAttendenceNotifications(
            @RequestBody AttendenceNotificationsSaveReq attendenceNotificationsSaveReq) {
        notificationService.saveAttendenceNotifications(attendenceNotificationsSaveReq);
        AppResp appResp = new AppResp();
        appResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_WORK_DIARY_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyNotificationResp> getWorkDairyNotifications(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        return new ResponseEntity<WorkDairyNotificationResp>(
                notificationService.getWorkDairyNotifications(notificationsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_WORK_DIARY_NOTIFICATIONS_BY_PROJECT, method = RequestMethod.POST)
    public ResponseEntity<WorkDairyNotificationResp> getWorkDairyNotificationsByProjId(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        return new ResponseEntity<WorkDairyNotificationResp>(
                notificationService.getWorkDairyNotificationsByProjId(notificationsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.SAVE_WORK_DIARY_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveWorkDairyNotifications(
            @RequestBody WorkDairyNotificationsSaveReq workDairyNotificationsSaveReq) {
        notificationService.saveWorkDairyNotifications(workDairyNotificationsSaveReq);

        AppResp appResp = new AppResp();
        appResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_EMP_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<EmployeeNotificationsResp> getEmployeeNotifications(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        return new ResponseEntity<EmployeeNotificationsResp>(
                notificationService.getEmployeeNotifications(notificationsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_EMP_NOTIFICATIONS_BY_PROJECT, method = RequestMethod.POST)
    public ResponseEntity<EmployeeNotificationsResp> getEmployeeNotificationsByProjId(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        return new ResponseEntity<EmployeeNotificationsResp>(
                notificationService.getEmployeeNotificationsByProjId(notificationsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.SAVE_EMP_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveEmployeeNotifications(
            @RequestBody EmployeeNotificationsSaveReq employeeNotificationsSaveReq) {
        notificationService.saveEmployeeNotifications(employeeNotificationsSaveReq);

        AppResp appResp = new AppResp();
        appResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_PLANT_NOTIFICATION, method = RequestMethod.POST)
    public ResponseEntity<PlantNotificationResp> getPlantNotification(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        return new ResponseEntity<PlantNotificationResp>(notificationService.getPlantNotifications(notificationsGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_PLANT_NOTIFICATION_BY_PROJECT, method = RequestMethod.POST)
    public ResponseEntity<PlantNotificationResp> getPlantNotificationsByProjId(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        return new ResponseEntity<>(notificationService.getPlantNotificationsByProjId(notificationsGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.SAVE_PLANT_NOTIFICATION, method = RequestMethod.POST)
    public ResponseEntity<AppResp> savePlantNotification(
            @RequestBody PlantNotificationsSaveReq plantNotificationsSaveReq) {
        notificationService.savePlantNotifications(plantNotificationsSaveReq);

        AppResp appResp = new AppResp();
        appResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_MATERIAL_NOTIFICATION, method = RequestMethod.POST)
    public ResponseEntity<MaterialNotificationResp> getMaterialNotification(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        return new ResponseEntity<MaterialNotificationResp>(
                notificationService.getMaterialNotifications(notificationsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_MATERIAL_NOTIFICATION_BY_PROJECT, method = RequestMethod.POST)
    public ResponseEntity<MaterialNotificationResp> getMaterialNotificationsByProjId(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        return new ResponseEntity<MaterialNotificationResp>(
                notificationService.getMaterialNotificationsByProjId(notificationsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.SAVE_MATERIAL_NOTIFICATION, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveMaterialNotification(
            @RequestBody MaterialNotificationsSaveReq materialNotificationsSaveReq) {
        notificationService.saveMaterialNotifications(materialNotificationsSaveReq);

        AppResp appResp = new AppResp();
        appResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_PROCUREMENT_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<ProcurementNotificationResp> getProcurementNotifications(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        return new ResponseEntity<ProcurementNotificationResp>(
                notificationService.getProcurementNotifications(notificationsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_PROCUREMENT_NOTIFICATIONS_BY_PROJECT, method = RequestMethod.POST)
    public ResponseEntity<ProcurementNotificationResp> getProcurementNotificationsByProjId(
            @RequestBody NotificationsGetReq notificationsGetReq) {
        return new ResponseEntity<ProcurementNotificationResp>(
                notificationService.getProcurementNotificationsByProjId(notificationsGetReq), HttpStatus.OK);
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
        return new ResponseEntity<NotificationStatusOnLoadResp>(notificationStatusOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.UPDATE_MATERIAL_NOTIFICATION_STATUS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> updateMaterialNotificationStatus(
            @RequestBody NotificationsSaveReq notificationsSaveReq) {

        NotificationsGetReq notificationsGetReq = new NotificationsGetReq();
        notificationsGetReq.setId(notificationsSaveReq.getId());
        notificationsGetReq.setNotifyStatus(notificationsSaveReq.getNotifyStatus());
        notificationService.updateMaterialNotificationStatus(notificationsSaveReq);

        AppResp appResp = new AppResp();
        appResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.UPDATE_PLANT_NOTIFICATION_STATUS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> updatePlantNotificationStatus(
            @RequestBody NotificationsSaveReq notificationsSaveReq) {

        NotificationsGetReq notificationsGetReq = new NotificationsGetReq();
        notificationsGetReq.setId(notificationsSaveReq.getId());
        notificationsGetReq.setNotifyStatus(notificationsSaveReq.getNotifyStatus());
        notificationService.updatePlantNotificationStatus(notificationsSaveReq);

        AppResp appResp = new AppResp();
        appResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.UPDATE_EMP_NOTIFICATION_STATUS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> updateEmpNotificationStatus(@RequestBody NotificationsSaveReq notificationsSaveReq) {

        NotificationsGetReq notificationsGetReq = new NotificationsGetReq();
        notificationsGetReq.setId(notificationsSaveReq.getId());
        notificationsGetReq.setNotifyStatus(notificationsSaveReq.getNotifyStatus());
        notificationService.updateEmpNotificationStatus(notificationsSaveReq);

        AppResp appResp = new AppResp();
        appResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_EMP_LEAVE_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<EmployeeLeaveNotificationsResp> getEmployeeLeaveNotifications(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        EmployeeLeaveNotificationsResp employeeLeaveNotificationsResp = new EmployeeLeaveNotificationsResp();
        employeeLeaveNotificationsResp = notificationService.getEmployeeLeaveNotifications(notificationsGetReq);
        employeeLeaveNotificationsResp.setCompanyMap(centralLibService.getCompanyMap());
        // employeeLeaveNotificationsResp.setEmpRegMap(empRegisterService.getRegEmpMap());
        return new ResponseEntity<EmployeeLeaveNotificationsResp>(employeeLeaveNotificationsResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_EMP_LEAVE_NOTIFICATIONS_BY_PROJID, method = RequestMethod.POST)
    public ResponseEntity<EmployeeLeaveNotificationsResp> getEmployeeLeaveNotificationsByProjId(
            @RequestBody NotificationsGetReq notificationsGetReq) {

        EmployeeLeaveNotificationsResp employeeLeaveNotificationsResp = new EmployeeLeaveNotificationsResp();
        employeeLeaveNotificationsResp = notificationService.getEmployeeLeaveNotificationsByProjId(notificationsGetReq);
        employeeLeaveNotificationsResp.setCompanyMap(centralLibService.getCompanyMap());
        return new ResponseEntity<EmployeeLeaveNotificationsResp>(employeeLeaveNotificationsResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.SAVE_EMP_LEAVE_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveEmployeeLeaveNotifications(
            @RequestBody EmployeeNotificationsSaveReq employeeNotificationsSaveReq) {
        notificationService.saveEmployeeLeaveNotifications(employeeNotificationsSaveReq);

        AppResp appResp = new AppResp();
        appResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.UPDATE_EMP_LEAVE_NOTIFICATION_STATUS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> updateEmployeeLeaveNotifications(
            @RequestBody NotificationsSaveReq notificationsSaveReq) {

        NotificationsGetReq notificationsGetReq = new NotificationsGetReq();
        notificationsGetReq.setId(notificationsSaveReq.getId());
        notificationsGetReq.setNotifyStatus(notificationsSaveReq.getNotifyStatus());
        notificationService.updateEmployeeLeaveNotificationStatus(notificationsSaveReq);

        AppResp appResp = new AppResp();
        appResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    @RequestMapping(value = NotificationURLConstants.GET_COUNT_NOTIFICATION, method = RequestMethod.POST)
    public ResponseEntity<NotificationCountResp> getCountNotifications(
            @RequestBody NotificationCountGetReq notificationCountGetReq) {

        return new ResponseEntity<NotificationCountResp>(
                notificationService.getCountNotification(notificationCountGetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = NotificationURLConstants.GET_SOE_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<SoeNotificationsResp> getSoeNotifications(
            @RequestBody NotificationsSoeGetReq notificationsSoeGetReq) {
        return new ResponseEntity<SoeNotificationsResp>(
                notificationService.getSoeNotifications(notificationsSoeGetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = NotificationURLConstants.SAVE_SOE_NOTIFICATIONS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveSoeNotifications(
            @RequestBody SoeNotificationsSaveReq soeNotificationsSaveReq) {
        notificationService.saveSoeNotifications(soeNotificationsSaveReq);
        AppResp appResp = new AppResp();
        appResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }
    
    @RequestMapping(value = NotificationURLConstants.GET_SOE_NOTIFICATION_BY_PROJECT, method = RequestMethod.POST)
    public ResponseEntity<SoeNotificationsResp> getSoeNotificationsByProjId(
            @RequestBody NotificationsSoeGetReq notificationsSoeGetReq) {

        return new ResponseEntity<SoeNotificationsResp>(
                notificationService.getSoeNotificationsByProjId(notificationsSoeGetReq), HttpStatus.OK);
    }
    
}
