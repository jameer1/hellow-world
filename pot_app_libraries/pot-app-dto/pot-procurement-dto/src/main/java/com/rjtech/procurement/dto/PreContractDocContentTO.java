package com.rjtech.procurement.dto;

import com.rjtech.common.dto.ProjectTO;

public class PreContractDocContentTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 2716516790559054029L;

    private Long id;
    private Long preContractDocId;
    private byte[] content;

    public Long getId() {
        return id;
    }

    public Long getPreContractDocId() {
        return preContractDocId;
    }

    public void setPreContractDocId(Long preContractDocId) {
        this.preContractDocId = preContractDocId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

}
