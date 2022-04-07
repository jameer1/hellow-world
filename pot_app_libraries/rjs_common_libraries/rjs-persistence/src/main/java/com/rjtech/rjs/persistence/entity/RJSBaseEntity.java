package com.rjtech.rjs.persistence.entity;

public interface RJSBaseEntity {

    void onPrePersist();

    void onPostPersist();

    void onPostLoad();

    void onPreUpdate();

    void onPostUpdate();

    void onPreRemove();

    void onPostRemove();
}
