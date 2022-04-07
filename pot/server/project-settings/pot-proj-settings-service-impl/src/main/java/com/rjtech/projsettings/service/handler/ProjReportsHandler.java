package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.dto.ProjectReportsTO;
import com.rjtech.projsettings.model.ProjectReportsEntity;

public class ProjReportsHandler {

    public static ProjectReportsTO convertEntityToPOJO(ProjectReportsEntity entity) {
        ProjectReportsTO projectReportsTO = new ProjectReportsTO();
        projectReportsTO.setId(entity.getId());
        if (CommonUtil.objectNotNull(entity.getProjMstrEntity())) {
            projectReportsTO.setProjId(entity.getProjMstrEntity().getProjectId());
        }
        projectReportsTO.setMonth(entity.getMonth());
        projectReportsTO.setWeek(entity.getWeek());
        projectReportsTO.setYear(entity.getYear());
        projectReportsTO.setFirstHalf(entity.getFirstHalf());
        projectReportsTO.setFirstQuarter(entity.getFirstQuarter());
        projectReportsTO.setStatus(entity.getStatus());
        return projectReportsTO;
    }

    public static List<ProjectReportsEntity> convertPOJOToEntity(List<ProjectReportsTO> projectReportsTOs, long projId,
            EPSProjRepository epsProjRepository) {
        List<ProjectReportsEntity> projectReportsEntites = new ArrayList<ProjectReportsEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        ProjectReportsEntity entity = null;
        for (ProjectReportsTO projectReportsTO : projectReportsTOs) {
            entity = new ProjectReportsEntity();
            if (CommonUtil.isNonBlankLong(projId)) {
                ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projId);
                entity.setProjMstrEntity(projMstrEntity);
            }
            entity.setStatus(projectReportsTO.getStatus());
            entity.setWeek(projectReportsTO.getWeek());
            entity.setMonth(projectReportsTO.getMonth());
            entity.setYear(projectReportsTO.getYear());
            entity.setFirstHalf(projectReportsTO.getFirstHalf());
            entity.setFirstQuarter(projectReportsTO.getFirstQuarter());
            projectReportsEntites.add(entity);
        }
        return projectReportsEntites;
    }

    public static List<ProjectReportsEntity> convertPOJOToEntityExisting(List<ProjectReportsEntity> projectReports,
            List<ProjectReportsTO> projectReportsTOs, long projId, EPSProjRepository epsProjRepository) {
        List<ProjectReportsEntity> projectReportsEntites = new ArrayList<ProjectReportsEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        if (!projectReportsTOs.isEmpty()) {
            ProjectReportsTO projectReportsTO = projectReportsTOs.get(0);
            ProjectReportsEntity entity = projectReports.get(0);
            if (CommonUtil.isNonBlankLong(projId)) {
                ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projId);
                entity.setProjMstrEntity(projMstrEntity);
            }
            entity.setWeek(projectReportsTO.getWeek());
            entity.setMonth(projectReportsTO.getMonth());
            entity.setYear(projectReportsTO.getYear());
            entity.setFirstHalf(projectReportsTO.getFirstHalf());
            entity.setFirstQuarter(projectReportsTO.getFirstQuarter());
            projectReportsEntites.add(entity);
        }
        return projectReportsEntites;
    }

}
