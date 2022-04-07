package com.rjtech.document.req;

import java.io.Serializable;
import java.util.List;

public class ProjDocDeleteReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Long> fileIds;

    public List<Long> getFileIds() {
        return fileIds;
    }

    public void setFileIds(List<Long> fileIds) {
        this.fileIds = fileIds;
    }

}
