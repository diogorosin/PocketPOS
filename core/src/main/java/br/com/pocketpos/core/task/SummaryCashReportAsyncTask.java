package br.com.pocketpos.core.task;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.List;

import br.com.pocketpos.data.exception.CannotInitializeDatabaseException;
import br.com.pocketpos.data.exception.InternalException;
import br.com.pocketpos.data.room.CashModel;
import br.com.pocketpos.data.util.DB;
import br.com.pocketpos.data.util.Messaging;

public final class SummaryCashReportAsyncTask<A extends Activity & SummaryCashReportAsyncTask.Listener> extends AsyncTask<Object, Void, Object> {


    private WeakReference<A> activity;


    public SummaryCashReportAsyncTask(A activity) {

        this.activity = new WeakReference<>(activity);

    }


    protected Object doInBackground(Object... parameters) {

        DB database = null;

        if (activity.get() != null)

            database = DB.getInstance(activity.get().getBaseContext());

        if (database==null)

            return new CannotInitializeDatabaseException();

        try {

            return database.cashDAO().cashSummaryReport();

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

            if (callResult instanceof List) {

                listener.onSummaryReportSuccess((List<CashModel>) callResult);

            } else {

                if (callResult instanceof Messaging) {

                    listener.onSummaryReportFailure((Messaging) callResult);

                }

            }

        }

    }


    public interface Listener {

        void onSummaryReportSuccess(List<CashModel> cashModel);

        void onSummaryReportFailure(Messaging messaging);

    }


}