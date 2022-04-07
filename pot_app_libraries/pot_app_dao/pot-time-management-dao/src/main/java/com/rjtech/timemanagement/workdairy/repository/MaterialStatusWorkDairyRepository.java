package com.rjtech.timemanagement.workdairy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rjtech.timemanagement.workdairy.model.WorkDairyMaterialStatusEntity;

@Repository
public interface MaterialStatusWorkDairyRepository extends JpaRepository<WorkDairyMaterialStatusEntity, Long> {

}
