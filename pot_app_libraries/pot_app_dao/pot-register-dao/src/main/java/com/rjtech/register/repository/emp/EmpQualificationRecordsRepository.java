package com.rjtech.register.repository.emp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.register.emp.model.EmpQualificationRecordsEntity;

@Repository
public interface EmpQualificationRecordsRepository extends JpaRepository<EmpQualificationRecordsEntity, Long> {

    @Query("SELECT EQR FROM EmpQualificationRecordsEntity EQR WHERE EQR.empRegisterDtlEntity.id=:empRegId AND EQR.status=:status")
    public List<EmpQualificationRecordsEntity> findEmpQualificationRecords( @Param("empRegId") Long empId, @Param("status") Integer status );
	
	/*@Query("SELECT EQR FROM EmpQualificationRecordsEntity EQR WHERE EQR.empRegisterDtlEntity.id=2006 AND EQR.status=1")
    public EmpQualificationRecordsEntity findEmpQualificationRecords();*/

}
