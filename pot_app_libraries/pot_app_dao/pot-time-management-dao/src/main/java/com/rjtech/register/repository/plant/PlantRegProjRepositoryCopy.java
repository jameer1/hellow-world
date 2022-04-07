package com.rjtech.register.repository.plant;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.register.plant.model.PlantRegProjEntity;
import com.rjtech.register.plant.model.PlantRegisterDtlEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEntity;
//import com.rjtech.register.plant.model.PlantRegProjEntityCopy;
//import com.rjtech.register.plant.model.PlantRegisterDtlEntityCopy;
//import com.rjtech.timemanagement.workdairy.model.WorkDairyEntity;

public interface PlantRegProjRepositoryCopy extends JpaRepository<PlantRegProjEntity, Long> {

    @Query("SELECT PP.plantRegisterDtlEntity.id, "
            + "PP.plantRegisterDtlEntity.assertId, PP.plantRegisterDtlEntity.desc, "
            + "PP.plantRegisterDtlEntity.regNumber, "
            + "PP.plantRegisterDtlEntity.manfacture, PP.plantRegisterDtlEntity.model, "
            + "PP.plantRegisterDtlEntity.plantClassMstrId.name, "
            + "PP.plantRegisterDtlEntity.plantClassMstrId.measurmentMstrEntity.name, "
            + "PP.plantRegisterDtlEntity.cmpId.name, " + "PP.plantRegisterDtlEntity.procurecatgId "
            + "FROM PlantRegProjEntity PP " + "JOIN PlantAttendanceEntity PA "
            + "ON PA.plantRegisterDtlEntity = PP.plantRegisterDtlEntity " + "JOIN PlantAttendanceDtlEntity PTD "
            + "ON PTD.plantAttendanceEntity = PA " + "WHERE ((PP.mobDate BETWEEN :workDairyDate AND :workDairyDate "
            + "OR PP.deMobDate BETWEEN :workDairyDate AND :workDairyDate) " + "OR PP.mobDate <= :workDairyDate ) "
            + "AND (PP.deMobDate IS NULL OR PP.deMobDate >= :workDairyDate) "
            + "AND PP.assignStatus = 'Y' AND PP.status = 1 " + "AND PP.projId = :projMstrEntity "
            + "AND PA.plantAttendanceMstrEntity.projMstrEntity = :projMstrEntity "
            + "AND PP.plantRegisterDtlEntity.projMstrEntity = :projMstrEntity "
            + "AND PP.plantRegisterDtlEntity.status = 1 " + "AND PTD.attendanceDate = :workDairyDate "
            + "AND PP.plantRegisterDtlEntity.clientId = :clientRegEntity")
    List<Object[]> getPlantsForWorkDairy(@Param("projMstrEntity") ProjMstrEntity projMstrEntity,
            @Param("clientRegEntity") ClientRegEntity clientRegEntity, @Param("workDairyDate") Date workDairyDate);

    @Query("SELECT PP.plantRegisterDtlEntity.id, "
            + "PP.plantRegisterDtlEntity.assertId, PP.plantRegisterDtlEntity.desc, "
            + "PP.plantRegisterDtlEntity.regNumber, "
            + "PP.plantRegisterDtlEntity.manfacture, PP.plantRegisterDtlEntity.model, "
            + "PP.plantRegisterDtlEntity.plantClassMstrId.name, "
            + "PP.plantRegisterDtlEntity.plantClassMstrId.measurmentMstrEntity.name, "
            + "PP.plantRegisterDtlEntity.cmpId.name, " + "PP.plantRegisterDtlEntity.procurecatgId "
            + "FROM PlantRegProjEntity PP " + "JOIN PlantAttendanceEntity PA "
            + "ON PA.plantRegisterDtlEntity = PP.plantRegisterDtlEntity " + "JOIN PlantAttendanceDtlEntity PTD "
            + "ON PTD.plantAttendanceEntity = PA " + "WHERE ((PP.mobDate BETWEEN :workDairyDate AND :workDairyDate "
            + "OR PP.deMobDate BETWEEN :workDairyDate AND :workDairyDate) " + "OR PP.mobDate <= :workDairyDate ) "
            + "AND (PP.deMobDate IS NULL OR PP.deMobDate >= :workDairyDate) "
            + "AND PP.assignStatus = 'Y' AND PP.status = 1 " + "AND PP.projId = :projMstrEntity "
            + "AND PA.plantAttendanceMstrEntity.projMstrEntity = :projMstrEntity "
            + "AND PP.plantRegisterDtlEntity.projMstrEntity = :projMstrEntity "
            + "AND PP.plantRegisterDtlEntity.status = 1 " + "AND PTD.attendanceDate = :workDairyDate "
            + "AND PP.plantRegisterDtlEntity.clientId = :clientRegEntity " + "AND PP.plantRegisterDtlEntity NOT IN "
            + "(SELECT plantRegId FROM " + "WorkDairyPlantDtlEntity WD " + "WHERE WD.workDairyEntity = :workDairy "
            + "AND WD.status = 1)")
    List<Object[]> getPlantsWorkDairyDetails(@Param("projMstrEntity") ProjMstrEntity projMstrEntity,
            @Param("clientRegEntity") ClientRegEntity clientRegEntity, @Param("workDairyDate") Date workDairyDate,
            @Param("workDairy") WorkDairyEntity workDairy);

    @Query("SELECT DISTINCT PPJD.plantRegisterDtlEntity.id, PPJD.plantRegisterDtlEntity.assertId,"
            + " PPJD.plantRegisterDtlEntity.desc, PPJD.plantRegisterDtlEntity.regNumber, "
            + " PPJD.plantRegisterDtlEntity.manfacture, PPJD.plantRegisterDtlEntity.model, "
            + " PPJD.plantRegisterDtlEntity.plantClassMstrId.name, "
            + " PPJD.plantRegisterDtlEntity.procurecatgId.name, PPJD.plantRegisterDtlEntity.cmpId.name, "
            + " PPJD.plantRegisterDtlEntity.plantClassMstrId.measurmentMstrEntity.name "
            + " FROM PlantRegProjEntity PPJD LEFT JOIN PPJD.plantRegisterDtlEntity.procurecatgId "
            + " WHERE PPJD.plantRegisterDtlEntity not in (:plantRegisterDtlEntities) and  "
            + " PPJD.plantRegisterDtlEntity.status = 1 and "
            + " PPJD.plantRegisterDtlEntity.projMstrEntity.projectId = :projId and "
            + " PPJD.plantRegisterDtlEntity.projMstrEntity.projectId = PPJD.projId.projectId and "
            + " ( ( PPJD.mobDate BETWEEN :startDate AND :endDate or PPJD.deMobDate BETWEEN :startDate AND :endDate ) "
            + " or PPJD.mobDate <= :startDate ) and (PPJD.deMobDate is null or PPJD.deMobDate >= :startDate ) "
            + " and PPJD.assignStatus = 'Y' and PPJD.isLatest = 'Y' and PPJD.status = 1 ")
    List<Object[]> findNonAttendancePlantRegDetails(@Param("projId") long projId, @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("plantRegisterDtlEntities") List<PlantRegisterDtlEntity> plantRegisterDtlEntities);

    @Query("SELECT DISTINCT PPJD.plantRegisterDtlEntity.id, PPJD.plantRegisterDtlEntity.assertId,"
            + " PPJD.plantRegisterDtlEntity.desc, PPJD.plantRegisterDtlEntity.regNumber, "
            + " PPJD.plantRegisterDtlEntity.manfacture, PPJD.plantRegisterDtlEntity.model, "
            + " PPJD.plantRegisterDtlEntity.plantClassMstrId.name, "
            + " PPJD.plantRegisterDtlEntity.procurecatgId.name, PPJD.plantRegisterDtlEntity.cmpId.name, "
            + " PPJD.plantRegisterDtlEntity.plantClassMstrId.measurmentMstrEntity.name "
            + " FROM PlantRegProjEntity PPJD LEFT JOIN PPJD.plantRegisterDtlEntity.procurecatgId "
            + " WHERE PPJD.plantRegisterDtlEntity.status = 1 and "
            + " PPJD.plantRegisterDtlEntity.projMstrEntity.projectId = :projId and "
            + " PPJD.plantRegisterDtlEntity.projMstrEntity.projectId = PPJD.projId.projectId and "
            + " ( ( PPJD.mobDate BETWEEN :startDate AND :endDate or PPJD.deMobDate BETWEEN :startDate AND :endDate ) "
            + " or PPJD.mobDate <= :startDate ) and (PPJD.deMobDate is null or PPJD.deMobDate >= :startDate ) "
            + " and PPJD.assignStatus = 'Y' and PPJD.isLatest = 'Y' and PPJD.status = 1 ")
    List<Object[]> findNonAttendancePlantRegDetails(@Param("projId") long projId, @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    @Query("SELECT DISTINCT PPJD FROM PlantAttendanceEntity PPAT "
            + " JOIN PlantRegProjEntity PPJD ON PPJD.plantRegisterDtlEntity = PPAT.plantRegisterDtlEntity "
            + " WHERE PPAT.plantAttendanceMstrEntity.id = :attId and PPJD.mobDate is not null")
    List<PlantRegProjEntity> getEmpProjMobilizationDates(@Param("attId") long attId);

    @Query("SELECT PPJD FROM PlantRegProjEntity PPJD where PPJD.plantRegisterDtlEntity.id in (:plantIds) ")
    List<PlantRegProjEntity> findByPlantId(@Param("plantIds") List<Long> plantIds);

    PlantRegProjEntity findByPlantRegisterDtlEntityAndProjId(PlantRegisterDtlEntity plantRegisterDtlEntity,
            ProjMstrEntity projId);

    @Query("SELECT TR FROM PlantRegProjEntity TR WHERE TR.projId.id in (:projIds) AND TR.plantRegisterDtlEntity.cmpId.id in (:cmpIds) "
            + " AND (TR.deMobDate >= :currentDate OR TR.deMobDate IS NULL)")
    List<PlantRegProjEntity> getCurrentActivePlants(@Param("projIds") List<Long> projIds,
            @Param("cmpIds") List<Long> cmpIds, @Param("currentDate") Date currentDate);

    @Query("SELECT DISTINCT PPJD.plantRegisterDtlEntity.id, PPJD.plantRegisterDtlEntity.assertId,"
            + " PPJD.plantRegisterDtlEntity.desc, PPJD.plantRegisterDtlEntity.regNumber, "
            + " PPJD.plantRegisterDtlEntity.manfacture, PPJD.plantRegisterDtlEntity.model, "
            + " PPJD.plantRegisterDtlEntity.plantClassMstrId.name, "
            + " PPJD.plantRegisterDtlEntity.procurecatgId.name, PPJD.plantRegisterDtlEntity.cmpId.name, "
            + " PPJD.plantRegisterDtlEntity.plantClassMstrId.measurmentMstrEntity.name "
            + " FROM PlantRegProjEntity PPJD LEFT JOIN PPJD.plantRegisterDtlEntity.procurecatgId "
            + " WHERE PPJD.plantRegisterDtlEntity in (:plantRegisterDtlEntities) and  "
            + " PPJD.plantRegisterDtlEntity.status = 1 and "
            + " PPJD.plantRegisterDtlEntity.projMstrEntity.projectId = :projId and "
            + " PPJD.plantRegisterDtlEntity.projMstrEntity.projectId = PPJD.projId.projectId and "
/*            + " ( ( PPJD.mobDate BETWEEN :startDate AND :endDate or PPJD.deMobDate BETWEEN :startDate AND :endDate ) "
            + " or PPJD.mobDate <= :startDate ) and (PPJD.deMobDate is null or PPJD.deMobDate >= :startDate ) " */
            + " (PPJD.deMobDate IS NULL OR PPJD.deMobDate >= :currtDate) "
            + " and PPJD.assignStatus = 'Y' and PPJD.isLatest = 'Y' and PPJD.status = 1 ")
    List<Object[]> findAttendancePlantFor(@Param("projId") long projId,
            @Param("plantRegisterDtlEntities") List<PlantRegisterDtlEntity> plantRegisterDtlEntities, 
            @Param("currtDate") Date currtDate);

}
