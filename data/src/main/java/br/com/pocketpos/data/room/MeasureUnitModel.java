package br.com.pocketpos.data.room;

public class MeasureUnitModel {

    private Integer identifier;

    private String denomination;

    private String acronym;

    private Integer group;

    public Integer getIdentifier() {

        return identifier;

    }

    public void setIdentifier(Integer identifier) {

        this.identifier = identifier;

    }

    public String getDenomination() {

        return denomination;

    }

    public void setDenomination(String denomination) {

        this.denomination = denomination;

    }

    public String getAcronym() {

        return acronym;

    }

    public void setAcronym(String acronym) {

        this.acronym = acronym;

    }

    public Integer getGroup() {

        return group;

    }

    public void setGroup(Integer group) {

        this.group = group;

    }

}