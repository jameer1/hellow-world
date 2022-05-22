	package com.rjtech.procurement.dto;

import java.math.BigDecimal;
import java.util.Date;

public class SearchManpowerDocketTO {
	
		private Long docketId;
		private Date docketDate;
		private Date finishDate;
		private String itemCode;
		private String itemDesc;
		private String unitOfMeasure;
		private Long quantity;
		private Integer processQuantity;
		private Integer claimedQuantity;
		private Integer progressQuantity;
		private Long rate;
		private BigDecimal amount;
		private String receiverComments;
		private String materildDtoId;
		public Long getDocketId() {
			return docketId;
		}
		public void setDocketId(Long docketId) {
			this.docketId = docketId;
		}
		public Date getDocketDate() {
			return docketDate;
		}
		public void setDocketDate(Date docketDate) {
			this.docketDate = docketDate;
		}
		public Date getFinishDate() {
			return finishDate;
		}
		public void setFinishDate(Date finishDate) {
			this.finishDate = finishDate;
		}
		public String getItemCode() {
			return itemCode;
		}
		public void setItemCode(String itemCode) {
			this.itemCode = itemCode;
		}
		public String getItemDesc() {
			return itemDesc;
		}
		public void setItemDesc(String itemDesc) {
			this.itemDesc = itemDesc;
		}
		public String getUnitOfMeasure() {
			return unitOfMeasure;
		}
		public void setUnitOfMeasure(String unitOfMeasure) {
			this.unitOfMeasure = unitOfMeasure;
		}
		public Long getQuantity() {
			return quantity;
		}
		public void setQuantity(Long quantity) {
			this.quantity = quantity;
		}
		public Integer getProcessQuantity() {
			return processQuantity;
		}
		public void setProcessQuantity(Integer processQuantity) {
			this.processQuantity = processQuantity;
		}
		public Integer getClaimedQuantity() {
			return claimedQuantity;
		}
		public void setClaimedQuantity(Integer claimedQuantity) {
			this.claimedQuantity = claimedQuantity;
		}
		public Integer getProgressQuantity() {
			return progressQuantity;
		}
		public void setProgressQuantity(Integer progressQuantity) {
			this.progressQuantity = progressQuantity;
		}
		public Long getRate() {
			return rate;
		}
		public void setRate(Long rate) {
			this.rate = rate;
		}
		public BigDecimal getAmount() {
			return amount;
		}
		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}
		public String getReceiverComments() {
			return receiverComments;
		}
		public void setReceiverComments(String receiverComments) {
			this.receiverComments = receiverComments;
		}
		public String getMaterildDtoId() {
			return materildDtoId;
		}
		public void setMaterildDtoId(String materildDtoId) {
			this.materildDtoId = materildDtoId;
		}
		
		



}
