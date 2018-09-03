package br.com.pocketpos.data.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

public class CashModel {


    private Integer identifier;

    private String payment;

    private String operation;

    private Integer origin;

    private String type;

    private String note;

    @ColumnInfo(name="dateTime")
    @TypeConverters({TimestampConverter.class})
    private Date dateTime;

    @Embedded(prefix = "user_")
    private UserVO user;

    private Double total;


    public Integer getIdentifier() {

        return identifier;

    }

    public void setIdentifier(Integer identifier) {

        this.identifier = identifier;

    }

    public String getPayment() {

        return payment;

    }

    public void setPayment(String payment) {

        this.payment = payment;

    }

    public String getOperation() {

        return operation;

    }

    public void setOperation(String operation) {

        this.operation = operation;

    }

    public Integer getOrigin() {

        return origin;

    }

    public void setOrigin(Integer origin) {

        this.origin = origin;

    }

    public String getType() {

        return type;

    }

    public void setType(String type) {

        this.type = type;

    }

    public String getNote() {

        return note;

    }

    public void setNote(String note) {

        this.note = note;

    }

    @NonNull
    public Date getDateTime() {

        return dateTime;

    }

    public void setDateTime(@NonNull Date dateTime) {

        this.dateTime = dateTime;

    }

    public UserVO getUser() {

        return user;

    }

    public void setUser(UserVO user) {

        this.user = user;

    }

    public Double getTotal() {

        return total;

    }

    public void setTotal(Double total) {

        this.total = total;

    }

}