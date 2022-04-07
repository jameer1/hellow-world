package com.rjtech.centrallib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.centrallib.model.MaterialClassMstrEntity;

@Repository
public interface MaterialClassRepository extends CentralLibRepository<MaterialClassMstrEntity, Long> {

    @Query("SELECT T FROM MaterialClassMstrEntity T WHERE  (T.clientId.clientId=:clientId OR T.clientId.clientId IS NULL) AND T.materialClassMstrEntity IS NULL AND T.status=:status  ORDER BY  T.code")
    public List<MaterialClassMstrEntity> findAllMaterials(@Param("clientId") Long clientId,
            @Param("status") Integer status);

    @Query("SELECT T FROM MaterialClassMstrEntity T WHERE  T.clientId.clientId=:clientId AND T.materialClassMstrEntity IS NULL AND T.status=:status  ORDER BY  T.code")
    public List<MaterialClassMstrEntity> findAllMaterialsForClient(@Param("clientId") Long clientId,
            @Param("status") Integer status);

    @Query("SELECT T FROM MaterialClassMstrEntity T WHERE  (T.clientId.clientId=:clientId OR T.clientId IS NULL) AND T.materialClassMstrEntity IS NOT NULL AND T.item=true AND T.status=:status  ORDER BY  T.code")
    public List<MaterialClassMstrEntity> findMaterialItems(@Param("clientId") Long clientId,
            @Param("status") Integer status);

    @Query("SELECT T FROM MaterialClassMstrEntity T WHERE  T.clientId.clientId=:clientId AND T.materialClassMstrEntity IS NOT NULL AND T.item=true AND T.status=:status  ORDER BY  T.code")
    public List<MaterialClassMstrEntity> findMaterialItemsForClient(@Param("clientId") Long clientId,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE MaterialClassMstrEntity T  SET T.status=:status  where T.id in :materialIds or T.materialClassMstrEntity.id in :materialIds")
    void deactivateMaterialGroups(@Param("materialIds") List<Long> materialIds, @Param("status") Integer status);

    @Query("SELECT MM FROM MaterialClassMstrEntity MM  WHERE (MM.clientId.clientId IS NULL OR MM.clientId.clientId=:clientId )  "
            + "AND (:materialCode IS NULL OR MM.code like :materialCode ) "
            + "AND  (:materialName IS NULL OR MM.name like :materialName ) " + "AND MM.materialClassMstrEntity IS NULL "
            + "AND MM.status=:status ORDER BY MM.code")
    List<MaterialClassMstrEntity> findMaterial(@Param("clientId") Long clientId,
            @Param("materialCode") String materialCode, @Param("materialName") String materialName,
            @Param("status") Integer status);

    @Query("SELECT MM FROM MaterialClassMstrEntity MM  WHERE MM.clientId.clientId=:clientId  AND (:materialCode IS NULL OR MM.code like :materialCode ) "
            + "AND  (:materialName IS NULL OR MM.name like :materialName )  "
            + "AND MM.materialClassMstrEntity IS NULL " + "AND MM.status=:status ORDER BY MM.code")
    List<MaterialClassMstrEntity> findMaterialForClient(@Param("clientId") Long clientId,
            @Param("materialCode") String materialCode, @Param("materialName") String materialName,
            @Param("status") Integer status);
    
    @Query("SELECT T FROM MaterialClassMstrEntity T WHERE T.clientId.clientId=:crmId AND T.code=:code AND T.status=:status")
    public MaterialClassMstrEntity findBy(@Param("crmId") Long crmId, @Param("code") String code, @Param("status") Integer status);

}