package br.com.pocketpos.data.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "MeasureUnit")
public class MeasureUnitVO {

    @PrimaryKey
    @ColumnInfo(name = "identifier")
    private Integer identifier;

    @ColumnInfo(name="denomination")
    private String denomination;

    @ColumnInfo(name="acronym")
    private String acronym;

    @ColumnInfo(name="group")
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