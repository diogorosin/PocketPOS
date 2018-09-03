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
import br.com.pocketpos.app.repository.PaymentViewModel;
import br.com.pocketpos.data.room.PaymentModel;


public class CatalogCartPaymentsFragment extends Fragment {


    private PaymentViewModel paymentFormViewModel;

    private CatalogCartPaymentsFormRecyclerViewAdapter paymentFormRecyclerViewAdapter;

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


    public static CatalogCartPaymentsFragment newInstance() {

        CatalogCartPaymentsFragment fragment = new CatalogCartPaymentsFragment();

        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_catalog_cart_payments_view, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.activity_catalog_cart_payments_form_recyclerview);

        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        recyclerView.addItemDecoration(new GridLayoutSpaceItemDecoration(10));

        paymentFormRecyclerViewAdapter = new CatalogCartPaymentsFormRecyclerViewAdapter(
                new ArrayList<PaymentModel>(),
                moneyOnClickListener,
                creditCardOnClickListener,
                debitCardOnClickListener,
                checkOnClickListener);

        paymentFormRecyclerViewAdapter.setHasStableIds(true);

        recyclerView.setAdapter(paymentFormRecyclerViewAdapter);

        paymentFormViewModel = ViewModelProviders.of(this).get(PaymentViewModel.class);

        paymentFormViewModel.getPayments().observe(CatalogCartPaymentsFragment.this, new Observer<List<PaymentModel>>() {

            public void onChanged(@Nullable List<PaymentModel> payments) {

                paymentFormRecyclerViewAdapter.setPayments(payments);

            }

        });

        return view;

    }

}