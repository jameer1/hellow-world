package com.rjtech.notification.service;

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
public interface NotificationService {

    public TimeSheetNotificationsResp getTimeSheetNotifications(NotificationsGetReq notificationsGetReq);

    public TimeSheetNotificationsResp getTimeSheetNotificationsByProjId(NotificationsGetReq notificationsGetReq);

    public void saveTimeSheetNotifications(TimeSheetNotificationsSaveReq timeSheetNotificationsSaveReq);

    public AttendenceNotificationsResp getAttendenceAddlTimeNotifications(NotificationsGetReq notificationsGetReq);
    
    public AttendenceNotificationsResp getAttendenceNotifications(NotificationsGetReq notificationsGetReq);

    public void saveAttendenceNotifications(AttendenceNotificationsSaveReq attendenceNotificationsSaveReq);

    public WorkDairyNotificationResp getWorkDairyNotifications(NotificationsGetReq notificationsGetReq);

    public WorkDairyNotificationResp getWorkDairyNotificationsByProjId(NotificationsGetReq notificationsGetReq);

    public void saveWorkDairyNotifications(WorkDairyNotificationsSaveReq workDairyNotificationsSaveReq);

    public EmployeeNotificationsResp getEmployeeNotifications(NotificationsGetReq notificationsGetReq);

    public EmployeeNotificationsResp getEmployeeNotificationsByProjId(NotificationsGetReq notificationsGetReq);

    public void saveEmployeeNotifications(EmployeeNotificationsSaveReq employeeNotificationsSaveReq);

    public ProcurementNotificationResp getProcurementNotifications(NotificationsGetReq notificationsGetReq);

    public ProcurementNotificationResp getProcurementNotificationsByProjId(NotificationsGetReq notificationsGetReq);

    public void saveProcurementNotifications(ProcureNotificationsSaveReq procureNotificationsSaveReq);

    public PlantNotificationResp getPlantNotificationsByProjId(NotificationsGetReq notificationsGetReq);

    public PlantNotificationResp getPlantNotifications(NotificationsGetReq notificationsGetReq);

    public void savePlantNotifications(PlantNotificationsSaveReq plantNotificationsSaveReq);

    public MaterialNotificationResp getMaterialNotifications(NotificationsGetReq notificationsGetReq);

    public MaterialNotificationResp getMaterialNotificationsByProjId(NotificationsGetReq notificationsGetReq);

    public void saveMaterialNotifications(MaterialNotificationsSaveReq materialNotificationsSaveReq);

    public void updateMaterialNotificationStatus(NotificationsSaveReq notificationsSaveReq);

    public void updatePlantNotificationStatus(NotificationsSaveReq notificationsSaveReq);

    public void updateEmpNotificationStatus(NotificationsSaveReq notificationsSaveReq);

    public EmployeeLeaveNotificationsResp getEmployeeLeaveNotifications(NotificationsGetReq notificationsGetReq);

    public EmployeeLeaveNotificationsResp getEmployeeLeaveNotificationsByProjId(
            NotificationsGetReq notificationsGetReq);

    public void saveEmployeeLeaveNotifications(EmployeeNotificationsSaveReq employeeNotificationsSaveReq);

    public void updateEmployeeLeaveNotificationStatus(NotificationsSaveReq notificationsSaveReq);

    public NotificationCountResp getCountNotification(NotificationCountGetReq notificationCountGetReq);
    
    public SoeNotificationsResp getSoeNotifications(NotificationsSoeGetReq notificationsSoeGetReq);
    
    public void saveSoeNotifications(SoeNotificationsSaveReq soeNotificationsSaveReq);
    
    public SoeNotificationsResp getSoeNotificationsByProjId(
    		NotificationsSoeGetReq notificationsSoeGetReq);

}
