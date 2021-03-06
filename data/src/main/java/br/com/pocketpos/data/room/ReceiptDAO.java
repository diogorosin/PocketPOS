package br.com.pocketpos.data.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ReceiptDAO {

    String LIST_QUERY =
            "SELECT R.*, " +
            "RM.identifier as 'method_identifier', " +
            "RM.denomination as 'method_denomination' " +
            "FROM Receipt R " +
            "INNER JOIN ReceiptMethod RM ON RM.identifier = R.method " +
            "ORDER BY R.identifier";

    String AMOUNT_RECEIVED =
            "SELECT IFNULL(SUM(R.value), 0) " +
            "FROM Receipt R ";

    String AMOUNT_TORECEIVE =
            "SELECT (SELECT IFNULL(SUM(CI.total), 0) " +
            "FROM CatalogItem CI) - (SELECT IFNULL(SUM(R.value),0) FROM Receipt R)";

    @Insert
    void create(ReceiptVO receipt);

    @Query("SELECT R.* FROM Receipt R WHERE R.identifier = :identifier")
    ReceiptVO retrieve(Integer identifier);

    @Query("SELECT R.* FROM Receipt R WHERE R.method = :method")
    List<ReceiptVO> retrieveByMethod(String method);

    @Query("SELECT COUNT(*) > 0 FROM Receipt R WHERE R.identifier = :identifier")
    Boolean exists(Integer identifier);

    @Query("DELETE FROM Receipt")
    void clear();

    @Update
    void update(ReceiptVO receipt);

    @Delete
    void delete(ReceiptVO receipt);

    @Query("SELECT COUNT(*) FROM Receipt R")
    Integer getCount();

    @Query(LIST_QUERY)
    List<ReceiptModel> getList();

    @Query(AMOUNT_RECEIVED)
    Double getAmountReceived();

    @Query(AMOUNT_TORECEIVE)
    Double getAmountToReceive();

    @Query(LIST_QUERY)
    LiveData<List<ReceiptModel>> getListLiveData();

    @Query(AMOUNT_RECEIVED)
    LiveData<Double> getAmountReceivedLiveData();

    @Query(AMOUNT_TORECEIVE)
    LiveData<Double> getAmountToReceiveLiveData();

}