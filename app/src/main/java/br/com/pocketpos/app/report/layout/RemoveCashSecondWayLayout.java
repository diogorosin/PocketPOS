package br.com.pocketpos.app.report.layout;

import java.util.Date;

public abstract class RemoveCashSecondWayLayout implements Layout {


    private String userName;

    private Date dateTime;

    private Double value;


    public String getUserName() {

        return userName;

    }

    public void setUserName(String userName) {

        this.userName = userName;

    }

    public Date getDateTime() {

        return dateTime;

    }

    public void setDateTime(Date dateTime) {

        this.dateTime = dateTime;

    }

    public Double getValue() {

        return value;

    }

    public void setValue(Double value) {

        this.value = value;

    }

}