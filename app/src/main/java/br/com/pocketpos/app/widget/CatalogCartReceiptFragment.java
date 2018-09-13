package br.com.pocketpos.app.widget;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codetroopers.betterpickers.numberpicker.NumberPickerBuilder;
import com.codetroopers.betterpickers.numberpicker.NumberPickerDialogFragment;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import br.com.pocketpos.R;
import br.com.pocketpos.app.repository.ReceiptMethodViewModel;
import br.com.pocketpos.app.repository.ReceiptViewModel;
import br.com.pocketpos.core.task.UpdateMoneyReceiptAsyncTask;
import br.com.pocketpos.core.util.StringUtils;
import br.com.pocketpos.data.room.ReceiptMethodModel;
import br.com.pocketpos.data.room.ReceiptModel;
import br.com.pocketpos.data.util.Messaging;


public class CatalogCartReceiptFragment extends Fragment implements UpdateMoneyReceiptAsyncTask.Listener{


    private TextView totalTextView;

    private TextView receivedTextView;

    private TextView toReceiveTextView;

    private TextView toReceiveTitleTextView;


    private ReceiptMethodViewModel receiptMethodViewModel;

    private ReceiptViewModel receiptViewModel;

    private CatalogCartReceiptMethodRecyclerViewAdapter receiptMethodRecyclerViewAdapter;

    private CatalogCartReceiptRecyclerViewAdapter receiptRecyclerViewAdapter;

    private View.OnClickListener moneyOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            NumberPickerBuilder npb = new NumberPickerBuilder()
                    .setFragmentManager(CatalogCartReceiptFragment.this.getFragmentManager())
                    .setStyleResId(R.style.BetterPickersDialogFragment)
                    .setPlusMinusVisibility(View.INVISIBLE)
                    .setMaxNumber(BigDecimal.valueOf(99999))
                    .setDecimalVisibility(View.VISIBLE)
                    .setLabelText("R$")
                    .addNumberPickerDialogHandler(new NumberPickerDialogFragment.NumberPickerDialogHandlerV2() {

                        public void onDialogNumberSet(int reference, BigInteger number, double decimal, boolean isNegative, BigDecimal fullNumber) {

                            new UpdateMoneyReceiptAsyncTask<>(getActivity()).execute(fullNumber.doubleValue());

                        }

                    });

            npb.show();

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

        totalTextView = view.findViewById(R.id.activity_catalog_cart_receipt_total);

        receivedTextView = view.findViewById(R.id.activity_catalog_cart_receipt_received);

        toReceiveTextView = view.findViewById(R.id.activity_catalog_cart_receipt_toreceive);

        toReceiveTitleTextView = view.findViewById(R.id.activity_catalog_cart_receipt_toreceive_title);

        RecyclerView receiptMethodRecyclerView = view.findViewById(R.id.activity_catalog_cart_receipt_method_recyclerview);

        receiptMethodRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        receiptMethodRecyclerView.addItemDecoration(new GridLayoutSpaceItemDecoration(10));

        receiptMethodRecyclerViewAdapter = new CatalogCartReceiptMethodRecyclerViewAdapter(
                new ArrayList<ReceiptMethodModel>(),
                moneyOnClickListener,
                creditCardOnClickListener,
                debitCardOnClickListener,
                checkOnClickListener);

        receiptMethodRecyclerViewAdapter.setHasStableIds(true);

        receiptMethodRecyclerView.setAdapter(receiptMethodRecyclerViewAdapter);

        receiptMethodViewModel = ViewModelProviders.of(this).get(ReceiptMethodViewModel.class);

        receiptMethodViewModel.getReceiptMethods().observe(CatalogCartReceiptFragment.this, new Observer<List<ReceiptMethodModel>>() {

            public void onChanged(@Nullable List<ReceiptMethodModel> receiptMethods) {

                receiptMethodRecyclerViewAdapter.setReceiptMethods(receiptMethods);

            }

        });


        RecyclerView receiptRecyclerView = view.findViewById(R.id.activity_catalog_cart_receipt_recyclerview);

        receiptRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        receiptRecyclerViewAdapter = new CatalogCartReceiptRecyclerViewAdapter(
                new ArrayList<ReceiptModel>());

        receiptRecyclerViewAdapter.setHasStableIds(true);

        receiptRecyclerView.setAdapter(receiptRecyclerViewAdapter);

        receiptViewModel = ViewModelProviders.of(this).get(ReceiptViewModel.class);


        receiptViewModel.getReceipts().observe(CatalogCartReceiptFragment.this, new Observer<List<ReceiptModel>>() {

            public void onChanged(@Nullable List<ReceiptModel> receipts) {

                receiptRecyclerViewAdapter.setReceipts(receipts);

            }

        });

        receiptViewModel.getTotal().observe(CatalogCartReceiptFragment.this, new Observer<Double>() {

            public void onChanged(@Nullable Double total) {

                totalTextView.setText(StringUtils.formatCurrencyWithSymbol(total));

            }

        });

        receiptViewModel.getReceived().observe(CatalogCartReceiptFragment.this, new Observer<Double>() {

            public void onChanged(@Nullable Double received) {

                receivedTextView.setText(StringUtils.formatCurrencyWithSymbol(received));

            }

        });

        receiptViewModel.getToReceive().observe(CatalogCartReceiptFragment.this, new Observer<Double>() {

            public void onChanged(@Nullable Double toReceive) {

                if (toReceive >= 0){

                    toReceiveTitleTextView.setText(R.string.to_receive);

                    toReceiveTextView.setText(StringUtils.formatCurrencyWithSymbol(toReceive));

                } else {

                    toReceiveTitleTextView.setText(R.string.change);

                    toReceiveTextView.setText(StringUtils.formatCurrencyWithSymbol(toReceive*-1));

                }

            }

        });

        return view;


    }

    public void onUpdateMoneyReceiptItemFailure(Messaging messaging) {}

}