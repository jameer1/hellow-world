package com.rjtech.procurement.req;

public class ProcurementDocGetReq extends ProcurementGetReq {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private Long documentId = null;

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

}
