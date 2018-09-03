package br.com.pocketpos.core.task;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.pocketpos.data.exception.HttpRequestException;
import br.com.pocketpos.data.jersey.DeviceBean;
import br.com.pocketpos.data.jersey.ExceptionBean;
import br.com.pocketpos.data.util.Messaging;
import br.com.pocketpos.data.util.RequestBuilder;

public final class RetrieveDeviceBySerialNumberAsyncTask<
        A extends Activity & RetrieveDeviceBySerialNumberAsyncTask.Listener,
        B extends String,
        C extends Void,
        D> extends AsyncTask<B, C, D> {


    private WeakReference<A> activity;


    public RetrieveDeviceBySerialNumberAsyncTask(A activity) {

        this.activity = new WeakReference<>(activity);

    }


    protected void onPreExecute(){

        A listener = this.activity.get();

        if (listener != null)

            listener.onRetrieveDeviceBySerialNumberPreExecute();

    }


    protected Object doInBackground(String... parameters) {

        String serialNumber = parameters[0];

        try {

            Response response = RequestBuilder.
                    build("device", "serialNumber", serialNumber).
                    request(MediaType.APPLICATION_JSON).
                    get();

            switch (response.getStatus()) {

                case HttpURLConnection.HTTP_OK:

                    return response.readEntity(DeviceBean.class);

                default:

                    return response.readEntity(ExceptionBean.class);

            }

        } catch (Exception e){

            return new HttpRequestException();

        }

    }


    protected void onPostExecute(Object callResult) {

        A listener = this.activity.get();

        if (listener != null) {

            if (callResult != null) {

                if (callResult instanceof DeviceBean){

                    listener.onRetrieveDeviceBySerialNumberSuccess((DeviceBean) callResult);

                } else {

                    if (callResult instanceof Messaging){

                        listener.onRetrieveDeviceBySerialNumberFailure((Messaging) callResult);

                    }

                }

            }

        }

    }


    public interface Listener {

        void onRetrieveDeviceBySerialNumberPreExecute();

        void onRetrieveDeviceBySerialNumberSuccess(DeviceBean downloadBean);

        void onRetrieveDeviceBySerialNumberFailure(Messaging messaging);

    }


}