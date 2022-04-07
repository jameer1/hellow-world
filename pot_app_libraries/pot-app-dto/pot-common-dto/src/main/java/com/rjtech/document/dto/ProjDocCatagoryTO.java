
package com.rjtech.document.dto;

import com.rjtech.common.dto.ClientTO;

public class ProjDocCatagoryTO extends ClientTO {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private String name;

    public ProjDocCatagoryTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}