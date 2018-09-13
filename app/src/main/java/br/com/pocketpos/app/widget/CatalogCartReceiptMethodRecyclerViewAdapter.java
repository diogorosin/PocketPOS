package br.com.pocketpos.app.widget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

import br.com.pocketpos.R;
import br.com.pocketpos.data.room.ReceiptMethodModel;

public class CatalogCartReceiptMethodRecyclerViewAdapter
        extends RecyclerView.Adapter<CatalogCartReceiptMethodRecyclerViewAdapter.CatalogCartReceiptMethodViewHolder>{


    public static final int MONEY_RECEIPT = 1;

    public static final int CREDIT_CARD_RECEIPT = 2;

    public static final int DEBIT_CARD_RECEIPT = 3;

    public static final int CHECK_RECEIPT = 4;


    private View.OnClickListener moneyOnClickListener;

    private View.OnClickListener creditCardOnClickListener;

    private View.OnClickListener debitCardOnClickListener;

    private View.OnClickListener checkOnClickListener;


    private List<ReceiptMethodModel> receiptMethods;


    public CatalogCartReceiptMethodRecyclerViewAdapter(
            List<ReceiptMethodModel> receiptMethods,
            View.OnClickListener moneyOnClickListener,
            View.OnClickListener creditCardOnClickListener,
            View.OnClickListener debitCardOnClickListener,
            View.OnClickListener checkOnClickListener) {

        this.receiptMethods = receiptMethods;

        this.moneyOnClickListener = moneyOnClickListener;

        this.creditCardOnClickListener = creditCardOnClickListener;

        this.debitCardOnClickListener = debitCardOnClickListener;

        this.checkOnClickListener = checkOnClickListener;

    }


    public CatalogCartReceiptMethodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layout = 0;

        switch (viewType){

            case MONEY_RECEIPT: layout = R.layout.activity_catalog_cart_receipt_method_money;
                break;

            case CREDIT_CARD_RECEIPT: layout = R.layout.activity_catalog_cart_receipt_method_credit_card;
                break;

            case DEBIT_CARD_RECEIPT: layout = R.layout.activity_catalog_cart_receipt_method_debit_card;
                break;

            case CHECK_RECEIPT: layout = R.layout.activity_catalog_cart_receipt_method_check;
                break;

        }

        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        return new CatalogCartReceiptMethodViewHolder(view);

    }


    public int getItemViewType(int position) {

        switch (receiptMethods.get(position).getIdentifier()){

            case "DIN": return MONEY_RECEIPT;

            case "CDE": return DEBIT_CARD_RECEIPT;

            case "CCR": return CREDIT_CARD_RECEIPT;

            case "CHE": return CHECK_RECEIPT;

            default:

                return 0;

        }

    }


    public void onBindViewHolder(final CatalogCartReceiptMethodViewHolder holder, int position) {

        holder.receiptMethodModel = receiptMethods.get(position);

        switch (holder.getItemViewType()){

            case MONEY_RECEIPT: holder.button.setOnClickListener(moneyOnClickListener);
                break;

            case CREDIT_CARD_RECEIPT: holder.button.setOnClickListener(creditCardOnClickListener);
                break;

            case DEBIT_CARD_RECEIPT: holder.button.setOnClickListener(debitCardOnClickListener);
                break;

            case CHECK_RECEIPT: holder.button.setOnClickListener(checkOnClickListener);
                break;

        }

    }


    public int getItemCount() {

        return receiptMethods.size();

    }


    public long getItemId(int position){

        String identifier = receiptMethods.get(position).getIdentifier();

        return UUID.nameUUIDFromBytes(identifier.getBytes()).getMostSignificantBits();

    }


    public void setReceiptMethods(List<ReceiptMethodModel> receiptMethods){

        this.receiptMethods = receiptMethods;

        notifyDataSetChanged();

    }


    public class CatalogCartReceiptMethodViewHolder extends RecyclerView.ViewHolder {

        public ReceiptMethodModel receiptMethodModel;

        public Button button;

        public CatalogCartReceiptMethodViewHolder(View view) {

            super(view);

            button = view.findViewById(R.id.activity_catalog_cart_receipt_method_button);

        }

    }

}