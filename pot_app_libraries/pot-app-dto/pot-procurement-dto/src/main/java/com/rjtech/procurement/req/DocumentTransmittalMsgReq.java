package com.rjtech.procurement.req;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.procurement.dto.DocumentTransmittalTO;

public class DocumentTransmittalMsgReq extends ProjectTO {

    private static final long serialVersionUID = -6674459895755442299L;

    private DocumentTransmittalTO documentTransmittalTO = new DocumentTransmittalTO();

    public DocumentTransmittalTO getDocumentTransmittalTO() {
        return documentTransmittalTO;
    }

    public void setDocumentTransmittalTO(DocumentTransmittalTO documentTransmittalTO) {
        this.documentTransmittalTO = documentTransmittalTO;
    }

}
