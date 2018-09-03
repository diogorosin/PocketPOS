package br.com.pocketpos.app.widget;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.pocketpos.R;
import br.com.pocketpos.app.repository.CatalogItemViewModel;
import br.com.pocketpos.data.room.CatalogItemModel;

public class CatalogItemFragment extends Fragment {


    private static final String ARG_COLUMNS = "COLUMNS";

    private static final String ARG_POSITION = "POSITION";


    private CatalogItemViewModel viewModel;

    private CatalogItemRecyclerViewAdapter recyclerViewAdapter;

    private CatalogItemFragmentListener fragmentListener;


    private int columns = 0;

    private int position = 0;


    public CatalogItemFragment() {}


    public static CatalogItemFragment newInstance(int position) {

        CatalogItemFragment fragment = new CatalogItemFragment();

        Bundle args = new Bundle();

        args.putInt(ARG_COLUMNS, 3);

        args.putInt(ARG_POSITION, position);

        fragment.setArguments(args);

        return fragment;

    }


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            columns = getArguments().getInt(ARG_COLUMNS);

            position = getArguments().getInt(ARG_POSITION);

        }

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.activity_catalog_item_view, container, false);

        if (view instanceof RecyclerView) {

            Context context = view.getContext();

            RecyclerView recyclerView = (RecyclerView) view;

            if (columns <= 1) {

                recyclerView.setLayoutManager(new LinearLayoutManager(context));

            } else {

                recyclerView.setLayoutManager(new GridLayoutManager(context, columns));

            }

            recyclerViewAdapter = new CatalogItemRecyclerViewAdapter(new ArrayList<CatalogItemModel>(), fragmentListener);

            recyclerView.setAdapter(recyclerViewAdapter);

            viewModel = ViewModelProviders.of(this).get(CatalogItemViewModel.class);

            viewModel.getCatalogItemsByPosition(position).observe(CatalogItemFragment.this, new Observer<List<CatalogItemModel>>() {

                public void onChanged(@Nullable List<CatalogItemModel> catalogItems) {

                    recyclerViewAdapter.setCatalogItems(catalogItems);

                }

            });

        }

        return view;

    }


    public void onAttach(Context context) {

        super.onAttach(context);

        if (context instanceof CatalogItemFragmentListener)

            fragmentListener = (CatalogItemFragmentListener) context;

        else

            throw new RuntimeException(context.toString()
                    + " must implement CatalogItemFragmentListener");

    }


    public void onDetach() {

        super.onDetach();

        fragmentListener = null;

    }


    public interface CatalogItemFragmentListener {

        void onIncrementCatalogItemQuantity(CatalogItemModel catalogItemModel);

        void onEditCatalogItem(CatalogItemModel catalogItemModel);

    }


}