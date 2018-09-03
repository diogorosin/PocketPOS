package br.com.pocketpos.app.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.pocketpos.data.exception.HttpRequestException;
import br.com.pocketpos.data.jersey.CompanyBean;
import br.com.pocketpos.data.jersey.ExceptionBean;
import br.com.pocketpos.data.util.Messaging;
import br.com.pocketpos.data.util.RequestBuilder;

public final class BindCompanyListAsyncTask<
        A extends Activity & BindCompanyListAsyncTask.Listener,
        B extends RadioButton,
        C extends RadioButton,
        D extends Spinner,
        E extends ArrayAdapter<CompanyBean>,
        F extends Integer>
        extends AsyncTask<Void, Void, Object> {


    private WeakReference<A> activity;

    private WeakReference<B> newCompanyRadioButton;

    private WeakReference<C> useCompanyRadioButton;

    private WeakReference<D> useCompanySpinner;

    private WeakReference<E> useCompanyArrayAdapter;

    private WeakReference<F> selected;


    public BindCompanyListAsyncTask(A activity, B newCompanyRadioButton, C useCompanyRadioButton, D useCompanySpinner, E useCompanyArrayAdapter, F selected) {

        this.activity = new WeakReference<>(activity);

        this.newCompanyRadioButton = new WeakReference<>(newCompanyRadioButton);

        this.useCompanyRadioButton = new WeakReference<>(useCompanyRadioButton);

        this.useCompanySpinner = new WeakReference<>(useCompanySpinner);

        this.useCompanyArrayAdapter = new WeakReference<>(useCompanyArrayAdapter);

        this.selected = new WeakReference<>(selected);

    }


    protected void onPreExecute(){

        A listener = this.activity.get();

        if (listener != null)

            listener.onBindCompanyListPreExecute();

    }


    protected Object doInBackground(Void... parameters) {

        try {

            Response response = RequestBuilder.
                    build("company").
                    request(MediaType.APPLICATION_JSON).
                    get();

            switch (response.getStatus()) {

                case HttpURLConnection.HTTP_PARTIAL:
                case HttpURLConnection.HTTP_OK:

                    return response.readEntity(new GenericType<List<CompanyBean>>(){});

                default:

                    return response.readEntity(ExceptionBean.class);

            }

        } catch (Exception e){

            return new HttpRequestException();

        }

    }


    protected void onPostExecute(Object callResult) {

        if (activity.get() == null ||
                newCompanyRadioButton.get() == null ||
                useCompanyRadioButton.get() == null ||
                useCompanySpinner.get() == null ||
                useCompanyArrayAdapter.get() == null)

            return;

        if (callResult instanceof List<?>) {

            List<CompanyBean> companyBeanList = (List<CompanyBean>) callResult;

            useCompanyArrayAdapter.get().clear();

            for (CompanyBean companyBean : companyBeanList)

                useCompanyArrayAdapter.get().add(companyBean);

            if (selected.get() != null) {

                CompanyBean companyBean = new CompanyBean();

                companyBean.setIdentifier(selected.get());

                int position = useCompanyArrayAdapter.get().getPosition(companyBean);

                useCompanySpinner.get().setSelection(position, false);

                useCompanySpinner.get().setEnabled(true);

                useCompanyRadioButton.get().setChecked(true);

            } else {

                newCompanyRadioButton.get().setChecked(true);

            }

            activity.get().onBindCompanyListSuccess();

        } else {

            if (callResult instanceof Messaging) {

                activity.get().onBindCompanyListFailure((Messaging) callResult);

            }

        }

    }


    public interface Listener {

        void onBindCompanyListPreExecute();

        void onBindCompanyListSuccess();

        void onBindCompanyListFailure(Messaging messaging);

    }


}