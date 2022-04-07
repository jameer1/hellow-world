package com.rjtech.register.repository.plant;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.plant.model.PlantRepairsEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface PlantRepairsRepository extends RegisterBaseRepository<PlantRepairsEntity, Long> {

    @Query("SELECT PRE FROM PlantRepairsEntity PRE WHERE PRE.plantId.id = :plantId AND PRE.status=:status")
    public List<PlantRepairsEntity> findPlantRepairs(@Param("plantId") Long plantId, @Param("status") Integer status);

}
