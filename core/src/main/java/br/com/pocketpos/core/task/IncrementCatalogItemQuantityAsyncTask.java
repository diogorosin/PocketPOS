package br.com.pocketpos.core.task;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import br.com.pocketpos.data.exception.CannotInitializeDatabaseException;
import br.com.pocketpos.data.exception.InternalException;
import br.com.pocketpos.data.room.CatalogItemModel;
import br.com.pocketpos.data.room.CatalogItemVO;
import br.com.pocketpos.data.util.DB;
import br.com.pocketpos.data.util.Messaging;

public final class IncrementCatalogItemQuantityAsyncTask<A extends Activity & IncrementCatalogItemQuantityAsyncTask.Listener> extends AsyncTask<CatalogItemModel, Void, Object> {


    private WeakReference<A> activity;


    public IncrementCatalogItemQuantityAsyncTask(A activity) {

        this.activity = new WeakReference<>(activity);

    }


    protected Object doInBackground(CatalogItemModel... parameters) {

        CatalogItemModel catalogItemModel = parameters[0];

        DB database = null;

        if (activity.get() != null)

            database = DB.getInstance(activity.get().getBaseContext());

        if (database==null)

            return new CannotInitializeDatabaseException();

        try {

            database.beginTransaction();

            CatalogItemVO catalogItemVO = database.
                    catalogItemDAO().
                    retrieve(catalogItemModel.getCatalog(), catalogItemModel.getItem());

            catalogItemVO.setQuantity(catalogItemVO.getQuantity() + 1);

            catalogItemVO.setTotal(catalogItemVO.getQuantity() * catalogItemVO.getPrice());

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

                listener.onIncrementCatalogItemQuantityFailure((Messaging) callResult);

    }


    public interface Listener {

        void onIncrementCatalogItemQuantityFailure(Messaging messaging);

    }


}