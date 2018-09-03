// REFERENCIA DE SQL COM DATAS
// http://androidkt.com/datetime-datatype-sqlite-using-room/

package br.com.pocketpos.data.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "Sale",
        foreignKeys = {
                @ForeignKey(entity = UserVO.class,
                        parentColumns = "identifier",
                        childColumns = "user",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE)},
        indices = {@Index("identifier")})
public class SaleVO implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "identifier")
    private Integer identifier;

    @NonNull
    @ColumnInfo(name="dateTime")
    @TypeConverters({TimestampConverter.class})
    private Date dateTime;

    @NonNull
    @ColumnInfo(name="user")
    private Integer user;

    public Integer getIdentifier() {

        return identifier;

    }

    public void setIdentifier(Integer identifier) {

        this.identifier = identifier;

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

}