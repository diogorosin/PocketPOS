package br.com.pocketpos.data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ProductProductDAO {

    @Insert
    void create(ProductProductVO productProductVO);

    @Query("SELECT * FROM ProductProduct WHERE productIdentifier = :product AND partIdentifier = :part")
    ProductProductVO retrieve(int product, int part);

    @Query("SELECT COUNT(*) > 0 FROM ProductProduct WHERE productIdentifier = :product AND partIdentifier = :part")
    Boolean exists(int product, int part);

    @Update
    void update(ProductProductVO productProductVO);

    @Delete
    void delete(ProductProductVO productProductVO);

    @Query("SELECT * FROM ProductProduct WHERE productIdentifier = :product")
    List<ProductProductVO> listByProduct(int product);

}