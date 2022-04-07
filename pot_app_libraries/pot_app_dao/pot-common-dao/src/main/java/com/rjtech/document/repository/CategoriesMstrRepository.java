package com.rjtech.document.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.document.model.CategoriesMstrEntity;

@Repository
public interface CategoriesMstrRepository extends JpaRepository<CategoriesMstrEntity, Integer> {
	
	@Query("SELECT CATSMSTR FROM CategoriesMstrEntity CATSMSTR  WHERE CATSMSTR.categoryMstrId=:categoryMstrId")
	CategoriesMstrEntity findByCategoryMstrId( @Param("categoryMstrId") Integer categoryMstrId );
}
