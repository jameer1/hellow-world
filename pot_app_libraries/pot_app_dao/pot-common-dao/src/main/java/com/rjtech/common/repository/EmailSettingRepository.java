package com.rjtech.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.common.model.EmailSettingEntity;

@Repository
public interface EmailSettingRepository extends JpaRepository<EmailSettingEntity, Long> {

    @Query("SELECT EMS FROM EmailSettingEntity EMS where (EMS.clientId.clientId IS NULL  or  EMS.clientId.clientId=:clientId ) AND EMS.status=:status")
    public List<EmailSettingEntity> findEmailSettings(@Param("clientId") Long clientId,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE EmailSettingEntity EMS SET EMS.status=:status WHERE EMS.id in :emailSettingIds")
    public void deactivateEmailSettings(@Param("emailSettingIds") List<Long> emailSettingIds,
            @Param("status") Integer status);

    @Query("SELECT EMS FROM EmailSettingEntity EMS")
    public List<EmailSettingEntity> findAllEmailSettings();

    @Query("SELECT EMS FROM EmailSettingEntity EMS where EMS.clientId.clientId=:clientId AND EMS.status=:status")
    public List<EmailSettingEntity> findEmailSettingsByClient(@Param("clientId") Long clientId,
            @Param("status") Integer status);

}
