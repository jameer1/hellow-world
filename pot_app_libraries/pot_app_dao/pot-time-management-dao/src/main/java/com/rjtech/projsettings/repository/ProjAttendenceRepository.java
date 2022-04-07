package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.AttendanceNormalTimeEntity;

//import com.rjtech.timemanagement.proj.settings.model.AttendanceNormalTimeEntityCopy;

public interface ProjAttendenceRepository extends JpaRepository<AttendanceNormalTimeEntity, Long> {

    @Query("SELECT PJA FROM AttendanceNormalTimeEntity PJA WHERE PJA.projId.projectId=:projId  AND PJA.type=:type  AND PJA.status=:status")
    public AttendanceNormalTimeEntity findProjAttendence(@Param("projId") Long projId, @Param("type") String type,
            @Param("status") Integer status);
    
    @Query("SELECT PJA FROM AttendanceNormalTimeEntity PJA WHERE PJA.projId.projectId=:projId  AND PJA.type=:type  AND PJA.status=:status")
    public List<AttendanceNormalTimeEntity> findProjAttendences(@Param("projId") Long projId, @Param("type") String type,
            @Param("status") Integer status);

    @Query("SELECT PJA FROM AttendanceNormalTimeEntity PJA WHERE PJA.isDefault='Y' AND PJA.projId.projectId IS NULL ")
    public List<AttendanceNormalTimeEntity> findDefaultProjAttendence();
}