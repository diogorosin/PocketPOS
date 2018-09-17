package br.com.pocketpos.data.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

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
    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "subtitle")
    private String subtitle;

    @NonNull
    @ColumnInfo(name = "deviceAlias")
    private String deviceAlias;

    @NonNull
    @ColumnInfo(name = "deviceIdentifier")
    private Integer deviceIdentifier;

    @NonNull
    @ColumnInfo(name="dateTime")
    @TypeConverters({TimestampConverter.class})
    private Date dateTime;

    @NonNull
    @ColumnInfo(name = "denomination")
    private String denomination;

    @ColumnInfo(name = "quantity")
    private Double quantity;

    @ColumnInfo(name = "measureUnit")
    private Integer measureUnit;

    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "footer")
    private String footer;

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

    public String getDeviceAlias() {

        return deviceAlias;

    }

    public void setDeviceAlias(String deviceAlias) {

        this.deviceAlias = deviceAlias;

    }

    public Integer getDeviceIdentifier() {

        return deviceIdentifier;

    }

    public void setDeviceIdentifier(Integer deviceIdentifier) {

        this.deviceIdentifier = deviceIdentifier;

    }

    public Date getDateTime() {

        return dateTime;

    }

    public void setDateTime(@NonNull Date dateTime) {

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

    public Integer getMeasureUnit() {

        return measureUnit;

    }

    public void setMeasureUnit(Integer measureUnit) {

        this.measureUnit = measureUnit;

    }

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

    public Boolean getPrinted() {

        return printed;

    }

    public void setPrinted(@NonNull Boolean printed) {

        this.printed = printed;

    }

}