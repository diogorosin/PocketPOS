package br.com.pocketpos.core.task;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;

import br.com.pocketpos.data.exception.CannotInitializeDatabaseException;
import br.com.pocketpos.data.exception.InternalException;
import br.com.pocketpos.data.room.CashVO;
import br.com.pocketpos.data.room.CatalogItemModel;
import br.com.pocketpos.data.room.MeasureUnitGroup;
import br.com.pocketpos.data.room.ReceiptModel;
import br.com.pocketpos.data.room.ReceiptVO;
import br.com.pocketpos.data.room.SaleCashVO;
import br.com.pocketpos.data.room.SaleItemModel;
import br.com.pocketpos.data.room.SaleItemTicketModel;
import br.com.pocketpos.data.room.SaleItemTicketVO;
import br.com.pocketpos.data.room.SaleItemVO;
import br.com.pocketpos.data.room.SaleModel;
import br.com.pocketpos.data.room.SaleVO;
import br.com.pocketpos.data.util.DB;
import br.com.pocketpos.data.util.Messaging;

public final class FinalizeSaleAsyncTask<A extends Activity & FinalizeSaleAsyncTask.Listener> extends AsyncTask<Object, Void, Object> {


    private WeakReference<A> activity;


    public FinalizeSaleAsyncTask(A activity) {

        this.activity = new WeakReference<>(activity);

    }


    protected Object doInBackground(Object... parameters) {

        Integer user = (Integer) parameters[0];

        Date date = new Date();

        DB database = null;

        if (activity.get() != null)

            database = DB.getInstance(activity.get().getBaseContext());

        if (database==null)

            return new CannotInitializeDatabaseException();

        try {

            database.beginTransaction();

            //CRIA A VENDA
            //SaleVO
            SaleVO saleVO = new SaleVO();

            saleVO.setDateTime(date);

            saleVO.setUser(user);

            saleVO.setIdentifier(database.saleDAO().create(saleVO).intValue());

            //SaleModel
            SaleModel saleModel = new SaleModel();

            saleModel.setIdentifier(saleVO.getIdentifier());

            saleModel.setDateTime(saleVO.getDateTime());

            saleModel.setUser(database.userDAO().retrieve(saleVO.getUser()));

            saleModel.setItems(new ArrayList<SaleItemModel>());

            //CRIA OS ITENS DA VENDA
            int item = 1;

            for (CatalogItemModel catalogItemModel : database.catalogItemDAO().getList()) {

                //SaleItemTicketVO
                SaleItemVO saleItemVO = new SaleItemVO();

                saleItemVO.setSale(saleVO.getIdentifier());

                saleItemVO.setItem(item);

                saleItemVO.setProduct(catalogItemModel.getProduct().getIdentifier());

                saleItemVO.setPrice(catalogItemModel.getPrice());

                saleItemVO.setQuantity(catalogItemModel.getQuantity());

                saleItemVO.setTotal(catalogItemModel.getTotal());

                database.saleItemDAO().create(saleItemVO);

                //SaleItemModel
                SaleItemModel saleItemModel = new SaleItemModel();

                saleItemModel.setSale(saleVO);

                saleItemModel.setItem(saleItemModel.getItem());

                saleItemModel.setProduct(database.productDAO().retrieve(saleItemVO.getProduct()));

                saleItemModel.setPrice(saleItemVO.getPrice());

                saleItemModel.setQuantity(saleItemVO.getQuantity());

                saleItemModel.setTotal(saleItemVO.getTotal());

                saleItemModel.setTickets(new ArrayList<SaleItemTicketModel>());

                //CRIA OS TICKETS DOS ITENS DA VENDA
                boolean isUnit = catalogItemModel.getMeasureUnit().getGroup() == MeasureUnitGroup.UNIT.ordinal();

                int quantity = isUnit ? catalogItemModel.getQuantity().intValue() : 1;

                for (int i = 0 ; i < quantity; i++){

                    //SaleItemTicketVO
                    SaleItemTicketVO saleItemTicketVO = new SaleItemTicketVO();

                    saleItemTicketVO.setSale(saleItemVO.getSale());

                    saleItemTicketVO.setItem(saleItemVO.getItem());

                    saleItemTicketVO.setTicket(i);

                    saleItemTicketVO.setOf(quantity);

                    saleItemTicketVO.setDenomination(catalogItemModel.getDenomination());

                    saleItemTicketVO.setQuantity(isUnit ? 1 : catalogItemModel.getQuantity());

                    saleItemTicketVO.setMeasureUnit(catalogItemModel.getMeasureUnit().getIdentifier());

                    saleItemTicketVO.setPrinted(false);

                    database.saleItemTicketDAO().create(saleItemTicketVO);

                    //SaleItemTicketModel
                    SaleItemTicketModel saleItemTicketModel = new SaleItemTicketModel();

                    saleItemTicketModel.setSaleItem(saleItemVO);

                    saleItemTicketModel.setTicket(saleItemTicketVO.getTicket());

                    saleItemTicketModel.setOf(saleItemTicketVO.getOf());

                    saleItemTicketModel.setDenomination(saleItemTicketVO.getDenomination());

                    saleItemTicketModel.setQuantity(saleItemTicketVO.getQuantity());

                    saleItemTicketModel.setMeasureUnit(database.measureUnitDAO().retrieve(saleItemTicketVO.getMeasureUnit()));

                    saleItemTicketModel.setPrinted(saleItemTicketVO.getPrinted());

                    saleItemModel.getTickets().add(saleItemTicketModel);

                }

                saleModel.getItems().add(saleItemModel);

                item++;

            }

            //CRIAR O RECEBIMENTO EM DINHEIRO CASO O USUARIO
            //NAO TENHA INFORMADO NENHUMA FORMA DE RECEBIMENTO
            if (database.receiptDAO().getCount() == 0){

                Double total = database.catalogItemDAO().getTotal();

                ReceiptVO receiptVO = new ReceiptVO();

                receiptVO.setMethod("DIN");

                receiptVO.setValue(total);

                database.receiptDAO().create(receiptVO);

            }

            //LANCA OS RECEBIMENTOS DA VENDA
            for (ReceiptModel receiptMethod: database.receiptDAO().getList()) {

                switch (receiptMethod.getMethod().getIdentifier()){

                    case "DIN":

                        CashVO cashVO = new CashVO();

                        cashVO.setDateTime(date);

                        cashVO.setOperation("VEN");

                        cashVO.setType("E");

                        cashVO.setNote("Venda Nº " + saleVO.getIdentifier().toString());

                        cashVO.setUser(user);

                        cashVO.setValue(receiptMethod.getValue() + database.receiptDAO().getAmountToReceive());

                        cashVO.setIdentifier(database.cashDAO().create(cashVO).intValue());

                        SaleCashVO saleCashVO = new SaleCashVO();

                        saleCashVO.setSale(saleVO.getIdentifier());

                        saleCashVO.setCash(cashVO.getIdentifier());

                        database.saleCashDAO().create(saleCashVO);

                        break;

                }

            }

            database.catalogItemDAO().reset();

            database.receiptDAO().clear();

            database.setTransactionSuccessful();

            return saleModel;

        } catch(Exception e) {

            return new InternalException();

        } finally {

            if (database.inTransaction())

                database.endTransaction();

        }

    }


    protected void onPostExecute(Object callResult) {

        A listener = this.activity.get();

        if (listener != null) {

            if (callResult instanceof SaleModel) {

                listener.onFinalizeSaleSuccess((SaleModel) callResult);

            } else {

                if (callResult instanceof Messaging) {

                    listener.onFinalizeSaleFailure((Messaging) callResult);

                }

            }

        }

    }


    public interface Listener {

        void onFinalizeSaleSuccess(SaleModel saleModel);

        void onFinalizeSaleFailure(Messaging messaging);

    }


}