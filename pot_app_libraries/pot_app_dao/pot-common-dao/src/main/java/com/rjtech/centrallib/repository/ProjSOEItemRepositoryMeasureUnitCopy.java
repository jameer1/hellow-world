package com.rjtech.centrallib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.centrallib.model.ProjSOEItemEntityMeasureUnitCopy;


@Repository
public interface ProjSOEItemRepositoryMeasureUnitCopy extends JpaRepository<ProjSOEItemEntityMeasureUnitCopy, Long> {
    @Query("SELECT distinct SOE.measurmentMstrEntity.id  FROM ProjSOEItemEntityMeasureUnitCopy SOE WHERE SOE.measurmentMstrEntity.id IS NOT NULL")
    public List<Long> getAllMeasureUnits();
    
   
}
