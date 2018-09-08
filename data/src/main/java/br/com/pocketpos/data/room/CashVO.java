package br.com.pocketpos.data.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;


/*
    IDENTIFIER: IDENTIFICADOR DO LANCAMENTO
    OPERATION.: CODIGO DA OPERACAO (ABE=ABERTURA, FEC=FECHAMENTO, SAN=SANGRIA, SUP=SUPRIMENTO, VEN=VENDA)
    TYPE......: TIPO DA OPERACAO (E=ENTRADA, S=SAIDA)
    VALUE.....: VALOR DO LANCAMENTO
    NOTE......: OBSERVACOES
    USER......: IDENTIFICADOR DO USUARIO
    DATETIME..: DATA E HORA
*/
@Entity(tableName = "Cash",
        foreignKeys = {
                @ForeignKey(entity = UserVO.class,
                        parentColumns = "identifier",
                        childColumns = "user",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE)})
public class CashVO {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "identifier")
    private Integer identifier;

    @ColumnInfo
    private String operation;

    @ColumnInfo
    private String type;

    @ColumnInfo
    private Double value;

    @ColumnInfo
    private String note;

    @ColumnInfo
    private Integer user;

    @NonNull
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

    public Double getValue() {

        return value;

    }

    public void setValue(Double value) {

        this.value = value;

    }

    public String getNote() {

        return note;

    }

    public void setNote(String note) {

        this.note = note;

    }

    public Integer getUser() {

        return user;

    }

    public void setUser(Integer user) {

        this.user = user;

    }

    public Date getDateTime() {

        return dateTime;

    }

    public void setDateTime(Date dateTime) {

        this.dateTime = dateTime;

    }

}