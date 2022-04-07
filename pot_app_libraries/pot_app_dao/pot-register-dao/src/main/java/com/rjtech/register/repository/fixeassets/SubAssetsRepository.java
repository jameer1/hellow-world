package com.rjtech.register.repository.fixeassets;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.register.fixedassets.model.SubAssetDtlEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

@Repository
public interface SubAssetsRepository extends RegisterBaseRepository<SubAssetDtlEntity, Long> {

    @Query("SELECT SA FROM SubAssetDtlEntity SA  WHERE SA.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid AND  SA.status=:status ")
    List<SubAssetDtlEntity> findAllSubAsset(@Param("fixedAssetid") Long fixedAssetid, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE SubAssetDtlEntity SA  SET SA.status=:status  where SA.subid in :subAssetIds")
    void deactivateSubAsset(@Param("subAssetIds") List<Long> subAssetIds, @Param("status") Integer status);

    @Modifying
    @Query("DELETE from SubAssetDtlEntity rv  where rv.subid in :subAssetIds")
    void subAssetDelete(@Param("subAssetIds") List<Long> subAssetIds);

    @Query("SELECT SA FROM SubAssetDtlEntity SA  WHERE SA.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid AND  SA.status=:status ")
    List<SubAssetDtlEntity> findAssetById(@Param("fixedAssetid") Long fixedAssetid, @Param("status") Integer status);

}
