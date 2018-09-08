package br.com.pocketpos.core.task;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.Date;

import br.com.pocketpos.data.exception.CannotInitializeDatabaseException;
import br.com.pocketpos.data.exception.InternalException;
import br.com.pocketpos.data.room.CashModel;
import br.com.pocketpos.data.room.CashVO;
import br.com.pocketpos.data.util.DB;
import br.com.pocketpos.data.util.Messaging;

public final class RemovalCashAsyncTask<A extends Activity & RemovalCashAsyncTask.Listener> extends AsyncTask<Object, Void, Object> {


    private WeakReference<A> activity;


    public RemovalCashAsyncTask(A activity) {

        this.activity = new WeakReference<>(activity);

    }


    protected Object doInBackground(Object... parameters) {

        Double value = (Double) parameters[0];

        Integer user = (Integer) parameters[1];

        DB database = null;

        if (activity.get() != null)

            database = DB.getInstance(activity.get().getBaseContext());

        if (database==null)

            return new CannotInitializeDatabaseException();

        try {

            database.beginTransaction();


            CashVO cashVO = new CashVO();

            cashVO.setType("S");

            cashVO.setOperation("SAN");

            cashVO.setValue(value);

            cashVO.setNote("Sangria do Caixa");

            cashVO.setUser(user);

            cashVO.setDateTime(new Date());

            cashVO.setIdentifier(database.cashDAO().create(cashVO).intValue());


            database.setTransactionSuccessful();


            CashModel cashModel = new CashModel();

            cashModel.setIdentifier(cashVO.getIdentifier());

            cashModel.setType(cashVO.getType());

            cashModel.setOperation(cashVO.getOperation());

            cashModel.setValue(cashVO.getValue());

            cashModel.setNote(cashVO.getNote());

            cashModel.setUser(database.userDAO().retrieve(cashVO.getUser()));

            cashModel.setDateTime(cashVO.getDateTime());


            return cashModel;

        } catch(Exception e) {

            return new InternalException();

        } finally {

            if (database.inTransaction())

                database.endTransaction();

        }

    }


    protected void onPostExecute(Object callResult) {

        A listener = this.activity.get();

        if (listener != null) {

            if (callResult instanceof CashModel) {

                listener.onRemoveCashSuccess((CashModel) callResult);

            } else {

                if (callResult instanceof Messaging) {

                    listener.onRemoveCashFailure((Messaging) callResult);

                }

            }

        }

    }


    public interface Listener {

        void onRemoveCashSuccess(CashModel cashModel);

        void onRemoveCashFailure(Messaging messaging);

    }


}