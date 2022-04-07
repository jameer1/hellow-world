package com.rjtech.centrallib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.BusinessActivityMstrEntity;

@NoRepositoryBean
public interface BusinessActRepository extends CentralLibRepository<BusinessActivityMstrEntity, Long> {

    @Query("SELECT BRM FROM BusinessActivityMstrEntity BRM  WHERE (BRM.clientId IS NULL OR BRM.clientId=:clientId ) AND  BRM.status=:status ORDER BY BRM.code")
    List<BusinessActivityMstrEntity> findByClientId(@Param("clientId") Long id, @Param("status") Integer status);

}
