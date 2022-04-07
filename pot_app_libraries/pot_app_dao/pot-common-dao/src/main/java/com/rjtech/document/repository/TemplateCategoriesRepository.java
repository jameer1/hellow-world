package com.rjtech.document.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.document.model.TemplateCategoriesEntity;

@Repository
public interface TemplateCategoriesRepository extends JpaRepository<TemplateCategoriesEntity, Long> {
	@Modifying
    @Query("UPDATE TemplateCategoriesEntity TCE SET TCE.categoryName=:categoryName  where TCE.categoryId=:categoryId")
	void updateTemplateCategory( @Param("categoryId") Long categoryId , @Param("categoryName") String categoryName );
	
	@Query("SELECT TEMPCATS FROM TemplateCategoriesEntity TEMPCATS  WHERE TEMPCATS.categoryMstrId.categoryMstrId=:categoryMstrId")
	List<TemplateCategoriesEntity> findByCategoryMstrId( @Param("categoryMstrId") Integer categoryMstrId );
	
	@Query("SELECT TEMPCATSL FROM TemplateCategoriesEntity TEMPCATSL  WHERE TEMPCATSL.categoryId=:categoryId")
	TemplateCategoriesEntity findCategoriesByCategoryId( @Param("categoryId") Long categoryId );
	
	@Query("SELECT TEMPPCATS FROM TemplateCategoriesEntity TEMPPCATS  WHERE TEMPPCATS.categoryName=:categoryName")
	List<TemplateCategoriesEntity> findPCategoriesByCategoryName( @Param("categoryName") String categoryName );
	
	@Query("SELECT TEMPCATSL FROM TemplateCategoriesEntity TEMPCATSL  WHERE TEMPCATSL.categoryName=:categoryName AND TEMPCATSL.categoryMstrId.categoryMstrId=:categoryMstrId")
	TemplateCategoriesEntity findCategoriesByCategoryNameAndMstrId( @Param("categoryName") String categoryName, @Param("categoryMstrId") Integer categoryMstrId );
}
