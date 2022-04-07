package com.rjtech.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.common.model.ResourceCurveEntity;

public interface ResourceCurveRepository extends JpaRepository<ResourceCurveEntity, Long> {

    @Query("SELECT RCE FROM ResourceCurveEntity RCE WHERE RCE.status=:status AND (RCE.clientId IS NULL OR RCE.clientId.clientId=:clientId)")
    public List<ResourceCurveEntity> findResourceCurves(@Param("status") Integer status,
            @Param("clientId") Long clientId);

    @Modifying
    @Query("UPDATE ResourceCurveEntity RCE SET RCE.status=:status  where RCE.id in :resourceCurveIds ")
    void deactivateResourceurves(@Param("resourceCurveIds") List<Long> resourceCurveIds,
            @Param("status") Integer status);

    @Query("SELECT RCE FROM ResourceCurveEntity RCE WHERE (RCE.clientId IS NULL OR RCE.clientId.clientId=:clientId) AND RCE.status=:status")
    public List<ResourceCurveEntity> findProjResourceCurves(@Param("clientId") Long clientId,
            @Param("status") Integer status);
    
    @Query("SELECT RCE FROM ResourceCurveEntity RCE WHERE RCE.curveType=:curveType")
    public ResourceCurveEntity findBy(@Param("curveType") String curveType);

}
