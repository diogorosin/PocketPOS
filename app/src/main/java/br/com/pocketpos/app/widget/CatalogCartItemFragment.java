package br.com.pocketpos.app.widget;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.pocketpos.R;
import br.com.pocketpos.app.repository.CatalogItemViewModel;
import br.com.pocketpos.core.util.StringUtils;
import br.com.pocketpos.data.room.CatalogItemModel;


public class CatalogCartItemFragment extends Fragment {


    private CatalogItemViewModel viewModel;

    private CatalogCartItemRecyclerViewAdapter recyclerViewAdapter;

    private TextView subtotalTextView;

    private TextView payTextView;


    public static CatalogCartItemFragment newInstance() {

        CatalogCartItemFragment fragment = new CatalogCartItemFragment();

        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_catalog_cart_item_view, container, false);

        subtotalTextView = view.findViewById(R.id.activity_catalog_cart_subtotal);

        payTextView = view.findViewById(R.id.activity_catalog_cart_total);

        RecyclerView recyclerView = view.findViewById(R.id.activity_catalog_cart_item_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        recyclerViewAdapter = new CatalogCartItemRecyclerViewAdapter(new ArrayList<CatalogItemModel>());

        recyclerViewAdapter.setHasStableIds(true);

        recyclerView.setAdapter(recyclerViewAdapter);

        viewModel = ViewModelProviders.of(this).get(CatalogItemViewModel.class);

        viewModel.getCatalogItemsOfCart().observe(CatalogCartItemFragment.this, new Observer<List<CatalogItemModel>>() {

            public void onChanged(@Nullable List<CatalogItemModel> catalogItems) {

                recyclerViewAdapter.setCatalogItems(catalogItems);

            }

        });

        viewModel.getSubtotalOfCart().observe(CatalogCartItemFragment.this, new Observer<Double>() {

            public void onChanged(@Nullable Double subtotal) {

                subtotalTextView.setText(StringUtils.formatCurrencyWithSymbol(subtotal));

            }

        });

        viewModel.getTotalOfCart().observe(CatalogCartItemFragment.this, new Observer<Double>() {

            public void onChanged(@Nullable Double total) {

                payTextView.setText(StringUtils.formatCurrencyWithSymbol(total));

            }

        });

        return view;

    }

}