package br.com.pocketpos.data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface SaleItemDAO {

    @Insert
    void create(SaleItemVO saleItemVO);

    @Query("SELECT SI.* " +
            "FROM SaleItem SI " +
            "WHERE SI.sale = :sale AND SI.item = :item")
    SaleItemVO retrieve(int sale, int item);

    @Query("SELECT COUNT(*) > 0 " +
            "FROM SaleItem SI " +
            "WHERE SI.sale = :sale AND SI.item = :item")
    Boolean exists(int sale, int item);

    @Update
    void update(SaleItemVO saleItemVO);

    @Delete
    void delete(SaleItemVO saleItemVO);

}