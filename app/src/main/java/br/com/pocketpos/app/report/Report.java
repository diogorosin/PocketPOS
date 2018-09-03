package br.com.pocketpos.app.report;

import java.util.Date;
import java.util.List;

import br.com.pocketpos.app.report.adapter.OnPrintListener;
import br.com.pocketpos.app.report.task.PT7003OpenCashAsyncTask;
import br.com.pocketpos.data.room.CashModel;

public interface Report {

    void printOpenCashCoupon(
            OnPrintListener listener,
            String title,
            String subtitle,
            Date dateTime,
            String deviceAlias,
            CashModel... cashModels);

    void printSupplyCashCoupon(
            OnPrintListener listener,
            String title,
            String subtitle,
            Date dateTime,
            String deviceAlias,
            CashModel... cashModels);

    void printRemoveCashCoupon(
            OnPrintListener listener,
            String title,
            String subtitle,
            Date dateTime,
            String deviceAlias,
            CashModel... cashModels);

    void printCloseCashCoupon(
            OnPrintListener listener,
            String title,
            String subtitle,
            Date dateTime,
            String deviceAlias,
            CashModel... cashModels);

}