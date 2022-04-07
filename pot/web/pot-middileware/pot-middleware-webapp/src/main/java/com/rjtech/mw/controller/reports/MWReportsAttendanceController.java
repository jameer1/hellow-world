package com.rjtech.mw.controller.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.attendance.reports.req.CrewWiseAttendanceGetReq;
import com.rjtech.attendance.reports.req.DailyAttendanceGetReq;
import com.rjtech.attendance.reports.req.DailyResourceAttendanceGetReq;
import com.rjtech.attendance.reports.req.MonthlyAttendanceGetReq;
import com.rjtech.mw.service.reports.MWReportsAttendanceService;
import com.rjtech.reports.constants.ReportsURLConstants;
import com.rjtech.reports.resp.ReportsResp;

@RestController
@RequestMapping(ReportsURLConstants.REPORTS_PARH_URL)
public class MWReportsAttendanceController {

    @Autowired
    private MWReportsAttendanceService mwReportsAttendanceService;

    @RequestMapping(value = ReportsURLConstants.GET_DAILY_RESOURCE_ATTENDANCE_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getDailyResourceAttendanceReport(
            @RequestBody DailyResourceAttendanceGetReq dailyResourceAttendGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsAttendanceService.getDailyResourceAttendanceReport(dailyResourceAttendGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_DAILY_EMP_ATTENDANCE_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getDailyEmpAttendanceReport(
            @RequestBody DailyAttendanceGetReq dailyAttendanceGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsAttendanceService.getDailyEmpAttendanceReport(dailyAttendanceGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_DAILY_PLANT_ATTENDANCE_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getDailyPlantAttendanceReport(
            @RequestBody DailyAttendanceGetReq dailyAttendanceGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsAttendanceService.getDailyPlantAttendanceReport(dailyAttendanceGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_MONTHLY_EMP_ATTENDANCE_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getMonthlyEmpAttendanceReport(
            @RequestBody MonthlyAttendanceGetReq monthlyAttendanceGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsAttendanceService.getMonthlyEmpAttendanceReport(monthlyAttendanceGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_MONTHLY_PLANT_ATTENDANCE_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getMonthlyPlantAttendanceReport(
            @RequestBody MonthlyAttendanceGetReq monthlyAttendanceGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsAttendanceService.getMonthlyPlantAttendanceReport(monthlyAttendanceGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_CREW_WISE_ATTENDANCE_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getCrewWiseAtttendanceReport(
            @RequestBody CrewWiseAttendanceGetReq crewWiseAttendanceGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsAttendanceService.getCrewWiseAtttendanceReport(crewWiseAttendanceGetReq), HttpStatus.OK);
    }

}
