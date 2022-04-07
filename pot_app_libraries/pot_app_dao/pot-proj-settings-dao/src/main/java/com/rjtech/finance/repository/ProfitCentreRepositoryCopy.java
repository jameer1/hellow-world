package com.rjtech.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.ProfitCentreEntity;

//import com.rjtech.finance.model.ProfitCentreEntityCopy;

@Repository
public interface ProfitCentreRepositoryCopy extends JpaRepository<ProfitCentreEntity, Long> {

}
