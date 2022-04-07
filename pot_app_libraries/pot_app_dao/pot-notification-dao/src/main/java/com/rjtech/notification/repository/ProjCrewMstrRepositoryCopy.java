package com.rjtech.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rjtech.projectlib.model.ProjCrewMstrEntity;

//import com.rjtech.notification.model.ProjCrewMstrEntity;

@Repository
public interface ProjCrewMstrRepositoryCopy extends JpaRepository<ProjCrewMstrEntity, Long> {

}
