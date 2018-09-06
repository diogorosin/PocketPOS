package br.com.pocketpos.app.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.pocketpos.R;


public class CatalogCartFragment
        extends BottomSheetDialogFragment
        implements ViewPager.OnPageChangeListener, View.OnClickListener {


    private static final int STEPS_COUNT = 3;


    private static final int ITEMS_STEP = 0;

    private static final int PAYMENT_STEP = 1;

    private static final int FINISH_STEP = 2;


    private LinearLayout dotsLayout;

    private HeightWrappingViewPager viewPager;

    private Button previewButton, nextButton;


    public static CatalogCartFragment newInstance() {

        CatalogCartFragment fragment = new CatalogCartFragment();

        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;

    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {

        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            public void onShow(DialogInterface dialog) {

                BottomSheetDialog d = (BottomSheetDialog) dialog;

                FrameLayout bottomSheet = d.findViewById(android.support.design.R.id.design_bottom_sheet);

                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);

            }

        });

        return dialog;

    }

    public void onResume() {

        super.onResume();

        CatalogCartPagerAdapter catalogPagerAdapter = new CatalogCartPagerAdapter(getChildFragmentManager());

        viewPager = getView().findViewById(R.id.activity_catalog_cart_viewpager);

        viewPager.setAdapter(catalogPagerAdapter);

        viewPager.addOnPageChangeListener(this);

        dotsLayout = getView().findViewById(R.id.activity_catalog_cart_layout_dots);

        previewButton = getView().findViewById(R.id.activity_catalog_cart_prev_button);

        previewButton.setOnClickListener(this);

        nextButton = getView().findViewById(R.id.activity_catalog_cart_next_button);

        nextButton.setOnClickListener(this);

        addBottomDots(0);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_catalog_cart_view, container, false);

        return view;

    }


    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}


    public void onPageSelected(int position) {

        addBottomDots(position);

        TextView title = getView().findViewById(R.id.activity_catalog_cart_title);

        switch (position) {

            case 0:

                title.setText(R.string.shopping_cart);

                break;

            case 1:

                title.setText(R.string.pay);

                break;

        }

        previewButton.setVisibility(position == ITEMS_STEP ? View.INVISIBLE : View.VISIBLE);

        nextButton.setText(position == STEPS_COUNT - 1 ? R.string.finish : R.string.next);

    }

    public void onPageScrollStateChanged(int state) {}

    private void addBottomDots(int currentPage) {

        TextView[] dotsTextView = new TextView[STEPS_COUNT];

        dotsLayout.removeAllViews();

        for (int i = 0; i < dotsTextView.length; i++) {

            dotsTextView[i] = new TextView(getContext());

            dotsTextView[i].setText(Html.fromHtml("&#8226;"));

            dotsTextView[i].setTextSize(35);

            dotsTextView[i].setTextColor(ContextCompat.getColor(getContext(), R.color.colorGreyDark));

            dotsLayout.addView(dotsTextView[i]);

        }

        int selectedDot;

        switch (currentPage){

            case ITEMS_STEP:

                selectedDot = 0;
                break;

            case PAYMENT_STEP:

                selectedDot = 1;
                break;

            case FINISH_STEP:

                selectedDot = 2;
                break;

            default:

                selectedDot = currentPage;
                break;

        }

        dotsTextView[selectedDot].setTextColor(
                ContextCompat.getColor(getContext(),
                        R.color.colorAccent));

    }

    public void onClick(View v) {

        switch (v.getId()){

            case R.id.activity_catalog_cart_next_button:

                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

                break;

            case R.id.activity_catalog_cart_prev_button:

                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);

                break;

        }

    }

}