package com.rjtech.register.fixedassets.resp;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.DocumentsDtlTO;
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentsResp extends AppResp {
    private static final long serialVersionUID = 1L;    
    
    private List<DocumentsDtlTO> documentsDtlTOs = new ArrayList<DocumentsDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<DocumentsDtlTO> getDocumentsDtlTOs() {
        return documentsDtlTOs;
    }

    public void setDocumentsDtlTOs(List<DocumentsDtlTO> documentsDtlTOs) {
        this.documentsDtlTOs = documentsDtlTOs;
    }
   
}
