package com.rjtech.register.repository.plant;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.plant.model.PlantDepriciationSalvageEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface PlantDepriciationSalvageRepository
        extends RegisterBaseRepository<PlantDepriciationSalvageEntity, Long> {

    @Query("SELECT T FROM PlantDepriciationSalvageEntity  T WHERE T.plantId.id = :plantId  order by T.updatedOn desc")
    public List<PlantDepriciationSalvageEntity> findPlantDepriciationSalvages(@Param("plantId") Long plantId);
}
