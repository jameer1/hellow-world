package com.rjtech.register.repository.emp;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.emp.model.EmpPayQuestionaryMstrEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface EmpPayQuestionMstrRepository extends RegisterBaseRepository<EmpPayQuestionaryMstrEntity, Long> {

    @Query("SELECT T FROM EmpPayQuestionaryMstrEntity T WHERE  ( T.clientId.clientId=:clientId OR T.clientId.clientId IS NULL ) AND T.status=:status")
    public List<EmpPayQuestionaryMstrEntity> findEmpPayQuestions(@Param("clientId") Long clientId,
            @Param("status") Integer status);

}
