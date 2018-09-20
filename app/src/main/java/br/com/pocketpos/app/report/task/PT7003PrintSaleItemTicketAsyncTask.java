package br.com.pocketpos.app.report.task;

import android.os.AsyncTask;
import android.os.Process;
import android.pt.printer.Printer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.pocketpos.app.report.ReportName;
import br.com.pocketpos.app.report.adapter.OnPrintListener;
import br.com.pocketpos.app.report.layout.PT7003SaleItemTicketFooterLayout;
import br.com.pocketpos.app.report.layout.PT7003SaleItemTicketHeaderLayout;
import br.com.pocketpos.app.report.layout.PT7003SaleItemTicketLayout;
import br.com.pocketpos.data.room.SaleItemTicketModel;

public class PT7003PrintSaleItemTicketAsyncTask<
        A extends OnPrintListener,
        B extends SaleItemTicketModel,
        C extends Integer,
        D extends List> extends AsyncTask<B, C, D>  {


    private WeakReference<A> listener;

    private PT7003SaleItemTicketHeaderLayout header;

    private PT7003SaleItemTicketFooterLayout footer;

    private Printer printer;


    private String userName;

    private String deviceAlias;

    private Date dateTime;


    public PT7003PrintSaleItemTicketAsyncTask(A listener,
                                              String title,
                                              String subtitle,
                                              Date dateTime,
                                              String deviceAlias,
                                              String userName,
                                              String note,
                                              String footer){

        this.dateTime = dateTime;

        this.deviceAlias = deviceAlias;

        this.userName = userName;

        this.listener = new WeakReference<>(listener);

        this.printer = new Printer();

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


    protected List<SaleItemTicketModel> doInBackground(SaleItemTicketModel... saleItemTicketModels) {

        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND + Process.THREAD_PRIORITY_MORE_FAVORABLE);

        A l = this.listener.get();

        if (l != null)

            l.onPrintProgressInitialize(ReportName.SALE_ITEM_COUPON, 0, saleItemTicketModels.length);

        List<SaleItemTicketModel> result = new ArrayList();

        this.printer.open();

        this.printer.init();

        for (SaleItemTicketModel saleItemTicketModel: saleItemTicketModels){

            this.header.print();

            PT7003SaleItemTicketLayout body = new PT7003SaleItemTicketLayout(printer);

            body.setDeviceAlias(this.deviceAlias);

            body.setSale(saleItemTicketModel.getSaleItem().getSale());

            body.setItem(saleItemTicketModel.getSaleItem().getItem());

            body.setTicket(saleItemTicketModel.getTicket());

            body.setOf(saleItemTicketModel.getOf());

            body.setUserName(this.userName);

            body.setDateTime(this.dateTime);

            body.setDenomination(saleItemTicketModel.getDenomination());

            body.setQuantity(saleItemTicketModel.getQuantity());

            body.setMeasureUnit(saleItemTicketModel.getMeasureUnit().getAcronym());

            body.print();

            this.footer.print();

            l = this.listener.get();

            if (l != null)

                l.onPrintProgressUpdate(ReportName.SALE_ITEM_COUPON, 1);

            result.add(saleItemTicketModel);

        }

        printer.printString(" ");

        this.printer.close();

        return result;

    }


    protected void onPostExecute(List callResult) {

        A l = this.listener.get();

        if (l == null)

            return;

        l.onPrintPostExecute(ReportName.SALE_ITEM_COUPON, callResult);

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