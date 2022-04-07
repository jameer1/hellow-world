package com.rjtech.document.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.document.dto.ProjDocFolderTO;

public class ProjDocFolderDeactiveReq extends ProjDocFolderTO {
    private static final long serialVersionUID = -7671175298681215590L;
    private List<Long> pojDocFolderIds = new ArrayList<Long>();
    
    public List<Long> getPojDocFolderIds() {
        return pojDocFolderIds;
    }
    public void setPojDocFolderIds(List<Long> pojDocFolderIds) {
        this.pojDocFolderIds = pojDocFolderIds;
    }
}
