package br.com.pocketpos.app.repository;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import br.com.pocketpos.data.room.CashDAO;
import br.com.pocketpos.data.room.CashModel;
import br.com.pocketpos.data.util.DB;

public class CashViewModel extends AndroidViewModel {


    private LiveData<Boolean> isOpen;

    private LiveData<Double> total;

    private LiveData<Double> money;

    private LiveData<Double> card;

    private LiveData<List<CashModel>> cashSummary;

    private LiveData<List<CashModel>> cashEntry;


    public CashViewModel(@NonNull Application application) {

        super(application);

    }


    public LiveData<Boolean> isOpen() {

        if (isOpen==null) {

            CashDAO cashDAO = DB.getInstance(
                    getApplication()).
                    cashDAO();

            isOpen = cashDAO.isOpen();

        }

        return isOpen;

    }


    public LiveData<Double> money() {

        if (money==null) {

            CashDAO cashDAO = DB.getInstance(
                    getApplication()).
                    cashDAO();

            money = cashDAO.money();

        }

        return money;

    }


    public LiveData<Double> card() {

        if (card==null) {

            CashDAO cashDAO = DB.getInstance(
                    getApplication()).
                    cashDAO();

            card = cashDAO.card();

        }

        return card;

    }


    public LiveData<Double> total() {

        if (total==null) {

            CashDAO cashDAO = DB.getInstance(
                    getApplication()).
                    cashDAO();

            total = cashDAO.total();

        }

        return total;

    }


    public LiveData<List<CashModel>> cashSummary() {

        if (cashSummary ==null) {

            CashDAO cashDAO = DB.getInstance(
                    getApplication()).
                    cashDAO();

            cashSummary = cashDAO.cashSummary();

        }

        return cashSummary;

    }


    public LiveData<List<CashModel>> cashEntry() {

        if (cashEntry ==null) {

            CashDAO cashDAO = DB.getInstance(
                    getApplication()).
                    cashDAO();

            cashEntry = cashDAO.cashEntry();

        }

        return cashEntry;

    }


}