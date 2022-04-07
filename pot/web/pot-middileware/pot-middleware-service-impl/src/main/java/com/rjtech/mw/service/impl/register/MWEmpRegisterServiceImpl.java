package com.rjtech.mw.service.impl.register;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.register.MWEmpRegisterService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.dto.NotificationsGetReq;
import com.rjtech.register.emp.req.EmpBankAccDeactivateReq;
import com.rjtech.register.emp.req.EmpBankAccountDetailsSaveReq;
import com.rjtech.register.emp.req.EmpChargeOutRateSaveReq;
import com.rjtech.register.emp.req.EmpContactSaveReeq;
import com.rjtech.register.emp.req.EmpContactsDeactiveReq;
import com.rjtech.register.emp.req.EmpEnrollmentGetReq;
import com.rjtech.register.emp.req.EmpLeaveAttendanceSaveReq;
import com.rjtech.register.emp.req.EmpLeaveReq;
import com.rjtech.register.emp.req.EmpLeaveReqApprSaveReq;
import com.rjtech.register.emp.req.EmpMedicalHistorySaveReq;
import com.rjtech.register.emp.req.EmpNokDeactiveReq;
import com.rjtech.register.emp.req.EmpNokSaveReq;
import com.rjtech.register.emp.req.EmpPayDeductionSaveReq;
import com.rjtech.register.emp.req.EmpPayRatesSaveReq;
import com.rjtech.register.emp.req.EmpProvidentFundSaveReq;
import com.rjtech.register.emp.req.EmpRegDeactivateReq;
import com.rjtech.register.emp.req.EmpRegisterGetReq;
import com.rjtech.register.emp.req.EmpRegisterReq;
import com.rjtech.register.emp.req.EmpRegisterSaveReq;
import com.rjtech.register.emp.req.EmpTransReq;
import com.rjtech.register.emp.req.EmpTransSaveReq;
import com.rjtech.register.emp.req.ManPowerGenderStatisticsReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.req.ProjEmpRegistersSaveReq;
import com.rjtech.register.emp.req.ProjEmpServiceHistoryReq;
import com.rjtech.register.emp.resp.EmpBankAccountDtlResp;
import com.rjtech.register.emp.resp.EmpChargeOutRateResp;
import com.rjtech.register.emp.resp.EmpContactDetailsResp;
import com.rjtech.register.emp.resp.EmpEnrollmentResp;
import com.rjtech.register.emp.resp.EmpLeaveAttendanceYearResp;
import com.rjtech.register.emp.resp.EmpLeaveReqApprResp;
import com.rjtech.register.emp.resp.EmpMedicalHistoryResp;
import com.rjtech.register.emp.resp.EmpNokResp;
import com.rjtech.register.emp.resp.EmpNotificationsResp;
import com.rjtech.register.emp.resp.EmpPayDeductionResp;
import com.rjtech.register.emp.resp.EmpPaybleRateResp;
import com.rjtech.register.emp.resp.EmpProvidentFundResp;
import com.rjtech.register.emp.resp.EmpRegResp;
import com.rjtech.register.emp.resp.EmpRegisterOnLoadResp;
import com.rjtech.register.emp.resp.EmpRegisterResp;
import com.rjtech.register.emp.resp.EmpReqTransOnLoadResp;
import com.rjtech.register.emp.resp.EmpServiceHistoryResp;
import com.rjtech.register.emp.resp.EmpTransResp;
import com.rjtech.register.emp.resp.EmpUniqueCodeMapRep;
import com.rjtech.register.emp.resp.ProjEmpRegLabelKeyTOMapResp;
import com.rjtech.register.emp.resp.ProjEmpRegLabelKeyTOResp;
import com.rjtech.register.emp.resp.ProjEmpRegisterOnLoadResp;
import com.rjtech.register.manpower.reports.dto.ManPowerGenderStatistics;
import com.rjtech.register.manpower.reports.dto.ManPowerMobilizationStatistics;
import com.rjtech.register.plant.req.PlantRegisterGetReq;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.register.emp.req.EmpQualificationRecordsSaveReq;
import com.rjtech.register.emp.resp.EmpQualificationRecordsResp;
import com.rjtech.register.emp.dto.MasterEmployeeDetailsTO;
import com.rjtech.register.emp.req.ManpowerEmployeeDetailsGetReq;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.io.IOException;
import com.rjtech.constants.ApplicationConstants;

@Service(value = "mwRegisterService")
@RJSService(modulecode = "mwRegisterService")
@Transactional
public class MWEmpRegisterServiceImpl extends RestConfigServiceImpl implements MWEmpRegisterService {

    public EmpRegisterOnLoadResp saveEmpRegisters(EmpRegisterSaveReq empRegisterSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empRegisterSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_EMP_REGISTERS);
        return AppUtils.fromJson(strResponse.getBody(), EmpRegisterOnLoadResp.class);
    }

    public EmpRegisterResp empRegistersDeactivate(EmpRegDeactivateReq empRegDeactivateReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empRegDeactivateReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.EMP_REGISTERS_DEACTIVATE);
        return AppUtils.fromJson(strResponse.getBody(), EmpRegisterResp.class);
    }

    public EmpRegisterOnLoadResp empRegistersOnLoad(EmpRegisterGetReq empRegisterGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empRegisterGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.EMP_REGISTERS_ONLOAD);
        return AppUtils.fromJson(strResponse.getBody(), EmpRegisterOnLoadResp.class);
    }

    public EmpServiceHistoryResp getEmpServiceHistory(ProjEmpServiceHistoryReq projEmpServiceHistoryReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(projEmpServiceHistoryReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PROJ_EMP_SERVICE_HISTORY_REGISTERS);
        return AppUtils.fromJson(strResponse.getBody(), EmpServiceHistoryResp.class);
    }

    public EmpServiceHistoryResp getEmpLatestServiceHistory(ProjEmpServiceHistoryReq projEmpServiceHistoryReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(projEmpServiceHistoryReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PROJ_EMP_LATEST_SERVICE_HISTORY);
        return AppUtils.fromJson(strResponse.getBody(), EmpServiceHistoryResp.class);
    }

    public EmpServiceHistoryResp saveEmpServiceHistory(ProjEmpRegistersSaveReq projEmpRegistersSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(projEmpRegistersSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_PROJ_EMP_SERVICE_HISTORY_REGISTERS);
        return AppUtils.fromJson(strResponse.getBody(), EmpServiceHistoryResp.class);
    }

    public EmpBankAccountDtlResp getEmpBanlAccountDetails(ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(projEmpRegisterGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_EMP_BANK_ACCOUNT_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), EmpBankAccountDtlResp.class);
    }

    public EmpBankAccountDtlResp deactivateEmpBanlAccountDetails(EmpBankAccDeactivateReq empBankAccDeactivateReq) {

        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empBankAccDeactivateReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.DEACTIVATE_EMP_BANK_ACCOUNTS);
        return AppUtils.fromJson(strResponse.getBody(), EmpBankAccountDtlResp.class);
    }

    public EmpBankAccountDtlResp saveEmpBanlAccountDetails(EmpBankAccountDetailsSaveReq empBankAccountDetailsSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empBankAccountDetailsSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_EMP_BANK_ACCOUNT_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), EmpBankAccountDtlResp.class);
    }

    public EmpContactDetailsResp getEmpContactDetails(ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(projEmpRegisterGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_EMP_CONTACT_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), EmpContactDetailsResp.class);
    }

    public EmpContactDetailsResp deactivateEmpContactDetails(EmpContactsDeactiveReq empContactsDeactiveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empContactsDeactiveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.DEACTIVATE_EMP_CONTACTS_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), EmpContactDetailsResp.class);
    }

    public EmpContactDetailsResp saveEmpContactDetails(EmpContactSaveReeq empContactSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empContactSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_EMP_CONTACT_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), EmpContactDetailsResp.class);
    }

    public EmpChargeOutRateResp getEmpChargeOutRates(ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(projEmpRegisterGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_EMP_CHARGEOUT_RATES);
        return AppUtils.fromJson(strResponse.getBody(), EmpChargeOutRateResp.class);
    }

    public EmpChargeOutRateResp saveEmpChargeOutRates(EmpChargeOutRateSaveReq empChargeOutRateSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empChargeOutRateSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_EMP_CHARGEOUT_RATES);
        return AppUtils.fromJson(strResponse.getBody(), EmpChargeOutRateResp.class);
    }

    public ProjEmpRegLabelKeyTOResp findNonAttendenceEmpRegisters(ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(projEmpRegisterGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_NONATTENDENCE_EMP_REGISTERS);
        return AppUtils.fromJson(strResponse.getBody(), ProjEmpRegLabelKeyTOResp.class);
    }

    public EmpNokResp getEmpNok(ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(projEmpRegisterGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_EMP_NOK);
        return AppUtils.fromJson(strResponse.getBody(), EmpNokResp.class);
    }

    public EmpNokResp deactivateEmpNok(EmpNokDeactiveReq empNokDeactiveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empNokDeactiveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.DEACTIVATE_EMP_NOK);
        return AppUtils.fromJson(strResponse.getBody(), EmpNokResp.class);
    }

    public EmpNokResp saveEmpNok(EmpNokSaveReq empNokSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empNokSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_EMP_NOK);
        return AppUtils.fromJson(strResponse.getBody(), EmpNokResp.class);

    }

    public ProjEmpRegLabelKeyTOMapResp getAttendenceEmpRegisters(ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(projEmpRegisterGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_ATTENDENCE_EMP_REGISTERS);
        return AppUtils.fromJson(strResponse.getBody(), ProjEmpRegLabelKeyTOMapResp.class);
    }

    public EmpLeaveAttendanceYearResp getEmpLeaveAttendanceDetails(ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(projEmpRegisterGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_EMP_LEAVE_ATTENDENCE_DTLS);
        return AppUtils.fromJson(strResponse.getBody(), EmpLeaveAttendanceYearResp.class);
    }

    public EmpLeaveAttendanceYearResp saveEmpLeaveAttendanceDetails(
            EmpLeaveAttendanceSaveReq empLeaveAttendanceSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empLeaveAttendanceSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_EMP_LEAVE_ATTENDENCE_DTLS);
        return AppUtils.fromJson(strResponse.getBody(), EmpLeaveAttendanceYearResp.class);
    }

    public EmpMedicalHistoryResp getEmpMedicalHistory(ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(projEmpRegisterGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_EMP_MEDICAL_HISTORY);
        System.out.println("getEmpMedicalHistory function from MWEmpRegisterServiceImpl class");
        System.out.println(strResponse);
        return AppUtils.fromJson(strResponse.getBody(), EmpMedicalHistoryResp.class);
    }

    public EmpPaybleRateResp getEmpRegularPaybleRates(EmpRegisterReq empRegisterReq) {
        ResponseEntity<String> strResponse = null;
        //System.out.println("==middlelayer====serviceimpl=getEmpRegularPaybleRates");
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empRegisterReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_EMP_REGULAR_PAYBLE_RATES);
        return AppUtils.fromJson(strResponse.getBody(), EmpPaybleRateResp.class);
    }

    public EmpPaybleRateResp getEmpRegularPaybleRateDetails(EmpRegisterReq empRegisterReq) {
        ResponseEntity<String> strResponse = null;
        //System.out.println("==middleware===serviceimpl==getEmpRegularPaybleRateDetails");
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empRegisterReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_EMP_REGULAR_PAYBLE_RATE_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), EmpPaybleRateResp.class);
    }

    public EmpPaybleRateResp getEmpNonRegularPaybleRates(ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(projEmpRegisterGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_EMP_NON_REGULAR_PAYBLE_RATES);
        return AppUtils.fromJson(strResponse.getBody(), EmpPaybleRateResp.class);
    }

    public EmpPaybleRateResp getEmpNonRegularPaybleRateDetails(EmpRegisterReq empRegisterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empRegisterReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_EMP_NON_REGULAR_PAYBLE_RATE_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), EmpPaybleRateResp.class);
    }

    public EmpPayDeductionResp getEmpPayDeductions(ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(projEmpRegisterGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_EMP_PAY_DEDUCTIONS);
        return AppUtils.fromJson(strResponse.getBody(), EmpPayDeductionResp.class);
    }

    public EmpPayDeductionResp getEmpPayDeductionDetails(EmpRegisterReq empRegisterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empRegisterReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_EMP_PAY_DEDUCTION_DTLS);
        return AppUtils.fromJson(strResponse.getBody(), EmpPayDeductionResp.class);
    }

    public EmpTransResp getEmpTransfers(EmpTransReq empTransReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empTransReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_EMP_TRANSFERS);
        return AppUtils.fromJson(strResponse.getBody(), EmpTransResp.class);
    }

    public EmpTransResp getEmpTranferReqDetails(EmpTransReq empTransReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empTransReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_EMP_TRANSFER_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), EmpTransResp.class);
    }

    public LabelKeyTOResp getEmpTransferReqDetails(EmpTransReq empTransReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empTransReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_EMP_TRANSFER_REQUEST_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOResp.class);
    }

    public EmpTransResp saveEmpTransfers(EmpTransSaveReq empTransSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empTransSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_EMP_TRANSFERS);
        return AppUtils.fromJson(strResponse.getBody(), EmpTransResp.class);
    }

    public EmpReqTransOnLoadResp empReqTransOnLoad(ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(projEmpRegisterGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.EMP_REQ_TRANS_ONLOAD);
        return AppUtils.fromJson(strResponse.getBody(), EmpReqTransOnLoadResp.class);
    }

    public ProjEmpRegisterOnLoadResp projEmpRegisterOnLoad(ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(projEmpRegisterGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.PROJ_EMP_REGISTERS_ONLOAD);
        return AppUtils.fromJson(strResponse.getBody(), ProjEmpRegisterOnLoadResp.class);
    }
    
    public EmpMedicalHistoryResp saveEmpMedicalHistory( MultipartFile[] files, EmpMedicalHistorySaveReq empMedicalHistorySaveReq ) {
    	System.out.print("saveEmpMedicalHistory function of MWEmpRegisterServiceImpl");
    	System.out.println(empMedicalHistorySaveReq);
        ResponseEntity<String> strResponse = null;
        strResponse = constructPOSTRestTemplateWithMultipartFiles( getRegisterExchangeUrl(
                ( RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_EMP_MEDICAL_HISTORY ) ), files,
                AppUtils.toJson( empMedicalHistorySaveReq ), "empMedicalHistoryStr" );
        System.out.println(strResponse);
        return AppUtils.fromJson( strResponse.getBody(), EmpMedicalHistoryResp.class );
    }

    public EmpPaybleRateResp saveEmpRegularPaybleRates(EmpPayRatesSaveReq empPayRatesSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empPayRatesSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_EMP_REGULAR_PAYBLE_RATES);
        return AppUtils.fromJson(strResponse.getBody(), EmpPaybleRateResp.class);
    }

    public EmpPaybleRateResp saveEmpNonRegularPaybleRates(EmpPayRatesSaveReq empPayRatesSaveReq) {

        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empPayRatesSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_EMP_NON_REGULAR_PAYBLE_RATES);
        return AppUtils.fromJson(strResponse.getBody(), EmpPaybleRateResp.class);
    }

    public EmpPayDeductionResp saveEmpPayDeductions(EmpPayDeductionSaveReq empPayDeductionSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empPayDeductionSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_EMP_PAY_DEDUCTIONS);
        return AppUtils.fromJson(strResponse.getBody(), EmpPayDeductionResp.class);
    }

    public EmpProvidentFundResp saveEmpProvidentFunds(EmpProvidentFundSaveReq empProvidentFundSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empProvidentFundSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_EMP_PROVIDENT_FUNDS);
        return AppUtils.fromJson(strResponse.getBody(), EmpProvidentFundResp.class);
    }

    public EmpProvidentFundResp saveEmpProvidentFundDetails(EmpProvidentFundSaveReq empProvidentFundSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empProvidentFundSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_EMP_PROVIDENT_FUNDS);
        return AppUtils.fromJson(strResponse.getBody(), EmpProvidentFundResp.class);
    }

    public EmpEnrollmentResp saveEmpEnrollments(MultipartFile file, String empEnrollmentSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getRegisterExchangeUrl(
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_EMP_ENROLLMENTS);
        strResponse = constructPOSTRestTemplateWithMultipart(url, file, empEnrollmentSaveReq);
        return AppUtils.fromJson(strResponse.getBody(), EmpEnrollmentResp.class);
    }

    public EmpEnrollmentResp getEmpEnrollments(EmpEnrollmentGetReq empEnrollmentGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empEnrollmentGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_EMP_ENROLLMENTS);
        return AppUtils.fromJson(strResponse.getBody(), EmpEnrollmentResp.class);
    }

    public EmpRegisterOnLoadResp getEmpsNotInUserProjects(EmpRegisterGetReq empRegisterGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empRegisterGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_EMPS_NOT_IN_USER_PROJECTS);
        return AppUtils.fromJson(strResponse.getBody(), EmpRegisterOnLoadResp.class);
    }

    public EmpNotificationsResp getEmpNotifications(NotificationsGetReq notificationsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(notificationsGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_EMP_REGISTERS);
        return AppUtils.fromJson(strResponse.getBody(), EmpNotificationsResp.class);
    }

    public EmpProvidentFundResp getEmpProvidentFunds(ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(projEmpRegisterGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_EMP_PROVIDENT_FUNDS);
        return AppUtils.fromJson(strResponse.getBody(), EmpProvidentFundResp.class);
    }

    public EmpProvidentFundResp getEmpProvidentFundDetails(EmpRegisterReq empRegisterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empRegisterReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_EMP_PROVIDENT_FUND_DTLS);
        return AppUtils.fromJson(strResponse.getBody(), EmpProvidentFundResp.class);
    }

    public EmpPayDeductionResp saveEmpPayDeductions(EmpPayRatesSaveReq empPayRatesSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empPayRatesSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_EMP_PAY_DEDUCTIONS);
        return AppUtils.fromJson(strResponse.getBody(), EmpPayDeductionResp.class);
    }

    public EmpLeaveReqApprResp getEmpLeaveReqApprovals(EmpLeaveReq empLeaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empLeaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_EMP_LEAVE_REQ_APPROVALS);
        return AppUtils.fromJson(strResponse.getBody(), EmpLeaveReqApprResp.class);
    }

    public EmpLeaveReqApprResp getEmpLeaveReqApprovalDetails(EmpLeaveReq empLeaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empLeaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_EMP_LEAVE_REQ_APPROVAL_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), EmpLeaveReqApprResp.class);
    }

    public EmpLeaveReqApprResp saveEmpLeaveReqApprovals(EmpLeaveReqApprSaveReq empLeaveReqApprSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empLeaveReqApprSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_EMP_LEAVE_REQ_APPROVALS);
        return AppUtils.fromJson(strResponse.getBody(), EmpLeaveReqApprResp.class);
    }

    public ProjEmpRegLabelKeyTOMapResp getMultiProjEmpListMap(PlantRegisterGetReq plantRegisterGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(plantRegisterGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_MULTI_PROJ_EMP_LIST_MAP);
        return AppUtils.fromJson(strResponse.getBody(), ProjEmpRegLabelKeyTOMapResp.class);
    }

    public LabelKeyTOResp getProjSettingsEmpLeaveCheck(EmpLeaveReq empLeaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empLeaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PROJ_SETTINGS_EMP_LEAVE_CHECK);
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOResp.class);
    }

    public EmpRegResp getAllRegEmp(EmpRegisterGetReq empRegisterGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empRegisterGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_ALL_REG_EMP);
        return AppUtils.fromJson(strResponse.getBody(), EmpRegResp.class);
    }

    public EmpUniqueCodeMapRep isEmpCodeUnique(EmpRegisterGetReq empRegisterGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(empRegisterGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.IS_EMPCODE_UNIQUE);
        return AppUtils.fromJson(strResponse.getBody(), EmpUniqueCodeMapRep.class);
    }

    public ResponseEntity<ByteArrayResource> downloadEnrollmentContract(Long enrollId) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("enrollId", enrollId);

        ResponseEntity<String[]> response = null;
        String url = AppUtils.getUrl(
                getRegisterExchangeUrl(
                        RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.DOWNLOAD_ENROLLMENT_CONTRACT),
                paramsMap);
        response = constructGETRestTemplate(url, String[].class);
        System.out.println("downloadEnrollmentContract function from MWEmpRegisterServiceImpl");
        String[] file_info = response.getBody();
        for(int j=0; j<file_info.length;j++)
        {
        	System.out.println( j+"->"+file_info[j] );
        }
        String type = file_info[0];
        String fileName = file_info[1];
        String file_path = ApplicationConstants.FILE_DIRECTORY+"/"+file_info[3]+"/"+fileName;
        byte[] fileBytes = null;
        System.out.println("file path:"+file_path);
        try
        {
        	Path path = Paths.get(file_path);
    	    fileBytes = Files.readAllBytes(path);
            System.out.println("response body from downloadProjectDocs function of MWFixedAssetsServiceImpl file:");
        }
        catch(IOException e)
        {
        	e.printStackTrace();
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(type))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(new ByteArrayResource(fileBytes));
    }

    @Override
    public EmpRegisterResp getProjEmployees(Long projId) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("projId", projId);
        String url = AppUtils.getUrl(getRegisterExchangeUrl(
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PROJ_EMPLOYEES), paramsMap);
        ResponseEntity<EmpRegisterResp> resp = constructGETRestTemplate(url, EmpRegisterResp.class);
        return resp.getBody();
    }

	@Override
	public List<ManPowerGenderStatistics> getManpowerGenderStatisticsReport(
			ManPowerGenderStatisticsReq manPowerGenderStatisticsReq) {
		ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(manPowerGenderStatisticsReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_MANPOWER_GENDER_STATISTICS_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), List.class);
	}

	public List<ManPowerMobilizationStatistics> getManpowerPeriodicalMobilisationReport(
			ManPowerGenderStatisticsReq manpowerMobilisationGetReq) {
		ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(manpowerMobilisationGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_MANPOWER_PERIODICAL_MOBILISATION_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), List.class);
	}
	
	public EmpRegisterResp saveEmployeeDocs( MultipartFile[] files, EmpRegisterReq empRegisterReq ) {        
    	ResponseEntity<String> strResponse = null;
        System.out.println("saveEmployeeDocs function of MWEmpRegisterServiceImpl");
        System.out.println(empRegisterReq);
        strResponse = constructPOSTRestTemplateWithMultipartFiles(getRegisterExchangeUrl(
                (RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_EMPLOYEE_DOCS)), files,
                AppUtils.toJson(empRegisterReq), "employeeDocsStr");
        System.out.println(strResponse);
        return AppUtils.fromJson(strResponse.getBody(), EmpRegisterResp.class);
    }
	
	public EmpRegisterResp getEmployeeDocs( EmpRegisterReq empRegisterReq ) {        
		ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate( AppUtils.toJson( empRegisterReq ),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_EMPLOYEE_DOCS );
        System.out.println("getEmployeeDocs function from MWEmpRegisterServiceImpl class");
        System.out.println(strResponse);
        return AppUtils.fromJson(strResponse.getBody(), EmpRegisterResp.class);
    }
	
	public EmpQualificationRecordsResp getEmpQualificationRecords( ProjEmpRegisterGetReq projEmpRegisterGetReq ) {
		ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate( AppUtils.toJson( projEmpRegisterGetReq ),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_EMP_QUALIFICATION_RECORDS );
        System.out.println("getEmpQualificationRecords function from MWEmpRegisterServiceImpl class");
        System.out.println(strResponse);
        return AppUtils.fromJson( strResponse.getBody(), EmpQualificationRecordsResp.class );
	}
	
	public EmpQualificationRecordsResp saveEmpQualificationRecords( MultipartFile[] files, EmpQualificationRecordsSaveReq empQualificationRecordsSaveReq ) {
		ResponseEntity<String> strResponse = null;
        String url = getRegisterExchangeUrl(
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_EMP_QUALIFICATION_RECORDS );
        strResponse = constructPOSTRestTemplateWithMultipartFiles( url, files, AppUtils.toJson( empQualificationRecordsSaveReq ), "empQualRecordsStr" );
        return AppUtils.fromJson( strResponse.getBody(), EmpQualificationRecordsResp.class );
	}
	
	 @Override   
	public List<MasterEmployeeDetailsTO> getManpowerEmployeeDetail(ManpowerEmployeeDetailsGetReq manpowerEmployeeDetailsGetReq){
		ResponseEntity<String> strResponse = null;		
		strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(manpowerEmployeeDetailsGetReq),
				RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_MANPOWER_EMPLOYEE_DETAILS);
			return AppUtils.fromJson(strResponse.getBody(), List.class);
		}	
}
