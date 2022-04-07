package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.dto.ProjNoteBookTO;
import com.rjtech.projsettings.model.ProjNoteBookEntity;

public class ProjNoteBookHandler {
    public static ProjNoteBookTO convertEntityToPOJO(ProjNoteBookEntity entity) {
        ProjNoteBookTO projNoteBookTO = new ProjNoteBookTO();

        projNoteBookTO.setId(entity.getId());
        projNoteBookTO.setProjId(entity.getProjMstrEntity().getProjectId());
        if (CommonUtil.objectNotNull(entity.getClientId())) {
            projNoteBookTO.setClientId(entity.getClientId().getClientId());
        }
        projNoteBookTO.setTopic(entity.getTopic());
        projNoteBookTO.setDescription(entity.getDescription());

        projNoteBookTO.setStatus(entity.getStatus());

        return projNoteBookTO;

    }

    public static List<ProjNoteBookEntity> convertPOJOToEntity(List<ProjNoteBookTO> projNoteBookTOs,
            EPSProjRepository epsProjRepository) {
        List<ProjNoteBookEntity> projNoteBookEntites = new ArrayList<ProjNoteBookEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        ProjNoteBookEntity entity = null;
        for (ProjNoteBookTO projNoteBookTO : projNoteBookTOs) {
            entity = new ProjNoteBookEntity();

            if (CommonUtil.isNonBlankLong(projNoteBookTO.getId())) {
                entity.setId(projNoteBookTO.getId());
            }
            ProjMstrEntity proj = epsProjRepository.findOne(projNoteBookTO.getProjId());
            entity.setProjMstrEntity(proj);
            entity.setTopic(projNoteBookTO.getTopic());
            entity.setDescription(projNoteBookTO.getDescription());

            entity.setStatus(projNoteBookTO.getStatus());

            projNoteBookEntites.add(entity);
        }
        return projNoteBookEntites;
    }

}
