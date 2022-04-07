package com.rjtech.register.repository.plant;

import org.springframework.data.repository.CrudRepository;

import com.rjtech.register.plant.model.LeaveNormalTimeEntityCopy;

public interface ProjLeaveRequestRepositoryCopy extends CrudRepository<LeaveNormalTimeEntityCopy, Long> {
}