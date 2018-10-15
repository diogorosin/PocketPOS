package br.com.pocketpos.core.task;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.pocketpos.data.exception.CannotInitializeDatabaseException;
import br.com.pocketpos.data.exception.InternalException;
import br.com.pocketpos.data.room.CashVO;
import br.com.pocketpos.data.room.CatalogItemModel;
import br.com.pocketpos.data.room.MeasureUnitGroup;
import br.com.pocketpos.data.room.ProductModel;
import br.com.pocketpos.data.room.ProductProductModel;
import br.com.pocketpos.data.room.ReceiptModel;
import br.com.pocketpos.data.room.ReceiptVO;
import br.com.pocketpos.data.room.SaleCashVO;
import br.com.pocketpos.data.room.SaleItemTicketVO;
import br.com.pocketpos.data.room.SaleItemVO;
import br.com.pocketpos.data.room.SaleVO;
import br.com.pocketpos.data.util.DB;
import br.com.pocketpos.data.util.Messaging;

public final class FinalizeSaleAsyncTask<A extends Activity & FinalizeSaleAsyncTask.Listener>
        extends AsyncTask<Object, Void, Object> {


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

            SaleVO saleVO = new SaleVO();

            saleVO.setDateTime(date);

            saleVO.setUser(user);

            saleVO.setIdentifier(database.saleDAO().create(saleVO).intValue());

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

                //VERIFICA SE O ITEM É UNITARIO.
                boolean isUnit = catalogItemModel.getMeasureUnit().getGroup() == MeasureUnitGroup.UNIT.ordinal();

                //CASO SEJA UNITARIO, IMPRIME UM TICKET POR UNIDADE.
                int quantity = isUnit ? catalogItemModel.getQuantity().intValue() : 1;

                //VERIFICA SE O ITEM É COMPOSTO.
                boolean isComposed = database.
                        productDAO().
                        isComposed(catalogItemModel.
                                getProduct().
                                getIdentifier());

                //SE FOR UNITARIO E O PRODUTO POSSUI COMPOSICAO
                if (isUnit && isComposed){

                    //CRIA LISTA DE TICKETS
                    List<SaleItemTicketVO> ticketList = new ArrayList<>();

                    //VARIAVEL QUE CONTROLA O NUMERO
                    //SEQUENCIAL DOS TICKETS DO ITEM VENDIDO
                    int ticket = 1;

                    //BUSCA A COMPOSICAO DO PRODUTO
                    List<ProductProductModel> parts = database.
                            productProductDAO().
                            getCompositionOfProduct(catalogItemModel.
                                    getProduct().getIdentifier());

                    //QUANTIDADE DO ITEM DA VENDA
                    for (int x = 1; x <= quantity; x++) {

                        //PARTES DO PRODUTO
                        for (ProductProductModel part: parts) {

                            //BUSCA OS DADOS DO COMPONENTE
                            ProductModel partModel = database.productDAO().get(part.getPart().getIdentifier());

                            //VERIFICA SE O COMPONENTE É UNITARIO
                            boolean isPartUnit = partModel.getMeasureUnit().getGroup() == MeasureUnitGroup.UNIT.ordinal();

                            //CASO SEJA UNITARIO, IMPRIME UM TICKET POR UNIDADE DO COMPONENTE.
                            int partQuantity = isPartUnit ? part.getQuantity().intValue() : 1;

                            //GERA O CUPOM DO COMPONENTE
                            for (int y = 1; y <= partQuantity; y++){

                                SaleItemTicketVO saleItemTicketVO = new SaleItemTicketVO();

                                saleItemTicketVO.setSale(saleItemVO.getSale());

                                saleItemTicketVO.setItem(saleItemVO.getItem());

                                saleItemTicketVO.setTicket(ticket);

                                saleItemTicketVO.setOf(0);

                                saleItemTicketVO.setDenomination(partModel.getDenomination());

                                saleItemTicketVO.setQuantity(isPartUnit ? 1 : part.getQuantity());

                                saleItemTicketVO.setMeasureUnit(partModel.getMeasureUnit().getIdentifier());

                                saleItemTicketVO.setPrinted(false);

                                ticketList.add(saleItemTicketVO);

                                ticket++;

                            }

                        }

                    }

                    for (SaleItemTicketVO saleItemTicketVO: ticketList) {

                        //ATUALIZA O TOTAL DE TICKETS CRIADOS PARA O ITEM
                        saleItemTicketVO.setOf(ticket);

                        //CRIA O ITEM
                        database.saleItemTicketDAO().create(saleItemTicketVO);

                    }

                } else {

                    for (int n = 1; n <= quantity; n++) {

                        SaleItemTicketVO saleItemTicketVO = new SaleItemTicketVO();

                        saleItemTicketVO.setSale(saleItemVO.getSale());

                        saleItemTicketVO.setItem(saleItemVO.getItem());

                        saleItemTicketVO.setTicket(n);

                        saleItemTicketVO.setOf(quantity);

                        saleItemTicketVO.setDenomination(catalogItemModel.getDenomination());

                        saleItemTicketVO.setQuantity(isUnit ? 1 : catalogItemModel.getQuantity());

                        saleItemTicketVO.setMeasureUnit(catalogItemModel.getMeasureUnit().getIdentifier());

                        saleItemTicketVO.setPrinted(false);

                        database.saleItemTicketDAO().create(saleItemTicketVO);

                    }

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

            return saleVO.getIdentifier();

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

            if (callResult instanceof Integer) {

                listener.onFinalizeSaleSuccess((Integer) callResult);

            } else {

                if (callResult instanceof Messaging) {

                    listener.onFinalizeSaleFailure((Messaging) callResult);

                }

            }

        }

    }


    public interface Listener {

        void onFinalizeSaleSuccess(Integer sale);

        void onFinalizeSaleFailure(Messaging messaging);

    }


}