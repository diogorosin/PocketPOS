package br.com.pocketpos.data.jersey;

import java.util.Map;

public class ProductBean extends ProgenyBean{

    //DIMENSOES
    private Integer widthUnit;

    private Double widthValue;

    private Integer heightUnit;

    private Double heightValue;

    private Integer lengthUnit;

    private Double lengthValue;

    //VOLUME
    private Integer contentUnit;

    private Double contentValue;

    //PESO
    private Integer grossWeightUnit;

    private Double grossWeightValue;

    private Integer netWeightUnit;

    private Double netWeightValue;

    //PARTES
    private Map<Integer, PartBean> parts;

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

    public void setParts(Map<Integer, PartBean> parts) {

        this.parts = parts;

    }

    public Map<Integer, PartBean> getParts() {

        return parts;

    }

    public void setPart(Map<Integer, PartBean> parts) {

        this.parts = parts;

    }

}