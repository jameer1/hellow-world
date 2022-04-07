package com.rjtech.register.repository.plant;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.plant.model.PlantServiceHistoryEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface PlantServiceHistoryRepository extends RegisterBaseRepository<PlantServiceHistoryEntity, Long> {

    @Query("SELECT PSH FROM PlantServiceHistoryEntity PSH WHERE PSH.plantId.id = :plantId AND PSH.status = :status")
    public List<PlantServiceHistoryEntity> findPlantPlantServiceHistory(@Param("plantId") Long plantId,
            @Param("status") Integer status);

}
