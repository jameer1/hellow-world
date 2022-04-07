package com.rjtech.register.repository.emp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.register.emp.model.EmpEnrollmentDtlEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;

@Repository
public interface EmpEnrollmentRepository extends JpaRepository<EmpEnrollmentDtlEntity, Long> {

    @Query("select distinct erd from EmpRegisterDtlEntity erd left join fetch erd.empProjRigisterEntities ep "
            + " where erd.id =:empId and erd.status = :empStatus order by ep.id desc ")
    public EmpRegisterDtlEntity getEmpEnrollments(@Param("empId") Long empId, @Param("empStatus") Integer empStatus);

}
