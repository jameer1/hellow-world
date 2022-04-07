package com.rjtech.notification.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.notification.model.EmpRegisterDtlEntityCopy;

@Repository
public interface NotificationEmpDtlRepository extends JpaRepository<EmpRegisterDtlEntityCopy, Long> {

    @Query("SELECT T FROM EmpRegisterDtlEntityCopy T WHERE T.id=:empId")
    List<EmpRegisterDtlEntityCopy> findEmpName(@Param("empId") Long empId);
}
