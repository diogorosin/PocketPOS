package br.com.pocketpos.data.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

@Entity(tableName = "SaleItemTicket",
        primaryKeys = {"sale", "item", "ticket"},
        foreignKeys = {
                @ForeignKey(entity = SaleItemVO.class,
                        parentColumns = {"sale", "item"},
                        childColumns = {"sale", "item"},
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE)})
public class SaleItemTicketVO {

    @NonNull
    @ColumnInfo(name = "sale")
    private Integer sale;

    @NonNull
    @ColumnInfo(name = "item")
    private Integer item;

    @NonNull
    @ColumnInfo(name = "ticket")
    private Integer ticket;

    @NonNull
    @ColumnInfo(name = "of")
    private Integer of;

    @NonNull
    @ColumnInfo(name = "denomination")
    private String denomination;

    @ColumnInfo(name = "quantity")
    private Double quantity;

    @ColumnInfo(name = "measureUnit")
    private Integer measureUnit;

    @NonNull
    @ColumnInfo(name="printed")
    private Boolean printed;

    public Integer getSale() {

        return sale;

    }

    public void setSale(@NonNull Integer sale) {

        this.sale = sale;

    }

    public Integer getItem() {

        return item;

    }

    public void setItem(@NonNull Integer item) {

        this.item = item;

    }

    public Integer getTicket() {

        return ticket;

    }

    public void setTicket(@NonNull Integer ticket) {

        this.ticket = ticket;

    }

    public Integer getOf() {

        return of;

    }

    public void setOf(@NonNull Integer of) {

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

    public Integer getMeasureUnit() {

        return measureUnit;

    }

    public void setMeasureUnit(Integer measureUnit) {

        this.measureUnit = measureUnit;

    }

    public Boolean getPrinted() {

        return printed;

    }

    public void setPrinted(@NonNull Boolean printed) {

        this.printed = printed;

    }

}