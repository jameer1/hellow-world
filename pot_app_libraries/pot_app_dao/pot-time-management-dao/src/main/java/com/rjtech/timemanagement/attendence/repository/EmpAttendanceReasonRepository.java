package com.rjtech.timemanagement.attendence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.timemanagement.attendance.model.EmpAttendanceReasonEntity;

public interface EmpAttendanceReasonRepository extends JpaRepository<EmpAttendanceReasonEntity, Long> {

    @Query("SELECT r FROM EmpAttendanceReasonEntity r  WHERE r.projMstrEntity.projectId =:projId and r.projCrewMstrEntity.id =:crewId "
            + "and r.attendanceId =:attendanceId and r.empId =:empId and attendanceMonth =:attendanceMonth   ")
    EmpAttendanceReasonEntity findAttendanceRecords(@Param("projId") Long projId, @Param("crewId") Long crewId,
            @Param("attendanceId") Long attendanceId, @Param("empId") Long empId,
            @Param("attendanceMonth") String attendanceMonth);

}
