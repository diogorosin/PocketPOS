package br.com.pocketpos.data.jersey;

public class CatalogItemBean {

    private Integer position;

    private Integer product;

    private Integer code;

    private String denomination;

    private Integer measureUnit;

    private Double price;

    public Integer getPosition() {

        return position;

    }

    public void setPosition(Integer position) {

        this.position = position;

    }

    public Integer getProduct() {

        return product;

    }

    public void setProduct(Integer product) {

        this.product = product;

    }

    public Integer getCode() {

        return code;

    }

    public void setCode(Integer code) {

        this.code = code;

    }

    public String getDenomination() {

        return denomination;

    }

    public void setDenomination(String denomination) {

        this.denomination = denomination;

    }

    public Integer getMeasureUnit() {

        return measureUnit;

    }

    public void setMeasureUnit(Integer measureUnit) {

        this.measureUnit = measureUnit;

    }

    public Double getPrice() {

        return price;

    }

    public void setPrice(Double price) {

        this.price = price;

    }

}