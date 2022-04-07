package com.rjtech.centrallib.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.PlantMstrEntity;

public interface PlantClassRepository extends CentralLibRepository<PlantMstrEntity, Long> {

    @Query("SELECT PCM FROM PlantMstrEntity PCM  WHERE (PCM.clientId.clientId IS NULL OR PCM.clientId.clientId=:clientId ) AND  PCM.status=:status ORDER BY PCM.code")
    List<PlantMstrEntity> findByClientId(@Param("clientId") Long id, @Param("status") Integer status);

    @Query("SELECT PCM FROM PlantMstrEntity PCM  WHERE (PCM.clientId.clientId IS NULL OR PCM.clientId.clientId=:clientId )  AND (:plantCode IS NULL OR PCM.code like :plantCode ) AND (:plantName IS NULL OR PCM.name like :plantName )  AND PCM.status=:status ORDER BY PCM.code")
    List<PlantMstrEntity> findPlantClass(@Param("clientId") Long clientId, @Param("plantCode") String plantCode,
            @Param("plantName") String plantName, @Param("status") Integer status);

    @Query("SELECT ECM FROM PlantMstrEntity ECM  WHERE ECM.clientId.clientId IS NULL OR ECM.clientId.clientId=:clientId ")
    List<PlantMstrEntity> findAllPlantClass(@Param("clientId") Long clientId);

    @Query("SELECT ECM.id,ECM.name,ECM.code,ECM.measurmentMstrEntity.name FROM PlantMstrEntity ECM  WHERE ECM.id in :ids")
    List<Object[]> findPlantPartialDetails(@Param("ids") Set<Long> ids);
    
    @Query("SELECT T FROM PlantMstrEntity T WHERE T.clientId.clientId=:crmId AND T.code=:code AND T.status=:status")
    public PlantMstrEntity findBy(@Param("crmId") Long crmId, @Param("code") String code, @Param("status") Integer status);

}