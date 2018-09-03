package br.com.pocketpos.core.task;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import br.com.pocketpos.data.exception.CannotInitializeDatabaseException;
import br.com.pocketpos.data.exception.InternalException;
import br.com.pocketpos.data.exception.ValidationException;
import br.com.pocketpos.data.room.UserVO;
import br.com.pocketpos.data.util.DB;
import br.com.pocketpos.data.util.Messaging;
import br.com.pocketpos.data.util.StringUtils;

public final class AuthenticateAsyncTask<
        A extends Activity & AuthenticateAsyncTask.Listener,
        B extends String,
        C extends Integer,
        D> extends AsyncTask<B, C, D> {


    private final WeakReference<A> activity;


    public AuthenticateAsyncTask(A activity) {

        this.activity = new WeakReference<>(activity);

    }


    protected void onPreExecute(){

        A listener = this.activity.get();

        if (listener != null)

            listener.onAuthenticatePreExecute();

    }


    protected Object doInBackground(String... parameters) {

        String login = parameters[0];

        String password = parameters[1];

        DB database = null;

        if (activity.get() != null)

            database = DB.getInstance(activity.get().getBaseContext());

        if (database==null)

            return new CannotInitializeDatabaseException();

        try {

            Thread.sleep(1000);

            UserVO userVO = database.userDAO().retrieveByLogin(login);

            if (userVO == null)

                throw new ValidationException("Nenhum usuário vinculado ao login informado.");

            if (!userVO.getActive())

                throw new ValidationException("Usuário não está ativo.");

            String digestedPassword = StringUtils.digestString(password);

            if (!digestedPassword.equals(userVO.getPassword()))

                throw new ValidationException("Senha incorreta.");

            return userVO;

        } catch (InterruptedException e) {

            return new InternalException();

        } catch (ValidationException e) {

            return e;

        }

    }


    protected void onPostExecute(Object callResult) {

        A listener = this.activity.get();

        if (listener != null) {

            if (callResult instanceof UserVO) {

                listener.onAuthenticateSuccess((UserVO) callResult);

            } else {

                if (callResult instanceof Messaging) {

                    listener.onAuthenticateFailure((Messaging) callResult);

                }

            }

        }

    }


    public interface Listener {

        void onAuthenticatePreExecute();

        void onAuthenticateSuccess(UserVO userVO);

        void onAuthenticateFailure(Messaging messaging);

    }


}