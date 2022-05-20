package com.rjtech.projectlib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rjtech.projectlib.model.ChangeOrderSOWEntity;

@Repository
public interface ChangeOrderSOWRepository  extends JpaRepository<ChangeOrderSOWEntity, Long>{

	
}
