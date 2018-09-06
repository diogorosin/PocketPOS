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

public final class CloseCashAsyncTask<A extends Activity & CloseCashAsyncTask.Listener> extends AsyncTask<Object, Void, Object> {


    private WeakReference<A> activity;


    public CloseCashAsyncTask(A activity) {

        this.activity = new WeakReference<>(activity);

    }


    protected Object doInBackground(Object... parameters) {

        Double money = (Double) parameters[0];

        Double card = (Double) parameters[1];

        Integer user = (Integer) parameters[2];

        Date date = new Date();

        DB database = null;

        if (activity.get() != null)

            database = DB.getInstance(activity.get().getBaseContext());

        if (database==null)

            return new CannotInitializeDatabaseException();

        try {

            database.beginTransaction();


            CashVO moneyCashVO = new CashVO();

            moneyCashVO.setType("S");

            moneyCashVO.setOperation("FEC");

            moneyCashVO.setDateTime(date);

            moneyCashVO.setMethod("DIN");

            moneyCashVO.setOrigin(null);

            moneyCashVO.setTotal(money);

            moneyCashVO.setUser(user);

            moneyCashVO.setNote("Fechamento do caixa (Dinheiro)");

            moneyCashVO.setIdentifier(database.cashDAO().create(moneyCashVO).intValue());


            CashVO cardCashVO = new CashVO();

            cardCashVO.setType("S");

            cardCashVO.setOperation("FEC");

            cardCashVO.setDateTime(date);

            cardCashVO.setMethod("CCR");

            cardCashVO.setOrigin(null);

            cardCashVO.setTotal(card);

            cardCashVO.setUser(user);

            cardCashVO.setNote("Fechamento do caixa (Cartão de Crédito)");

            cardCashVO.setIdentifier(database.cashDAO().create(cardCashVO).intValue());


            database.setTransactionSuccessful();


            CashModel moneyCashModel = new CashModel();

            moneyCashModel.setIdentifier(moneyCashVO.getIdentifier());

            moneyCashModel.setDateTime(moneyCashVO.getDateTime());

            moneyCashModel.setType(moneyCashVO.getType());

            moneyCashModel.setNote(moneyCashVO.getNote());

            moneyCashModel.setOperation(moneyCashVO.getOperation());

            moneyCashModel.setOrigin(moneyCashVO.getOrigin());

            moneyCashModel.setMethod(moneyCashVO.getMethod());

            moneyCashModel.setTotal(moneyCashVO.getTotal());

            moneyCashModel.setUser(database.userDAO().retrieve(moneyCashVO.getUser()));


            CashModel cardCashModel = new CashModel();

            cardCashModel.setIdentifier(cardCashVO.getIdentifier());

            cardCashModel.setDateTime(cardCashVO.getDateTime());

            cardCashModel.setType(cardCashVO.getType());

            cardCashModel.setNote(cardCashVO.getNote());

            cardCashModel.setOperation(cardCashVO.getOperation());

            cardCashModel.setOrigin(cardCashVO.getOrigin());

            cardCashModel.setMethod(cardCashVO.getMethod());

            cardCashModel.setTotal(cardCashVO.getTotal());

            cardCashModel.setUser(database.userDAO().retrieve(cardCashVO.getUser()));


            return new CashModel[]{ moneyCashModel, cardCashModel };

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

            if (callResult instanceof CashModel[]) {

                listener.onCloseCashSuccess(((CashModel[]) callResult)[0], ((CashModel[]) callResult)[1] );

            } else {

                if (callResult instanceof Messaging) {

                    listener.onCloseCashFailure((Messaging) callResult);

                }

            }

        }

    }


    public interface Listener {

        void onCloseCashSuccess(CashModel moneyCashModel, CashModel cardCashModel);

        void onCloseCashFailure(Messaging messaging);

    }


}