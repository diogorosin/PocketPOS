package br.com.pocketpos.data.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;


@Entity(tableName = "MeasureUnitMeasureUnit",
        primaryKeys = {"fromIdentifier", "toIdentifier"},
        foreignKeys = {
                @ForeignKey(entity = MeasureUnitVO.class,
                        parentColumns = "identifier",
                        childColumns = "fromIdentifier",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = MeasureUnitVO.class,
                        parentColumns = "identifier",
                        childColumns = "toIdentifier",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE)},
        indices = {@Index(value={"fromIdentifier", "toIdentifier"})})
public class MeasureUnitMeasureUnitVO {

    @NonNull
    @ColumnInfo(name = "fromIdentifier")
    private Integer fromIdentifier;

    @NonNull
    @ColumnInfo(name = "toIdentifier")
    private Integer toIdentifier;

    @ColumnInfo(name="factor")
    private Double factor;

    public Integer getFromIdentifier() {

        return fromIdentifier;

    }

    public void setFromIdentifier(Integer fromIdentifier) {

        this.fromIdentifier = fromIdentifier;

    }

    public Integer getToIdentifier() {

        return toIdentifier;

    }

    public void setToIdentifier(Integer toIdentifier) {

        this.toIdentifier = toIdentifier;

    }

    public Double getFactor() {

        return factor;

    }

    public void setFactor(Double factor) {

        this.factor = factor;

    }

}