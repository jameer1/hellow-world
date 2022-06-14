package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.timemanagement.timesheet.model.TimeSheetAdditionalTimeEntity;

//import com.rjtech.timemanagement.proj.settings.model.TimeSheetAdditionalTimeEntity;

public interface ProjTimeSheetRepository extends JpaRepository<TimeSheetAdditionalTimeEntity, Long> {

    @Query("SELECT T FROM com.rjtech.timemanagement.timesheet.model.TimeSheetAdditionalTimeEntity T where T.projId=:projId and T.projCrewMstrEntity.id=:crewId "
    	+ "AND T.grantHrs is not null AND T.updatedOn >= TO_CHAR(SYSDATE-2, 'DD-MM-YYYY')")
    List<TimeSheetAdditionalTimeEntity> findCutOffDaysForCrew(@Param("projId") Long projId,
    		@Param("crewId") Long crewId);
    
    @Query("SELECT T FROM com.rjtech.timemanagement.timesheet.model.TimeSheetAdditionalTimeEntity T where T.projId=:projId and T.projCrewMstrEntity.id is null "
        	+ "AND T.empId=:empId AND T.grantHrs is not null AND T.updatedOn >= TO_CHAR(SYSDATE-2, 'DD-MM-YYYY')")
        List<TimeSheetAdditionalTimeEntity> findCutOffDaysForIndividual(@Param("projId") Long projId,
        		@Param("empId") Long empId);
    
    @Query("SELECT T FROM com.rjtech.timemanagement.timesheet.model.TimeSheetAdditionalTimeEntity T where T.projId=:projId  "
        	+ "AND T.timeSheetId =:timeSheetId")
        List<TimeSheetAdditionalTimeEntity> findAdditionalTimeAvailable(@Param("projId") Long projId,
        		@Param("timeSheetId") Long timeSheetId);
}