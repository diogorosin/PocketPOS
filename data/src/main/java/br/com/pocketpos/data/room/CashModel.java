package br.com.pocketpos.data.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

public class CashModel {

    private Integer identifier;

    private String operation;

    private String type;

    private Double value;

    private String note;

    @Embedded(prefix = "user_")
    private UserVO user;

    @ColumnInfo(name="dateTime")
    @TypeConverters({TimestampConverter.class})
    private Date dateTime;

    public Integer getIdentifier() {

        return identifier;

    }

    public void setIdentifier(Integer identifier) {

        this.identifier = identifier;

    }

    public String getOperation() {

        return operation;

    }

    public void setOperation(String operation) {

        this.operation = operation;

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

    public Double getValue() {

        return value;

    }

    public void setValue(Double value) {

        this.value = value;

    }

}