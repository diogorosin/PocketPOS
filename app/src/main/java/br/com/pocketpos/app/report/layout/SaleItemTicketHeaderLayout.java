package br.com.pocketpos.app.report.layout;

public abstract class SaleItemTicketHeaderLayout implements Layout {

    private String title;

    private String subtitle;

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

}