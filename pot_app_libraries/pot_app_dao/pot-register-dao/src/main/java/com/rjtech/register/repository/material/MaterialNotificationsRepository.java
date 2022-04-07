package com.rjtech.register.repository.material;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.register.material.model.MaterialNotificationsEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

@Repository("registerMaterialNotificationsRepository")
public interface MaterialNotificationsRepository extends RegisterBaseRepository<MaterialNotificationsEntity, Long> {

    @Query("SELECT T FROM MaterialNotificationsEntity T WHERE T.notificationStatus=:notificationStatus "
            + "AND T.projId.projectId=:projId AND  T.status=:status AND "
            + "T.toStore.id = :toStoreId")
    List<MaterialNotificationsEntity> findMaterialNotifications(@Param("notificationStatus") String notificationStatus,
            @Param("projId") Long projId, @Param("toStoreId") Long toStoreId,
            @Param("status") Integer status);

    @Query("SELECT T FROM MaterialNotificationsEntity T WHERE T.notificationStatus=:notificationStatus"
            + "  AND T.fromProjId.projectId = :projId AND T.toProjId.projectId = :toProjId "
            + "  AND ( (T.fromStore.id IS NOT NULL AND T.fromStore.id = :fromStoreId) OR (T.fromStoreProject.id IS NOT NULL AND T.fromStoreProject.id = :fromStoreProjectId) )"
            + "  AND ( (T.toStore.id IS NOT NULL AND T.toStore.id = :toStoreId) OR (T.toStoreProject.id IS NOT NULL AND T.toStoreProject.id = :toStoreProjectId) )"
            + " AND T.status = :status")
    List<MaterialNotificationsEntity> findMaterialNotificationsBy4(@Param("notificationStatus") String notificationStatus,
            @Param("projId") Long projId, @Param("toProjId") Long toProjId,
            @Param("fromStoreId") Long fromStoreId, @Param("fromStoreProjectId") Long fromStoreProjectId,
            @Param("toStoreId") Long toStoreId, @Param("toStoreProjectId") Long toStoreProjectId,
            @Param("status") Integer status);

}
