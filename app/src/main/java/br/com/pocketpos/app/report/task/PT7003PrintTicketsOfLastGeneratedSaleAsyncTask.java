package br.com.pocketpos.app.report.task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Process;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.pocketpos.app.report.ReportName;
import br.com.pocketpos.app.report.adapter.OnPrintListener;
import br.com.pocketpos.app.report.exception.FailPrinterException;
import br.com.pocketpos.app.report.exception.InParametersErrorPrinterException;
import br.com.pocketpos.app.report.exception.NoPaperPrinterException;
import br.com.pocketpos.app.report.exception.TimeoutPrinterException;
import br.com.pocketpos.app.report.layout.PT7003SaleItemTicketFooterLayout;
import br.com.pocketpos.app.report.layout.PT7003SaleItemTicketHeaderLayout;
import br.com.pocketpos.app.report.layout.PT7003SaleItemTicketLayout;
import br.com.pocketpos.app.report.util.PT7003Printer;
import br.com.pocketpos.data.room.SaleItemTicketModel;
import br.com.pocketpos.data.util.DB;
import br.com.pocketpos.data.util.Messaging;

public class PT7003PrintTicketsOfLastGeneratedSaleAsyncTask<
        E extends Context,
        A extends OnPrintListener,
        B extends Void,
        C extends Integer,
        D extends Map> extends AsyncTask<B, C, D>  {


    private static final int ERROR_PROPERTY = 1;


    private PT7003SaleItemTicketHeaderLayout header;

    private PT7003SaleItemTicketFooterLayout footer;

    private PT7003Printer printer;


    private WeakReference<E> context;

    private WeakReference<A> listener;

    private String userName;

    private String deviceAlias;


    public PT7003PrintTicketsOfLastGeneratedSaleAsyncTask(E context,
                                                          A listener,
                                                          String title,
                                                          String subtitle,
                                                          String deviceAlias,
                                                          String userName,
                                                          String note,
                                                          String footer){

        this.context = new WeakReference<>(context);

        this.listener = new WeakReference<>(listener);

        this.deviceAlias = deviceAlias;

        this.userName = userName;

        this.printer = new PT7003Printer();

        this.header = new PT7003SaleItemTicketHeaderLayout(printer);

        this.header.setTitle(title);

        this.header.setSubtitle(subtitle);

        this.footer = new PT7003SaleItemTicketFooterLayout(printer);

        this.footer.setNote(note);

        this.footer.setFooter(footer);

    }


    protected void onPreExecute() {

        A l = this.listener.get();

        if (l != null)

            l.onPrintPreExecute(ReportName.SALE_ITEM_COUPON);

    }


    protected Map doInBackground(Void... voids) {

        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND + Process.THREAD_PRIORITY_MORE_FAVORABLE);

        E c = context.get();

        if (c != null) {

            DB database = DB.getInstance(c);

            int sale = database.saleDAO().lastGeneratedSale();

            int printedTicketsCountOfSale = database.saleItemTicketDAO().getPrintedTicketsCountOfSale(sale);

            List<SaleItemTicketModel> saleItemTicketModels = database.
                    saleItemTicketDAO().
                    getTicketsOfSale(sale);

            A l = listener.get();

            if (l != null)

                l.onPrintProgressInitialize(
                        ReportName.SALE_ITEM_COUPON,
                        printedTicketsCountOfSale,
                        saleItemTicketModels.size());

            try {

                printer.open();

                printer.init();

                for (SaleItemTicketModel saleItemTicketModel : saleItemTicketModels) {

                    if (!saleItemTicketModel.getPrinted()) {

                        //IMPRIME O CABECALHO DO TICKET
                        header.print();

                        //IMPRIME O CORPO DO TICKET
                        PT7003SaleItemTicketLayout body = new PT7003SaleItemTicketLayout(printer);

                        body.setDeviceAlias(deviceAlias);

                        body.setSale(saleItemTicketModel.getSaleItem().getSale());

                        body.setItem(saleItemTicketModel.getSaleItem().getItem());

                        body.setTicket(saleItemTicketModel.getTicket());

                        body.setOf(saleItemTicketModel.getOf());

                        body.setUserName(userName);

                        body.setDateTime(new Date());

                        body.setDenomination(saleItemTicketModel.getDenomination());

                        body.setQuantity(saleItemTicketModel.getQuantity());

                        body.setMeasureUnit(saleItemTicketModel.getMeasureUnit().getAcronym());

                        body.print();

                        //IMPRIME O RODAPE DO TICKET
                        footer.print();

                        //DEFINE O TICKET COMO IMPRESSO.
                        database.saleItemTicketDAO().setTicketAsPrinted(
                                saleItemTicketModel.getSaleItem().getSale(),
                                saleItemTicketModel.getSaleItem().getItem(),
                                saleItemTicketModel.getTicket());

                        //ATUALIZA O STATUS
                        if (l != null)

                            l.onPrintProgressUpdate(ReportName.SALE_ITEM_COUPON, 1);

                    }

                }

                printer.printString(" ");

            } catch (NoPaperPrinterException |
                    FailPrinterException |
                    InParametersErrorPrinterException |
                    TimeoutPrinterException e) {

                Map<Integer, Object> result = new HashMap<>();

                result.put(ERROR_PROPERTY, e);

                return result;

            } finally {

                printer.close();

            }

        }

        return new HashMap<>();

    }


    protected void onPostExecute(Map callResult) {

        A listener = this.listener.get();

        if (listener != null) {

            if (callResult instanceof Map) {

                Map<Integer, Object> map = (Map<Integer, Object>) callResult;

                if (map.containsKey(ERROR_PROPERTY)){

                    listener.onPrintFailure(
                            ReportName.SALE_ITEM_COUPON,
                            (Messaging) map.get(ERROR_PROPERTY));

                } else {

                    listener.onPrintSuccess(
                            ReportName.SALE_ITEM_COUPON);

                }

            }

        }

    }


    protected void onProgressUpdate(Integer... progress) {

        A l = this.listener.get();

        if (l != null)

            l.onPrintProgressUpdate(ReportName.SALE_ITEM_COUPON, progress[0]);

    }


    protected void onCancelled() {

        A l = this.listener.get();

        if (l != null)

            l.onPrintCancelled(ReportName.SALE_ITEM_COUPON);

    }


}