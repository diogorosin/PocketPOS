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
    PAYMENT...: FORMA DE PAGAMENTO
    OPERATION.: CODIGO DA OPERACAO (ABE=ABERTURA, FEC=FECHAMENTO, SAN=SANGRIA, SUP=SUPRIMENTO, VEN=VENDA)
    TYPE......: TIPO DA OPERACAO (E=ENTRADA, S=SAIDA)
    NOTE......: OBSERVACOES
    DATETIME..: DATA E HORA
    USER......: IDENTIFICADOR DO USUARIO
    TOTAL.....: TOTAL DO LANCAMENTO

*/
@Entity(tableName = "Cash",
        foreignKeys = {
                @ForeignKey(entity = UserVO.class,
                        parentColumns = "identifier",
                        childColumns = "user",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = PaymentVO.class,
                        parentColumns = "identifier",
                        childColumns = "payment",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE)})
public class CashVO {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "identifier")
    private Integer identifier;

    @ColumnInfo
    private String payment;

    @ColumnInfo
    private String operation;

    @ColumnInfo
    private Integer origin;

    @ColumnInfo
    private String type;

    @ColumnInfo
    private String note;

    @NonNull
    @ColumnInfo(name="dateTime")
    @TypeConverters({TimestampConverter.class})
    private Date dateTime;

    @ColumnInfo
    private Integer user;

    @ColumnInfo
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

    public Date getDateTime() {

        return dateTime;

    }

    public void setDateTime(Date dateTime) {

        this.dateTime = dateTime;

    }

    public Integer getUser() {

        return user;

    }

    public void setUser(Integer user) {

        this.user = user;

    }

    public Double getTotal() {

        return total;

    }

    public void setTotal(Double total) {

        this.total = total;

    }

}