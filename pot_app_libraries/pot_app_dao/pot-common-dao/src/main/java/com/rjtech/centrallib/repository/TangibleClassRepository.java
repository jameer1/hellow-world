package com.rjtech.centrallib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.centrallib.model.TangibleClassificationEntity;

@Repository
public interface TangibleClassRepository extends CentralLibRepository<TangibleClassificationEntity, Long> {

    @Query("SELECT T FROM TangibleClassificationEntity T WHERE  (T.clientId.clientId=:clientId OR T.clientId.clientId IS NULL) AND T.tangibleClassificationEntity IS NULL AND T.status=:status  ORDER BY  T.code")
    public List<TangibleClassificationEntity> findAllTangible(@Param("clientId") Long clientId,
            @Param("status") Integer status);

    @Query("SELECT T FROM TangibleClassificationEntity T WHERE  T.clientId.clientId=:clientId AND T.tangibleClassificationEntity IS NULL AND T.status=:status  ORDER BY  T.code")
    public List<TangibleClassificationEntity> findAllTangibleForClient(@Param("clientId") Long clientId,
            @Param("status") Integer status);

    @Query("SELECT T FROM TangibleClassificationEntity T WHERE  (T.clientId.clientId=:clientId OR T.clientId IS NULL) AND T.tangibleClassificationEntity IS NOT NULL AND T.item=true AND T.status=:status  ORDER BY  T.code")
    public List<TangibleClassificationEntity> findTangibleItems(@Param("clientId") Long clientId,
            @Param("status") Integer status);

    @Query("SELECT T FROM TangibleClassificationEntity T WHERE  T.clientId.clientId=:clientId AND T.tangibleClassificationEntity IS NOT NULL AND T.item=true AND T.status=:status  ORDER BY  T.code")
    public List<TangibleClassificationEntity> findTangibleItemsForClient(@Param("clientId") Long clientId,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE TangibleClassificationEntity T  SET T.status=:status  where T.id in :tangibleIds or T.tangibleClassificationEntity.id in :tangibleIds")
    void deactivateTangibleGroups(@Param("tangibleIds") List<Long> tangibleIds, @Param("status") Integer status);

    @Query("SELECT MM FROM TangibleClassificationEntity MM  WHERE (MM.clientId.clientId IS NULL OR MM.clientId.clientId=:clientId )  "
            + "AND (:tangibleCode IS NULL OR MM.code like :tangibleCode ) "
            + "AND  (:tangibleName IS NULL OR MM.name like :tangibleName ) " + "AND MM.tangibleClassificationEntity IS NULL "
            + "AND MM.status=:status ORDER BY MM.code")
    List<TangibleClassificationEntity> findTangible(@Param("clientId") Long clientId,
            @Param("tangibleCode") String tangibleCode, @Param("tangibleName") String tangibleName,
            @Param("status") Integer status);

    @Query("SELECT MM FROM TangibleClassificationEntity MM  WHERE MM.clientId.clientId=:clientId  AND (:tangibleCode IS NULL OR MM.code like :tangibleCode ) "
            + "AND  (:tangibleName IS NULL OR MM.name like :tangibleName )  "
            + "AND MM.tangibleClassificationEntity IS NULL " + "AND MM.status=:status ORDER BY MM.code")
    List<TangibleClassificationEntity> findTangibleForClient(@Param("clientId") Long clientId,
            @Param("tangibleCode") String tangibleCode, @Param("tangibleName") String tangibleName,
            @Param("status") Integer status);
    
    @Query("SELECT T FROM TangibleClassificationEntity T WHERE T.clientId.clientId=:crmId AND T.code=:code")
    public TangibleClassificationEntity findBy(@Param("crmId") Long crmId, @Param("code") String code);

}
