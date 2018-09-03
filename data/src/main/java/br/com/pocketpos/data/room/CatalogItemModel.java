package br.com.pocketpos.data.room;

import android.arch.persistence.room.Embedded;

public class CatalogItemModel {

    private Integer catalog;

    private Integer item;

    private Integer position;

    @Embedded(prefix="product_")
    private ProductModel product;

    private Integer code;

    private String denomination;

    private Double quantity;

    @Embedded(prefix="measureUnit_")
    private MeasureUnitModel measureUnit;

    private Double price;

    private Double total;

    public Integer getCatalog() {

        return catalog;

    }

    public void setCatalog(Integer catalog) {

        this.catalog = catalog;

    }

    public Integer getItem() {

        return item;

    }

    public void setItem(Integer item) {

        this.item = item;

    }

    public Integer getPosition() {

        return position;

    }

    public void setPosition(Integer position) {

        this.position = position;

    }

    public ProductModel getProduct() {

        return product;

    }

    public void setProduct(ProductModel product) {

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

    public Double getQuantity() {

        return quantity;

    }

    public void setQuantity(Double quantity) {

        this.quantity = quantity;

    }

    public MeasureUnitModel getMeasureUnit() {

        return measureUnit;

    }

    public void setMeasureUnit(MeasureUnitModel measureUnit) {

        this.measureUnit = measureUnit;

    }

    public Double getPrice() {

        return price;

    }

    public void setPrice(Double price) {

        this.price = price;

    }

    public Double getTotal() {

        return total;

    }

    public void setTotal(Double total) {

        this.total = total;

    }

}