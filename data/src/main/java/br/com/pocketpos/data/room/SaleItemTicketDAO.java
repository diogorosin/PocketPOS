package br.com.pocketpos.data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

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

    @Query("UPDATE SaleItemTicket " +
            "SET printed = 1 " +
            "WHERE sale = :sale AND item = :item AND ticket = :ticket")
    void setTicketAsPrinted(int sale, int item, int ticket);

    @Update
    void update(SaleItemTicketVO saleItemTicketVO);

    @Delete
    void delete(SaleItemTicketVO saleItemTicketVO);

    @Query("SELECT SI.sale AS 'saleitem_sale', " +
            "SI.item AS 'saleitem_item', " +
            "SIT.ticket, " +
            "SIT.'of', " +
            "SIT.denomination, " +
            "SIT.quantity, " +
            "MU.identifier AS 'measureunit_identifier', " +
            "MU.denomination AS 'measureunit_denomination', " +
            "MU.acronym AS 'measureunit_acronym', " +
            "MU.'group' AS 'measureunit_group', " +
            "SIT.printed " +
            "FROM SaleItemTicket SIT " +
            "INNER JOIN MeasureUnit MU ON MU.identifier = SIT.measureunit " +
            "INNER JOIN SaleItem SI ON SI.sale = SIT.sale AND SI.item = SIT.item " +
            "INNER JOIN Sale S ON S.identifier = SI.sale " +
            "WHERE S.identifier = :sale")
    List<SaleItemTicketModel> getTicketsOfSale(int sale);

    @Query("SELECT COUNT(*) " +
            "FROM SaleItemTicket SIT " +
            "WHERE SIT.sale = :sale AND SIT.printed = 1")
    Integer getPrintedTicketsCountOfSale(int sale);

}