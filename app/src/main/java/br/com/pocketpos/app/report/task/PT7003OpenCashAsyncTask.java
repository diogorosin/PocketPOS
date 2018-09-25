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
import br.com.pocketpos.app.report.layout.PT7003HeaderLayout;
import br.com.pocketpos.app.report.layout.PT7003OpenCashLayout;
import br.com.pocketpos.data.room.CashModel;

public class PT7003OpenCashAsyncTask<
        A extends OnPrintListener,
        B extends CashModel,
        C extends Integer,
        D extends List> extends AsyncTask<B, C, D> {


    private WeakReference<A> listener;

    private PT7003HeaderLayout header;

    private Printer printer;


    public PT7003OpenCashAsyncTask(A listener,
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

            l.onPrintPreExecute(ReportName.OPEN_CASH_COUPON);

    }


    protected List<CashModel> doInBackground(CashModel... cashModels) {

        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND + Process.THREAD_PRIORITY_MORE_FAVORABLE);

        A l = this.listener.get();

        if (l != null)

            l.onPrintProgressInitialize(ReportName.OPEN_CASH_COUPON, 0, 1);

        List<CashModel> result = new ArrayList();

        this.printer.open();

        this.printer.init();

        this.header.print();

        for (CashModel cashModel : cashModels) {

            PT7003OpenCashLayout body = new PT7003OpenCashLayout(printer);

            body.setReportName("ABERTURA DO CAIXA");

            body.setDateTime(cashModel.getDateTime());

            body.setUserName(cashModel.getUser().getName());

            body.setValue(cashModel.getValue());

            body.print();

            l = this.listener.get();

            if (l != null)

                l.onPrintProgressUpdate(ReportName.OPEN_CASH_COUPON, 1);

            result.add(cashModels[0]);

        }

        this.printer.close();

        return result;

    }


    protected void onPostExecute(List callResult) {

        A l = this.listener.get();

        if (l == null)

            return;

        l.onPrintSuccess(ReportName.OPEN_CASH_COUPON);

    }


    protected void onProgressUpdate(Integer... progress) {

        A l = this.listener.get();

        if (l != null)

            l.onPrintProgressUpdate(ReportName.OPEN_CASH_COUPON, progress[0]);

    }


    protected void onCancelled() {

        A l = this.listener.get();

        if (l != null)

            l.onPrintCancelled(ReportName.OPEN_CASH_COUPON);

    }


}