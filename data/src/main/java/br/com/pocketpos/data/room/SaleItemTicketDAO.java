package br.com.pocketpos.data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface SaleItemTicketDAO {

    @Insert
    void create(SaleItemTicketVO saleItemTicketVO);

    @Query("SELECT SIT.* " +
            "FROM SaleItemTicket SIT " +
            "WHERE SIT.sale = :sale AND SIT.item = :item AND SIT.ticket = :ticket")
    SaleItemTicketVO retrieve(int sale, int item, int ticket);

    @Query("SELECT COUNT(*) > 0 " +
            "FROM SaleItemTicket SIT " +
            "WHERE SIT.sale = :sale AND SIT.item = :item AND SIT.ticket = :ticket")
    Boolean exists(int sale, int item, int ticket);

    @Update
    void update(SaleItemTicketVO saleItemTicketVO);

    @Delete
    void delete(SaleItemTicketVO saleItemTicketVO);

}