package br.com.pocketpos.data.room;

import android.arch.persistence.room.Embedded;

public class ProductModel {

    private Integer identifier;

    private String denomination;

    @Embedded(prefix = "measureUnit_")
    private MeasureUnitVO measureUnit;

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

    public MeasureUnitVO getMeasureUnit() {

        return measureUnit;

    }

    public void setMeasureUnit(MeasureUnitVO measureUnit) {

        this.measureUnit = measureUnit;

    }

}