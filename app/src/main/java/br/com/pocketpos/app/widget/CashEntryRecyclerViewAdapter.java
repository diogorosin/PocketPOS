package br.com.pocketpos.app.widget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import br.com.pocketpos.R;
import br.com.pocketpos.core.util.StringUtils;
import br.com.pocketpos.data.room.CashModel;

public class CashEntryRecyclerViewAdapter extends RecyclerView.Adapter<CashEntryRecyclerViewAdapter.CashEntryViewHolder>{


    private static final int UNSELECTED = -1;

    private int selectedItem = UNSELECTED;

    private RecyclerView recyclerView;

    private List<CashModel> cashModelList;


    public CashEntryRecyclerViewAdapter(RecyclerView recyclerView, List<CashModel> cashModelList) {

        this.recyclerView = recyclerView;

        this.cashModelList = cashModelList;

    }


    @NonNull
    public CashEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cash_entry_row, parent, false);

        return new CashEntryViewHolder(view);

    }


    public void onBindViewHolder(@NonNull CashEntryViewHolder holder, int position) {

        holder.cashModel = cashModelList.get(position);

        Integer operation;

        switch (cashModelList.get(position).getOperation()){

            case "ABE": operation = R.string.opening;
                break;

            case "FEC": operation = R.string.closure;
                break;

            case "SAN": operation = R.string.removal;
                break;

            case "COM": operation = R.string.supply;
                break;

            case "VEN": operation = R.string.sale;
                break;

            default: operation = R.string.undefined;

        }

        Integer payment;

        switch (cashModelList.get(position).getMethod()){

            case "DIN": payment = R.string.money;
                break;

            case "CAR": payment = R.string.card;
                break;

            default: payment = R.string.undefined;

        }

        Integer type;

        switch (cashModelList.get(position).getType()){

            case "E": type = R.string.input;
                break;

            case "S": type = R.string.output;
                break;

            default: type = R.string.undefined;

        }

        Double total = cashModelList.get(position).getTotal();

        holder.operation.setText(operation);

        holder.payment.setText(payment);

        holder.dateTime.setText(StringUtils.formatShortDateTime(cashModelList.get(position).getDateTime()));

        holder.value.setText(StringUtils.formatCurrencyWithSymbol(cashModelList.get(position).getType().equals("S") ? total * -1 : total));

        holder.type.setText(type);

        holder.userName.setText(cashModelList.get(position).getUser().getName());

        holder.note.setText(cashModelList.get(position).getNote());

        boolean isSelected = position == selectedItem;

        holder.rowLayout.setSelected(isSelected);

        holder.expandableLayout.setExpanded(isSelected, false);

    }


    public int getItemCount() {

        return cashModelList.size();

    }


    public void setCashEntries(List<CashModel> cashModelList){

        this.cashModelList = cashModelList;

        notifyDataSetChanged();

    }


    public class CashEntryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener{


        CashModel cashModel;

        LinearLayout rowLayout;

        ExpandableLayout expandableLayout;


        public TextView operation;

        public TextView payment;

        public TextView dateTime;

        public TextView value;

        public TextView type;

        public TextView userName;

        public TextView note;


        CashEntryViewHolder(View view) {

            super(view);

            operation = view.findViewById(R.id.activity_cash_entry_row_operation_textview);

            payment = view.findViewById(R.id.activity_cash_entry_row_payment_textview);

            dateTime = view.findViewById(R.id.activity_cash_entry_row_datetime_textview);

            value = view.findViewById(R.id.activity_cash_entry_row_value_textview);

            type = view.findViewById(R.id.activity_cash_entry_row_type_textview);

            userName = view.findViewById(R.id.activity_cash_entry_row_username_textview);

            note = view.findViewById(R.id.activity_cash_entry_row_note_textview);

            rowLayout = view.findViewById(R.id.activity_cash_entry_row);

            rowLayout.setOnClickListener(this);

            expandableLayout = itemView.findViewById(R.id.activity_cash_entry_row_detail_layout);

            expandableLayout.setInterpolator(new OvershootInterpolator());

            expandableLayout.setOnExpansionUpdateListener(this);

        }


        public void onClick(View v) {

            CashEntryViewHolder holder = (CashEntryViewHolder) recyclerView.findViewHolderForAdapterPosition(selectedItem);

            if (holder != null) {

                holder.rowLayout.setSelected(false);

                holder.expandableLayout.collapse();

            }

            int position = getAdapterPosition();

            if (position == selectedItem) {

                selectedItem = UNSELECTED;

            } else {

                rowLayout.setSelected(true);

                expandableLayout.expand();

                selectedItem = position;

            }

        }

        public void onExpansionUpdate(float expansionFraction, int state) {

            if (state == ExpandableLayout.State.EXPANDING)

                recyclerView.smoothScrollToPosition(getAdapterPosition());

        }

    }


}