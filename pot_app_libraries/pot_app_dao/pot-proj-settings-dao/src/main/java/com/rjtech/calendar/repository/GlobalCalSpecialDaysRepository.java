package com.rjtech.calendar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.calendar.model.GlobalCalSpecialDaysEntity;

public interface GlobalCalSpecialDaysRepository extends JpaRepository<GlobalCalSpecialDaysEntity, Long> {

    @Query("SELECT T FROM GlobalCalSpecialDaysEntity T  WHERE T.globalCalEntity.id=:calendarId AND T.status=:status")
    List<GlobalCalSpecialDaysEntity> findGlobalCalSpecialDays(@Param("calendarId") Long calendarId,
            @Param("status") Integer status);

    @Query("SELECT T FROM GlobalCalSpecialDaysEntity T  WHERE T.globalCalEntity.id=:calendarId AND T.status=:status AND to_char(T.date,'MM-YYYY')=:month")
    List<GlobalCalSpecialDaysEntity> findGlobalCalSpecialDaysByMonth(@Param("calendarId") Long calendarId,
            @Param("status") Integer status, @Param("month") String month);

    @Query("SELECT T FROM GlobalCalSpecialDaysEntity T WHERE T.globalCalEntity.id=:calendarId AND to_char(T.date,'DD-MM-YYYY')=:existingDate")
    GlobalCalSpecialDaysEntity findExistingByCalDate(@Param("calendarId") Long calendarId,
            @Param("existingDate") String existingDate);

    @Query("SELECT to_char(T.date, 'DD-MON-YYYY') FROM GlobalCalSpecialDaysEntity T "
            + " JOIN ProjGeneralMstrEntity PGV on PGV.projMstrEntity.projectId = :projId and PGV.isLatest = 'Y' and PGV.status = 1"
            + " WHERE PGV.globalCalEntity = T.globalCalEntity AND T.holiday = 1")
    List<String> findProjCalNonWorkingDays(@Param("projId") Long projId);

    @Query("SELECT to_char(T.date, 'DD-MON-YYYY') FROM GlobalCalSpecialDaysEntity T "
            + " JOIN ProjGeneralMstrEntity PGV on PGV.projMstrEntity.projectId = :projId and PGV.isLatest = 'Y' and PGV.status = 1"
            + " WHERE PGV.globalCalEntity = T.globalCalEntity AND T.holiday = 0")
    List<String> findProjCalSpecialWorkingDays(@Param("projId") Long projId);

}
