package br.com.pocketpos.data.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PaymentDAO {

    @Insert
    void create(PaymentVO paymentVO);

    @Query("SELECT P.* FROM Payment P WHERE P.identifier = :identifier")
    PaymentVO retrieve(String identifier);

    @Query("SELECT COUNT(*) > 0 FROM Payment P WHERE P.identifier = :identifier")
    Boolean exists(String identifier);

    @Update
    void update(PaymentVO paymentVO);

    @Delete
    void delete(PaymentVO paymentVO);

    @Query("SELECT COUNT(*) FROM Payment P")
    Integer count();

    @Query("SELECT P1.* " +
            "FROM Payment P1 " +
            "ORDER BY P1.identifier")
    LiveData<List<PaymentModel>> list();

}