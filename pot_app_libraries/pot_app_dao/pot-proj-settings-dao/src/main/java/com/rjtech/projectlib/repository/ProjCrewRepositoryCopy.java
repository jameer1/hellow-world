/*
 * package com.rjtech.projectlib.repository;
 * 
 * import org.springframework.data.jpa.repository.JpaRepository; import
 * org.springframework.data.jpa.repository.Query; import
 * org.springframework.data.repository.query.Param;
 * 
 * import com.rjtech.projectlib.model.ProjCrewMstrEntity;
 * 
 * //import com.rjtech.projectlib.model.ProjCrewMstrEntityCopy;
 * 
 * public interface ProjCrewRepositoryCopy extends
 * JpaRepository<ProjCrewMstrEntity, Long> {
 * 
 * @Query("SELECT CRW FROM ProjCrewMstrEntity CRW  WHERE  CRW.projId.projectId =:projectId AND CRW.desc=:crewCode"
 * ) ProjCrewMstrEntity findCrewByCrewCodeAndProjId(@Param("projectId") long
 * projectId,
 * 
 * @Param("crewCode") String crewCode);
 * 
 * }
 */