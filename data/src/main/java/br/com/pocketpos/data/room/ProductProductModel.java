package br.com.pocketpos.data.room;

import android.arch.persistence.room.Embedded;

public class ProductProductModel {

    @Embedded(prefix = "product_")
    private ProductVO product;

    @Embedded(prefix = "part_")
    private ProductVO part;

    private Boolean active;

    private Double quantity;

    public ProductVO getProduct() {

        return product;

    }

    public void setProduct(ProductVO product) {

        this.product = product;

    }

    public ProductVO getPart() {

        return part;

    }

    public void setPart(ProductVO part) {

        this.part = part;

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