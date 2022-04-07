package com.rjtech.register.repository.plant;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.plant.model.PlantChargeOutRatesEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface PlantChargeOutRateRepository extends RegisterBaseRepository<PlantChargeOutRatesEntity, Long> {

    @Query("SELECT T FROM PlantChargeOutRatesEntity T  WHERE T.plantRegProjEntity.plantRegisterDtlEntity.id=:plantId order by T.latest desc")
    public List<PlantChargeOutRatesEntity> findPlantChargeOutRates(@Param("plantId") Long plantId);

}
