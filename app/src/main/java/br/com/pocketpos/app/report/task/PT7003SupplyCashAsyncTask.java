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
import br.com.pocketpos.app.report.layout.PT7003SupplyCashLayout;
import br.com.pocketpos.data.room.CashModel;

public class PT7003SupplyCashAsyncTask<
        A extends OnPrintListener,
        B extends CashModel,
        C extends Integer,
        D extends List> extends AsyncTask<B, C, D> {


    private WeakReference<A> listener;

    private PT7003HeaderLayout header;

    private Printer printer;


    public PT7003SupplyCashAsyncTask(A listener,
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

            l.onPrintPreExecute(ReportName.SUPPLY_CASH_COUPON);

    }


    protected List<CashModel> doInBackground(CashModel... cashModels) {

        List<CashModel> result = new ArrayList();

        this.printer.open();

        this.printer.init();

        this.header.print();

        for (CashModel cashModel : cashModels) {

            PT7003SupplyCashLayout body = new PT7003SupplyCashLayout(printer);

            body.setReportName("COMPLEMENTO DO CAIXA");

            body.setDateTime(cashModel.getDateTime());

            body.setUserName(cashModel.getUser().getName());

            body.setValue(cashModel.getValue());

            body.print();

            A l = this.listener.get();

            if (l != null)

                l.onPrintProgressUpdate(ReportName.SUPPLY_CASH_COUPON, 1);

            result.add(cashModels[0]);

        }

        this.printer.close();

        return result;

    }


    protected void onPostExecute(List callResult) {

        A l = this.listener.get();

        if (l == null)

            return;

        l.onPrintPostExecute(ReportName.SUPPLY_CASH_COUPON, callResult);

    }


    protected void onProgressUpdate(Integer... progress) {

        A l = this.listener.get();

        if (l != null)

            l.onPrintProgressUpdate(ReportName.SUPPLY_CASH_COUPON, progress[0]);

    }


    protected void onCancelled() {

        A l = this.listener.get();

        if (l != null)

            l.onPrintCancelled(ReportName.SUPPLY_CASH_COUPON);

    }


}