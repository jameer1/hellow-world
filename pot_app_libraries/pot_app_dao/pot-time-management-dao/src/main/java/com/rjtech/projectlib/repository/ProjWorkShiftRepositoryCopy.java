package com.rjtech.projectlib.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rjtech.projectlib.model.ProjWorkShiftMstrEntity;

//import com.rjtech.projectlib.model.ProjWorkShiftMstrEntityCopy;

public interface ProjWorkShiftRepositoryCopy extends JpaRepository<ProjWorkShiftMstrEntity, Long> {

}
