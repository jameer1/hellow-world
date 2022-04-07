package com.rjtech.timemanagement.attendence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.timemanagement.attendance.model.EmpAttendanceMstrEntity;

@Repository
public interface EmpAttendanceMstrRepository extends JpaRepository<EmpAttendanceMstrEntity, Long> {

    @Query("SELECT T FROM EmpAttendanceMstrEntity T  WHERE T.projId.projectId=:projId AND T.crewId.id=:crewId "
            + " AND LOWER(to_char(T.attendenceMonth,'MON-YYYY')) = LOWER(:attendenceMonth) AND  T.status=:status")
    List<EmpAttendanceMstrEntity> findAttendance(@Param("projId") Long projId, @Param("crewId") Long crewId,
            @Param("attendenceMonth") String attendenceMonth, @Param("status") Integer status);
    
    @Query("SELECT T FROM EmpAttendanceMstrEntity T  WHERE "
            + "T.attendenceMonth >= :fromAttendenceMonth "
    		+ "AND T.attendenceMonth <= :toAttendenceMonth "
            + "AND T.createdBy.userId = :createdBy AND T.projId.clientId.clientId=:crmId")
    List<EmpAttendanceMstrEntity> findAll(@Param("createdBy") Long createdBy, @Param("fromAttendenceMonth") Date fromAttendenceMonth, 
    		@Param("toAttendenceMonth") Date toAttendenceMonth,@Param("crmId") Long crmId);
    
    @Query("SELECT T FROM EmpAttendanceMstrEntity T  WHERE "
            + "T.attendenceMonth >= :fromAttendenceMonth "
    		+ "AND T.attendenceMonth <= :toAttendenceMonth AND T.projId.clientId.clientId=:crmId")
    List<EmpAttendanceMstrEntity> findAll(@Param("fromAttendenceMonth") Date fromAttendenceMonth, @Param("toAttendenceMonth") Date toAttendenceMonth,@Param("crmId") Long crmId);

    @Query("SELECT T FROM EmpAttendanceMstrEntity T  WHERE "
            + "T.attendenceMonth >= :fromAttendenceMonth "
    		+ "AND T.attendenceMonth <= :toAttendenceMonth "
            + "AND T.createdBy.userId = :createdBy "
    		+ "AND T.projId.projectId IN :projIds AND T.projId.clientId.clientId=:crmId")
    List<EmpAttendanceMstrEntity> findAll(@Param("createdBy") Long createdBy, @Param("fromAttendenceMonth") Date fromAttendenceMonth, 
    		@Param("toAttendenceMonth") Date toAttendenceMonth, @Param("projIds") List<Long> projIds,@Param("crmId") Long crmId);
    
    @Query("SELECT T FROM EmpAttendanceMstrEntity T  WHERE "
            + "T.attendenceMonth >= :fromAttendenceMonth "
    		+ "AND T.attendenceMonth <= :toAttendenceMonth "
            + "AND T.projId.projectId IN :projIds AND T.projId.clientId.clientId=:crmId")
    List<EmpAttendanceMstrEntity> findAll(@Param("fromAttendenceMonth") Date fromAttendenceMonth, @Param("toAttendenceMonth") Date toAttendenceMonth,
    		@Param("projIds") List<Long> projIds,@Param("crmId") Long crmId);
}