package br.com.pocketpos.app;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.codetroopers.betterpickers.numberpicker.NumberPickerBuilder;
import com.codetroopers.betterpickers.numberpicker.NumberPickerDialogFragment;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import br.com.pocketpos.R;
import br.com.pocketpos.app.repository.CatalogViewModel;
import br.com.pocketpos.app.widget.CatalogCartFragment;
import br.com.pocketpos.app.widget.CatalogItemFragment;
import br.com.pocketpos.app.widget.CatalogPagerAdapter;
import br.com.pocketpos.core.task.IncrementCatalogItemQuantityAsyncTask;
import br.com.pocketpos.core.task.ResetCatalogItemAsyncTask;
import br.com.pocketpos.core.task.UpdateCatalogItemAsyncTask;
import br.com.pocketpos.data.room.CatalogItemModel;
import br.com.pocketpos.data.room.CatalogModel;
import br.com.pocketpos.data.room.MeasureUnitGroup;
import br.com.pocketpos.data.util.Messaging;

public class CatalogActivity extends AppCompatActivity
        implements
        CatalogItemFragment.CatalogItemFragmentListener,
        UpdateCatalogItemAsyncTask.Listener,
        IncrementCatalogItemQuantityAsyncTask.Listener,
        ResetCatalogItemAsyncTask.Listener{


    private ViewPager viewPager;

    private CatalogViewModel catalogViewModel;

    private CatalogPagerAdapter catalogPagerAdapter;

    private FloatingActionButton floatingActionButton;


    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_catalog);


        Window window = getWindow();

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(getResources().getColor(R.color.colorAccent));


        Toolbar toolbar = findViewById(R.id.activity_catalog_toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);


        catalogPagerAdapter = new CatalogPagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.activity_catalog_viewpager);

        viewPager.setAdapter(catalogPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.activity_catalog_tab);

        tabLayout.setupWithViewPager(viewPager);


        floatingActionButton = findViewById(R.id.activity_catalog_fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                BottomSheetDialogFragment bottomSheetDialogFragment = CatalogCartFragment.
                        newInstance();

                bottomSheetDialogFragment.show(
                        getSupportFragmentManager(),
                        bottomSheetDialogFragment.getTag());

            }

        });


        catalogViewModel = ViewModelProviders.of(this).get(CatalogViewModel.class);

        catalogViewModel.getCatalogs().observe(CatalogActivity.this, new Observer<List<CatalogModel>>() {

            public void onChanged(@Nullable List<CatalogModel> catalogs) {

                catalogPagerAdapter.setCatalogs(catalogs);

            }

        });

    }


    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case android.R.id.home:

                this.finish();

                break;

        }

        return super.onOptionsItemSelected(item);

    }


    public void onUpdateCatalogItemFailure(Messaging messaging) {}


    public void onResetCatalogItemFailure(Messaging messaging) {}


    public void onIncrementCatalogItemQuantity(CatalogItemModel catalogItemModel) {

        new IncrementCatalogItemQuantityAsyncTask<>(this).
                execute(catalogItemModel);

    }

    public void onEditCatalogItem(final CatalogItemModel catalogItemModel) {

        boolean withDecimal = catalogItemModel.
                getMeasureUnit().
                getGroup().
                equals(MeasureUnitGroup.LENGHT.ordinal()) ||
                catalogItemModel.
                        getMeasureUnit().
                        getGroup().
                        equals(MeasureUnitGroup.MASS.ordinal()) ||
                catalogItemModel.
                        getMeasureUnit().
                        getGroup().
                        equals(MeasureUnitGroup.MEASURE.ordinal());

        NumberPickerBuilder npb = new NumberPickerBuilder()
                .setFragmentManager(getSupportFragmentManager())
                .setStyleResId(R.style.BetterPickersDialogFragment)
                .setPlusMinusVisibility(View.INVISIBLE)
                .setMaxNumber(BigDecimal.valueOf(999))
                .setDecimalVisibility(withDecimal ? View.VISIBLE : View.INVISIBLE)
                .setLabelText(catalogItemModel.getMeasureUnit().getDenomination()+"(S)")
                .addNumberPickerDialogHandler(new NumberPickerDialogFragment.NumberPickerDialogHandlerV2() {

                    public void onDialogNumberSet(int reference, BigInteger number, double decimal, boolean isNegative, BigDecimal fullNumber) {

                        catalogItemModel.setQuantity(fullNumber.doubleValue());

                        new UpdateCatalogItemAsyncTask<>(CatalogActivity.this).execute(catalogItemModel);

                    }

                });

        if (withDecimal)

            npb.setCurrentNumber(catalogItemModel.getQuantity() == null
                    ? BigDecimal.valueOf(0) : BigDecimal.valueOf(catalogItemModel.getQuantity()));

        else

            npb.setCurrentNumber(catalogItemModel.getQuantity() == null ? 0 : catalogItemModel.getQuantity().intValue());

        npb.show();

    }

    public void onIncrementCatalogItemQuantityFailure(Messaging messaging) {}

}