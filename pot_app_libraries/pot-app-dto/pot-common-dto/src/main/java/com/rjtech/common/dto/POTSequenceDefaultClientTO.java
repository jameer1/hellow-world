package com.rjtech.common.dto;

public class POTSequenceDefaultClientTO {

    private static final long serialVersionUID = 7826246974311588537L;
    private Long id;
    private String moduleCode;
    private Integer status;
    private Integer seqCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSeqCount() {
        return seqCount;
    }

    public void setSeqCount(Integer seqCount) {
        this.seqCount = seqCount;
    }

}
