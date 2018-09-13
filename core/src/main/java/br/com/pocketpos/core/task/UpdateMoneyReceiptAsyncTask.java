package br.com.pocketpos.core.task;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.List;

import br.com.pocketpos.data.exception.CannotInitializeDatabaseException;
import br.com.pocketpos.data.exception.InternalException;
import br.com.pocketpos.data.room.ReceiptVO;
import br.com.pocketpos.data.util.DB;
import br.com.pocketpos.data.util.Messaging;

public final class UpdateMoneyReceiptAsyncTask<C extends Context & UpdateMoneyReceiptAsyncTask.Listener> extends AsyncTask<Double, Void, Object> {


    private WeakReference<C> context;


    public UpdateMoneyReceiptAsyncTask(Context context) {

        this.context = new WeakReference<>((C) context);

    }


    protected Object doInBackground(Double... parameters) {

        Double newValue = parameters[0];

        DB database = null;

        if (context.get() != null)

            database = DB.getInstance(context.get());

        if (database==null)

            return new CannotInitializeDatabaseException();

        try {

            database.beginTransaction();

            List<ReceiptVO> moneyReceipts = database.
                    receiptDAO().
                    retrieveByMethod("DIN");

            if (moneyReceipts != null && !moneyReceipts.isEmpty()){

                ReceiptVO moneyReceipt = moneyReceipts.get(0);

                moneyReceipt.setValue(newValue);

                database.receiptDAO().update(moneyReceipt);

            } else {

                ReceiptVO moneyReceipt = new ReceiptVO();

                moneyReceipt.setIdentifier(database.receiptDAO().count() + 1);

                moneyReceipt.setMethod("DIN");

                moneyReceipt.setValue(newValue);

                database.receiptDAO().create(moneyReceipt);

            }

            database.setTransactionSuccessful();

            return null;

        } catch(Exception e) {

            return new InternalException();

        } finally {

            if (database.inTransaction())

                database.endTransaction();

        }

    }


    protected void onPostExecute(Object callResult) {

        C listener = this.context.get();

        if (listener != null)

            if (callResult instanceof Messaging)

                listener.onUpdateMoneyReceiptItemFailure((Messaging) callResult);

    }


    public interface Listener {

        void onUpdateMoneyReceiptItemFailure(Messaging messaging);

    }


}