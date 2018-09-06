package br.com.pocketpos.data.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "PaymentMethod")
public class PaymentMethodVO {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "identifier")
    private String identifier;

    @ColumnInfo(name="denomination")
    private String denomination;

    public String getIdentifier() {

        return identifier;

    }

    public void setIdentifier(String identifier) {

        this.identifier = identifier;

    }

    public String getDenomination() {

        return denomination;

    }

    public void setDenomination(String denomination) {

        this.denomination = denomination;

    }

}