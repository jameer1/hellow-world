package com.rjtech.timemanagement.attendence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//import com.rjtech.register.timemanagement.attendance.model.EmpAttendanceDtlEntityCopy;
import com.rjtech.timemanagement.attendance.model.EmpAttendanceDtlEntity;

@Repository
public interface EmpAttendanceDtlRepositoryCopy extends JpaRepository<EmpAttendanceDtlEntity, Long> {

    @Query("select extract(year from pead.attendanceDate), pead.projAttdCode, count(pead) "
            + " from EmpAttendanceDtlEntity pead where pead.empAttendanceEntity.empId.id = :empId "
            + " and extract(year from pead.attendanceDate) in (:years) "
            + " group by extract(year from pead.attendanceDate), pead.projAttdCode"
            + " order by extract(year from pead.attendanceDate)")
    List<Object[]> getEmpLeaveAttendanceYearWise(@Param("empId") long empId, @Param("years") List<Integer> years);

    @Query("select count(pead) from EmpAttendanceDtlEntity pead where pead.empAttendanceEntity.empId.id = :empId"
            + " and pead.attendanceDate between to_date(:startDate, 'DD-MON-YYYY') and to_date(:endDate, 'DD-MON-YYYY')")
    int getEmpAttendanceCount(@Param("empId") long empId, @Param("startDate") String startDate,
            @Param("endDate") String endDate);

}
