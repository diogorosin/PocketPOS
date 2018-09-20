package br.com.pocketpos.app.report.layout;

import java.util.Date;

public abstract class SaleItemTicketLayout implements Layout {

    private String deviceAlias;

    private Integer sale;

    private Integer item;

    private Integer ticket;

    private Integer of;

    private String userName;

    private Date dateTime;

    private String denomination;

    private Double quantity;

    private String measureUnit;

    public String getDeviceAlias() {

        return deviceAlias;

    }

    public void setDeviceAlias(String deviceAlias) {

        this.deviceAlias = deviceAlias;

    }

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

    public Integer getTicket() {

        return ticket;

    }

    public void setTicket(Integer ticket) {

        this.ticket = ticket;

    }

    public Integer getOf() {

        return of;

    }

    public void setOf(Integer of) {

        this.of = of;

    }

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

    public String getMeasureUnit() {

        return measureUnit;

    }

    public void setMeasureUnit(String measureUnit) {

        this.measureUnit = measureUnit;

    }

}