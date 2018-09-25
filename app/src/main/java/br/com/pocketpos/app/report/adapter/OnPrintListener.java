package br.com.pocketpos.app.report.adapter;

import br.com.pocketpos.app.report.ReportName;
import br.com.pocketpos.data.util.Messaging;

public interface OnPrintListener {

    void onPrintPreExecute(ReportName report);

    void onPrintProgressInitialize(ReportName report, int progress, int max);

    void onPrintProgressUpdate(ReportName report, int status);

    void onPrintSuccess(ReportName report);

    void onPrintFailure(ReportName report, Messaging message);

    void onPrintCancelled(ReportName report);

}