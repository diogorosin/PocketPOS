package br.com.pocketpos.data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface SaleCashDAO {

    @Insert
    void create(SaleCashVO saleCashVO);

    @Query("SELECT * FROM SaleCash WHERE sale = :sale AND cash = :cash")
    SaleCashVO retrieve(int sale, int cash);

    @Query("SELECT COUNT(*) > 0 FROM SaleCash WHERE sale = :sale AND cash = :cash")
    Boolean exists(int sale, int cash);

    @Update
    void update(SaleCashVO saleCashVO);

    @Delete
    void delete(SaleCashVO saleCashVO);

}