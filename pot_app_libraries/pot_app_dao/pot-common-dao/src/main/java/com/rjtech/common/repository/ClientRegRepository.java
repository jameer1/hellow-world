package com.rjtech.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rjtech.common.model.ClientRegEntity;

@Repository
public interface ClientRegRepository extends JpaRepository<ClientRegEntity, Long> {

}
