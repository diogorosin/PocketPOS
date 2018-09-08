package br.com.pocketpos.data.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

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

    @Query("SELECT SI.sale, SI.item, P.identifier, P.shortDenomination AS denomination, SI.quantity, SI.price, SI.value " +
            "FROM SaleItem SI " +
            "INNER JOIN Product P ON SI.product = P.identifier " +
            "INNER JOIN Sale S ON SI.sale = S.identifier " +
            "INNER JOIN FolderProduct FP ON FP.product = SI.product " +
            "INNER JOIN Folder F ON F.identifier = FP.folder " +
            "WHERE SI.sale = :sale AND F.position = :folder AND F.active = 1 AND FP.active = 1 " +
            "ORDER BY F.position, FP.position")
    LiveData<List<CatalogItemModel>> liveDataListBySaleAndFolder(int sale, int folder);

    @Query("SELECT SI.sale, SI.item, P.identifier, P.shortDenomination AS denomination, SI.quantity, SI.price, SI.value " +
            "FROM SaleItem SI " +
            "INNER JOIN Product P ON SI.product = P.identifier " +
            "INNER JOIN Sale S ON SI.sale = S.identifier " +
            "WHERE SI.sale = :sale AND SI.quantity > 0 " +
            "ORDER BY S.identifier, SI.item")
    LiveData<List<CatalogItemModel>> getSaleItemsOfSale(int sale);

}