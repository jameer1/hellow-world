package com.rjtech.timemanagement.workdairy.repository.copy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.timemanagement.workdairy.model.WorkDairyEntity;

//import com.rjtech.workdairy.WorkDairyEntityCopy;

public interface WorkDairyRepositoryCopy extends JpaRepository<WorkDairyEntity, Long>{

	@Query("SELECT T FROM WorkDairyEntity T WHERE "
	+ "T.projId.projectId IN :projIds "
            + "ORDER BY T.workDairyDate")
    List<WorkDairyEntity> findAll( @Param("projIds") List<Long> projIds);
}
