package com.rjtech.user.req;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.user.dto.ClientRegTO;

public class ClientRegReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 2740448397989044791L;
    private String folderCategory;

    private ClientRegTO clientRegTO = new ClientRegTO();

    public ClientRegTO getClientRegTO() {
        return clientRegTO;
    }

    public void setClientRegTO(ClientRegTO clientRegTO) {
        this.clientRegTO = clientRegTO;
    }

    public String getFolderCategory() {
    	return folderCategory;
    }
    
    public void setFolderCategory() {
    	this.folderCategory = folderCategory;
    }
}
