package br.com.pocketpos.app.repository;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import br.com.pocketpos.data.room.CatalogItemDAO;
import br.com.pocketpos.data.room.ReceiptDAO;
import br.com.pocketpos.data.room.ReceiptModel;
import br.com.pocketpos.data.util.DB;

public class ReceiptViewModel extends AndroidViewModel {

    private LiveData<List<ReceiptModel>> receipts;

    private LiveData<Double> total;

    private LiveData<Double> received;

    private LiveData<Double> toReceive;

    public ReceiptViewModel(@NonNull Application application) {

        super(application);

    }

    public LiveData<List<ReceiptModel>> getReceipts() {

        if (receipts ==null) {

            ReceiptDAO receiptDAO = DB.getInstance(
                    getApplication()).
                    receiptDAO();

            receipts = receiptDAO.list();

        }

        return receipts;

    }

    public LiveData<Double> getTotal(){

        if (total ==null) {

            CatalogItemDAO catalogItemDAO = DB.getInstance(
                    getApplication()).
                    catalogItemDAO();

            total = catalogItemDAO.total();

        }

        return total;

    }

    public LiveData<Double> getReceived(){

        if (received ==null) {

            ReceiptDAO receiptDAO = DB.getInstance(
                    getApplication()).
                    receiptDAO();

            received = receiptDAO.received();

        }

        return received;

    }

    public LiveData<Double> getToReceive(){

        if (toReceive ==null) {

            ReceiptDAO receiptDAO = DB.getInstance(
                    getApplication()).
                    receiptDAO();

            toReceive = receiptDAO.toReceive();

        }

        return toReceive;

    }

}