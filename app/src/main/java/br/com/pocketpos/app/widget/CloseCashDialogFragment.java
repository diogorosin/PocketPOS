package br.com.pocketpos.app.widget;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.pocketpos.R;
import br.com.pocketpos.app.repository.CashViewModel;
import br.com.pocketpos.core.util.StringUtils;
import br.com.pocketpos.data.room.CashModel;

public class CloseCashDialogFragment extends DialogFragment
        implements DialogInterface.OnClickListener{


    private CloseCashDialogFragment.Listener listener;

    private CashSummaryRecyclerViewAdapter recyclerViewAdapter;

    private TextView valueTextView;

    private Double value = 0.0;


    public void onAttach(Context context) {

        super.onAttach(context);

        try {

            listener = (CloseCashDialogFragment.Listener) context;

        } catch (ClassCastException e) {

            throw new ClassCastException(context.toString()
                    + " must implement CloseCashDialogFragment.Listener");

        }

    }


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();

        @SuppressLint("InflateParams")
        final View rootView = inflater.inflate(R.layout.activity_cash_close_dialog, null);

        builder.setTitle(R.string.dlg_title_cash_close);

        builder.setView(rootView)
                .setPositiveButton(android.R.string.ok,  this)
                .setNegativeButton(android.R.string.cancel, this);

        return builder.create();

    }


    public void onClick(DialogInterface dialog, int which) {

        switch (which){

            case DialogInterface.BUTTON_POSITIVE:

                listener.onCloseCash(value);

                dialog.dismiss();

                break;

        }

    }


    public void onResume() {

        super.onResume();

        valueTextView = getDialog().findViewById(R.id.activity_cash_summary_value_textview);

        RecyclerView recyclerView = getDialog().findViewById(R.id.activity_cash_summary_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(Objects.requireNonNull(getActivity()).getBaseContext()));

        recyclerViewAdapter = new CashSummaryRecyclerViewAdapter(new ArrayList<CashModel>());

        recyclerView.setAdapter(recyclerViewAdapter);

        CashViewModel cashViewModel = ViewModelProviders.of(getActivity()).get(CashViewModel.class);

        cashViewModel.cashSummary().observe(getActivity(), new Observer<List<CashModel>>() {

            public void onChanged(@Nullable List<CashModel> cashModelList) {

                recyclerViewAdapter.setCashSummary(cashModelList);

            }

        });

        cashViewModel.value().observe(getActivity(), new Observer<Double>() {

            public void onChanged(final Double value) {

                valueTextView.setText(StringUtils.formatCurrencyWithSymbol(value));

                CloseCashDialogFragment.this.value = value;

            }

        });

    }


    public interface Listener {

        void onCloseCash(Double value);
        
    }


}