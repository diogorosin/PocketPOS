package br.com.pocketpos.data.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;


@Entity(tableName = "SaleCash",
        primaryKeys = {"sale", "cash"},
        foreignKeys = {
                @ForeignKey(entity = SaleVO.class,
                        parentColumns = "identifier",
                        childColumns = "sale",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = CashVO.class,
                        parentColumns = "identifier",
                        childColumns = "cash",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE)},
        indices = {@Index(value={"sale", "cash"})})
public class SaleCashVO {

    @NonNull
    @ColumnInfo(name = "sale")
    private Integer sale;

    @NonNull
    @ColumnInfo(name = "cash")
    private Integer cash;

    public Integer getSale() {

        return sale;

    }

    public void setSale(Integer sale) {

        this.sale = sale;

    }

    public Integer getCash() {

        return cash;

    }

    public void setCash(Integer cash) {

        this.cash = cash;

    }

}