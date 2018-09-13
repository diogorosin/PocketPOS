package br.com.pocketpos.app.widget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.pocketpos.R;
import br.com.pocketpos.core.util.StringUtils;
import br.com.pocketpos.data.room.ReceiptModel;

public class CatalogCartReceiptRecyclerViewAdapter
        extends RecyclerView.Adapter<CatalogCartReceiptRecyclerViewAdapter.CatalogCartReceiptViewHolder>{


    private List<ReceiptModel> receipts;


    public CatalogCartReceiptRecyclerViewAdapter(List<ReceiptModel> receipts) {

        this.receipts = receipts;

    }


    public CatalogCartReceiptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_catalog_cart_receipt_row, parent, false);

        return new CatalogCartReceiptViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CatalogCartReceiptViewHolder holder, int position) {

        holder.receiptModel = receipts.get(position);

        holder.method.setText(receipts.get(position).getMethod().getDenomination());

        holder.value.setText(StringUtils.formatCurrency(receipts.get(position).getValue()));

    }


    public int getItemCount() {

        return receipts.size();

    }


    public long getItemId(int position){

        return receipts.get(position).getIdentifier();

    }


    public void setReceipts(List<ReceiptModel> receipts){

        this.receipts = receipts;

        notifyDataSetChanged();

    }


    public class CatalogCartReceiptViewHolder extends RecyclerView.ViewHolder {

        public ReceiptModel receiptModel;

        public TextView method;

        public TextView value;

        public CatalogCartReceiptViewHolder(View view) {

            super(view);

            method = view.findViewById(R.id.activity_catalog_cart_receipt_row_method);

            value = view.findViewById(R.id.activity_catalog_cart_receipt_row_value);

        }

    }

}