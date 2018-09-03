package br.com.pocketpos.data.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CatalogDAO {

    @Insert
    void create(CatalogVO catalogVO);

    @Query("SELECT C.* FROM Catalog C WHERE C.position = :identifier")
    CatalogVO retrieve(int identifier);

    @Query("SELECT COUNT(*) > 0 FROM Catalog C WHERE C.identifier = :identifier")
    Boolean exists(int identifier);

    @Update
    void update(CatalogVO catalogVO);

    @Delete
    void delete(CatalogVO catalogVO);

    @Query("DELETE FROM Catalog WHERE identifier NOT IN (:catalogs)")
    void deleteCatalogNotIN(int[] catalogs);

    @Query("SELECT COUNT(*) FROM Catalog C")
    Integer count();

    @Query("SELECT C.position, C.denomination " +
            "FROM Catalog C " +
            "ORDER BY C.position")
    LiveData<List<CatalogModel>> list();

}