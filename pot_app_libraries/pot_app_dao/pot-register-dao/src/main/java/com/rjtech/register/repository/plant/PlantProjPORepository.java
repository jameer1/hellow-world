package com.rjtech.register.repository.plant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.register.plant.model.PlantProjPODtlEntity;

@Repository
public interface PlantProjPORepository extends JpaRepository<PlantProjPODtlEntity, Long> {

    @Query("select T FROM PlantProjPODtlEntity T where T.projId.projectId =:projId  AND  T.purchaseTypeId.id =:poId   AND  T.purchasePlantTypeId.id =:schId "
            + "AND   T.scheduleCompanyPlantId.id =:schCompId ")
    public PlantProjPODtlEntity getPlantProjPODetails(@Param("projId") Long projId, @Param("poId") Long poId,
            @Param("schId") Long schId, @Param("schCompId") Long schCompId);

    @Query("select T FROM PlantProjPODtlEntity T where T.projId.projectId =:projId")
    public PlantProjPODtlEntity getPlantPODetailsByProject(@Param("projId") Long projId);
}
