package com.rjtech.centrallib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.AddressTO;
import com.rjtech.centrallib.dto.BusinessActivityTO;
import com.rjtech.common.resp.AppResp;


public class AddressResp extends AppResp {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<AddressTO> addressTOs = null;

    public AddressResp() {
        addressTOs = new ArrayList<AddressTO>(5);
    }

    public List<AddressTO> getAddressTOs() {
        return addressTOs;
    }

    public void setAddressTOs(List<AddressTO> addressTOs) {
        this.addressTOs = addressTOs;
    }

}
