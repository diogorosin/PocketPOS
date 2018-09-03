package br.com.pocketpos.app.repository;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import br.com.pocketpos.data.room.PaymentDAO;
import br.com.pocketpos.data.room.PaymentModel;
import br.com.pocketpos.data.util.DB;

public class PaymentViewModel extends AndroidViewModel {


    private LiveData<List<PaymentModel>> payments;


    public PaymentViewModel(@NonNull Application application) {

        super(application);

    }


    public LiveData<List<PaymentModel>> getPayments() {

        if (payments ==null) {

            PaymentDAO paymentDAO = DB.getInstance(
                    getApplication()).
                    paymentDAO();

            payments = paymentDAO.list();

        }

        return payments;

    }


}