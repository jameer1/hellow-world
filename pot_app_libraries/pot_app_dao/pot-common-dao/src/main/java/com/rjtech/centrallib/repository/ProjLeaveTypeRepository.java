package com.rjtech.centrallib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.ProjLeaveTypeEntity;
import com.rjtech.common.model.ClientRegEntity;

public interface ProjLeaveTypeRepository extends JpaRepository<ProjLeaveTypeEntity, Long> {

    @Query("SELECT PLT FROM ProjLeaveTypeEntity PLT WHERE lower(PLT.countryCode) = lower(:countryCode) "
            + " and lower(TO_CHAR(PLT.effectiveFrom,'MON-YYYY')) = lower(:effectiveFrom) "
            + " and PLT.clientRegEntity.clientId = :clientId order by PLT.id ")
    List<ProjLeaveTypeEntity> findByCountryCodeAndEffectiveFrom(@Param("countryCode") String countryCode,
            @Param("effectiveFrom") String effectiveFrom, @Param("clientId") Long clientId);

    @Query("SELECT PLT FROM ProjLeaveTypeEntity PLT WHERE lower(PLT.countryCode) = lower(:countryCode) "
            + " and PLT.clientRegEntity = :clientRegEntity order by PLT.id ")
    List<ProjLeaveTypeEntity> findByCountryCodeAndClient(@Param("countryCode") String countryCode,
            @Param("clientRegEntity") ClientRegEntity clientRegEntity);

    @Query("SELECT PLT FROM ProjLeaveTypeEntity PLT WHERE PLT.clientRegEntity is null")
    List<ProjLeaveTypeEntity> findDefaultProjLeaveTypes();

    @Query("SELECT count(PLT) FROM ProjLeaveTypeEntity PLT JOIN PLT.projLeaveCategoryTypes PLCT "
            + " WHERE PLT.clientRegEntity.clientId = :clientId and PLCT.procureCatgDtlEntity.id in (:procureId)")
    int findLeaveTypesWithProcure(@Param("clientId") long clientId, @Param("procureId") long procureId);

    @Query("SELECT count(PLT) FROM ProjLeaveTypeEntity PLT WHERE PLT.clientRegEntity = :clientRegEntity")
    int leaveTypesForClient(@Param("clientRegEntity") ClientRegEntity clientRegEntity);

    @Query("SELECT TO_CHAR(PLT.effectiveFrom,'MON-YYYY') FROM ProjLeaveTypeEntity PLT WHERE lower(PLT.countryCode) = lower(:countryCode) "
            + " and PLT.clientRegEntity.clientId = :clientId group by PLT.effectiveFrom order by PLT.effectiveFrom desc ")
    List<String> findUniqueEffectiveDatesForCountry(@Param("countryCode") String countryCode,
            @Param("clientId") Long clientId);

    @Query("SELECT PJL FROM ProjLeaveTypeEntity PJL join PJL.projLeaveCategoryTypes PLCT "
            + " where PJL.countryCode=:country and lower(PLCT.payType)='paid' ")
    List<ProjLeaveTypeEntity> findPaidLeaveCodes(@Param("country") String country);

    @Query("SELECT PLT FROM ProjLeaveTypeEntity PLT WHERE PLT.clientRegEntity.clientId=:clientId")
    List<ProjLeaveTypeEntity> findLeaveTypesForClient(@Param("clientId") Long clientId);

}
