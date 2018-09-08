package br.com.pocketpos.app.widget;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.pocketpos.R;
import br.com.pocketpos.app.repository.ReceiptMethodViewModel;
import br.com.pocketpos.data.room.ReceiptMethodModel;


public class CatalogCartReceiptFragment extends Fragment {

    private ReceiptMethodViewModel receiptMethodViewModel;

    private CatalogCartReceiptMethodRecyclerViewAdapter receiptMethodRecyclerViewAdapter;

    private View.OnClickListener moneyOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {

        }
    };

    private View.OnClickListener creditCardOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {

        }
    };

    private View.OnClickListener debitCardOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {

        }
    };

    private View.OnClickListener checkOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {

        }
    };

    public static CatalogCartReceiptFragment newInstance() {

        CatalogCartReceiptFragment fragment = new CatalogCartReceiptFragment();

        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_catalog_cart_receipt_view, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.activity_catalog_cart_receipt_method_recyclerview);

        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        recyclerView.addItemDecoration(new GridLayoutSpaceItemDecoration(10));

        receiptMethodRecyclerViewAdapter = new CatalogCartReceiptMethodRecyclerViewAdapter(
                new ArrayList<ReceiptMethodModel>(),
                moneyOnClickListener,
                creditCardOnClickListener,
                debitCardOnClickListener,
                checkOnClickListener);

        receiptMethodRecyclerViewAdapter.setHasStableIds(true);

        recyclerView.setAdapter(receiptMethodRecyclerViewAdapter);

        receiptMethodViewModel = ViewModelProviders.of(this).get(ReceiptMethodViewModel.class);

        receiptMethodViewModel.getReceiptMethods().observe(CatalogCartReceiptFragment.this, new Observer<List<ReceiptMethodModel>>() {

            public void onChanged(@Nullable List<ReceiptMethodModel> payments) {

                receiptMethodRecyclerViewAdapter.setReceiptMethods(payments);

            }

        });

        return view;

    }

}