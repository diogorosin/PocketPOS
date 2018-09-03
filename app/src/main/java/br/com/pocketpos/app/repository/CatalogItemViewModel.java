package br.com.pocketpos.app.repository;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import br.com.pocketpos.data.room.CatalogItemDAO;
import br.com.pocketpos.data.room.CatalogItemModel;
import br.com.pocketpos.data.util.DB;

public class CatalogItemViewModel extends AndroidViewModel {

    private LiveData<List<CatalogItemModel>> catalogItems;

    private LiveData<List<CatalogItemModel>> catalogItemsOfCart;

    private LiveData<Double> subtotalOfCart;

    private LiveData<Double> totalOfCart;

    public CatalogItemViewModel(Application application) {

        super(application);

    }

    public LiveData<List<CatalogItemModel>> getCatalogItemsByPosition(int position) {

        if (catalogItems ==null){

            CatalogItemDAO catalogItemDAO = DB.getInstance(
                    getApplication()).
                    catalogItemDAO();

            catalogItems = catalogItemDAO.listByPosition(position);

        }

        return catalogItems;

    }

    public LiveData<List<CatalogItemModel>> getCatalogItemsOfCart() {

        if (catalogItemsOfCart ==null){

            CatalogItemDAO catalogItemDAO = DB.getInstance(
                    getApplication()).
                    catalogItemDAO();

            catalogItemsOfCart = catalogItemDAO.list();

        }

        return catalogItemsOfCart;

    }

    public LiveData<Double> getSubtotalOfCart() {

        if (subtotalOfCart ==null){

            CatalogItemDAO catalogItemDAO = DB.getInstance(
                    getApplication()).
                    catalogItemDAO();

            subtotalOfCart = catalogItemDAO.subtotal();

        }

        return subtotalOfCart;

    }

    public LiveData<Double> getTotalOfCart() {

        if (totalOfCart ==null){

            CatalogItemDAO catalogItemDAO = DB.getInstance(
                    getApplication()).
                    catalogItemDAO();

            totalOfCart = catalogItemDAO.total();

        }

        return totalOfCart;

    }

}