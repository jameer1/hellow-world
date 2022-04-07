package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjNoteBookTO;

public class ProjNoteBookSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1742517094310387320L;
    private List<ProjNoteBookTO> projNoteBookTOs = new ArrayList<ProjNoteBookTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjNoteBookTO> getProjNoteBookTOs() {
        return projNoteBookTOs;
    }

    public void setProjNoteBookTOs(List<ProjNoteBookTO> projNoteBookTOs) {
        this.projNoteBookTOs = projNoteBookTOs;
    }

}
