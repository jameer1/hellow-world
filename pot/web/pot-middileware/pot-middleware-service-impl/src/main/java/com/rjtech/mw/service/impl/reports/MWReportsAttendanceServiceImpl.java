package com.rjtech.mw.service.impl.reports;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.attendance.reports.req.CrewWiseAttendanceGetReq;
import com.rjtech.attendance.reports.req.DailyAttendanceGetReq;
import com.rjtech.attendance.reports.req.DailyResourceAttendanceGetReq;
import com.rjtech.attendance.reports.req.MonthlyAttendanceGetReq;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.reports.MWReportsAttendanceService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.reports.constants.ReportsURLConstants;
import com.rjtech.reports.resp.ReportsResp;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "mwReportsAttendanceService")
@RJSService(modulecode = "mwReportsAttendanceService")
@Transactional
public class MWReportsAttendanceServiceImpl extends RestConfigServiceImpl implements MWReportsAttendanceService {

    public ReportsResp getDailyResourceAttendanceReport(DailyResourceAttendanceGetReq dailyResourceAttendGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(dailyResourceAttendGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_DAILY_RESOURCE_ATTENDANCE_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getDailyEmpAttendanceReport(DailyAttendanceGetReq dailyAttendanceGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(dailyAttendanceGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_DAILY_EMP_ATTENDANCE_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getDailyPlantAttendanceReport(DailyAttendanceGetReq dailyAttendanceGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(dailyAttendanceGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_DAILY_PLANT_ATTENDANCE_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getMonthlyEmpAttendanceReport(MonthlyAttendanceGetReq monthlyAttendanceGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(monthlyAttendanceGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_MONTHLY_EMP_ATTENDANCE_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getMonthlyPlantAttendanceReport(MonthlyAttendanceGetReq monthlyAttendanceGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(monthlyAttendanceGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_MONTHLY_PLANT_ATTENDANCE_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getCrewWiseAtttendanceReport(CrewWiseAttendanceGetReq crewWiseAttendanceGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(crewWiseAttendanceGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_CREW_WISE_ATTENDANCE_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

}
