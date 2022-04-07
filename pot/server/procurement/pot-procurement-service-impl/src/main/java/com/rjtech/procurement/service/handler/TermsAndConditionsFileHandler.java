package com.rjtech.procurement.service.handler;

import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.service.handler.ProjDocFileHandler;
import com.rjtech.procurement.dto.TermsAndConditionsFileDetailsTo;
import com.rjtech.procurement.model.TermsAndConditionsFileEntity;

public class TermsAndConditionsFileHandler {

    private TermsAndConditionsFileHandler() {

    }

    public static TermsAndConditionsFileDetailsTo convertEntityToPOJO(
            TermsAndConditionsFileEntity termsAndConditionsFileEntity) {

        TermsAndConditionsFileDetailsTo termsAndConditionsFileDetailsTo = new TermsAndConditionsFileDetailsTo();
        termsAndConditionsFileDetailsTo.setId(termsAndConditionsFileEntity.getId());
        ProjDocFileEntity file = termsAndConditionsFileEntity.getFile();

        termsAndConditionsFileDetailsTo.setProjDocFileId(file.getId());
        termsAndConditionsFileDetailsTo.setName(file.getName());
        termsAndConditionsFileDetailsTo.setFileSize(file.getFileSize());
        termsAndConditionsFileDetailsTo.setFileType(file.getFileType());
        termsAndConditionsFileDetailsTo.setCategory(file.getCategory());
        termsAndConditionsFileDetailsTo.setFileStatus(file.getFileStatus());
        termsAndConditionsFileDetailsTo.setCode(ProjDocFileHandler.generateCode(file));
        termsAndConditionsFileDetailsTo.setVersion(file.getVersion());

        return termsAndConditionsFileDetailsTo;

    }

}
