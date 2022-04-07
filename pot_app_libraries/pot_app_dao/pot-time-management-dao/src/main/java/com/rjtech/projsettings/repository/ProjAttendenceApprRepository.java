package com.rjtech.projsettings.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.AttendanceAddtionalTimeApprEntity;

//import com.rjtech.timemanagement.proj.settings.model.AttendanceAddtionalTimeApprEntityCopy;

public interface ProjAttendenceApprRepository extends JpaRepository<AttendanceAddtionalTimeApprEntity, Long> {

    @Query("select PAA from AttendanceAddtionalTimeApprEntity PAA where PAA.status = 1 and PAA.attendanceNormalTimeEntity.status = 1 "
            + " and PAA.attendanceNormalTimeEntity.type = :type and PAA.attendanceNormalTimeEntity.projId.projectId = :projId"
            + " and PAA.projCrewMasterEntity.id = :crewId and PAA.approveReqEndDate >= :currentDate ")
    List<AttendanceAddtionalTimeApprEntity> findAddtionalTimeForProjCrew(@Param("projId") long projId,
            @Param("crewId") long crewId, @Param("currentDate") Date currentDate, @Param("type") String type);

}
