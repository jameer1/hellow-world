package com.rjtech.timemanagement.workdairy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rjtech.projectlib.model.ProjCrewMstrEntity;

//import com.rjtech.projectlib.model.ProjCrewMstrEntityCopy;

@Repository
public interface ProjCrewMstrRepository extends JpaRepository<ProjCrewMstrEntity, Long> {

}
