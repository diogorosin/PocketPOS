package br.com.pocketpos.data.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

@Entity(tableName = "SaleItem",
        primaryKeys = {"sale", "item"},
        foreignKeys = {
                @ForeignKey(entity = SaleVO.class,
                        parentColumns = "identifier",
                        childColumns = "sale",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = ProductVO.class,
                        parentColumns = "identifier",
                        childColumns = "product",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE)})
public class SaleItemVO {

    @NonNull
    @ColumnInfo(name = "sale")
    private Integer sale;

    @NonNull
    @ColumnInfo(name = "item")
    private Integer item;

    @NonNull
    @ColumnInfo(name = "product")
    private Integer product;

    @ColumnInfo(name="quantity")
    private Double quantity;

    @ColumnInfo(name="price")
    private Double price;

    @ColumnInfo(name="total")
    private Double total;

    public Integer getSale() {

        return sale;

    }

    public void setSale(Integer sale) {

        this.sale = sale;

    }

    public Integer getItem() {

        return item;

    }

    public void setItem(Integer item) {

        this.item = item;

    }

    public Integer getProduct() {

        return product;

    }

    public void setProduct(Integer product) {

        this.product = product;

    }

    public Double getPrice() {

        return price;

    }

    public void setPrice(Double price) {

        this.price = price;

    }

    public Double getQuantity() {

        return quantity;

    }

    public void setQuantity(Double quantity) {

        this.quantity = quantity;

    }

    public Double getTotal() {

        return total;

    }

    public void setTotal(Double total) {

        this.total = total;

    }

}