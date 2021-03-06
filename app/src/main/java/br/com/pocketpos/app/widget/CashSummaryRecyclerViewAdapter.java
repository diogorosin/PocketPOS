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
import br.com.pocketpos.data.room.CashModel;

public class CashSummaryRecyclerViewAdapter extends RecyclerView.Adapter<CashSummaryRecyclerViewAdapter.CashSummaryViewHolder>{


    private List<CashModel> cashModelList;


    public CashSummaryRecyclerViewAdapter(List<CashModel> cashModelList) {

        this.cashModelList = cashModelList;

    }


    @NonNull
    public CashSummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cash_summary_row, parent, false);

        return new CashSummaryViewHolder(view);

    }


    public void onBindViewHolder(@NonNull final CashSummaryViewHolder holder, int position) {

        holder.cashModel = cashModelList.get(position);

        Integer operation;

        switch (cashModelList.get(position).getOperation()){

            case "ABE": operation = R.string.opening;
                break;

            case "FEC": operation = R.string.closure;
                break;

            case "SAN": operation = R.string.removals;
                break;

            case "COM": operation = R.string.supplies;
                break;

            case "VEN": operation = R.string.sales;
                break;

            default: operation = R.string.undefined;

        }

        String type = cashModelList.get(position).getType();

        Double value = cashModelList.get(position).getValue();

        holder.operation.setText(operation);

        holder.value.setText(StringUtils.formatCurrencyWithSymbol(type.equals("S") ? value * -1 : value));

    }


    public int getItemCount() {

        return cashModelList.size();

    }


    public void setCashSummary(List<CashModel> cashModelList){

        this.cashModelList = cashModelList;

        notifyDataSetChanged();

    }


    public List<CashModel> getCashSummary(){

        return this.cashModelList;

    }


    public class CashSummaryViewHolder extends RecyclerView.ViewHolder {

        CashModel cashModel;

        public TextView operation;

        public TextView value;

        CashSummaryViewHolder(View view) {

            super(view);

            operation = view.findViewById(R.id.activity_cash_summary_row_operation_textview);

            value = view.findViewById(R.id.activity_cash_summary_row_value_textview);

        }

    }


}