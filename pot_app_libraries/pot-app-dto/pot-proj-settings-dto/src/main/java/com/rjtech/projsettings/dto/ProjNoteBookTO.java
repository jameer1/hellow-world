package com.rjtech.projsettings.dto;

import com.rjtech.common.dto.ProjectTO;

public class ProjNoteBookTO extends ProjectTO {

    private static final long serialVersionUID = 8845591830410093029L;

    private Long id;
    private String topic;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
