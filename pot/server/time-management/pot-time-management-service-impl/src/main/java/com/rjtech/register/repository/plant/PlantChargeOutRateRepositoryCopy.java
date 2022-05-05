package com.rjtech.register.repository.plant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.register.plant.model.PlantChargeOutRatesEntityCopy;

@Repository
public interface PlantChargeOutRateRepositoryCopy extends JpaRepository<PlantChargeOutRatesEntityCopy, Long> {

    @Query("SELECT T FROM PlantChargeOutRatesEntityCopy T "
            + " WHERE T.plantRegProjEntity.plantRegisterDtlEntity.id = :plantId"
            + " AND T.plantRegProjEntity.projId.projectId = :projId " + " AND T.latest = 1 ")
    public PlantChargeOutRatesEntityCopy findPlantChargeOutRates(@Param("plantId") Long plantId,
            @Param("projId") Long projId);
    
    @Query("SELECT T FROM PlantChargeOutRatesEntityCopy T "
            + " WHERE T.plantRegProjEntity.plantRegisterDtlEntity.id = :plantId"
            + " AND T.plantRegProjEntity.projId.projectId = :projId " + " AND T.latest = 1 ")
    public PlantChargeOutRatesEntityCopy findPlantChargeOutRate(@Param("plantId") Long plantId,
            @Param("projId") Long projId);

}
