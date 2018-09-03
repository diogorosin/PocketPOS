package br.com.pocketpos.data.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Product")
public class ProductVO {

    @PrimaryKey
    @ColumnInfo(name = "identifier")
    private Integer identifier;

    @ColumnInfo(name="active")
    private Boolean active;

    @ColumnInfo(name="denomination")
    private String denomination;

    //DIMENSOES
    @ColumnInfo(name="widthUnit")
    private Integer widthUnit;

    @ColumnInfo(name="widthValue")
    private Double widthValue;

    @ColumnInfo(name="heightUnit")
    private Integer heightUnit;

    @ColumnInfo(name="heightValue")
    private Double heightValue;

    @ColumnInfo(name="lengthUnit")
    private Integer lengthUnit;

    @ColumnInfo(name="lengthValue")
    private Double lengthValue;

    //VOLUME
    @ColumnInfo(name="contentUnit")
    private Integer contentUnit;

    @ColumnInfo(name="contentValue")
    private Double contentValue;

    //PESO
    @ColumnInfo(name="grossWeightUnit")
    private Integer grossWeightUnit;

    @ColumnInfo(name="grossWeightValue")
    private Double grossWeightValue;

    @ColumnInfo(name="netWeightUnit")
    private Integer netWeightUnit;

    @ColumnInfo(name="netWeightValue")
    private Double netWeightValue;

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

    public String getDenomination() {

        return denomination;

    }

    public void setDenomination(String denomination) {

        this.denomination = denomination;

    }

    public Integer getWidthUnit() {

        return widthUnit;

    }

    public void setWidthUnit(Integer widthUnit) {

        this.widthUnit = widthUnit;

    }

    public Double getWidthValue() {

        return widthValue;

    }

    public void setWidthValue(Double widthValue) {

        this.widthValue = widthValue;

    }

    public Integer getHeightUnit() {

        return heightUnit;

    }

    public void setHeightUnit(Integer heightUnit) {

        this.heightUnit = heightUnit;

    }

    public Double getHeightValue() {

        return heightValue;

    }

    public void setHeightValue(Double heightValue) {

        this.heightValue = heightValue;

    }

    public Integer getLengthUnit() {

        return lengthUnit;

    }

    public void setLengthUnit(Integer lengthUnit) {

        this.lengthUnit = lengthUnit;

    }

    public Double getLengthValue() {

        return lengthValue;

    }

    public void setLengthValue(Double lengthValue) {

        this.lengthValue = lengthValue;

    }

    public Integer getContentUnit() {

        return contentUnit;

    }

    public void setContentUnit(Integer contentUnit) {

        this.contentUnit = contentUnit;

    }

    public Double getContentValue() {

        return contentValue;

    }

    public void setContentValue(Double contentValue) {

        this.contentValue = contentValue;

    }

    public Integer getGrossWeightUnit() {

        return grossWeightUnit;

    }

    public void setGrossWeightUnit(Integer grossWeightUnit) {

        this.grossWeightUnit = grossWeightUnit;

    }

    public Double getGrossWeightValue() {

        return grossWeightValue;

    }

    public void setGrossWeightValue(Double grossWeightValue) {

        this.grossWeightValue = grossWeightValue;

    }

    public Integer getNetWeightUnit() {

        return netWeightUnit;

    }

    public void setNetWeightUnit(Integer netWeightUnit) {

        this.netWeightUnit = netWeightUnit;

    }

    public Double getNetWeightValue() {

        return netWeightValue;

    }

    public void setNetWeightValue(Double netWeightValue) {

        this.netWeightValue = netWeightValue;

    }

}