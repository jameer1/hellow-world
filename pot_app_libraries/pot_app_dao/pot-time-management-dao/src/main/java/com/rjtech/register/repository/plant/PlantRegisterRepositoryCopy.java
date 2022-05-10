package com.rjtech.register.repository.plant;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.plant.model.PlantRegisterDtlEntity;

//import com.rjtech.register.plant.model.PlantRegisterDtlEntityCopy;

public interface PlantRegisterRepositoryCopy extends JpaRepository<PlantRegisterDtlEntity, Long> {
    
    @Query("SELECT DISTINCT PPAT.plantRegisterDtlEntity FROM PlantAttendanceEntity PPAT "
            + " WHERE PPAT.plantAttendanceMstrEntity.projMstrEntity.projectId =:projId AND PPAT.plantAttendanceMstrEntity.projCrewMstrEntity.id=:crewId "
            + " AND TO_CHAR(PPAT.plantAttendanceMstrEntity.attendanceMonth,'MON-YYYY') = TO_CHAR(:startDate,'MON-YYYY')")
    List<PlantRegisterDtlEntity> findAttendanceByProjIdCrewId(@Param("projId") long projId,
            @Param("crewId") long crewId, @Param("startDate") Date startDate);

    @Query("SELECT DISTINCT PPAT.plantRegisterDtlEntity FROM PlantAttendanceEntity PPAT "
            + " WHERE PPAT.plantAttendanceMstrEntity.projMstrEntity.projectId =:projId AND PPAT.plantAttendanceMstrEntity.projCrewMstrEntity.id=:crewId "
            + " AND TO_CHAR(PPAT.plantAttendanceMstrEntity.attendanceMonth,'MON-YYYY') = TO_CHAR(:startDate,'MON-YYYY')")
    List<PlantRegisterDtlEntity> findAttendanceForByProjIdCrewId(@Param("projId") long projId,
            @Param("crewId") long crewId, @Param("startDate") Date startDate);
    
    @Query("SELECT DISTINCT PPAT.plantRegisterDtlEntity FROM PlantAttendanceEntity PPAT "
            + " WHERE PPAT.plantAttendanceMstrEntity.projMstrEntity.projectId =:projId AND PPAT.plantAttendanceMstrEntity.projCrewMstrEntity.id=:crewId "
            + " AND TO_CHAR(PPAT.plantAttendanceMstrEntity.attendanceMonth,'MON-YYYY') = TO_CHAR(:startDate,'MON-YYYY')")
    List<PlantRegisterDtlEntity> findAttendanceForCopyByProjIdCrewId(@Param("projId") long projId,
            @Param("crewId") long crewId, @Param("startDate") Date startDate);
}
