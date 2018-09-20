package br.com.pocketpos.app.report.layout;

public abstract class SaleItemTicketFooterLayout implements Layout {

    private String note;

    private String footer;

    public String getNote() {

        return note;

    }

    public void setNote(String note) {

        this.note = note;

    }

    public String getFooter() {

        return footer;

    }

    public void setFooter(String footer) {

        this.footer = footer;

    }

}