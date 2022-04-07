package com.rjtech.register.repository.emp;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.emp.model.EmpLeaveAttendanceYearEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface EmpLeaveAttendanceRepository extends RegisterBaseRepository<EmpLeaveAttendanceYearEntity, Long> {

    @Query("SELECT T FROM EmpLeaveAttendanceYearEntity T WHERE T.empRegId.id = :empRegId AND T.status=:status ORDER BY T.year DESC")
    public List<EmpLeaveAttendanceYearEntity> findEmpLeaveAttenanceDetails(@Param("empRegId") Long empRegId,
            @Param("status") Integer status);

}
