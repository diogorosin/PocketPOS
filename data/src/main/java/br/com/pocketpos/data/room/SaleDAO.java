package br.com.pocketpos.data.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.pocketpos.data.room.SaleVO;

@Dao
public interface SaleDAO {

    @Insert
    Long create(SaleVO saleVO);

    @Query("SELECT S.* FROM Sale S WHERE S.identifier = :identifier")
    SaleVO retrieve(int identifier);

    @Query("SELECT S.* FROM Sale S WHERE S.identifier = :identifier")
    LiveData<SaleVO> liveDataRetrieve(int identifier);

    @Query("SELECT COUNT(*) > 0 FROM Sale S WHERE S.identifier = :identifier")
    Boolean exists(int identifier);

    @Query("SELECT MAX(S.identifier) FROM Sale S")
    Integer lastSale();

    @Update
    void update(SaleVO saleVO);

    @Delete
    void delete(SaleVO saleVO);

    @Query("SELECT S.* FROM Sale S ORDER BY S.dateTime")
    LiveData<List<SaleVO>> list();

}