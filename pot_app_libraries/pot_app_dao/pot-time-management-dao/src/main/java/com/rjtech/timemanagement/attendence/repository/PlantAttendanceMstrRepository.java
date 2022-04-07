package com.rjtech.timemanagement.attendence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.timemanagement.attendance.model.PlantAttendanceMstrEntity;

@Repository
public interface PlantAttendanceMstrRepository extends JpaRepository<PlantAttendanceMstrEntity, Long> {

    @Query("SELECT T FROM PlantAttendanceMstrEntity T  WHERE T.projMstrEntity.projectId=:projId "
            + " AND T.projCrewMstrEntity.id = :crewId  AND LOWER(TO_CHAR(T.attendanceMonth,'MON-YYYY')) = LOWER(:attendanceMonth) "
            + " AND T.status=:status")
    List<PlantAttendanceMstrEntity> findAttendance(@Param("projId") Long projId, @Param("crewId") Long crewId,
            @Param("attendanceMonth") String attendanceMonth, @Param("status") Integer status);

    @Query("SELECT T FROM PlantAttendanceMstrEntity T  WHERE "
    		+ "T.attendanceMonth >= :fromAttendenceMonth "
    		+ "AND T.attendanceMonth <= :toAttendenceMonth "
            + "AND T.createdBy.userId = :createdBy AND T.projMstrEntity.clientId.clientId=:crmId")
    List<PlantAttendanceMstrEntity> findAll(@Param("createdBy") Long createdBy, @Param("fromAttendenceMonth") Date fromAttendenceMonth, 
    		@Param("toAttendenceMonth") Date toAttendenceMonth, @Param("crmId") Long crmId);

    @Query("SELECT T FROM PlantAttendanceMstrEntity T  WHERE "
            + "T.attendanceMonth >= :fromAttendenceMonth "
    		+ "AND T.attendanceMonth <= :toAttendenceMonth AND T.projMstrEntity.clientId.clientId=:crmId")
    List<PlantAttendanceMstrEntity> findAll(@Param("fromAttendenceMonth") Date fromAttendenceMonth, @Param("toAttendenceMonth") Date toAttendenceMonth,@Param("crmId") Long crmId);

    @Query("SELECT T FROM PlantAttendanceMstrEntity T  WHERE "
            + "T.attendanceMonth >= :fromAttendenceMonth "
    		+ "AND T.attendanceMonth <= :toAttendenceMonth "
            + "AND T.createdBy.userId = :createdBy AND T.projMstrEntity.projectId IN :projIds AND T.projMstrEntity.clientId.clientId=:crmId")
    List<PlantAttendanceMstrEntity> findAll(@Param("createdBy") Long createdBy, @Param("fromAttendenceMonth") Date fromAttendenceMonth, 
    		@Param("toAttendenceMonth") Date toAttendenceMonth, @Param("projIds") List<Long> projIds,@Param("crmId") Long crmId);
    
    @Query("SELECT T FROM PlantAttendanceMstrEntity T  WHERE "
            + "T.attendanceMonth >= :fromAttendenceMonth "
    		+ "AND T.attendanceMonth <= :toAttendenceMonth "
            + "AND T.projMstrEntity.projectId IN :projIds AND T.projMstrEntity.clientId.clientId=:crmId")
    List<PlantAttendanceMstrEntity> findAll(@Param("fromAttendenceMonth") Date fromAttendenceMonth, @Param("toAttendenceMonth") Date toAttendenceMonth,
    		@Param("projIds") List<Long> projIds,@Param("crmId") Long crmId);
}