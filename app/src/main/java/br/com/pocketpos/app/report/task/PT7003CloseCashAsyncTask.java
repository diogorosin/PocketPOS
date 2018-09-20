package br.com.pocketpos.app.report.task;

import android.os.AsyncTask;
import android.os.Process;
import android.pt.printer.Printer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;

import br.com.pocketpos.app.report.ReportName;
import br.com.pocketpos.app.report.adapter.OnPrintListener;
import br.com.pocketpos.app.report.layout.CloseCashSummaryLayout;
import br.com.pocketpos.app.report.layout.PT7003CloseCashLayout;
import br.com.pocketpos.app.report.layout.PT7003CloseCashSummaryLayout;
import br.com.pocketpos.app.report.layout.PT7003HeaderLayout;
import br.com.pocketpos.data.room.CashModel;

public class PT7003CloseCashAsyncTask<
        A extends OnPrintListener,
        B extends CashModel,
        C extends Integer,
        D extends Void> extends AsyncTask<B, C, D> {


    private WeakReference<A> listener;

    private PT7003HeaderLayout header;

    private Printer printer;


    public PT7003CloseCashAsyncTask(A listener,
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

            l.onPrintPreExecute(ReportName.CLOSE_CASH_COUPON);

    }


    protected Void doInBackground(CashModel... cashModels) {

        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND + Process.THREAD_PRIORITY_MORE_FAVORABLE);

        A l = this.listener.get();

        if (l != null)

            l.onPrintProgressInitialize(ReportName.CLOSE_CASH_COUPON,0,1);

        printer.open();

        printer.init();

        header.print();

        PT7003CloseCashLayout body = new PT7003CloseCashLayout(printer);

        body.setReportName("FECHAMENTO DO CAIXA");

        body.setSummary(new ArrayList<CloseCashSummaryLayout>());

        for (CashModel cashModel: cashModels) {

            PT7003CloseCashSummaryLayout summaryRow = new PT7003CloseCashSummaryLayout(printer);

            summaryRow.setOperation(cashModel.getOperation());

            summaryRow.setDateTime(cashModel.getDateTime());

            summaryRow.setType(cashModel.getType());

            summaryRow.setValue(cashModel.getValue());

            summaryRow.setUserName(cashModel.getUser().getName().toUpperCase());

            body.getSummary().add(summaryRow);

        }

        body.print();

        printer.close();

        l = this.listener.get();

        if (l != null)

            l.onPrintProgressUpdate(ReportName.CLOSE_CASH_COUPON, 1);

        return null;

    }


    protected void onPostExecute(Void object) {

        A l = this.listener.get();

        if (l == null)

            return;

        l.onPrintPostExecute(ReportName.CLOSE_CASH_COUPON, null);

    }


    protected void onProgressUpdate(Integer... progress) {

        A l = this.listener.get();

        if (l != null)

            l.onPrintProgressUpdate(ReportName.CLOSE_CASH_COUPON, progress[0]);

    }


    protected void onCancelled() {

        A l = this.listener.get();

        if (l != null)

            l.onPrintCancelled(ReportName.CLOSE_CASH_COUPON);

    }


}