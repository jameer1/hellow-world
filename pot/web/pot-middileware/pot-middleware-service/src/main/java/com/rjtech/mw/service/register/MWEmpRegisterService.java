package com.rjtech.mw.service.register;

import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.resp.LabelKeyTOResp;
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
import com.rjtech.register.emp.req.EmpQualificationRecordsSaveReq;
import com.rjtech.register.emp.resp.EmpQualificationRecordsResp;
import com.rjtech.register.emp.dto.MasterEmployeeDetailsTO;
import com.rjtech.register.emp.req.ManpowerEmployeeDetailsGetReq;

public interface MWEmpRegisterService {

    EmpRegisterOnLoadResp saveEmpRegisters(EmpRegisterSaveReq empRegisterSaveReq);

    EmpRegisterResp empRegistersDeactivate(EmpRegDeactivateReq empRegDeactivateReq);

    EmpRegisterOnLoadResp empRegistersOnLoad(EmpRegisterGetReq empRegisterGetReq);

    EmpServiceHistoryResp getEmpServiceHistory(ProjEmpServiceHistoryReq projEmpServiceHistoryReq);

    EmpServiceHistoryResp getEmpLatestServiceHistory(ProjEmpServiceHistoryReq projEmpServiceHistoryReq);

    EmpServiceHistoryResp saveEmpServiceHistory(ProjEmpRegistersSaveReq projEmpRegistersSaveReq);

    EmpBankAccountDtlResp getEmpBanlAccountDetails(ProjEmpRegisterGetReq projEmpRegisterGetReq);

    EmpBankAccountDtlResp saveEmpBanlAccountDetails(EmpBankAccountDetailsSaveReq empBankAccountDetailsSaveReq);

    EmpContactDetailsResp getEmpContactDetails(ProjEmpRegisterGetReq projEmpRegisterGetReq);

    EmpContactDetailsResp saveEmpContactDetails(EmpContactSaveReeq empContactSaveReq);

    EmpChargeOutRateResp getEmpChargeOutRates(ProjEmpRegisterGetReq projEmpRegisterGetReq);

    EmpChargeOutRateResp saveEmpChargeOutRates(EmpChargeOutRateSaveReq empChargeOutRateSaveReq);

    ProjEmpRegLabelKeyTOResp findNonAttendenceEmpRegisters(ProjEmpRegisterGetReq projEmpRegisterGetReq);

    EmpNokResp getEmpNok(ProjEmpRegisterGetReq projEmpRegisterGetReq);

    EmpNokResp saveEmpNok(EmpNokSaveReq empNokSaveReq);

    ProjEmpRegLabelKeyTOMapResp getAttendenceEmpRegisters(ProjEmpRegisterGetReq projEmpRegisterGetReq);

    EmpBankAccountDtlResp deactivateEmpBanlAccountDetails(EmpBankAccDeactivateReq empBankAccDeactivateReq);

    EmpContactDetailsResp deactivateEmpContactDetails(EmpContactsDeactiveReq empContactsDeactiveReq);

    EmpNokResp deactivateEmpNok(EmpNokDeactiveReq empNokDeactiveReq);

    EmpLeaveAttendanceYearResp getEmpLeaveAttendanceDetails(ProjEmpRegisterGetReq projEmpRegisterGetReq);

    EmpLeaveAttendanceYearResp saveEmpLeaveAttendanceDetails(EmpLeaveAttendanceSaveReq empLeaveAttendanceSaveReq);

    EmpProvidentFundResp getEmpProvidentFunds(ProjEmpRegisterGetReq projEmpRegisterGetReq);

    EmpProvidentFundResp getEmpProvidentFundDetails(EmpRegisterReq empRegisterReq);

    EmpMedicalHistoryResp getEmpMedicalHistory(ProjEmpRegisterGetReq projEmpRegisterGetReq);

    EmpPaybleRateResp getEmpRegularPaybleRates(EmpRegisterReq empRegisterReq);

    EmpPaybleRateResp getEmpRegularPaybleRateDetails(EmpRegisterReq empRegisterReq);

    EmpPaybleRateResp getEmpNonRegularPaybleRates(ProjEmpRegisterGetReq projEmpRegisterGetReq);

    EmpPaybleRateResp getEmpNonRegularPaybleRateDetails(EmpRegisterReq empRegisterReq);

    EmpPayDeductionResp getEmpPayDeductions(ProjEmpRegisterGetReq projEmpRegisterGetReq);

    EmpPayDeductionResp getEmpPayDeductionDetails(EmpRegisterReq empRegisterReq);

    EmpPayDeductionResp saveEmpPayDeductions(EmpPayDeductionSaveReq empPayDeductionSaveReq);

    EmpTransResp getEmpTransfers(EmpTransReq empTransReq);

    EmpTransResp getEmpTranferReqDetails(EmpTransReq empTransReq);

    LabelKeyTOResp getEmpTransferReqDetails(EmpTransReq empTransReq);

    EmpTransResp saveEmpTransfers(EmpTransSaveReq empTransSaveReq);

    EmpReqTransOnLoadResp empReqTransOnLoad(ProjEmpRegisterGetReq projEmpRegisterGetReq);

    ProjEmpRegisterOnLoadResp projEmpRegisterOnLoad(ProjEmpRegisterGetReq projEmpRegisterGetReq);

    EmpMedicalHistoryResp saveEmpMedicalHistory( MultipartFile[] files, EmpMedicalHistorySaveReq empMedicalHistorySaveReq );

    EmpPaybleRateResp saveEmpNonRegularPaybleRates(EmpPayRatesSaveReq empPayRatesSaveReq);

    EmpPaybleRateResp saveEmpRegularPaybleRates(EmpPayRatesSaveReq empPayRatesSaveReq);

    EmpEnrollmentResp getEmpEnrollments(EmpEnrollmentGetReq empEnrollmentGetReq);

    EmpEnrollmentResp saveEmpEnrollments(MultipartFile file, String empEnrollmentSaveReq);

    EmpProvidentFundResp saveEmpProvidentFunds(EmpProvidentFundSaveReq empProvidentFundSaveReq);

    EmpRegisterOnLoadResp getEmpsNotInUserProjects(EmpRegisterGetReq empRegisterGetReq);

    EmpNotificationsResp getEmpNotifications(NotificationsGetReq notificationsGetReq);

    EmpLeaveReqApprResp getEmpLeaveReqApprovals(EmpLeaveReq empLeaveReq);

    EmpLeaveReqApprResp getEmpLeaveReqApprovalDetails(EmpLeaveReq empLeaveReq);

    EmpLeaveReqApprResp saveEmpLeaveReqApprovals(EmpLeaveReqApprSaveReq empLeaveReqApprSaveReq);

    ProjEmpRegLabelKeyTOMapResp getMultiProjEmpListMap(PlantRegisterGetReq plantRegisterGetReq);

    public LabelKeyTOResp getProjSettingsEmpLeaveCheck(EmpLeaveReq empLeaveReq);

    EmpRegResp getAllRegEmp(EmpRegisterGetReq empRegisterGetReq);

    EmpUniqueCodeMapRep isEmpCodeUnique(EmpRegisterGetReq empRegisterGetReq);

    ResponseEntity<ByteArrayResource> downloadEnrollmentContract(Long enrollId);

    EmpRegisterResp getProjEmployees(Long projId);
    
    List<ManPowerGenderStatistics> getManpowerGenderStatisticsReport(ManPowerGenderStatisticsReq manPowerGenderStatisticsReq);

    List<ManPowerMobilizationStatistics> getManpowerPeriodicalMobilisationReport(ManPowerGenderStatisticsReq manpowerMobilisationGetReq);
    
    EmpRegisterResp saveEmployeeDocs( MultipartFile[] files, EmpRegisterReq empRegisterReq );
    
    EmpRegisterResp getEmployeeDocs( EmpRegisterReq empRegisterReq );
    
    EmpQualificationRecordsResp getEmpQualificationRecords( ProjEmpRegisterGetReq projEmpRegisterGetReq );

    EmpQualificationRecordsResp saveEmpQualificationRecords( MultipartFile[] files, EmpQualificationRecordsSaveReq empQualificationRecordsSaveReq );
    
    List<MasterEmployeeDetailsTO> getManpowerEmployeeDetail(ManpowerEmployeeDetailsGetReq manpowerEmployeeDetailsGetReq);    
}
