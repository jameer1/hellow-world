package com.rjtech.calendar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.calendar.model.GlobalCalRegularDaysEntity;
import com.rjtech.calendar.model.GlobalCalSpecialDaysEntity;

public interface GlobalCalRegularDaysRepository extends JpaRepository<GlobalCalRegularDaysEntity, Long> {

    @Query("SELECT T FROM GlobalCalRegularDaysEntity T  WHERE T.globalCalEntity.id=:calendarId AND T.status=:status AND T.latest=true")
    List<GlobalCalRegularDaysEntity> findGlobalCalRegularDays(@Param("calendarId") Long calendarId,
            @Param("status") Integer status);

    @Query("SELECT T FROM GlobalCalSpecialDaysEntity T  WHERE T.globalCalEntity.id=:calendarId AND T.status=:status AND lower(to_char(T.date,'MM-YYYY'))=:month")
    List<GlobalCalSpecialDaysEntity> findProjCalSpecialDaysByMonth(@Param("calendarId") Long calendarId,
            @Param("status") Integer status, @Param("month") String month);

    @Query("SELECT T FROM GlobalCalRegularDaysEntity T  WHERE T.globalCalEntity.id=:calendarId AND T.status=:status AND T.latest=true")
    List<GlobalCalRegularDaysEntity> findProjCalRegularDays(@Param("calendarId") Long calendarId,
            @Param("status") Integer status);

}
