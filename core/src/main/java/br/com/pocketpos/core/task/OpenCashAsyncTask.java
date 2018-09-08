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

public final class OpenCashAsyncTask<A extends Activity & OpenCashAsyncTask.Listener> extends AsyncTask<Object, Void, Object> {


    private WeakReference<A> activity;


    public OpenCashAsyncTask(A activity) {

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

            cashVO.setType("E");

            cashVO.setOperation("ABE");

            cashVO.setValue(value);

            cashVO.setNote("Abertura do Caixa");

            cashVO.setDateTime(new Date());

            cashVO.setUser(user);

            cashVO.setIdentifier(database.cashDAO().create(cashVO).intValue());


            database.setTransactionSuccessful();


            CashModel cashModel = new CashModel();

            cashModel.setIdentifier(cashVO.getIdentifier());

            cashModel.setType(cashVO.getType());

            cashModel.setOperation(cashVO.getOperation());

            cashModel.setValue(cashVO.getValue());

            cashModel.setNote(cashVO.getNote());

            cashModel.setDateTime(cashVO.getDateTime());

            cashModel.setUser(database.userDAO().retrieve(cashVO.getUser()));


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

                listener.onOpenCashSuccess((CashModel) callResult);

            } else {

                if (callResult instanceof Messaging) {

                    listener.onOpenCashFailure((Messaging) callResult);

                }

            }

        }

    }


    public interface Listener {

        void onOpenCashSuccess(CashModel cashModel);

        void onOpenCashFailure(Messaging messaging);

    }


}