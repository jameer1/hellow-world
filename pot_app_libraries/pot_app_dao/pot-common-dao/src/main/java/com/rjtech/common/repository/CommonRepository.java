package com.rjtech.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rjtech.common.model.UserMstrEntity;

@Repository
public interface CommonRepository extends JpaRepository<UserMstrEntity, Long> {

}
