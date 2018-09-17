package br.com.pocketpos.data.util;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import br.com.pocketpos.data.room.CashDAO;
import br.com.pocketpos.data.room.CashVO;
import br.com.pocketpos.data.room.CatalogDAO;
import br.com.pocketpos.data.room.CatalogItemDAO;
import br.com.pocketpos.data.room.CatalogItemVO;
import br.com.pocketpos.data.room.CatalogVO;
import br.com.pocketpos.data.room.MeasureUnitDAO;
import br.com.pocketpos.data.room.MeasureUnitMeasureUnitDAO;
import br.com.pocketpos.data.room.MeasureUnitMeasureUnitVO;
import br.com.pocketpos.data.room.MeasureUnitVO;
import br.com.pocketpos.data.room.PaymentMethodDAO;
import br.com.pocketpos.data.room.PaymentMethodVO;
import br.com.pocketpos.data.room.ReceiptDAO;
import br.com.pocketpos.data.room.ReceiptMethodDAO;
import br.com.pocketpos.data.room.ReceiptMethodVO;
import br.com.pocketpos.data.room.ProductDAO;
import br.com.pocketpos.data.room.ProductProductDAO;
import br.com.pocketpos.data.room.ProductProductVO;
import br.com.pocketpos.data.room.ProductVO;
import br.com.pocketpos.data.room.ReceiptVO;
import br.com.pocketpos.data.room.SaleCashDAO;
import br.com.pocketpos.data.room.SaleCashVO;
import br.com.pocketpos.data.room.SaleDAO;
import br.com.pocketpos.data.room.SaleItemDAO;
import br.com.pocketpos.data.room.SaleItemTicketDAO;
import br.com.pocketpos.data.room.SaleItemTicketVO;
import br.com.pocketpos.data.room.SaleItemVO;
import br.com.pocketpos.data.room.SaleVO;
import br.com.pocketpos.data.room.UserDAO;
import br.com.pocketpos.data.room.UserVO;

@Database(entities = {
        UserVO.class,
        MeasureUnitVO.class,
        MeasureUnitMeasureUnitVO.class,
        ProductVO.class,
        ProductProductVO.class,
        CatalogVO.class,
        CatalogItemVO.class,
        CashVO.class,
        ReceiptVO.class,
        ReceiptMethodVO.class,
        PaymentMethodVO.class,
        SaleVO.class,
        SaleItemVO.class,
        SaleCashVO.class,
        SaleItemTicketVO.class},
        version = 001, exportSchema = false)
public abstract class DB extends RoomDatabase {

    private static DB INSTANCE;

    public static DB getInstance(Context context) {

        if (INSTANCE == null) {

            INSTANCE = Room
                    .databaseBuilder(context.getApplicationContext(), DB.class, "pocketpos-database")
                    .allowMainThreadQueries()
                    .build();

        }

        return INSTANCE;

    }

    public abstract UserDAO userDAO();

    public abstract MeasureUnitDAO measureUnitDAO();

    public abstract MeasureUnitMeasureUnitDAO measureUnitMeasureUnitDAO();

    public abstract ProductDAO productDAO();

    public abstract ProductProductDAO productProductDAO();

    public abstract CatalogDAO catalogDAO();

    public abstract CatalogItemDAO catalogItemDAO();

    public abstract CashDAO cashDAO();

    public abstract ReceiptDAO receiptDAO();

    public abstract ReceiptMethodDAO receiptMethodDAO();

    public abstract PaymentMethodDAO paymentMethodDAO();

    public abstract SaleDAO saleDAO();

    public abstract SaleItemDAO saleItemDAO();

    public abstract SaleCashDAO saleCashDAO();

    public abstract SaleItemTicketDAO saleItemTicketDAO();

}