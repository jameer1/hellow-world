package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;

import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projsettings.model.ProjManpowerEntity;

public interface ProjManpowerRepository extends ProjSettingsBaseRepository<ProjManpowerEntity, Long> {

    @Query("SELECT MPD FROM com.rjtech.projsettings.model.ProjManpowerEntity MPD  WHERE MPD.projMstrEntity.projectId=:projId AND MPD.status=:status ")
    public List<ProjManpowerEntity> findManpowersByProject(@Param("projId") Long projId,
            @Param("status") Integer status);
	
	@Query("SELECT MPD FROM com.rjtech.projsettings.model.ProjManpowerEntity MPD  WHERE MPD.projMstrEntity.projectId=:projId AND MPD.status NOT IN (2,3) ")
    public List<ProjManpowerEntity> findManpowersByProject(@Param("projId") Long projId);

    @Query("SELECT WDMW.workDairyEmpDtlEntity.empRegId.empClassMstrEntity.id, SUM(WDMW.usedTotal + WDMW.idleTotal) FROM WorkDairyEmpWageEntity WDMW "
            + " WHERE WDMW.workDairyEmpDtlEntity.workDairyEntity.projId = :projId AND "
            + " ((WDMW.apprStatus = 'Approved' AND WDMW.workDairyEmpDtlEntity.workDairyEntity.clientApproval = 0) "
            + " OR (WDMW.apprStatus = 'Client Approved' and WDMW.workDairyEmpDtlEntity.workDairyEntity.clientApproval=1)) "
            + " GROUP BY WDMW.workDairyEmpDtlEntity.empRegId.empClassMstrEntity.id ")
    public List<Object[]> getManpowerWorkDiaryActualHrs(@Param("projId") ProjMstrEntity projId);

    @Query("SELECT TSED.empRegisterDtlEntity.empClassMstrEntity.id, "
            + "SUM(COALESCE(TSWD.day1, 0) + COALESCE(TSWD.day2, 0) + COALESCE(TSWD.day3, 0) + "
            + "COALESCE(TSWD.day4, 0) + COALESCE(TSWD.day5, 0) + COALESCE(TSWD.day6, 0) + COALESCE(TSWD.day7, 0)) "
            + "FROM TimeSheetEmpWorkDtlEntity TSWD JOIN TSWD.timeSheetEmpDtlEntity TSED JOIN TSED.timeSheetEntity TSM "
            + "WHERE TSM.projMstrEntity = :projId AND TSWD.apprStatus = 'Approved' "
            + "GROUP BY TSED.empRegisterDtlEntity.empClassMstrEntity.id")
    public List<Object[]> getManpowerTimesheetActualHrs(@Param("projId") ProjMstrEntity projId);
    
    @Query("SELECT MPD FROM ProjManpowerEntity MPD  WHERE MPD.empClassMstrEntity.id=:projId ")
    public List<ProjManpowerEntity> findbyIds(@Param("projId") Long projId);

    @Query("SELECT ECM FROM EmpClassMstrEntity ECM  WHERE " + "ECM.clientId=:clientId AND ECM.status=:status "
            + "AND ECM NOT IN "
            + "(SELECT MPD.empClassMstrEntity FROM ProjManpowerEntity MPD  WHERE MPD.projMstrEntity=:projId "
            + "AND MPD.status=:status)" + "ORDER BY ECM.code")
    public List<EmpClassMstrEntity> getEmpClasses(@Param("clientId") ClientRegEntity clientId,
            @Param("projId") ProjMstrEntity projId, @Param("status") Integer status);
    
    @Query("SELECT MPD FROM ProjManpowerEntity MPD WHERE MPD.projMstrEntity.projectId=:projId AND MPD.empClassMstrEntity.id=:resId ")
    public ProjManpowerEntity findBy(@Param("projId") Long projId, @Param("resId") Long resId);

    @Modifying
    @Query("UPDATE ProjManpowerEntity MPD SET MPD.itemStatus=:itemStatus,MPD.status=:status where MPD.id in :manpowerIds")
    public void updateApprovalStatusByIds( @Param("manpowerIds") List<Long> manpowerIds, @Param("itemStatus") String itemStatus, @Param("status") Integer status );
    
    @Modifying
    @Query("UPDATE ProjManpowerEntity MPD SET MPD.itemStatus=:itemStatus,MPD.originatorUserId.userId=:originatorUserId,MPD.approverUserId.userId=:approverUserId where MPD.id in :manpowerIds")
    public void updateApproverDetailsByIds( @Param("manpowerIds") List<Long> manpowerIds, @Param("approverUserId") Long approverUserId, @Param("originatorUserId") Long originatorUserId, @Param("itemStatus") String itemStatus );
    
    @Modifying
    @Query("UPDATE ProjManpowerEntity MPD SET MPD.itemStatus=:itemStatus,MPD.returnedByUserId.userId=:returnedByUserId,MPD.isItemReturned=:isItemReturned,MPD.returnedComments=:comments WHERE MPD.id=:manpowerId")
    public void updateReturnItemDetailsById( @Param("manpowerId") Long manpowerId, @Param("returnedByUserId") Long returnedByUserId , @Param("isItemReturned") Integer isItemReturned, @Param("comments") String comments, @Param("itemStatus") String itemStatus );
    
    @Modifying
    @Query("UPDATE ProjManpowerEntity MPD SET MPD.itemStatus=:itemStatus WHERE MPD.id=:manpowerId")
    public void updateManpowerItemStatusById( @Param("manpowerId") Long manpowerId, @Param("itemStatus") String itemStatus );  
    
    @Query("SELECT MPD FROM ProjManpowerEntity MPD  WHERE MPD.empClassMstrEntity.id=:projId ")
    public List<ProjManpowerEntity> findIds(@Param("projId") Long projId  );
    
    
}
