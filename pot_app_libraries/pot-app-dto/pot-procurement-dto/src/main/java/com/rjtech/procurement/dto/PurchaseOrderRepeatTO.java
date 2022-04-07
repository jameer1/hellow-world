package com.rjtech.procurement.dto;

import java.util.Date;

public class PurchaseOrderRepeatTO {

    private Long id;

    private Long parentPOId;

    private Long empId;

    private Long materialId;

    private Long sowId;

    private Long plantId;

    private Long serviceId;

    private String deliveryPlace;

    private Long quantity;

    private Date startDate;

    private Date finishDate;

    private Integer isBid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentPOId() {
        return parentPOId;
    }

    public void setParentPOId(Long parentPOId) {
        this.parentPOId = parentPOId;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Long getSowId() {
        return sowId;
    }

    public void setSowId(Long sowId) {
        this.sowId = sowId;
    }

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Integer getIsBid() {
        return isBid;
    }

    public void setIsBid(Integer isBid) {
        this.isBid = isBid;
    }
}
