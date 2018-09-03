package br.com.pocketpos.app.widget;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import br.com.pocketpos.data.room.CatalogModel;

public class CatalogPagerAdapter extends FragmentPagerAdapter {

    private List<CatalogModel> catalogs = new ArrayList<>();

    public CatalogPagerAdapter(FragmentManager fm) {

        super(fm);

    }

    public CatalogItemFragment getItem(int position) {

        return CatalogItemFragment.newInstance(position);

    }

    public int getCount() {

        return catalogs.size();

    }

    public CharSequence getPageTitle(int position) {

        return catalogs.get(position).getDenomination();

    }

    public void setCatalogs(List<CatalogModel> catalogs){

        this.catalogs = catalogs;

        notifyDataSetChanged();

    }

}