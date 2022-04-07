package com.rjtech.projectlib.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;


public interface ProjStoreStockRepositoryCopy extends JpaRepository<ProjStoreStockMstrEntity, Long> {

}