package br.com.pocketpos.data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MeasureUnitMeasureUnitDAO {

    @Insert
    void create(MeasureUnitMeasureUnitVO measureUnitMeasureUnitVO);

    @Query("SELECT * FROM MeasureUnitMeasureUnit WHERE fromIdentifier = :from AND toIdentifier = :to")
    MeasureUnitMeasureUnitVO retrieve(int from, int to);

    @Query("SELECT COUNT(*) > 0 FROM MeasureUnitMeasureUnit WHERE fromIdentifier = :from AND toIdentifier = :to")
    Boolean exists(int from, int to);

    @Update
    void update(MeasureUnitMeasureUnitVO measureUnitMeasureUnitVO);

    @Delete
    void delete(MeasureUnitMeasureUnitVO measureUnitMeasureUnitVO);

    @Query("SELECT * FROM MeasureUnitMeasureUnit WHERE fromIdentifier = :from")
    List<MeasureUnitMeasureUnitVO> listByFrom(int from);

}