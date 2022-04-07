package com.rjtech.procurement.resp;

import com.rjtech.common.resp.AppResp;
import com.rjtech.procurement.dto.DocumentTransmittalTO;

public class DocumentTransmittalMsgResp extends AppResp {

    private static final long serialVersionUID = 2331218655814518146L;
    private DocumentTransmittalTO documentTransmittalTO = null;

    public DocumentTransmittalTO getDocumentTransmittalTO() {
        return documentTransmittalTO;
    }

    public void setDocumentTransmittalTO(DocumentTransmittalTO documentTransmittalTO) {
        this.documentTransmittalTO = documentTransmittalTO;
    }

}
