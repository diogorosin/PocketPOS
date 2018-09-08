package br.com.pocketpos.app.report.task;

import android.os.AsyncTask;
import android.pt.printer.Printer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.pocketpos.app.report.ReportName;
import br.com.pocketpos.app.report.adapter.OnPrintListener;
import br.com.pocketpos.app.report.layout.PT7003HeaderLayout;
import br.com.pocketpos.app.report.layout.PT7003RemoveCashFirstWayLayout;
import br.com.pocketpos.app.report.layout.PT7003RemoveCashSecondWayLayout;
import br.com.pocketpos.data.room.CashModel;

public class PT7003RemoveCashAsyncTask<
        A extends OnPrintListener,
        B extends CashModel,
        C extends Integer,
        D extends List> extends AsyncTask<B, C, D> {


    private WeakReference<A> listener;

    private PT7003HeaderLayout header;

    private Printer printer;


    public PT7003RemoveCashAsyncTask(A listener,
                                     String title,
                                     String subtitle,
                                     Date dateTime,
                                     String deviceAlias){

        this.listener = new WeakReference<>(listener);

        this.printer = new Printer();

        this.header = new PT7003HeaderLayout(printer);

        this.header.setTitle(title);

        this.header.setSubtitle(subtitle);

        this.header.setDateTime(dateTime);

        this.header.setAlias(deviceAlias);

    }


    protected void onPreExecute() {

        A l = this.listener.get();

        if (l != null)

            l.onPrintPreExecute(ReportName.REMOVE_CASH_COUPON);

    }


    protected List<CashModel> doInBackground(CashModel... cashModels) {

        List<CashModel> result = new ArrayList();

        printer.open();

        printer.init();

        header.print();

        PT7003RemoveCashFirstWayLayout firstWay = new PT7003RemoveCashFirstWayLayout(printer);

        firstWay.setReportName("SANGRIA DO CAIXA");

        firstWay.setDateTime(cashModels[0].getDateTime());

        firstWay.setUserName(cashModels[0].getUser().getName());

        firstWay.setValue(cashModels[0].getValue());

        firstWay.print();

        printer.printString("--------------------------------");

        printer.printString(" ");

        printer.printString(" ");

        header.print();

        PT7003RemoveCashSecondWayLayout secondWay = new PT7003RemoveCashSecondWayLayout(printer);

        secondWay.setReportName("SANGRIA DO CAIXA");

        secondWay.setDateTime(cashModels[0].getDateTime());

        secondWay.setUserName(cashModels[0].getUser().getName());

        secondWay.setValue(cashModels[0].getValue());

        secondWay.print();

        A l = this.listener.get();

        if (l != null)

            l.onPrintProgressUpdate(ReportName.REMOVE_CASH_COUPON, 1);

        result.add(cashModels[0]);

        this.printer.close();

        return result;

    }


    protected void onPostExecute(List callResult) {

        A l = this.listener.get();

        if (l == null)

            return;

        l.onPrintPostExecute(ReportName.REMOVE_CASH_COUPON, callResult);

    }


    protected void onProgressUpdate(Integer... progress) {

        A l = this.listener.get();

        if (l != null)

            l.onPrintProgressUpdate(ReportName.REMOVE_CASH_COUPON, progress[0]);

    }


    protected void onCancelled() {

        A l = this.listener.get();

        if (l != null)

            l.onPrintCancelled(ReportName.REMOVE_CASH_COUPON);

    }


}