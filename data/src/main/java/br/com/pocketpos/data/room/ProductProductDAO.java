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

    @Query("SELECT PR.identifier AS 'product_identifier'," +
            "PR.denomination AS 'product_denomination'," +
            "PA.identifier AS 'part_identifier'," +
            "PA.denomination AS 'part_identifier'," +
            "PP.quantity " +
            "FROM ProductProduct PP " +
            "INNER JOIN Product PR ON PR.identifier = PP.productIdentifier " +
            "INNER JOIN Product PA ON PA.identifier = PP.partIdentifier " +
            "WHERE PR.identifier = :product AND PP.active = 1")
    List<ProductProductModel> getCompositionOfProduct(int product);

}