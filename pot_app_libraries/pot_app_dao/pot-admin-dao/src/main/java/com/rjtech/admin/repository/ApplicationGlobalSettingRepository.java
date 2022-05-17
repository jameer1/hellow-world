package com.rjtech.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rjtech.role.model.ApplicationGlobalSettingEntity;
@Repository
public interface ApplicationGlobalSettingRepository extends JpaRepository<ApplicationGlobalSettingEntity, Long> {

}
