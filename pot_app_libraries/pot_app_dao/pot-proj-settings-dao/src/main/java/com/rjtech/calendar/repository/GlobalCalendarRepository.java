package com.rjtech.calendar.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.rjtech.calendar.model.GlobalCalEntity;

public interface GlobalCalendarRepository extends JpaRepository<GlobalCalEntity, Long> {

    @Query("SELECT T FROM GlobalCalEntity T  WHERE  (:clientId is null AND T.clientId.clientId is null) or (:clientId is not null AND T.clientId.clientId=:clientId) AND T.status=:status AND T.calType=:calType")
    List<GlobalCalEntity> findGlobalCalendarsByClientId(@Param("clientId") Long clientId,
            @Param("status") Integer status, @Param("calType") String calType);

    @Query("SELECT T FROM GlobalCalEntity T  WHERE T.id=:id  AND  T.status=:status")
    List<GlobalCalEntity> findGlobalCalendarById(@Param("id") Long id, @Param("status") Integer status);

    @Query("SELECT T FROM GlobalCalEntity T where T.calDefaultValue = 1 and T.clientId.clientId = :clientId")
    GlobalCalEntity getDefaultGlobalCalendar(@Param("clientId") Long clientId);

    @Modifying
    @Query("DELETE From GlobalCalEntity cal where cal.id IN(:id) and cal.status=:status")
    public void deleteGlobalCalendars(@Param("id") List<Long> id, @Param("status") Integer status);

    @Query("SELECT T FROM GlobalCalEntity T  WHERE T.projMstrEntity.projectId=:projId AND T.status=:status")
    List<GlobalCalEntity> findProjCalendarsByProject(@Param("projId") Long projId, @Param("status") Integer status);

    @Query("SELECT T FROM GlobalCalEntity T  WHERE T.id=:id  AND  T.status=:status AND T.latest=true")
    List<GlobalCalEntity> findProjCalendarById(@Param("id") Long id, @Param("status") Integer status);

    @Query("SELECT T FROM GlobalCalEntity T  WHERE T.clientId.clientId=:clientId AND T.status=:status")
    List<GlobalCalEntity> findProjCalendarsByClientId(@Param("clientId") Long clientId,
            @Param("status") Integer status);

    @Query("SELECT T FROM GlobalCalEntity T WHERE T.projMstrEntity.projectId=:projId  AND T.status=:status")
    List<GlobalCalEntity> findAllCalendars(@Param("projId") Long projId, @Param("status") Integer status);

    @Modifying
    @Query("DELETE From GlobalCalEntity cal where cal.id IN(:id) and cal.status=:status")
    public void deleteProjCalendarsByProject(@Param("id") List<Long> id, @Param("status") Integer status);

}
