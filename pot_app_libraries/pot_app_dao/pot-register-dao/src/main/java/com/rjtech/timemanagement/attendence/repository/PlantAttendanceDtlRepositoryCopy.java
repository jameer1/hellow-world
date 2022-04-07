package com.rjtech.timemanagement.attendence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.timemanagement.attendance.model.PlantAttendanceDtlEntity;

//import com.rjtech.register.timemanagement.attendance.model.PlantAttendanceDtlEntityCopy;

@Repository
public interface PlantAttendanceDtlRepositoryCopy extends JpaRepository<PlantAttendanceDtlEntity, Long> {

    @Query("select extract(year from PPAD.attendanceDate), COUNT(CASE WHEN PPAD.projAttdCode = 'I' THEN 1 END), "
            + " COUNT(CASE WHEN PPAD.projAttdCode = 'NW' THEN 1 END), "
            + " COUNT(CASE WHEN PPAD.projAttdCode = 'W' THEN 1 END) "
            + " from PlantAttendanceDtlEntity PPAD where PPAD.plantAttendanceEntity.plantRegisterDtlEntity.id = :plantId "
            + " and extract(year from PPAD.attendanceDate) in (:years) "
            + " group by extract(year from PPAD.attendanceDate), PPAD.projAttdCode"
            + " order by extract(year from PPAD.attendanceDate)")
    List<Object[]> getPlantLeaveAttendanceYearWise(@Param("plantId") long plantId, @Param("years") List<Integer> years);

}
