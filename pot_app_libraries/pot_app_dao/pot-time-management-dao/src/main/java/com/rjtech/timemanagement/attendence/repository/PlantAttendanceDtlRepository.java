package com.rjtech.timemanagement.attendence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rjtech.timemanagement.attendance.model.PlantAttendanceDtlEntity;

@Repository
public interface PlantAttendanceDtlRepository extends JpaRepository<PlantAttendanceDtlEntity, Long> {

}
