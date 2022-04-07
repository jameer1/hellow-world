package com.rjtech.mw.service.reports;

import com.rjtech.attendance.reports.req.CrewWiseAttendanceGetReq;
import com.rjtech.attendance.reports.req.DailyAttendanceGetReq;
import com.rjtech.attendance.reports.req.DailyResourceAttendanceGetReq;
import com.rjtech.attendance.reports.req.MonthlyAttendanceGetReq;
import com.rjtech.reports.resp.ReportsResp;

public interface MWReportsAttendanceService {

    ReportsResp getDailyResourceAttendanceReport(DailyResourceAttendanceGetReq dailyResourceAttendGetReq);

    ReportsResp getDailyEmpAttendanceReport(DailyAttendanceGetReq dailyAttendanceGetReq);

    ReportsResp getDailyPlantAttendanceReport(DailyAttendanceGetReq dailyAttendanceGetReq);

    ReportsResp getMonthlyEmpAttendanceReport(MonthlyAttendanceGetReq monthlyAttendanceGetReq);

    ReportsResp getMonthlyPlantAttendanceReport(MonthlyAttendanceGetReq monthlyAttendanceGetReq);

    ReportsResp getCrewWiseAtttendanceReport(CrewWiseAttendanceGetReq crewWiseAttendanceGetReq);

}
