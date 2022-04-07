package com.rjtech.mw.service.impl.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.resp.AppResp;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.notification.MWNotificationService;
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
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.notification.resp.SoeNotificationsResp;
import com.rjtech.notification.req.NotificationsSoeGetReq;
import com.rjtech.notification.req.SoeNotificationsSaveReq;
@Service(value = "mwNotificationService")
@RJSService(modulecode = "mwNotificationService")
@Transactional
public class MWNotificationServiceImpl extends RestConfigServiceImpl implements MWNotificationService {
	
	private static final Logger log = LoggerFactory.getLogger(MWNotificationServiceImpl.class);

    public TimeSheetNotificationsResp getTimeSheetNotifications(NotificationsGetReq notificationsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(notificationsGetReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL + NotificationURLConstants.GET_TIME_SHEET_NOTIFICATIONS);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetNotificationsResp.class);
    }

    public TimeSheetNotificationsResp getTimeSheetNotificationsByProjId(NotificationsGetReq notificationsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(notificationsGetReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL
                        + NotificationURLConstants.GET_TIME_SHEET_NOTIFICATIONS_BY_PROJID);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetNotificationsResp.class);
    }

    public AppResp saveTimeSheetNotifications(TimeSheetNotificationsSaveReq sheetNotificationsSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(sheetNotificationsSaveReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL + NotificationURLConstants.SAVE_TIMESHEET_NOTIFICATIONS);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }
    
    public AttendenceNotificationsResp getAttendenceAddlTimeNotifications(NotificationsGetReq notificationsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(notificationsGetReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL + NotificationURLConstants.GET_ADDL_TIME_ATTENDANCE_NOTIFICATIONS);
        return AppUtils.fromJson(strResponse.getBody(), AttendenceNotificationsResp.class);
    }

    public AttendenceNotificationsResp getAttendanceNotification(NotificationsGetReq notificationsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(notificationsGetReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL + NotificationURLConstants.GET_ATTENDANCE_NOTIFICATIONS);
        return AppUtils.fromJson(strResponse.getBody(), AttendenceNotificationsResp.class);
    }

    public AppResp saveAttendenceNotifications(AttendenceNotificationsSaveReq attendenceNotificationsSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(attendenceNotificationsSaveReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL
                        + NotificationURLConstants.SAVE_ATTENDANCE_NOTIFICATIONS);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public WorkDairyNotificationResp getWorkDiaryNotifications(NotificationsGetReq notificationsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(notificationsGetReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL + NotificationURLConstants.GET_WORK_DIARY_NOTIFICATIONS);
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyNotificationResp.class);
    }

    public WorkDairyNotificationResp getWorkDiaryNotificationsByProjId(NotificationsGetReq notificationsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(notificationsGetReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL
                        + NotificationURLConstants.GET_WORK_DIARY_NOTIFICATIONS_BY_PROJECT);
        return AppUtils.fromJson(strResponse.getBody(), WorkDairyNotificationResp.class);
    }

    public AppResp saveWorkDiaryNotifications(WorkDairyNotificationsSaveReq workDairyNotificationsSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(workDairyNotificationsSaveReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL
                        + NotificationURLConstants.SAVE_WORK_DIARY_NOTIFICATIONS);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public EmployeeNotificationsResp getEmployeeNotifications(NotificationsGetReq notificationsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(notificationsGetReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL + NotificationURLConstants.GET_EMP_NOTIFICATIONS);
        return AppUtils.fromJson(strResponse.getBody(), EmployeeNotificationsResp.class);
    }

    public EmployeeNotificationsResp getEmployeeNotificationsByProjId(NotificationsGetReq notificationsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(notificationsGetReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL
                        + NotificationURLConstants.GET_EMP_NOTIFICATIONS_BY_PROJECT);
        return AppUtils.fromJson(strResponse.getBody(), EmployeeNotificationsResp.class);
    }

    public AppResp saveEmployeeNotifications(EmployeeNotificationsSaveReq employeeNotificationsSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(employeeNotificationsSaveReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL + NotificationURLConstants.SAVE_EMP_NOTIFICATIONS);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public PlantNotificationResp getPlantNotifications(NotificationsGetReq notificationsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(notificationsGetReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL + NotificationURLConstants.GET_PLANT_NOTIFICATION);
        return AppUtils.fromJson(strResponse.getBody(), PlantNotificationResp.class);
    }

    public PlantNotificationResp getPlantNotificationsByProjId(NotificationsGetReq notificationsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(notificationsGetReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL
                        + NotificationURLConstants.GET_PLANT_NOTIFICATION_BY_PROJECT);
        return AppUtils.fromJson(strResponse.getBody(), PlantNotificationResp.class);
    }

    public AppResp savePlantNotifications(PlantNotificationsSaveReq plantNotificationsSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(plantNotificationsSaveReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL + NotificationURLConstants.SAVE_PLANT_NOTIFICATION);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public MaterialNotificationResp getMaterialNotifications(NotificationsGetReq notificationsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(notificationsGetReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL + NotificationURLConstants.GET_MATERIAL_NOTIFICATION);
        return AppUtils.fromJson(strResponse.getBody(), MaterialNotificationResp.class);
    }

    public MaterialNotificationResp getMaterialNotificationsByProjId(NotificationsGetReq notificationsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(notificationsGetReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL
                        + NotificationURLConstants.GET_MATERIAL_NOTIFICATION_BY_PROJECT);
        return AppUtils.fromJson(strResponse.getBody(), MaterialNotificationResp.class);
    }

    public AppResp saveMaterialNotifications(MaterialNotificationsSaveReq materialNotificationsSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(materialNotificationsSaveReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL + NotificationURLConstants.SAVE_MATERIAL_NOTIFICATION);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public ProcurementNotificationResp getProcureNotifications(NotificationsGetReq notificationsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(notificationsGetReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL
                        + NotificationURLConstants.GET_PROCUREMENT_NOTIFICATIONS);
        return AppUtils.fromJson(strResponse.getBody(), ProcurementNotificationResp.class);
    }

    public ProcurementNotificationResp getProcurementNotificationsByProjId(NotificationsGetReq notificationsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(notificationsGetReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL
                        + NotificationURLConstants.GET_PROCUREMENT_NOTIFICATIONS_BY_PROJECT);
        return AppUtils.fromJson(strResponse.getBody(), ProcurementNotificationResp.class);
    }

    public AppResp saveProcurementNotifications(ProcureNotificationsSaveReq procureNotificationsSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(procureNotificationsSaveReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL
                        + NotificationURLConstants.SAVE_PROCUREMENT_NOTIFICATIONS);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public AppResp updateMaterialNotificationStatus(NotificationsSaveReq notificationsSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(notificationsSaveReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL
                        + NotificationURLConstants.UPDATE_MATERIAL_NOTIFICATION_STATUS);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public AppResp updatePlantNotificationStatus(NotificationsSaveReq notificationsSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(notificationsSaveReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL
                        + NotificationURLConstants.UPDATE_PLANT_NOTIFICATION_STATUS);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public AppResp updateEmpNotificationStatus(NotificationsSaveReq notificationsSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(notificationsSaveReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL
                        + NotificationURLConstants.UPDATE_EMP_NOTIFICATION_STATUS);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public EmployeeLeaveNotificationsResp getEmployeeLeaveNotifications(NotificationsGetReq notificationsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(notificationsGetReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL + NotificationURLConstants.GET_EMP_LEAVE_NOTIFICATIONS);
        return AppUtils.fromJson(strResponse.getBody(), EmployeeLeaveNotificationsResp.class);
    }

    public EmployeeLeaveNotificationsResp getEmployeeLeaveNotificationsByProjId(
            NotificationsGetReq notificationsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(notificationsGetReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL
                        + NotificationURLConstants.GET_EMP_LEAVE_NOTIFICATIONS_BY_PROJID);
        return AppUtils.fromJson(strResponse.getBody(), EmployeeLeaveNotificationsResp.class);
    }

    public AppResp saveEmployeeLeaveNotifications(EmployeeNotificationsSaveReq employeeNotificationsSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(employeeNotificationsSaveReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL + NotificationURLConstants.SAVE_EMP_LEAVE_NOTIFICATIONS);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public AppResp updateEmployeeLeaveNotificationStatus(NotificationsSaveReq notificationsSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(notificationsSaveReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL
                        + NotificationURLConstants.UPDATE_EMP_LEAVE_NOTIFICATION_STATUS);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public NotificationCountResp getCountNotification(NotificationCountGetReq notificationCountGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(notificationCountGetReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL + NotificationURLConstants.GET_COUNT_NOTIFICATION);
        return AppUtils.fromJson(strResponse.getBody(), NotificationCountResp.class);
    }
    
    public SoeNotificationsResp getSoeNotifications(NotificationsSoeGetReq notificationsSoeGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(notificationsSoeGetReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL + NotificationURLConstants.GET_SOE_NOTIFICATIONS);
        return AppUtils.fromJson(strResponse.getBody(), SoeNotificationsResp.class);
    }
    
    public AppResp saveSoeNotifications(SoeNotificationsSaveReq soeNotificationsSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(soeNotificationsSaveReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL
                        + NotificationURLConstants.SAVE_SOE_NOTIFICATIONS);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }
    
    public SoeNotificationsResp getSoeNotificationsByProjId(NotificationsSoeGetReq notificationsSoeGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getNotificationPOSTRestTemplate(AppUtils.toJson(notificationsSoeGetReq),
                NotificationURLConstants.NOTIFICATION_PARH_URL
                        + NotificationURLConstants.GET_SOE_NOTIFICATION_BY_PROJECT);
        return AppUtils.fromJson(strResponse.getBody(), SoeNotificationsResp.class);
    }

}
