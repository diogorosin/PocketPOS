package br.com.pocketpos.app.repository;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

import br.com.pocketpos.data.room.CatalogDAO;
import br.com.pocketpos.data.room.CatalogModel;
import br.com.pocketpos.data.util.DB;

public class CatalogViewModel extends AndroidViewModel {

    private LiveData<List<CatalogModel>> catalogs;

    public CatalogViewModel(Application application) {

        super(application);

    }

    public LiveData<List<CatalogModel>> getCatalogs() {

        if (catalogs ==null){

            CatalogDAO catalogDAO = DB.getInstance(
                    getApplication()).
                    catalogDAO();

            catalogs = catalogDAO.list();

        }

        return catalogs;

    }

}