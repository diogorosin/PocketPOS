package br.com.pocketpos.data.room;

public class PaymentMethodModel {

    private String identifier;

    private String denomination;

    public String getIdentifier() {

        return identifier;

    }

    public void setIdentifier(String identifier) {

        this.identifier = identifier;

    }

    public String getDenomination() {

        return denomination;

    }

    public void setDenomination(String denomination) {

        this.denomination = denomination;

    }

}