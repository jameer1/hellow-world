package com.rjtech.rjs.persistence.config;

public class AuditConfigBean {
    private String createdByColumn;
    private String createdDateColumn;
    private String modifiedByColumn;
    private String modifiedDateColumn;

    public AuditConfigBean(String createdByColumn, String createdDateColumn, String modifiedByColumn,
            String modifiedDateColumn) {
        this.createdByColumn = createdByColumn;
        this.createdDateColumn = createdDateColumn;
        this.modifiedByColumn = modifiedByColumn;
        this.modifiedDateColumn = modifiedDateColumn;
    }

    public AuditConfigBean() {
        this(null, null, null, null);
    }

    public String getCreatedByColumn() {
        return createdByColumn;
    }

    public void setCreatedByColumn(String createdByColumn) {
        this.createdByColumn = createdByColumn;
    }

    public String getCreatedDateColumn() {
        return createdDateColumn;
    }

    public void setCreatedDateColumn(String createdDateColumn) {
        this.createdDateColumn = createdDateColumn;
    }

    public String getModifiedByColumn() {
        return modifiedByColumn;
    }

    public void setModifiedByColumn(String modifiedByColumn) {
        this.modifiedByColumn = modifiedByColumn;
    }

    public String getModifiedDateColumn() {
        return modifiedDateColumn;
    }

    public void setModifiedDateColumn(String modifiedDateColumn) {
        this.modifiedDateColumn = modifiedDateColumn;
    }
}
