package com.rjtech.register.repository.plant;

import java.util.List;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.plant.model.PlantRegProjEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface PlantRegProjRepository extends RegisterBaseRepository<PlantRegProjEntity, Long> {

    @Query("SELECT DISTINCT T FROM PlantRegProjEntity T where  T.plantRegisterDtlEntity.id =:plantId  AND T.status =:status   order by T.deploymentId desc")
    List<PlantRegProjEntity> findPlantProjects(@Param("plantId") Long plantId, @Param("status") Integer status);

    @Query("SELECT DISTINCT T FROM PlantRegProjEntity T where  T.plantRegisterDtlEntity.id =:plantId  AND T.isLatest ='Y'")
    PlantRegProjEntity findLatestPlantDeployment(@Param("plantId") Long plantId);

    @Query("SELECT DISTINCT T FROM PlantRegProjEntity T where  T.projId.projectId =:projId  AND T.isLatest ='Y'  AND T.status =:status   order by T.deploymentId desc")
    List<PlantRegProjEntity> findLatestPlantDeploymentByProj(@Param("projId") Long projId,
            @Param("status") Integer status);

    @Query("SELECT DISTINCT T FROM PlantRegProjEntity T where  T.plantRegisterDtlEntity.id =:plantId AND T.plantProjPODtlEntity is not null  AND T.status =:status   order by T.deploymentId desc")
    PlantRegProjEntity findPlantPOProject(@Param("plantId") Long plantId, @Param("status") Integer status);
    
    @Query("SELECT COUNT(T) FROM PlantRegProjEntity T where  T.plantRegisterDtlEntity.id =:plantId AND T.plantProjPODtlEntity is not null  AND T.status =:status order by T.deploymentId desc")
    int findPlantPOProjectCnt(@Param("plantId") Long plantId, @Param("status") Integer status);

    @Query("SELECT DISTINCT T FROM PlantRegProjEntity T where  T.plantRegisterDtlEntity.id =:plantId AND "
            + "T.plantProjPODtlEntity.id = :plantProjPoId  AND T.status =:status   order by T.deploymentId desc")
    PlantRegProjEntity findPlantPOProject(@Param("plantId") Long plantId, @Param("plantProjPoId") Long plantProjPoId,
            @Param("status") Integer status);

    @Query("SELECT DISTINCT PPJD.plantRegisterDtlEntity.id, PPJD.plantRegisterDtlEntity.assertId, "
            + " PPJD.plantRegisterDtlEntity.desc, PPJD.plantRegisterDtlEntity.regNumber,"
            + " PPJD.plantRegisterDtlEntity.manfacture, PPJD.plantRegisterDtlEntity.model, PPJD.deMobDate "
            + " FROM PlantRegProjEntity PPJD "
            + " left join PlantTransReqApprDtlEntity PTRAD on PTRAD.plantId = PPJD.plantRegisterDtlEntity "
            + " left join PlantTransferReqApprEntity PTRA on PTRAD.plantTransferReqApprEntity = PTRA "
            + " WHERE PPJD.isLatest = 'Y' and PPJD.projId.projectId = :projId "
            + " and lower(PPJD.postDeMobStatus) = 'on transfer' and PPJD.plantRegisterDtlEntity.clientId.clientId = :clientId "
            + " and ( PTRA.apprStatus is null or lower(PTRA.apprStatus) = 'approved' or lower(PTRA.apprStatus) = 'rejected' ) ")
    List<Object[]> findNewRequestTransferPlants(@Param("clientId") Long clientId, @Param("projId") Long projId);

    @Query("SELECT deMobDate from PlantRegProjEntity where projId.projectId=:projIds and plantRegisterDtlEntity.id=:id")
    public Date findDemobDate(@Param("projIds") Long projIds, @Param("id") Long id);
}
