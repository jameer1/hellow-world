package com.rjtech.timemanagement.workdairy.repository.copy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.timemanagement.workdairy.model.WorkDairyEmpDtlEntity;

//import com.rjtech.workdairy.WorkDairyEmpDtlEntityCopy;

public interface WorkDairyEmpDtlRepositoryCopy extends JpaRepository<WorkDairyEmpDtlEntity, Long> {

	@Query("SELECT T FROM WorkDairyEmpDtlEntity T JOIN FETCH T.workDairyEmpWageEntities WEE WHERE T.workDairyEntity.id=:workDairyId AND T.status=:status")
    List<WorkDairyEmpDtlEntity> findWorkDairyEmpDetails(@Param("workDairyId") Long workDairyId, @Param("status") Integer status);
	
}
