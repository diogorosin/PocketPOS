package br.com.pocketpos.data.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PaymentMethodDAO {

    @Insert
    void create(PaymentMethodVO paymentMethod);

    @Query("SELECT PM.* FROM PaymentMethod PM WHERE PM.identifier = :identifier")
    PaymentMethodVO retrieve(String identifier);

    @Query("SELECT COUNT(*) > 0 FROM PaymentMethod PM WHERE PM.identifier = :identifier")
    Boolean exists(String identifier);

    @Update
    void update(PaymentMethodVO paymentMethod);

    @Delete
    void delete(PaymentMethodVO paymentMethod);

    @Query("SELECT COUNT(*) FROM PaymentMethod PM")
    Integer count();

    @Query("SELECT PM.* " +
            "FROM PaymentMethod PM " +
            "ORDER BY PM.identifier")
    LiveData<List<PaymentMethodModel>> list();

}