package br.com.pocketpos.core.task;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.Date;

import br.com.pocketpos.data.exception.CannotInitializeDatabaseException;
import br.com.pocketpos.data.exception.InternalException;
import br.com.pocketpos.data.room.CashModel;
import br.com.pocketpos.data.room.CashVO;
import br.com.pocketpos.data.room.CatalogItemModel;
import br.com.pocketpos.data.room.MeasureUnitGroup;
import br.com.pocketpos.data.room.ReceiptModel;
import br.com.pocketpos.data.room.ReceiptVO;
import br.com.pocketpos.data.room.SaleCashVO;
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

        Integer deviceIdentifier = (Integer) parameters[1];

        String deviceAlias = (String) parameters[2];

        String title = (String) parameters[3];

        String subtitle = (String) parameters[4];

        String note = (String) parameters[5];

        String footer = (String) parameters[6];

        Date date = new Date();

        DB database = null;

        if (activity.get() != null)

            database = DB.getInstance(activity.get().getBaseContext());

        if (database==null)

            return new CannotInitializeDatabaseException();

        try {

            database.beginTransaction();

            //CRIA A VENDA
            SaleVO saleVO = new SaleVO();

            saleVO.setDateTime(date);

            saleVO.setUser(user);

            saleVO.setIdentifier(database.saleDAO().create(saleVO).intValue());

            //CRIA OS ITENS DA VENDA
            int item = 1;

            for (CatalogItemModel catalogItemModel : database.catalogItemDAO().getList()) {

                SaleItemVO saleItemVO = new SaleItemVO();

                saleItemVO.setSale(saleVO.getIdentifier());

                saleItemVO.setItem(item);

                saleItemVO.setProduct(catalogItemModel.getProduct().getIdentifier());

                saleItemVO.setPrice(catalogItemModel.getPrice());

                saleItemVO.setQuantity(catalogItemModel.getQuantity());

                saleItemVO.setTotal(catalogItemModel.getTotal());

                database.saleItemDAO().create(saleItemVO);

                //CRIA OS TICKETS
                boolean isUnit = catalogItemModel.getMeasureUnit().getGroup().equals(MeasureUnitGroup.UNIT);

                int quantity = isUnit ? catalogItemModel.getQuantity().intValue() : 1;

                for (int i = 0 ; i < quantity; i++){

                    SaleItemTicketVO saleItemTicketVO = new SaleItemTicketVO();

                    saleItemTicketVO.setSale(saleItemVO.getSale());

                    saleItemTicketVO.setItem(saleItemVO.getItem());

                    saleItemTicketVO.setTicket(i);

                    saleItemTicketVO.setOf(quantity);

                    saleItemTicketVO.setTitle(title);

                    saleItemTicketVO.setSubtitle(subtitle);

                    saleItemTicketVO.setDateTime(date);

                    saleItemTicketVO.setDeviceIdentifier(deviceIdentifier);

                    saleItemTicketVO.setDeviceAlias(deviceAlias);

                    saleItemTicketVO.setDenomination(catalogItemModel.getDenomination());

                    saleItemTicketVO.setQuantity(isUnit ? 1 : catalogItemModel.getQuantity());

                    saleItemTicketVO.setMeasureUnit(catalogItemModel.getMeasureUnit().getIdentifier());

                    saleItemTicketVO.setNote(note);

                    saleItemTicketVO.setFooter(footer);

                    saleItemTicketVO.setPrinted(false);

                    database.saleItemTicketDAO().create(saleItemTicketVO);

                }

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

                        cashVO.setValue(receiptMethod.getValue());

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

            SaleModel saleModel = new SaleModel();

            saleModel.setIdentifier(saleVO.getIdentifier());

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