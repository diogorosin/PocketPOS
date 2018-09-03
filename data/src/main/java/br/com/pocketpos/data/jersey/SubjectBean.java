package br.com.pocketpos.data.jersey;

import java.io.Serializable;

public class SubjectBean implements Serializable{

    private Integer identifier;

    private Boolean active;

    private Integer level;

    public Integer getIdentifier() {

        return identifier;

    }

    public void setIdentifier(Integer identifier) {

        this.identifier = identifier;

    }

    public Boolean getActive() {

        return active;

    }

    public void setActive(Boolean active) {

        this.active = active;

    }

    public Integer getLevel() {

        return level;

    }

    public void setLevel(Integer level) {

        this.level = level;

    }

}