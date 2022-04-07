package com.rjtech.common.service;

public interface POTSequenceGeneratorService {

    String getSequenceGenerator(Long projId, String modulePrefix, String moduleCode);

    String getClientSequenceGenerator(Long clientId, String modulePrefix, String moduleCode);

}
