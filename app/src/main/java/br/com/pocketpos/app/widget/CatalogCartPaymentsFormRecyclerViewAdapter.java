package br.com.pocketpos.app.widget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

import br.com.pocketpos.R;
import br.com.pocketpos.data.room.PaymentModel;

public class CatalogCartPaymentsFormRecyclerViewAdapter
        extends RecyclerView.Adapter<CatalogCartPaymentsFormRecyclerViewAdapter.CatalogCartPaymentsFormViewHolder>{


    public static final int MONEY_PAYMENT = 1;

    public static final int CREDIT_CARD_PAYMENT = 2;

    public static final int DEBIT_CARD_PAYMENT = 3;

    public static final int CHECK_PAYMENT = 4;


    private View.OnClickListener moneyOnClickListener;

    private View.OnClickListener creditCardOnClickListener;

    private View.OnClickListener debitCardOnClickListener;

    private View.OnClickListener checkOnClickListener;


    private List<PaymentModel> payments;


    public CatalogCartPaymentsFormRecyclerViewAdapter(
            List<PaymentModel> payments,
            View.OnClickListener moneyOnClickListener,
            View.OnClickListener creditCardOnClickListener,
            View.OnClickListener debitCardOnClickListener,
            View.OnClickListener checkOnClickListener) {

        this.payments = payments;

        this.moneyOnClickListener = moneyOnClickListener;

        this.creditCardOnClickListener = creditCardOnClickListener;

        this.debitCardOnClickListener = debitCardOnClickListener;

        this.checkOnClickListener = checkOnClickListener;

    }


    public CatalogCartPaymentsFormViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layout = 0;

        switch (viewType){

            case MONEY_PAYMENT: layout = R.layout.activity_catalog_cart_payments_form_money;
                break;

            case CREDIT_CARD_PAYMENT: layout = R.layout.activity_catalog_cart_payments_form_credit_card;
                break;

            case DEBIT_CARD_PAYMENT: layout = R.layout.activity_catalog_cart_payments_form_debit_card;
                break;

            case CHECK_PAYMENT: layout = R.layout.activity_catalog_cart_payments_form_check;
                break;

        }

        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        return new CatalogCartPaymentsFormViewHolder(view);

    }


    public int getItemViewType(int position) {

        switch (payments.get(position).getIdentifier()){

            case "DIN": return MONEY_PAYMENT;

            case "CDE": return DEBIT_CARD_PAYMENT;

            case "CCR": return CREDIT_CARD_PAYMENT;

            case "CHE": return CHECK_PAYMENT;

            default:

                return 0;

        }

    }


    public void onBindViewHolder(final CatalogCartPaymentsFormViewHolder holder, int position) {

        holder.paymentModel = payments.get(position);

        switch (holder.getItemViewType()){

            case MONEY_PAYMENT: holder.button.setOnClickListener(moneyOnClickListener);
                break;

            case CREDIT_CARD_PAYMENT: holder.button.setOnClickListener(creditCardOnClickListener);
                break;

            case DEBIT_CARD_PAYMENT: holder.button.setOnClickListener(debitCardOnClickListener);
                break;

            case CHECK_PAYMENT: holder.button.setOnClickListener(checkOnClickListener);
                break;

        }

    }


    public int getItemCount() {

        return payments.size();

    }


    public long getItemId(int position){

        String identifier = payments.get(position).getIdentifier();

        return UUID.nameUUIDFromBytes(identifier.getBytes()).getMostSignificantBits();

    }


    public void setPayments(List<PaymentModel> payments){

        this.payments = payments;

        notifyDataSetChanged();

    }


    public class CatalogCartPaymentsFormViewHolder extends RecyclerView.ViewHolder {

        public PaymentModel paymentModel;

        public Button button;

        public CatalogCartPaymentsFormViewHolder(View view) {

            super(view);

            button = view.findViewById(R.id.activity_catalog_cart_payments_form_button);

        }

    }

}