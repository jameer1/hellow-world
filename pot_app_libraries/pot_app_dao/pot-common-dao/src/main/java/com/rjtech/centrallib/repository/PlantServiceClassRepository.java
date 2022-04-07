package com.rjtech.centrallib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.PlantServiceClassificationMstrEntity;

public interface PlantServiceClassRepository extends CentralLibRepository<PlantServiceClassificationMstrEntity, Long> {

    @Query("SELECT PSCM FROM PlantServiceClassificationMstrEntity PSCM  WHERE (PSCM.clientId.clientId IS NULL OR PSCM.clientId.clientId=:clientId ) AND  PSCM.status=:status AND PSCM.plantServiceClassificationMstrEntity IS NULL")
    List<PlantServiceClassificationMstrEntity> findPlantServiceClass(@Param("clientId") Long clientId,
            @Param("status") Integer status);

    @Query("SELECT PSCM FROM PlantServiceClassificationMstrEntity PSCM WHERE  PSCM.clientId.clientId=:clientId AND PSCM.isItem=1 AND PSCM.status=:status")
    public List<PlantServiceClassificationMstrEntity> findPlantServiceClassItems(@Param("clientId") Long clientId,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE PlantServiceClassificationMstrEntity PSCM  SET PSCM.status=:status  where PSCM.id in :plantServiceIds ")
    void deactivatePlantServiceClassification(@Param("plantServiceIds") List<Long> plantServiceIds,
            @Param("status") Integer status);

    @Query("SELECT PSCM FROM PlantServiceClassificationMstrEntity PSCM  WHERE PSCM.clientId.clientId IS NULL OR PSCM.clientId.clientId=:clientId")
    List<PlantServiceClassificationMstrEntity> findAllPlantServices(@Param("clientId") Long clientId);
}
