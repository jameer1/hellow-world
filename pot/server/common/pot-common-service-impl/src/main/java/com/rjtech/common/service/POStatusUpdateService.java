package com.rjtech.common.service;

public interface POStatusUpdateService {

    public void updatePurchaseOrderStatus(Long schCmpId, String type, double receviedQty);

}
