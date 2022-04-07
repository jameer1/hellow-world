package com.rjtech.rjs.apperror.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rjtech.rjs.entity.apperror.AppErrorEntity;

public interface AppErrorRepository extends JpaRepository<AppErrorEntity, String> {

}
