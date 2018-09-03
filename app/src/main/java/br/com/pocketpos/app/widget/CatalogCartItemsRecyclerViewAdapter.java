package br.com.pocketpos.app.widget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.pocketpos.R;
import br.com.pocketpos.core.util.Hashids;
import br.com.pocketpos.core.util.StringUtils;
import br.com.pocketpos.data.room.CatalogItemModel;

public class CatalogCartItemsRecyclerViewAdapter extends RecyclerView.Adapter<CatalogCartItemsRecyclerViewAdapter.CatalogCartItemViewHolder> {


    private List<CatalogItemModel> catalogItems;


    public CatalogCartItemsRecyclerViewAdapter(List<CatalogItemModel> catalogItems) {

        this.catalogItems = catalogItems;

    }


    public CatalogCartItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_catalog_cart_items_row, parent, false);

        return new CatalogCartItemViewHolder(view);

    }


    public void onBindViewHolder(final CatalogCartItemViewHolder holder, int position) {

        holder.catalogItemModel = catalogItems.get(position);

        holder.title.setText(catalogItems.get(position).getDenomination());

        holder.quantity.setText(StringUtils.formatQuantityWithMinimumFractionDigit(catalogItems.get(position).getQuantity()));

        holder.measureUnit.setText(catalogItems.get(position).getMeasureUnit().getAcronym().toUpperCase());

        holder.price.setText(StringUtils.formatCurrency(catalogItems.get(position).getPrice()));

        holder.total.setText(StringUtils.formatCurrency(catalogItems.get(position).getTotal()));

    }


    public int getItemCount() {

        return catalogItems.size();

    }


    public long getItemId(int position){

        return new Hashids().encode(
                catalogItems.get(position).getCatalog(),
                catalogItems.get(position).getItem());

    }


    public void setCatalogItems(List<CatalogItemModel> catalogItems){

        this.catalogItems = catalogItems;

        notifyDataSetChanged();

    }


    public class CatalogCartItemViewHolder extends RecyclerView.ViewHolder {

        public CatalogItemModel catalogItemModel;

        public TextView title;

        public TextView quantity;

        public TextView measureUnit;

        public TextView price;

        public TextView total;

        public CatalogCartItemViewHolder(View view) {

            super(view);

            title = view.findViewById(R.id.activity_catalog_cart_items_row_title);

            quantity = view.findViewById(R.id.activity_catalog_cart_items_row_quantity);

            measureUnit = view.findViewById(R.id.activity_catalog_cart_items_row_measureunit);

            price = view.findViewById(R.id.activity_catalog_cart_items_row_price);

            total = view.findViewById(R.id.activity_catalog_cart_items_row_total);

        }

    }


}