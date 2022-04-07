package com.rjtech.document.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;

import com.rjtech.document.model.ProjectTemplatesProposalEntity;

@Repository
public interface ProjectTemplatesProposalRepository extends JpaRepository<ProjectTemplatesProposalEntity, Long> {
	
	@Query("SELECT PTEMPSPROPOSAL FROM ProjectTemplatesProposalEntity PTEMPSPROPOSAL WHERE PTEMPSPROPOSAL.crmId.clientId=:crmId")
	List<ProjectTemplatesProposalEntity> findProposalsByCrmId( @Param("crmId") Long crmId );
	
	@Modifying
    @Query("UPDATE ProjectTemplatesProposalEntity PTEMPSPROPOSAL SET PTEMPSPROPOSAL.status=:status  where PTEMPSPROPOSAL.proposalId=:proposalId")
    void updateProposalStatusById( @Param("proposalId") Long proposalId , @Param("status") String status );
}
