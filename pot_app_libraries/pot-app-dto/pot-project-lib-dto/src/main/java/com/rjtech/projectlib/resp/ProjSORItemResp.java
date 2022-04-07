package com.rjtech.projectlib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjSORItemTO;

public class ProjSORItemResp extends AppResp {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<ProjSORItemTO> projSORItemTOs = null;
    private Boolean isUserOriginator;
    private Boolean isUserInternalApprover;
    private Boolean isUserExternalApprover;

    public ProjSORItemResp() {
        projSORItemTOs = new ArrayList<ProjSORItemTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjSORItemTO> getProjSORItemTOs() {
        return projSORItemTOs;
    }

    public void setProjSORItemTOs(List<ProjSORItemTO> projSORItemTOs) {
        this.projSORItemTOs = projSORItemTOs;
    }

    public Boolean getIsUserOriginator() {
        return isUserOriginator;
    }

    public void setIsUserOriginator( Boolean isUserOriginator ) {
        this.isUserOriginator = isUserOriginator;
    }
    
    public Boolean getIsUserInternalApprover() {
        return isUserInternalApprover;
    }

    public void setIsUserInternalApprover( Boolean isUserInternalApprover ) {
        this.isUserInternalApprover = isUserInternalApprover;
    }
    
    public Boolean getIsUserExternalApprover() {
        return isUserExternalApprover;
    }

    public void setIsUserExternalApprover( Boolean isUserExternalApprover ) {
        this.isUserExternalApprover = isUserExternalApprover;
    }
    
}
