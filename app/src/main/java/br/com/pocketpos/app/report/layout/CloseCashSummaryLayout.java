package br.com.pocketpos.app.report.layout;

import java.util.Date;

public abstract class CloseCashSummaryLayout implements Layout {


    private String operation;

    private Date dateTime;

    private String payment;

    private String type;

    private Double value;

    private String userName;


    public String getOperation() {

        return operation;

    }

    public void setOperation(String operation) {

        this.operation = operation;

    }

    public Date getDateTime() {

        return dateTime;

    }

    public void setDateTime(Date dateTime) {

        this.dateTime = dateTime;

    }

    public String getPayment() {

        return payment;

    }

    public void setPayment(String payment) {

        this.payment = payment;

    }

    public String getType() {

        return type;

    }

    public void setType(String type) {

        this.type = type;

    }

    public Double getValue() {

        return value;

    }

    public void setValue(Double value) {

        this.value = value;

    }

    public String getUserName() {

        return userName;

    }

    public void setUserName(String userName) {

        this.userName = userName;

    }

}