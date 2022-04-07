package com.rjtech.timemanagement.attendence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
//import com.rjtech.projectlib.model.ProjCrewMstrEntityCopy;
import com.rjtech.timemanagement.attendance.model.PlantAttendanceEntity;

@Repository
public interface PlantAttendanceRepository extends JpaRepository<PlantAttendanceEntity, Long> {

    @Query("SELECT DISTINCT T FROM PlantAttendanceEntity T LEFT JOIN FETCH T.plantAttendanceDtlEntities TD WHERE T.plantAttendanceMstrEntity.id=:attendanceId "
            + " AND T.plantAttendanceMstrEntity.projMstrEntity.projectId=:projId AND  T.status=:status")
    List<PlantAttendanceEntity> findAttendanceRecords(@Param("attendanceId") Long attendanceId,
            @Param("projId") Long projId, @Param("status") Integer status);

    @Query("SELECT DISTINCT T FROM PlantAttendanceEntity T JOIN FETCH T.plantAttendanceDtlEntities TD "
            + " WHERE T.plantAttendanceMstrEntity.projMstrEntity.projectId=:projId AND "
            + " T.plantAttendanceMstrEntity.projCrewMstrEntity.id!=:currenCrewId AND  T.status=:status")
    List<PlantAttendanceEntity> findOtherCrewAttendanceRecords(@Param("projId") Long projId,
            @Param("currenCrewId") Long currenCrewId, @Param("status") Integer status);

    @Query("SELECT plant FROM PlantAttendanceEntity plant JOIN plant.plantAttendanceDtlEntities plantAttndDtl "
            + "WHERE plant.projCrewMstrEntity = :crewId AND plant.plantRegisterDtlEntity.projMstrEntity = :projId "
            + "AND plant.plantRegisterDtlEntity.clientId = :clientRegEntity "
            + "AND trunc(plantAttndDtl.attendanceDate) = trunc(:attendanceDate) AND plantAttndDtl.projAttdCode ='W'")
    List<PlantAttendanceEntity> getPlantsByAttendanceDate(@Param("projId") ProjMstrEntity projId,
            @Param("crewId") ProjCrewMstrEntity crewId, @Param("clientRegEntity") ClientRegEntity clientRegEntity,
            @Param("attendanceDate") Date attendanceDate);

    @Query("SELECT DISTINCT T FROM PlantAttendanceEntity T LEFT JOIN FETCH T.plantAttendanceDtlEntities TD "
            + " WHERE T.plantAttendanceMstrEntity.status = 1 AND T.status = 1 AND "
            + " LOWER(to_char(T.plantAttendanceMstrEntity.attendanceMonth,'MON-YYYY')) = LOWER(:attendanceMonth) AND "
            + " T.plantAttendanceMstrEntity.projCrewMstrEntity.id IN (:crewIds) AND T.plantAttendanceMstrEntity.projMstrEntity.projectId IN (:projIds) ")
    List<PlantAttendanceEntity> findAttendanceRecords(@Param("projIds") List<Long> projIds,
            @Param("crewIds") List<Long> crewIds, @Param("attendanceMonth") String attendanceMonth);

    @Query("SELECT DISTINCT T FROM PlantAttendanceEntity T LEFT JOIN FETCH T.plantAttendanceDtlEntities TD "
            + " WHERE T.plantAttendanceMstrEntity.status = 1 AND T.status = 1 AND "
            + " T.plantAttendanceMstrEntity.attendanceMonth between :fromMonth and :toMonth AND "
            + " T.plantAttendanceMstrEntity.projCrewMstrEntity.id IN (:crewIds) AND T.plantAttendanceMstrEntity.projMstrEntity.projectId IN (:projIds) ")
    List<PlantAttendanceEntity> findAttendanceRecordsBtwnDates(@Param("projIds") List<Long> projIds,
            @Param("crewIds") List<Long> crewIds, @Param("fromMonth") Date fromMonth, @Param("toMonth") Date toMonth);

    @Query("SELECT DISTINCT T FROM PlantAttendanceEntity T LEFT JOIN FETCH T.plantAttendanceDtlEntities TD "
            + " WHERE T.plantAttendanceMstrEntity.status = 1 AND T.status = 1 AND "
            + " LOWER(to_char(TD.attendanceDate, 'DD-MON-YYYY')) = LOWER(:attendenceDate) AND "
            + " T.plantAttendanceMstrEntity.projMstrEntity.projectId IN (:projIds)")
    List<PlantAttendanceEntity> findDailyResourceStatus(@Param("projIds") List<Long> projIds, @Param("attendenceDate") String attendenceDate);

}
