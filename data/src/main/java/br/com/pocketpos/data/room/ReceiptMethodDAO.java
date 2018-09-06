package br.com.pocketpos.data.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ReceiptMethodDAO {

    @Insert
    void create(ReceiptMethodVO receiptMethod);

    @Query("SELECT RM.* FROM ReceiptMethod RM WHERE RM.identifier = :identifier")
    ReceiptMethodVO retrieve(String identifier);

    @Query("SELECT COUNT(*) > 0 FROM ReceiptMethod RM WHERE RM.identifier = :identifier")
    Boolean exists(String identifier);

    @Update
    void update(ReceiptMethodVO receiptMethod);

    @Delete
    void delete(ReceiptMethodVO receiptMethod);

    @Query("SELECT COUNT(*) FROM ReceiptMethod RM")
    Integer count();

    @Query("SELECT RM.* " +
            "FROM ReceiptMethod RM " +
            "ORDER BY RM.identifier")
    LiveData<List<ReceiptMethodModel>> list();

}