package br.com.pocketpos.data.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

@Entity(tableName = "CatalogItem",
        primaryKeys = {"catalog", "item"},
        foreignKeys = {
                @ForeignKey(entity = CatalogVO.class,
                        parentColumns = "identifier",
                        childColumns = "catalog",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = ProductVO.class,
                        parentColumns = "identifier",
                        childColumns = "product",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE)},
        indices = {@Index(value={"catalog", "item"})})
public class CatalogItemVO {

    @NonNull
    @ColumnInfo(name = "catalog")
    private Integer catalog;

    @NonNull
    @ColumnInfo(name = "item")
    private Integer item;

    @ColumnInfo(name="position")
    private Integer position;

    @ColumnInfo(name = "product")
    private Integer product;

    @ColumnInfo(name="code")
    private Integer code;

    @ColumnInfo(name="denomination")
    private String denomination;

    @ColumnInfo(name="quantity")
    private Double quantity;

    @ColumnInfo(name="measureUnit")
    private Integer measureUnit;

    @ColumnInfo(name="price")
    private Double price;

    @ColumnInfo(name="total")
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

    public Double getQuantity() {

        return quantity;

    }

    public void setQuantity(Double quantity) {

        this.quantity = quantity;

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

    public Double getTotal() {

        return total;

    }

    public void setTotal(Double total) {

        this.total = total;

    }

}