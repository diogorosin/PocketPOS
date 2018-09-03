package br.com.pocketpos.data.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CatalogItemDAO {

    @Insert
    void create(CatalogItemVO catalogItemVO);

    @Query("SELECT * FROM CatalogItem WHERE catalog = :catalog AND item = :item")
    CatalogItemVO retrieve(int catalog, int item);

    @Query("SELECT COUNT(*) > 0 FROM CatalogItem WHERE catalog = :catalog AND item = :item")
    Boolean exists(int catalog, int item);

    @Update
    void update(CatalogItemVO catalogItemVO);

    @Delete
    void delete(CatalogItemVO catalogItemVO);

    @Query("DELETE FROM CatalogItem WHERE catalog = :catalog AND item NOT IN (:items)")
    void deleteCatalogItemsNotIN(int catalog, int[] items);

    @Query("SELECT CI.catalog, CI.item, CI.position, " +
            "P.identifier AS 'product_identifier', " +
            "P.denomination AS 'product_denomination', " +
            "CI.code, " +
            "CI.denomination, " +
            "CI.price, " +
            "CI.quantity, " +
            "MU.identifier AS 'measureUnit_identifier', " +
            "MU.denomination AS 'measureUnit_denomination', " +
            "MU.acronym AS 'measureUnit_acronym', " +
            "MU.`group` AS 'measureUnit_group', " +
            "CI.total " +
            "FROM CatalogItem CI " +
            "INNER JOIN Catalog C ON C.identifier = CI.catalog " +
            "INNER JOIN Product P ON P.identifier = CI.product " +
            "INNER JOIN MeasureUnit MU ON MU.identifier = CI.measureUnit " +
            "WHERE C.position = :position " +
            "ORDER BY C.position, CI.position")
    LiveData<List<CatalogItemModel>> listByPosition(int position);

    @Query("SELECT CI.catalog, CI.item, CI.position, " +
            "P.identifier AS 'product_identifier', " +
            "P.denomination AS 'product_denomination', " +
            "CI.code, " +
            "CI.denomination, " +
            "CI.price, " +
            "MU.identifier AS 'measureUnit_identifier', " +
            "MU.denomination AS 'measureUnit_denomination', " +
            "MU.acronym AS 'measureUnit_acronym', " +
            "MU.`group` AS 'measureUnit_group', " +
            "CI.quantity, " +
            "CI.total " +
            "FROM CatalogItem CI " +
            "INNER JOIN Catalog C ON C.identifier = CI.catalog " +
            "INNER JOIN Product P ON P.identifier = CI.product " +
            "INNER JOIN MeasureUnit MU ON MU.identifier = CI.measureUnit " +
            "WHERE CI.quantity > 0 " +
            "ORDER BY C.position, CI.position")
    LiveData<List<CatalogItemModel>> list();

    @Query("SELECT SUM(CI.total) " +
            "FROM CatalogItem CI ")
    LiveData<Double> subtotal();

    /*TODO: Implementar o calculo com os acrescimos e descontos da venda */
    @Query("SELECT SUM(CI.total) " +
            "FROM CatalogItem CI ")
    LiveData<Double> total();

}