package com.rjtech.rjs.persistence.entity;

import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.rjtech.rjs.persistence.parser.RJSPrimaryKeyGenerator;

@MappedSuperclass
public abstract class RJSAbstractEntity implements RJSBaseEntity {

    @PrePersist
    public void onPrePersist() {
        RJSPrimaryKeyGenerator.setPrimaryKey(this);

    }

    @PostPersist
    public void onPostPersist() {

    }

    @PostLoad
    public void onPostLoad() {
    }

    @PreUpdate
    public void onPreUpdate() {

    }

    @PostUpdate
    public void onPostUpdate() {

    }

    @PreRemove
    public void onPreRemove() {

    }

    @PostRemove
    public void onPostRemove() {

    }

}
