package br.com.pocketpos.app.report.layout;

import java.util.Date;

public abstract class HeaderLayout implements Layout {


    private String title;

    private String subtitle;

    private Date dateTime;

    private String alias;


    public String getTitle() {

        return title;

    }

    public void setTitle(String title) {

        this.title = title;

    }

    public String getSubtitle() {

        return subtitle;

    }


    public void setSubtitle(String subtitle) {

        this.subtitle = subtitle;

    }

    public Date getDateTime() {

        return dateTime;

    }

    public void setDateTime(Date dateTime) {

        this.dateTime = dateTime;

    }

    public String getAlias() {

        return alias;

    }

    public void setAlias(String alias) {

        this.alias = alias;

    }

}