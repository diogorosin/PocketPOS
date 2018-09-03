package br.com.pocketpos.app.report.adapter;

import java.util.List;

import br.com.pocketpos.app.report.ReportName;

public interface OnPrintListener {

    void onPrintPreExecute(ReportName report);

    void onPrintProgressUpdate(ReportName report, int status);

    void onPrintPostExecute(ReportName report, List<Object> printed);

    void onPrintCancelled(ReportName report);

}