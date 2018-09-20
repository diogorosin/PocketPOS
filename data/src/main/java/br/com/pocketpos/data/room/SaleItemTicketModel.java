package br.com.pocketpos.data.room;

public class SaleItemTicketModel {

    private SaleItemVO saleItem;

    private Integer ticket;

    private Integer of;

    private String denomination;

    private Double quantity;

    private MeasureUnitVO measureUnit;

    private Boolean printed;

    public SaleItemVO getSaleItem() {

        return saleItem;

    }

    public void setSaleItem(SaleItemVO saleItem) {

        this.saleItem = saleItem;

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

    public MeasureUnitVO getMeasureUnit() {

        return measureUnit;

    }

    public void setMeasureUnit(MeasureUnitVO measureUnit) {

        this.measureUnit = measureUnit;

    }

    public Boolean getPrinted() {

        return printed;

    }

    public void setPrinted(Boolean printed) {

        this.printed = printed;

    }

}