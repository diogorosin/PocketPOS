package br.com.pocketpos.data.room;

import android.arch.persistence.room.Embedded;


public class ReceiptModel {

    private Integer identifier;

    @Embedded(prefix = "method_")
    private ReceiptMethodVO method;

    private Double value;

    public Integer getIdentifier() {

        return identifier;

    }

    public void setIdentifier(Integer identifier) {

        this.identifier = identifier;

    }

    public ReceiptMethodVO getMethod() {

        return method;

    }

    public void setMethod(ReceiptMethodVO method) {

        this.method = method;

    }

    public Double getValue() {

        return value;

    }

    public void setValue(Double value) {

        this.value = value;

    }

}