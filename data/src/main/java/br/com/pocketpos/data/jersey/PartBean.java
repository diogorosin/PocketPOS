package br.com.pocketpos.data.jersey;

import java.io.Serializable;

public class PartBean implements Serializable{

    private Boolean active;

    private Double quantity;

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