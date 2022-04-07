package com.rjtech.mw.service.notification;

import com.rjtech.common.resp.AppResp;
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
import com.rjtech.notification.resp.SoeNotificationsResp;
import com.rjtech.notification.req.NotificationsSoeGetReq;
import com.rjtech.notification.req.SoeNotificationsSaveReq;
public interface MWNotificationService {

    public TimeSheetNotificationsResp getTimeSheetNotifications(NotificationsGetReq notificationsGetReq);

    public TimeSheetNotificationsResp getTimeSheetNotificationsByProjId(NotificationsGetReq notificationsGetReq);

    public AppResp saveTimeSheetNotifications(TimeSheetNotificationsSaveReq timeSheetNotificationsSaveReq);
    
    public AttendenceNotificationsResp getAttendenceAddlTimeNotifications(NotificationsGetReq notificationsGetReq);

    public AttendenceNotificationsResp getAttendanceNotification(NotificationsGetReq notificationsGetReq);

    public AppResp saveAttendenceNotifications(AttendenceNotificationsSaveReq attendenceNotificationsSaveReq);

    public WorkDairyNotificationResp getWorkDiaryNotifications(NotificationsGetReq notificationsGetReq);

    public WorkDairyNotificationResp getWorkDiaryNotificationsByProjId(NotificationsGetReq notificationsGetReq);

    public AppResp saveWorkDiaryNotifications(WorkDairyNotificationsSaveReq workDairyNotificationsSaveReq);

    public EmployeeNotificationsResp getEmployeeNotifications(NotificationsGetReq notificationsGetReq);

    public EmployeeNotificationsResp getEmployeeNotificationsByProjId(NotificationsGetReq notificationsGetReq);

    public AppResp saveEmployeeNotifications(EmployeeNotificationsSaveReq employeeNotificationsSaveReq);

    public PlantNotificationResp getPlantNotifications(NotificationsGetReq notificationsGetReq);

    public PlantNotificationResp getPlantNotificationsByProjId(NotificationsGetReq notificationsGetReq);

    public AppResp savePlantNotifications(PlantNotificationsSaveReq plantNotificationsSaveReq);

    public MaterialNotificationResp getMaterialNotifications(NotificationsGetReq notificationsGetReq);

    public MaterialNotificationResp getMaterialNotificationsByProjId(NotificationsGetReq notificationsGetReq);

    public AppResp saveMaterialNotifications(MaterialNotificationsSaveReq materialNotificationsSaveReq);

    public ProcurementNotificationResp getProcureNotifications(NotificationsGetReq notificationsGetReq);

    public ProcurementNotificationResp getProcurementNotificationsByProjId(NotificationsGetReq notificationsGetReq);

    public AppResp saveProcurementNotifications(ProcureNotificationsSaveReq procureNotificationsSaveReq);

    public AppResp updateMaterialNotificationStatus(NotificationsSaveReq notificationsSaveReq);

    public AppResp updatePlantNotificationStatus(NotificationsSaveReq notificationsSaveReq);

    public AppResp updateEmpNotificationStatus(NotificationsSaveReq notificationsSaveReq);

    public EmployeeLeaveNotificationsResp getEmployeeLeaveNotifications(NotificationsGetReq notificationsGetReq);

    public EmployeeLeaveNotificationsResp getEmployeeLeaveNotificationsByProjId(
            NotificationsGetReq notificationsGetReq);

    public AppResp saveEmployeeLeaveNotifications(EmployeeNotificationsSaveReq employeeNotificationsSaveReq);

    public AppResp updateEmployeeLeaveNotificationStatus(NotificationsSaveReq notificationsSaveReq);

    public NotificationCountResp getCountNotification(NotificationCountGetReq notificationCountGetReq);
    
    public SoeNotificationsResp getSoeNotifications(NotificationsSoeGetReq notificationsSoeGetReq);
    
    public AppResp saveSoeNotifications(SoeNotificationsSaveReq soeNotificationsSaveReq);
    
    public SoeNotificationsResp getSoeNotificationsByProjId(NotificationsSoeGetReq notificationsSoeGetReq);

}
