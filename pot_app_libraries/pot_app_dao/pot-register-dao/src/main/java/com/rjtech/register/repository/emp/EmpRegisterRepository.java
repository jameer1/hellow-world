package com.rjtech.register.repository.emp;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface EmpRegisterRepository extends RegisterBaseRepository<EmpRegisterDtlEntity, Long> {

    List<EmpRegisterDtlEntity> findByCodeAndClientId(@Param("code") String code,
            @Param("clientId") ClientRegEntity clientId);

    @Modifying
    @Query("UPDATE EmpRegisterDtlEntity ERD SET ERD.status=:status  where ERD.id in :empRegIds ")
    void deactivateEmpRegisters(@Param("empRegIds") List<Long> empRegIds, @Param("status") Integer status);

    @Query("SELECT erd FROM EmpRegisterDtlEntity erd WHERE erd.projMstrEntity.projectId=:projId and erd.status=:status")
    public List<EmpRegisterDtlEntity> findEmployeesByProject(@Param("projId") Long projId,
            @Param("status") Integer status);

    @Query("SELECT  erd FROM EmpRegisterDtlEntity erd join fetch erd.empProjRigisterEntities eprd "
            + " WHERE eprd.projMstrEntity.projectId in  :projIds  and erd.status=:status and erd.id =eprd.empRegisterDtlEntity.id  and  eprd.isLatest ='Y' ")
    public List<EmpRegisterDtlEntity> findLatestEmployeesByProject(@Param("projIds") List<Long> projIds,
            @Param("status") Integer status);

    @Query("SELECT erd FROM EmpRegisterDtlEntity erd WHERE erd.clientId.clientId=:clientId and erd.status=:status and erd.projMstrEntity is null")
    public List<EmpRegisterDtlEntity> findEmployeesByClient(@Param("clientId") Long clientId,
            @Param("status") Integer status);

    @Query("SELECT erd FROM EmpRegisterDtlEntity erd WHERE erd.clientId.clientId=:clientId and erd.projMstrEntity.projectId not in :projIds and erd.projMstrEntity is not null and erd.status=:status")
    public List<EmpRegisterDtlEntity> findEmpsNotInUserProjects(@Param("clientId") Long clientId,
            @Param("projIds") List<Long> projIds, @Param("status") Integer status);

    @Query("SELECT erd FROM EmpRegisterDtlEntity erd left join fetch erd.empEnrollmentDtlEntities enroll "
            + " WHERE erd.clientId.clientId=:clientId and (erd.projMstrEntity.projectId in (:projList) or erd.projMstrEntity is null ) and erd.status=:status "
            + "and (enroll.isLatest ='Y' or enroll is null) order by erd.id desc")

    public List<EmpRegisterDtlEntity> findEmployeesByClientProject(@Param("clientId") Long clientId,
            @Param("projList") List<Long> projList, @Param("status") Integer status);

    @Query("SELECT erd FROM EmpRegisterDtlEntity erd WHERE erd.projMstrEntity.projectId IN :projIds and erd.status=:status")
    public List<EmpRegisterDtlEntity> findMultiProjEmpDetails(@Param("projIds") List<Long> projIds,
            @Param("status") Integer status);

    @Query("SELECT erd FROM EmpRegisterDtlEntity erd WHERE erd.clientId.clientId=:clientId and erd.status=:status")
    public List<EmpRegisterDtlEntity> findAllRegEmployees(@Param("clientId") Long clientId,
            @Param("status") Integer status);

    @Query("SELECT DISTINCT erd from EmpRegisterDtlEntity erd "
            + "join EmpProjRigisterEntity per on per.empRegisterDtlEntity.id = erd.id and per.projMstrEntity.projectId = :projId "
            + "left join EmpTransReqApprDetailEntity etrad on etrad.empRegisterDtlEntity.id = erd.id "
            + "left join EmpTransferReqApprEntity etra on etrad.empTransferReqApprEntity.id = etra.id "
            + " and etrad.empTransferReqApprEntity.apprStatus is not null and etrad.empTransferReqApprEntity.apprStatus != 'Approved' "
            + "where erd.clientId.clientId = :clientId and per.isLatest = 'Y' and per.demobilizationStatus = 'On Transfer' "
            + " and ( etra.apprStatus is null or lower(etra.apprStatus) = 'approved' or lower(etra.apprStatus) = 'rejected' )")
    public List<EmpRegisterDtlEntity> findNewRequestTransferEmployees(@Param("clientId") Long clientId,
            @Param("projId") Long projId);

    @Query("SELECT erd FROM EmpRegisterDtlEntity erd JOIN erd.empProjRigisterEntities per WHERE erd.projMstrEntity.projectId IN :projIds "
            + "AND erd.companyMstrEntity.id IN :cmpIds AND per.isLatest='Y' AND per.mobilaizationDate <= :date AND "
            + "erd.empClassMstrEntity.id IN :empClassIds")
    public List<EmpRegisterDtlEntity> manpowerGenderStatisticsReport(@Param("projIds") List<Long> projIds,
            @Param("cmpIds") List<Long> cmpIds, @Param("date") Date date, @Param("empClassIds") List<Long> empClassIds);

    @Query("SELECT erd FROM EmpRegisterDtlEntity erd JOIN erd.empProjRigisterEntities per WHERE erd.projMstrEntity.projectId IN :projIds "
            + "AND erd.companyMstrEntity.id IN :cmpIds AND per.isLatest='Y' AND per.mobilaizationDate <= :date AND (per.deMobilaizationDate >= :date OR per.deMobilaizationDate IS NULL)")
    public List<EmpRegisterDtlEntity> manpowerGenderStatisticsReport(@Param("projIds") List<Long> projIds,
            @Param("cmpIds") List<Long> cmpId, @Param("date") Date date);

    @Query("SELECT erd FROM EmpRegisterDtlEntity erd JOIN erd.empProjRigisterEntities per WHERE erd.projMstrEntity.projectId IN :projIds "
            + "AND erd.companyMstrEntity.id IN :cmpIds AND per.isLatest='Y' AND "
            + "(per.mobilaizationDate <= :toDate OR per.deMobilaizationDate BETWEEN :fromDate AND :toDate)")
    public List<EmpRegisterDtlEntity> manpowerPeriodicalMobilizationStatistics(@Param("projIds") List<Long> projIds,
            @Param("cmpIds") List<Long> cmpIds, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender in :genderType and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus in :currStatus and T.enrollmentDate between :fromDate and :toDate")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithEnrollDate(@Param("projIds") List<Long> projIds, @Param("genderType") List<String> genderType, @Param("empTypes") String empTypes, @Param("currStatus") List<String> currStatus, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
 
   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender in :genderType and per.procureCatgDtlEntity.name =:empTypes and per.companyMstrEntity.id in :cmpIds and T.demobilizationStatus in :currStatus and T.enrollmentDate between :fromDate and :toDate")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithMale(@Param("projIds") List<Long> projIds, @Param("genderType") List<String> genderType, @Param("empTypes") String empTypes, @Param("cmpIds")List<Long> cmpIds, @Param("currStatus") List<String> currStatus, @Param("fromDate")Date fromDate, @Param("toDate")Date toDate);
    
    @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender =:genderType and per.companyMstrEntity.id IN :cmpIds and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus =:currStatus")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithCmpny1(@Param("projIds") List<Long> projIds, @Param("genderType") String genderType, @Param("cmpIds") List<Long> cmpIds, @Param("empTypes") String empTypes, @Param("currStatus") String currStatus);
 
    @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender in :genderType and per.companyMstrEntity.id IN :cmpIds and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus in :currStatus")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithCmpny(@Param("projIds") List<Long> projIds, @Param("genderType") List<String> genderType, @Param("cmpIds") List<Long> cmpIds, @Param("empTypes") String empTypes, @Param("currStatus") List <String> currStatus);
 
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender in :genderType and per.companyMstrEntity.id IN :cmpIds and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus =:currStatus")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithGenderType(@Param("projIds") List<Long> projIds, @Param("genderType") List <String> genderType, @Param("cmpIds") List<Long> cmpIds, @Param("empTypes") String empTypes, @Param("currStatus") String currStatus);	
	   
	   
	   
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender =:genderType and per.companyMstrEntity.id IN :cmpIds and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus in :currStat")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithCurrntStatus(@Param("projIds") List<Long> projIds, @Param("genderType") String genderType, @Param("cmpIds") List<Long> cmpIds, @Param("empTypes") String empTypes, @Param("currStat") List <String> currStat);	   	   
	   
	   
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender in :genderType and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus in :currStat")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithOutEnrollDate(@Param("projIds") List<Long> projIds, @Param("genderType") List<String> genderType, @Param("empTypes") String empTypes, @Param("currStat") List<String> currStat);	   	   
	   
	   
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender =:genderType and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus =:currStat")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithOutcurrStatus(@Param("projIds") List<Long> projIds, @Param("genderType") String genderType, @Param("empTypes") String empTypes, @Param("currStat") String currStat);	   	   
	   
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender in:genderType and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus =:currStat")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithOutgenderTypeNull(@Param("projIds") List<Long> projIds, @Param("genderType")List<String> genderType, @Param("empTypes") String empTypes, @Param("currStat") String currStat);
	   
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender =:genderType and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus in:currStat")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithOutCurrStatusNull(@Param("projIds") List<Long> projIds, @Param("genderType") String genderType, @Param("empTypes") String empTypes, @Param("currStat") List<String> currStat);
	   
	   
	   
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender =:genderType and per.companyMstrEntity.id IN :cmpIds and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus =:currStat and T.enrollmentDate between :fromDate and :toDate")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithCompGenderTypeCurrStatusNull(@Param("projIds") List<Long> projIds, @Param("genderType") String genderType, @Param("cmpIds") List<Long> cmpIds, @Param("empTypes") String empTypes, @Param("currStat") String currStat, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
	   
	   
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender in:genderType and per.companyMstrEntity.id IN :cmpIds and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus =:currStat and T.enrollmentDate between :fromDate and :toDate")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithCompGenderTypeNull(@Param("projIds") List<Long> projIds, @Param("genderType")List <String> genderType, @Param("cmpIds") List<Long> cmpIds, @Param("empTypes") String empTypes, @Param("currStat") String currStat, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
	   
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender =:genderType and per.companyMstrEntity.id IN :cmpIds and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus in:currStat and T.enrollmentDate between :fromDate and :toDate")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithCompCurrStatusNull(@Param("projIds") List<Long> projIds, @Param("genderType")String genderType, @Param("cmpIds") List<Long> cmpIds, @Param("empTypes") String empTypes, @Param("currStat")List<String> currStat, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
	   
	   
	   
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender =:genderType and per.companyMstrEntity.id IN :cmpIds and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus =:currStat and T.mobilaizationDate between :expecMobfromDate and :expecMobToDate")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithCompExcepMobDate(@Param("projIds") List<Long> projIds, @Param("genderType") String genderType, @Param("cmpIds") List<Long> cmpIds, @Param("empTypes") String empTypes, @Param("currStat") String currStat, @Param("expecMobfromDate") Date expecMobfromDate, @Param("expecMobToDate") Date expecMobToDate);
	   
	   
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender in:genderType and per.companyMstrEntity.id IN :cmpIds and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus =:currStat and T.mobilaizationDate between :expecMobfromDate and :expecMobToDate")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithCompExcepMobDateGenderTypeNull(@Param("projIds") List<Long> projIds, @Param("genderType")List <String> genderType, @Param("cmpIds") List<Long> cmpIds, @Param("empTypes") String empTypes, @Param("currStat") String currStat, @Param("expecMobfromDate") Date expecMobfromDate, @Param("expecMobToDate") Date expecMobToDate);
	   
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender =:genderType and per.companyMstrEntity.id IN :cmpIds and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus in:currStat and T.mobilaizationDate between :expecMobfromDate and :expecMobToDate")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithCompExcepMobDateCurrStatusNull(@Param("projIds") List<Long> projIds, @Param("genderType")String genderType, @Param("cmpIds") List<Long> cmpIds, @Param("empTypes") String empTypes, @Param("currStat")List<String> currStat, @Param("expecMobfromDate") Date expecMobfromDate, @Param("expecMobToDate") Date expecMobToDate);
	   
	   
	   
	   
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender =:genderType and per.companyMstrEntity.id IN :cmpIds and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus =:currStat and T.deMobilaizationDate between :actualDeMobfromDate and :actualDeMobToDate")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithCompActualMobDate(@Param("projIds") List<Long> projIds, @Param("genderType") String genderType, @Param("cmpIds") List<Long> cmpIds, @Param("empTypes") String empTypes, @Param("currStat") String currStat, @Param("actualDeMobfromDate") Date actualDeMobfromDate, @Param("actualDeMobToDate") Date actualDeMobToDate);
	   
	   
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender in:genderType and per.companyMstrEntity.id IN :cmpIds and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus =:currStat and T.deMobilaizationDate between :actualDeMobfromDate and :actualDeMobToDate")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithCompActualMobDateGenderTypeNull(@Param("projIds") List<Long> projIds, @Param("genderType")List <String> genderType, @Param("cmpIds") List<Long> cmpIds, @Param("empTypes") String empTypes, @Param("currStat") String currStat, @Param("actualDeMobfromDate") Date actualDeMobfromDate, @Param("actualDeMobToDate") Date actualDeMobToDate);
	   
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender =:genderType and per.companyMstrEntity.id IN :cmpIds and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus in:currStat and T.deMobilaizationDate between :actualDeMobfromDate and :actualDeMobToDate")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithCompActualMobDateCurrStatusNull(@Param("projIds") List<Long> projIds, @Param("genderType")String genderType, @Param("cmpIds") List<Long> cmpIds, @Param("empTypes") String empTypes, @Param("currStat")List<String> currStat, @Param("actualDeMobfromDate") Date actualDeMobfromDate, @Param("actualDeMobToDate") Date actualDeMobToDate);
	   
	   
	   
	   
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender =:genderType and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus =:currStat and T.deMobilaizationDate between :actualDeMobfromDate and :actualDeMobToDate")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithOutCmpActual(@Param("projIds") List<Long> projIds, @Param("genderType")String genderType, @Param("empTypes") String empTypes, @Param("currStat") String currStat, @Param("actualDeMobfromDate") Date actualDeMobfromDate, @Param("actualDeMobToDate") Date actualDeMobToDate);
	   
	   
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender in:genderType and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus =:currStat and T.deMobilaizationDate between :actualDeMobfromDate and :actualDeMobToDate")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithOutCmpActualgenderTypeNull(@Param("projIds") List<Long> projIds, @Param("genderType")List <String> genderType, @Param("empTypes") String empTypes, @Param("currStat") String currStat, @Param("actualDeMobfromDate") Date actualDeMobfromDate, @Param("actualDeMobToDate") Date actualDeMobToDate);
	   
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender =:genderType and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus in:currStat and T.deMobilaizationDate between :actualDeMobfromDate and :actualDeMobToDate")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithOutCmpActualCurrStatusNull(@Param("projIds") List<Long> projIds, @Param("genderType")String genderType, @Param("empTypes") String empTypes, @Param("currStat")List<String> currStat, @Param("actualDeMobfromDate") Date actualDeMobfromDate, @Param("actualDeMobToDate") Date actualDeMobToDate);
	   
	   
	   
	   
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender =:genderType and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus =:currStat and T.mobilaizationDate between :expecMobfromDate and :expecMobToDate")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithOutCmpExcepted(@Param("projIds") List<Long> projIds, @Param("genderType")String genderType, @Param("empTypes") String empTypes, @Param("currStat") String currStat, @Param("expecMobfromDate") Date expecMobfromDate, @Param("expecMobToDate") Date expecMobToDate);
	   
	   
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender in:genderType and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus =:currStat and T.mobilaizationDate between :expecMobfromDate and :expecMobToDate")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithOutCmpExceptedgenderTypeNull(@Param("projIds") List<Long> projIds, @Param("genderType")List <String> genderType, @Param("empTypes") String empTypes, @Param("currStat") String currStat, @Param("expecMobfromDate") Date expecMobfromDate, @Param("expecMobToDate") Date expecMobToDate);
	   
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender =:genderType and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus in:currStat and T.mobilaizationDate between :expecMobfromDate and :expecMobToDate")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithOutCmpExceptedCurrStatusNull(@Param("projIds") List<Long> projIds, @Param("genderType")String genderType, @Param("empTypes") String empTypes, @Param("currStat")List<String> currStat, @Param("expecMobfromDate") Date expecMobfromDate, @Param("expecMobToDate") Date expecMobToDate);
  
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.companyMstrEntity.id IN :cmpIds and per.gender =:genderType and per.procureCatgDtlEntity.name =:empTypes and T.demobilizationStatus =:currStat and T.enrollmentDate between :fromDate and :toDate and T.mobilaizationDate between :expecMobfromDate and :expecMobToDate and T.deMobilaizationDate between :actualDeMobfromDate and :actualDeMobToDate")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithCompDtls(@Param("projIds") List<Long> projIds, @Param("genderType")String genderType, @Param("cmpIds") List<Long> cmpIds, @Param("empTypes") String empTypes, @Param("currStat") String currStat, @Param("fromDate")Date fromDate, @Param("toDate")Date toDate, @Param("expecMobfromDate")Date expecMobfromDate, @Param("expecMobToDate")Date expecMobToDate, @Param("actualDeMobfromDate")Date actualDeMobfromDate, @Param("actualDeMobToDate")Date actualDeMobToDate);
	   
	   
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.companyMstrEntity.id IN :cmpIds and per.gender in:genderType and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus =:currStat and T.enrollmentDate between :fromDate and :toDate and T.mobilaizationDate between :expecMobfromDate and :expecMobToDate and T.deMobilaizationDate between :actualDeMobfromDate and :actualDeMobToDate")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithCompDtlsGenderTypeNull(@Param("projIds") List<Long> projIds, @Param("genderType")List<String> genderType, @Param("cmpIds") List<Long> cmpIds, @Param("empTypes") String empTypes, @Param("currStat") String currStat, @Param("fromDate")Date fromDate, @Param("toDate")Date toDate, @Param("expecMobfromDate")Date expecMobfromDate, @Param("expecMobToDate")Date expecMobToDate, @Param("actualDeMobfromDate")Date actualDeMobfromDate, @Param("actualDeMobToDate")Date actualDeMobToDate);
	   
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.companyMstrEntity.id IN :cmpIds and per.gender =:genderType and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus in :currStat and T.enrollmentDate between :fromDate and :toDate and T.mobilaizationDate between :expecMobfromDate and :expecMobToDate and T.deMobilaizationDate between :actualDeMobfromDate and :actualDeMobToDate")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithCompDtlsCurrStatusNull(@Param("projIds") List<Long> projIds, @Param("genderType")String genderType, @Param("cmpIds")List<Long> cmpIds, @Param("empTypes") String empTypes, @Param("currStat")List<String> currStat, @Param("fromDate")Date fromDate, @Param("toDate")Date toDate, @Param("expecMobfromDate")Date expecMobfromDate, @Param("expecMobToDate")Date expecMobToDate, @Param("actualDeMobfromDate")Date actualDeMobfromDate, @Param("actualDeMobToDate")Date actualDeMobToDate);
	   
	   
	   
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender =:genderType and per.procureCatgDtlEntity.name =:empTypes and T.demobilizationStatus =:currStat and T.enrollmentDate between :fromDate and :toDate and T.mobilaizationDate between :expecMobfromDate and :expecMobToDate and T.deMobilaizationDate between :actualDeMobfromDate and :actualDeMobToDate")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithOutCompDtls(@Param("projIds") List<Long> projIds, @Param("genderType")String genderType, @Param("empTypes") String empTypes, @Param("currStat") String currStat, @Param("fromDate")Date fromDate, @Param("toDate")Date toDate, @Param("expecMobfromDate")Date expecMobfromDate, @Param("expecMobToDate")Date expecMobToDate, @Param("actualDeMobfromDate")Date actualDeMobfromDate, @Param("actualDeMobToDate")Date actualDeMobToDate);
	   
	   
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender in:genderType and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus =:currStat and T.enrollmentDate between :fromDate and :toDate and T.mobilaizationDate between :expecMobfromDate and :expecMobToDate and T.deMobilaizationDate between :actualDeMobfromDate and :actualDeMobToDate")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithOutCompDtlsGenderTypeNull(@Param("projIds") List<Long> projIds, @Param("genderType")List<String> genderType, @Param("empTypes") String empTypes, @Param("currStat") String currStat, @Param("fromDate")Date fromDate, @Param("toDate")Date toDate, @Param("expecMobfromDate")Date expecMobfromDate, @Param("expecMobToDate")Date expecMobToDate, @Param("actualDeMobfromDate")Date actualDeMobfromDate, @Param("actualDeMobToDate")Date actualDeMobToDate);
	   
	   @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender =:genderType and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus in :currStat and T.enrollmentDate between :fromDate and :toDate and T.mobilaizationDate between :expecMobfromDate and :expecMobToDate and T.deMobilaizationDate between :actualDeMobfromDate and :actualDeMobToDate")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithOutCompDtlsCurrStatusNull(@Param("projIds") List<Long> projIds, @Param("genderType")String genderType, @Param("empTypes") String empTypes, @Param("currStat")List<String> currStat, @Param("fromDate")Date fromDate, @Param("toDate")Date toDate, @Param("expecMobfromDate")Date expecMobfromDate, @Param("expecMobToDate")Date expecMobToDate, @Param("actualDeMobfromDate")Date actualDeMobfromDate, @Param("actualDeMobToDate")Date actualDeMobToDate);

	   
 @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and T.isLatest='Y' and per.gender =:genderType and per.procureCatgDtlEntity.name =:empTypes and per.companyMstrEntity.id IN :cmpIds and T.demobilizationStatus =:currStatus and T.enrollmentDate between :fromDate and :toDate")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtlswithCompany(@Param("projIds") List<Long> projIds, @Param("genderType") String genderType, @Param("empTypes") String empTypes, @Param("cmpIds") List<Long> cmpIds, @Param("currStatus") String currStatus, @Param("fromDate")Date fromDate, @Param("toDate")Date toDate );
    //if no one is null
    @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and per.companyMstrEntity.id IN :cmpIds and T.isLatest='Y' and per.gender = :genderType and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus = :currStatus and T.enrollmentDate between :fromDate and :toDate and T.mobilaizationDate between :expecMobfromDate and :expecMobToDate and T.deMobilaizationDate between :actualDeMobfromDate and :actualDeMobToDate ")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtls(@Param("projIds") List<Long> projIds, @Param("cmpIds") List<Long> cmpIds, @Param("genderType") String genderType, @Param("empTypes") String empTypes, @Param("currStatus") String currStatus, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("expecMobfromDate") Date expecMobfromDate, @Param("expecMobToDate") Date expecMobToDate, @Param("actualDeMobfromDate") Date actualDeMobfromDate, @Param("actualDeMobToDate") Date actualDeMobToDate);
    //if gender is null
    @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and per.companyMstrEntity.id IN :cmpIds and T.isLatest='Y' and per.gender in :genderType and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus = :currStatus and T.enrollmentDate between :fromDate and :toDate and T.mobilaizationDate between :expecMobfromDate and :expecMobToDate and T.deMobilaizationDate between :actualDeMobfromDate and :actualDeMobToDate ")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtls(@Param("projIds") List<Long> projIds, @Param("cmpIds") List<Long> cmpIds, @Param("genderType") List<String> genderType, @Param("empTypes") String empTypes, @Param("currStatus") String currStatus, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("expecMobfromDate") Date expecMobfromDate, @Param("expecMobToDate") Date expecMobToDate, @Param("actualDeMobfromDate") Date actualDeMobfromDate, @Param("actualDeMobToDate") Date actualDeMobToDate);
    // if current status is null
    @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and per.companyMstrEntity.id IN :cmpIds and T.isLatest='Y' and per.gender = :genderType and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus in :currStatus and T.enrollmentDate between :fromDate and :toDate and T.mobilaizationDate between :expecMobfromDate and :expecMobToDate and T.deMobilaizationDate between :actualDeMobfromDate and :actualDeMobToDate ")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtls(@Param("projIds") List<Long> projIds, @Param("cmpIds") List<Long> cmpIds, @Param("genderType") String genderType, @Param("empTypes") String empTypes, @Param("currStatus") List<String> currStatus, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("expecMobfromDate") Date expecMobfromDate, @Param("expecMobToDate") Date expecMobToDate, @Param("actualDeMobfromDate") Date actualDeMobfromDate, @Param("actualDeMobToDate") Date actualDeMobToDate);
    //if gender is null and current status is null
    @Query("SELECT per FROM EmpRegisterDtlEntity per join fetch per.empProjRigisterEntities T where per.projMstrEntity.projectId in :projIds and per.companyMstrEntity.id IN :cmpIds and T.isLatest='Y' and per.gender in :genderType and per.procureCatgDtlEntity.name= :empTypes and T.demobilizationStatus in :currStatus and T.enrollmentDate between :fromDate and :toDate and T.mobilaizationDate between :expecMobfromDate and :expecMobToDate and T.deMobilaizationDate between :actualDeMobfromDate and :actualDeMobToDate ")
	   public List<EmpRegisterDtlEntity> findEmpMasterDtls(@Param("projIds") List<Long> projIds, @Param("cmpIds") List<Long> cmpIds, @Param("genderType") List<String> genderType, @Param("empTypes") String empTypes, @Param("currStatus") List<String> currStatus, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("expecMobfromDate") Date expecMobfromDate, @Param("expecMobToDate") Date expecMobToDate, @Param("actualDeMobfromDate") Date actualDeMobfromDate, @Param("actualDeMobToDate") Date actualDeMobToDate);   
}
