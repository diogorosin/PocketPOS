package br.com.pocketpos.core.task;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.pocketpos.data.exception.HttpRequestException;
import br.com.pocketpos.data.jersey.AccountBean;
import br.com.pocketpos.data.jersey.DatasetBean;
import br.com.pocketpos.data.jersey.ExceptionBean;
import br.com.pocketpos.data.util.Messaging;
import br.com.pocketpos.data.util.RequestBuilder;

public final class CreateAccountAsyncTask<
        A extends Activity & CreateAccountAsyncTask.Listener,
        B extends AccountBean,
        C extends Integer,
        D> extends AsyncTask<B, C, D> {


    private WeakReference<A> activity;


    public CreateAccountAsyncTask(A activity) {

        this.activity = new WeakReference<>(activity);

    }


    protected void onPreExecute(){

        A listener = this.activity.get();

        if (listener != null)

            listener.onCreateAccountPreExecute();

    }


    protected Object doInBackground(AccountBean... parameters) {

        AccountBean accountBean = parameters[0];

        try {

            Response response = RequestBuilder.
                    build("account").
                    request(MediaType.APPLICATION_JSON).
                    post(Entity.entity(accountBean, MediaType.APPLICATION_JSON));

            switch (response.getStatus()) {

                case HttpURLConnection.HTTP_OK:

                    return response.readEntity(DatasetBean.class);

                default:

                    return response.readEntity(ExceptionBean.class);

            }

        } catch (Exception e){

            e.printStackTrace();

            return new HttpRequestException();

        }

    }


    protected void onPostExecute(Object callResult) {

        A listener = this.activity.get();

        if (listener != null) {

            if (callResult != null) {

                if (callResult instanceof DatasetBean){

                    listener.onCreateAccountSuccess((DatasetBean) callResult);

                } else {

                    if (callResult instanceof Messaging){

                        listener.onCreateAccountFailure((Messaging) callResult);

                    }

                }

            }

        }

    }


    public interface Listener {

        void onCreateAccountPreExecute();

        void onCreateAccountSuccess(DatasetBean datasetBean);

        void onCreateAccountFailure(Messaging messaging);

    }


}