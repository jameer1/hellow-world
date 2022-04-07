package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjNoteBookTO;

public class ProjNoteBookResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<ProjNoteBookTO> projNoteBookTOs = null;

    public ProjNoteBookResp() {
        projNoteBookTOs = new ArrayList<ProjNoteBookTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjNoteBookTO> getProjNoteBookTOs() {
        return projNoteBookTOs;
    }

    public void setProjNoteBookTOs(List<ProjNoteBookTO> projNoteBookTOs) {
        this.projNoteBookTOs = projNoteBookTOs;
    }

}
