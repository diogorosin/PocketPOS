package br.com.pocketpos.app.repository;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import br.com.pocketpos.data.room.ReceiptMethodDAO;
import br.com.pocketpos.data.room.ReceiptMethodModel;
import br.com.pocketpos.data.util.DB;

public class ReceiptMethodViewModel extends AndroidViewModel {

    private LiveData<List<ReceiptMethodModel>> receiptMethods;

    public ReceiptMethodViewModel(@NonNull Application application) {

        super(application);

    }

    public LiveData<List<ReceiptMethodModel>> getReceiptMethods() {

        if (receiptMethods ==null) {

            ReceiptMethodDAO receiptMethodDAO = DB.getInstance(
                    getApplication()).
                    receiptMethodDAO();

            receiptMethods = receiptMethodDAO.list();

        }

        return receiptMethods;

    }

}