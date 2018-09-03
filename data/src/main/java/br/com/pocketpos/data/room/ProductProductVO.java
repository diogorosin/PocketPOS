package br.com.pocketpos.data.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;


@Entity(tableName = "ProductProduct",
        primaryKeys = {"productIdentifier", "partIdentifier"},
        foreignKeys = {
                @ForeignKey(entity = ProductVO.class,
                        parentColumns = "identifier",
                        childColumns = "productIdentifier",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = ProductVO.class,
                        parentColumns = "identifier",
                        childColumns = "partIdentifier",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE)},
        indices = {@Index(value={"productIdentifier", "partIdentifier"})})
public class ProductProductVO {

    @NonNull
    @ColumnInfo(name = "productIdentifier")
    private Integer productIdentifier;

    @NonNull
    @ColumnInfo(name = "partIdentifier")
    private Integer partIdentifier;

    @ColumnInfo(name="active")
    private Boolean active;

    @ColumnInfo(name="quantity")
    private Double quantity;

    public Integer getProductIdentifier() {

        return productIdentifier;

    }

    public void setProductIdentifier(Integer productIdentifier) {

        this.productIdentifier = productIdentifier;

    }

    public Integer getPartIdentifier() {

        return partIdentifier;

    }

    public void setPartIdentifier(Integer partIdentifier) {

        this.partIdentifier = partIdentifier;

    }

    public Boolean getActive() {

        return active;

    }

    public void setActive(Boolean active) {

        this.active = active;

    }

    public Double getQuantity() {

        return quantity;

    }

    public void setQuantity(Double quantity) {

        this.quantity = quantity;

    }

}