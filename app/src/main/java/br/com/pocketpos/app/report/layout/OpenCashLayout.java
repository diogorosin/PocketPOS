package br.com.pocketpos.app.report.layout;

import java.util.Date;

public abstract class OpenCashLayout implements Layout {


    private String reportName;

    private String userName;

    private Date dateTime;

    private Double value;


    public String getReportName() {

        return reportName;

    }

    public void setReportName(String reportName) {

        this.reportName = reportName;

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

    public Double getValue() {

        return value;

    }

    public void setValue(Double value) {

        this.value = value;

    }

}