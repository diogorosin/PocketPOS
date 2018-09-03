package br.com.pocketpos.app.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CatalogCartPagerAdapter extends FragmentPagerAdapter {

    public CatalogCartPagerAdapter(FragmentManager fm) {

        super(fm);

    }

    public Fragment getItem(int position) {

        switch (position){

            case 0:

                return CatalogCartItemsFragment.newInstance();

            case 1:

                return CatalogCartPaymentsFragment.newInstance();

            default:

                return null;

        }

    }

    public int getCount() {

        return 2;

    }

    public CharSequence getPageTitle(int position) {

        switch (position){

            case 0:

                return "Itens";

            case 1:

                return "Pagamentos";

            default:

                return "Indefinido";

        }

    }

}