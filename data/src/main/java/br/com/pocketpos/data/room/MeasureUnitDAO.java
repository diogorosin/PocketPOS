package br.com.pocketpos.data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MeasureUnitDAO {

    @Insert
    void create(MeasureUnitVO measureUnitVO);

    @Query("SELECT * FROM MeasureUnit WHERE identifier = :identifier")
    MeasureUnitVO retrieve(int identifier);

    @Query("SELECT COUNT(*) > 0 FROM MeasureUnit WHERE identifier = :identifier")
    Boolean exists(int identifier);

    @Update
    void update(MeasureUnitVO measureUnitVO);

    @Delete
    void delete(MeasureUnitVO measureUnitVO);

    @Query("SELECT * FROM MeasureUnit")
    List<MeasureUnitVO> list();

}