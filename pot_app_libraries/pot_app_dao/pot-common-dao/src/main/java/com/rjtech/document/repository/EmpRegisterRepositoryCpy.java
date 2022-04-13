package com.rjtech.document.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
//import com.rjtech.register.model.EmpRegisterDtlEntityCopy;

public interface EmpRegisterRepositoryCpy extends CrudRepository<EmpRegisterDtlEntity, Long> {

    @Query("SELECT erd FROM EmpRegisterDtlEntity erd where erd.clientId.clientId= :clientId and erd.status=:status "
            + " and erd.id NOT IN (select coalesce(empRegId,0) from UserMstrEntity usr where status=:status)")
    public List<EmpRegisterDtlEntity> findNewEmployees(@Param("clientId") Long clientId,
            @Param("status") Integer status);
}
