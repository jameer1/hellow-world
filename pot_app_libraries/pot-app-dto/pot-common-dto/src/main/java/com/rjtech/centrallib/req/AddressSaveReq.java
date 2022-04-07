package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.AddressTO;


public class AddressSaveReq extends CompanyGetReq {
    private static final long serialVersionUID = -3763356942184775462L;

    private List<AddressTO> addressTOs = new ArrayList<AddressTO>(5);

    public List<AddressTO> getAddressTOs() {
        return addressTOs;
    }

    public void setAddressTOs(List<AddressTO> addressTOs) {
        this.addressTOs = addressTOs;
    }

}