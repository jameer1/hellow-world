package com.rjtech.mw.controller.reports;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.req.UserGetReq;
import com.rjtech.common.utils.ActionCodes;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.mw.service.projlib.MWProjLibMapService;
import com.rjtech.mw.service.reports.MWReportsWorkDairyService;
import com.rjtech.mw.service.user.MWUserService;
import com.rjtech.projectlib.req.ProjGetReq;
import com.rjtech.reports.constants.ReportsURLConstants;
import com.rjtech.reports.resp.ReportsResp;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.user.dto.UserTO;
import com.rjtech.user.req.ClientReq;
import com.rjtech.user.resp.UserResp;
import com.rjtech.workdairy.reports.req.WorkDairyApprStatusGetReq;
import com.rjtech.workdairy.reports.req.WorkDairyDailyGetReq;

@RestController
@RequestMapping(ReportsURLConstants.REPORTS_PARH_URL)
public class MWReportsWorkDairyController {

    @Autowired
    private MWReportsWorkDairyService mwReportsWorkDairyService;

    @Autowired
    private MWProjLibMapService mwProjLibMapService;

    @Autowired
    private MWUserService mwUserService;

    @RequestMapping(value = ReportsURLConstants.GET_WORKDAIRY_DAILY_MANPOWER_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getWorkDairyDailyManpowerReport(
            @RequestBody WorkDairyDailyGetReq workDairyDailyGetReq) {
        ProjGetReq projGetReq = new ProjGetReq();
        projGetReq.setProjIds(workDairyDailyGetReq.getProjIds());
        ReportsResp reportsResp = mwReportsWorkDairyService.getWorkDairyDailyManpowerReport(workDairyDailyGetReq);
        reportsResp.setUserProjMap(mwProjLibMapService.getMultiProjCodeMap(projGetReq).getLabelKeyTOList());
        return new ResponseEntity<ReportsResp>(reportsResp, HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_WORKDAIRY_DAILY_MATERIAL_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getWorkDairyDailyMaterialReport(
            @RequestBody WorkDairyDailyGetReq workDairyDailyGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsWorkDairyService.getWorkDairyDailyMaterialReport(workDairyDailyGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_WORKDAIRY_DAILY_PLANT_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getMonthlyEmpAttendanceReport(
            @RequestBody WorkDairyDailyGetReq workDairyDailyGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsWorkDairyService.getWorkDairyDailyPlantReport(workDairyDailyGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_WORKDAIRY_DAILY_PROGRESS_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getMonthlyPlantAttendanceReport(
            @RequestBody WorkDairyDailyGetReq workDairyDailyGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsWorkDairyService.getWorkDairyDailyProgressReport(workDairyDailyGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_WORKDAIRY_APPROVESTATUS_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getCrewWiseAtttendanceReport(
            @RequestBody WorkDairyApprStatusGetReq workDairyApprStatusGetReq) {
        ProjGetReq projGetReq = new ProjGetReq();
        projGetReq.setProjIds(workDairyApprStatusGetReq.getProjIds());
        ReportsResp reportsResp = mwReportsWorkDairyService.getWorkDairyApprStatusReport(workDairyApprStatusGetReq);
        reportsResp.setUserProjMap(mwProjLibMapService.getMultiProjCodeMap(projGetReq).getLabelKeyTOList());
        reportsResp.setUserEpsMap(mwProjLibMapService.getMultiEPSProjCodeMap(projGetReq).getLabelKeyTOList());
        reportsResp.setUsersMap(getUsersMap());
        return new ResponseEntity<ReportsResp>(reportsResp, HttpStatus.OK);
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

}
