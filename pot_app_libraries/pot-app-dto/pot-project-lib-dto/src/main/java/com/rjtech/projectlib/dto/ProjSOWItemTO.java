package com.rjtech.projectlib.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rjtech.centrallib.dto.MeasureUnitTO;
import com.rjtech.common.dto.ProjectTO;
import com.rjtech.centrallib.dto.TangibleClassTO;

public class ProjSOWItemTO extends ProjectTO {
    private static final long serialVersionUID = 2950084862079755848L;

    private Long id;
    private String code;
    private String name;
    private boolean item;
    private boolean itemParent = false;
    private boolean expand = false;
    private Long parentId;
    private String parentName;
    private String comments;
    private Long measureId;
    private Long manHours;

    private String startDate;
    private String finishDate;
    private String actualStartDate;
    private String actualFinishDate;

    private BigDecimal quantity;
    private BigDecimal originalQty;

    private BigDecimal revisedQty;
    private BigDecimal actualQty;
    public Long resourceCurveId;
    private MeasureUnitTO measureUnitTO;
    private Long sowId;
    private Long soeId;
    private Long sorId;
    private Long projCostId;
    private Long tangibleItemId;
    private BigDecimal balQty;

    private ProjSOEItemTO projSOEItemTO = new ProjSOEItemTO();
    private ProjSORItemTO projSORItemTO = new ProjSORItemTO();
    private ProjCostItemTO projCostItemTO = new ProjCostItemTO();
    private TangibleClassTO tangibleClassTO = new TangibleClassTO();
    private List<ProjSOWItemTO> childSOWItemTOs = new ArrayList<ProjSOWItemTO>();
    private Date minStartDateOfBaseline;
    private Date maxFinishDateOfBaseline;

    public BigDecimal getOriginalQty() {
        return originalQty;
    }

    public void setOriginalQty(BigDecimal originalQty) {
        this.originalQty = originalQty;
    }

    public String getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(String actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public String getActualFinishDate() {
        return actualFinishDate;
    }

    public void setActualFinishDate(String actualFinishDate) {
        this.actualFinishDate = actualFinishDate;
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

    public boolean isItem() {
        return item;
    }

    public void setItem(boolean item) {
        this.item = item;
    }

    public boolean isItemParent() {
        return itemParent;
    }

    public void setItemParent(boolean itemParent) {
        this.itemParent = itemParent;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public Long getSoeId() {
        return soeId;
    }

    public void setSoeId(Long soeId) {
        this.soeId = soeId;
    }

    public Long getSorId() {
        return sorId;
    }

    public void setSorId(Long sorId) {
        this.sorId = sorId;
    }

    public Long getSowId() {
        return sowId;
    }

    public void setSowId(Long sowId) {
        this.sowId = sowId;
    }

    public Long getProjCostId() {
        return projCostId;
    }

    public void setProjCostId(Long projCostId) {
        this.projCostId = projCostId;
    }
    
    public Long getTangibleItemId() {
    	return tangibleItemId;
    }
    
    public void setTangibleItemId(Long tangibleItemId) {
    	this.tangibleItemId = tangibleItemId;
    }

    public ProjSOEItemTO getProjSOEItemTO() {
        return projSOEItemTO;
    }

    public void setProjSOEItemTO(ProjSOEItemTO projSOEItemTO) {
        this.projSOEItemTO = projSOEItemTO;
    }

    public ProjSORItemTO getProjSORItemTO() {
        return projSORItemTO;
    }

    public void setProjSORItemTO(ProjSORItemTO projSORItemTO) {
        this.projSORItemTO = projSORItemTO;
    }

    public ProjCostItemTO getProjCostItemTO() {
        return projCostItemTO;
    }

    public void setProjCostItemTO(ProjCostItemTO projCostItemTO) {
        this.projCostItemTO = projCostItemTO;
    }
    
    public TangibleClassTO getTangibleClassTO() {
        return tangibleClassTO;
    }

    public void setTangibleClassTO(TangibleClassTO tangibleClassTO) {
        this.tangibleClassTO = tangibleClassTO;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getMeasureId() {
        return measureId;
    }

    public void setMeasureId(Long measureId) {
        this.measureId = measureId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public BigDecimal getRevisedQty() {
        return revisedQty;
    }

    public void setRevisedQty(BigDecimal revisedQty) {
        this.revisedQty = revisedQty;
    }

    public BigDecimal getActualQty() {
        return actualQty;
    }

    public void setActualQty(BigDecimal actualQty) {
        this.actualQty = actualQty;
    }
    
    public BigDecimal getBalQty() {
        return balQty;
    }

    public void setBalQty(BigDecimal balQty) {
        this.balQty = balQty;
    }
    public MeasureUnitTO getMeasureUnitTO() {
        return measureUnitTO;
    }

    public void setMeasureUnitTO(MeasureUnitTO measureUnitTO) {
        this.measureUnitTO = measureUnitTO;
    }

    public List<ProjSOWItemTO> getChildSOWItemTOs() {
        return childSOWItemTOs;
    }

    public void setChildSOWItemTOs(List<ProjSOWItemTO> childSOWItemTOs) {
        this.childSOWItemTOs = childSOWItemTOs;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProjSOWItemTO other = (ProjSOWItemTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public Long getResourceCurveId() {
        return resourceCurveId;
    }

    public void setResourceCurveId(Long resourceCurveId) {
        this.resourceCurveId = resourceCurveId;
    }

	public Date getMinStartDateOfBaseline() {
		return minStartDateOfBaseline;
	}

	public void setMinStartDateOfBaseline(Date minStartDateOfBaseline) {
		this.minStartDateOfBaseline = minStartDateOfBaseline;
	}

	public Date getMaxFinishDateOfBaseline() {
		return maxFinishDateOfBaseline;
	}

	public void setMaxFinishDateOfBaseline(Date maxFinishDateOfBaseline) {
		this.maxFinishDateOfBaseline = maxFinishDateOfBaseline;
	}

	public Long getManHours() {
		return manHours;
	}

	public void setManHours(Long manHours) {
		this.manHours = manHours;
	}
	public String toString() {
		return "id:"+id+" code:"+code+" sowId:"+sowId+" itemParent:"+itemParent+" name:"+name;
	}
}
