package br.com.pocketpos.app.report;

import java.util.Date;

import br.com.pocketpos.app.report.adapter.OnPrintListener;
import br.com.pocketpos.app.report.task.PT7003CloseCashAsyncTask;
import br.com.pocketpos.app.report.task.PT7003OpenCashAsyncTask;
import br.com.pocketpos.app.report.task.PT7003RemoveCashAsyncTask;
import br.com.pocketpos.app.report.task.PT7003SupplyCashAsyncTask;
import br.com.pocketpos.data.room.CashModel;

public class PT7003Report implements Report {

    /* CUPOM DE ABERTURA DO CAIXA */
    public void printOpenCashCoupon(
            OnPrintListener listener,
            String title,
            String subtitle,
            Date dateTime,
            String deviceAlias,
            CashModel... cashModels) {

        new PT7003OpenCashAsyncTask<>(
                listener,
                title,
                subtitle,
                dateTime,
                deviceAlias).execute(cashModels);

    }

    /* CUPOM DE COMPLEMENTO DO CAIXA */
    public void printSupplyCashCoupon(
            OnPrintListener listener,
            String title,
            String subtitle,
            Date dateTime,
            String deviceAlias,
            CashModel... cashModels) {

        new PT7003SupplyCashAsyncTask<>(
                listener,
                title,
                subtitle,
                dateTime,
                deviceAlias).execute(cashModels);

    }

    /* CUPOM DE SANGRIA DO CAIXA */
    public void printRemoveCashCoupon(
            OnPrintListener listener,
            String title,
            String subtitle,
            Date dateTime,
            String deviceAlias,
            CashModel... cashModels) {

        new PT7003RemoveCashAsyncTask<>(
                listener,
                title,
                subtitle,
                dateTime,
                deviceAlias).execute(cashModels);

    }

    /* CUPOM DE FECHAMENTO DO CAIXA */
    public void printCloseCashCoupon(
            OnPrintListener listener,
            String title,
            String subtitle,
            Date dateTime,
            String deviceAlias,
            CashModel... cashModels) {

        new PT7003CloseCashAsyncTask<>(
                listener,
                title,
                subtitle,
                dateTime,
                deviceAlias).execute(cashModels);

    }

}