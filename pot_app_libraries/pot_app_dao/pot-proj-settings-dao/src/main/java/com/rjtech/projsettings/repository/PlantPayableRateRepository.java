package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.plant.model.PlantPayableRatesEntity;


public interface PlantPayableRateRepository extends JpaRepository<PlantPayableRatesEntity, Long> {

	 @Query("SELECT T FROM PlantPayableRatesEntity T  WHERE T.plantRegProjEntity.plantRegisterDtlEntity.id=:plantId order by T.latest desc")
	    public List<PlantPayableRatesEntity> findPlantChargeOutRates(@Param("plantId") Long plantId);
}
