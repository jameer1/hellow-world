package com.rjtech.timemanagement.workdairy.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
import com.rjtech.register.plant.model.PlantRegisterDtlEntity;
//import com.rjtech.register.plant.model.PlantRegisterDtlEntityCopy;
//import com.rjtech.projectlib.model.ProjCrewMstrEntityCopy;
//import com.rjtech.register.plant.model.PlantRegisterDtlEntityCopy;
import com.rjtech.timemanagement.workdairy.model.WorkDairyPlantDtlEntity;

@Repository
public interface PlantWorkDairyRepository extends JpaRepository<WorkDairyPlantDtlEntity, Long> {

    @Query("SELECT T FROM WorkDairyPlantDtlEntity T  WHERE T.workDairyEntity.id=:workDairyId AND T.status=:status")
    List<WorkDairyPlantDtlEntity> findWorkDairyPlantDetails(@Param("workDairyId") Long workDairyId,
            @Param("status") Integer status);
    
    @Query("SELECT WDPD.plantRegId.id, SUM(WPTC.usedTime + WPTC.idleTime) "
            + "FROM WorkDairyPlantDtlEntity WDPD "
            + "JOIN WorkDairyPlantStatusEntity WDPS "
            + "ON WDPS.workDairyPlantDtlEntity = WDPD "
            + "JOIN WorkDairyPlantCostEntity WPTC ON WPTC.workDairyPlantStatusEntity = WDPS "
            + "WHERE WDPD.workDairyEntity.workDairyDate = :workDairyDate "
            + "AND WDPD.workDairyEntity.crewId = :crewId "
            + "AND WDPD.workDairyEntity.projId = :projId "
            + "AND WDPD.plantRegId IN :empRegList GROUP BY WDPD.plantRegId.id")
    List<Object[]> getBookedHrsForOtherCrew(@Param("projId") ProjMstrEntity projId, 
            @Param("crewId") ProjCrewMstrEntity crewId, 
            @Param("workDairyDate") Date workDairyDate,
            @Param("empRegList") List<PlantRegisterDtlEntity> plantReg);
    
    @Modifying
    @Query("UPDATE WorkDairyPlantDtlEntity T SET T.status=:status where T.id in :plantIds")
    void updateWorkDairyPlantByIds( @Param("plantIds") List<Long> progressIds , @Param("status") Integer status );
}
