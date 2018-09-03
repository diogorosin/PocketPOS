package br.com.pocketpos.core.task;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import br.com.pocketpos.data.exception.CannotInitializeDatabaseException;
import br.com.pocketpos.data.exception.InternalException;
import br.com.pocketpos.data.room.CatalogItemVO;
import br.com.pocketpos.data.util.DB;
import br.com.pocketpos.data.util.Messaging;

public final class ResetCatalogItemAsyncTask<A extends Activity & ResetCatalogItemAsyncTask.Listener> extends AsyncTask<Object, Void, Object> {


    private WeakReference<A> activity;


    public ResetCatalogItemAsyncTask(A activity) {

        this.activity = new WeakReference<>(activity);

    }


    protected Object doInBackground(Object... parameters) {

        Integer tab = (Integer) parameters[0];

        Integer item = (Integer) parameters[1];

        DB database = null;

        if (activity.get() != null)

            database = DB.getInstance(activity.get().getBaseContext());

        if (database==null)

            return new CannotInitializeDatabaseException();

        try {

            database.beginTransaction();

            CatalogItemVO catalogItemVO = database.
                    catalogItemDAO().
                    retrieve(tab, item);

            catalogItemVO.setQuantity(0.0);

            catalogItemVO.setTotal(0.0);

            database.catalogItemDAO().update(catalogItemVO);

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

        A listener = this.activity.get();

        if (listener != null)

            if (callResult instanceof Messaging)

                listener.onResetCatalogItemFailure((Messaging) callResult);

    }


    public interface Listener {

        void onResetCatalogItemFailure(Messaging messaging);

    }


}