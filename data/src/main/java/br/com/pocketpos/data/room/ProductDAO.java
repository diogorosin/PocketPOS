package br.com.pocketpos.data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ProductDAO {

    @Insert
    void create(ProductVO productVO);

    @Query("SELECT * FROM Product WHERE identifier = :identifier")
    ProductVO retrieve(int identifier);

    @Query("SELECT COUNT(*) > 0 " +
            "FROM ProductProduct PP " +
            "WHERE PP.productIdentifier = :product AND PP.active = 1")
    Boolean isComposed(int product);

    @Query("SELECT COUNT(*) > 0 FROM Product WHERE identifier = :identifier")
    Boolean exists(int identifier);

    @Update
    void update(ProductVO productVO);

    @Delete
    void delete(ProductVO productVO);

    @Query("SELECT * FROM Product")
    List<ProductVO> list();

}