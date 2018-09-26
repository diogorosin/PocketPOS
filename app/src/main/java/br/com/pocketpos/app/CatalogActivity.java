package br.com.pocketpos.app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.codetroopers.betterpickers.numberpicker.NumberPickerBuilder;
import com.codetroopers.betterpickers.numberpicker.NumberPickerDialogFragment;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import br.com.pocketpos.R;
import br.com.pocketpos.app.report.Report;
import br.com.pocketpos.app.report.ReportName;
import br.com.pocketpos.app.report.adapter.OnPrintListener;
import br.com.pocketpos.app.repository.CatalogViewModel;
import br.com.pocketpos.app.widget.CatalogCartFragment;
import br.com.pocketpos.app.widget.CatalogItemFragment;
import br.com.pocketpos.app.widget.CatalogPagerAdapter;
import br.com.pocketpos.core.task.FinalizeSaleAsyncTask;
import br.com.pocketpos.core.task.IncrementCatalogItemQuantityAsyncTask;
import br.com.pocketpos.core.task.ResetCatalogItemAsyncTask;
import br.com.pocketpos.core.task.UpdateCatalogItemAsyncTask;
import br.com.pocketpos.core.util.Constants;
import br.com.pocketpos.data.room.CatalogItemModel;
import br.com.pocketpos.data.room.CatalogModel;
import br.com.pocketpos.data.room.MeasureUnitGroup;
import br.com.pocketpos.data.util.Messaging;

public class CatalogActivity extends AppCompatActivity
        implements
        CatalogItemFragment.CatalogItemFragmentListener,
        UpdateCatalogItemAsyncTask.Listener,
        IncrementCatalogItemQuantityAsyncTask.Listener,
        ResetCatalogItemAsyncTask.Listener,
        CatalogCartFragment.CatalogCartFragmentListener,
        FinalizeSaleAsyncTask.Listener, OnPrintListener {


    private ViewPager viewPager;

    private CatalogViewModel catalogViewModel;

    private CatalogPagerAdapter catalogPagerAdapter;

    private FloatingActionButton floatingActionButton;

    private SharedPreferences preferences;

    private ProgressDialog progressDialog;


    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_catalog);

        preferences = getSharedPreferences(
                Constants.SHARED_PREFERENCES_NAME, 0);


        progressDialog = new ProgressDialog(this);


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

    public void onIncrementCatalogItemQuantity(CatalogItemModel catalogItemModel) {

        new IncrementCatalogItemQuantityAsyncTask<>(this).
                execute(catalogItemModel);

    }

    public void onFinalizeSale() {

        SharedPreferences preferences = getSharedPreferences(
                Constants.SHARED_PREFERENCES_NAME, 0);

        Integer user = preferences.getInt(Constants.USER_IDENTIFIER_PROPERTY, 0);

        new FinalizeSaleAsyncTask<>(this).execute(user);

    }

    public void onFinalizeSaleSuccess(Integer sale) {

        printTicketsOfLastGeneratedSale();

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

    public void onFinalizeSaleFailure(Messaging messaging) {}

    public void onUpdateCatalogItemFailure(Messaging messaging) {}

    public void onResetCatalogItemFailure(Messaging messaging) {}

    //PRINTER LISTNER
    public void onPrintPreExecute(ReportName report) {

        progressDialog.setCancelable(false);

        progressDialog.setTitle("Aguarde");

        progressDialog.setMessage("Imprimindo cupons...");

        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        progressDialog.show();

    }

    public void onPrintProgressInitialize(ReportName report, int progress, int max) {

        progressDialog.setProgress(progress);

        progressDialog.setMax(max);

    }

    public void onPrintProgressUpdate(ReportName report, int status) {

        progressDialog.incrementProgressBy(status);

    }

    public void onPrintSuccess(ReportName report) {

        progressDialog.hide();

        switch (report){

            case SALE_ITEM_COUPON:

                Toast.makeText(getBaseContext(), getResources().getString(R.string.success_sale_finished), Toast.LENGTH_SHORT).show();

                break;

        }

    }

    public void onPrintFailure(ReportName report, Messaging message) {

        progressDialog.hide();

        showPrinterAlertDialog(message, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                printTicketsOfLastGeneratedSale();

            }

        });

    }

    public void onPrintCancelled(ReportName report) {

        progressDialog.hide();

    }

    public void showPrinterAlertDialog(Messaging messaging, DialogInterface.OnClickListener listener){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(  Html.fromHtml(    TextUtils.join("\n", messaging.getMessages())  ) );

        builder.setCancelable(false);

        builder.setTitle(R.string.dlg_title_print_failure);

        builder.setPositiveButton(R.string.try_again, listener);

        AlertDialog alert = builder.create();

        alert.setCanceledOnTouchOutside(false);

        alert.show();

    }

    public void printTicketsOfLastGeneratedSale(){

        String title = preferences.getString(Constants.COUPON_TITLE_PROPERTY,"");

        String subtitle = preferences.getString(Constants.COUPON_SUBTITLE_PROPERTY,"");

        String deviceAlias = preferences.getString(Constants.DEVICE_ALIAS_PROPERTY,"");

        String userName = preferences.getString(Constants.USER_NAME_PROPERTY, "");

        String note = "PROIBIDA A VENDA E ENTREGA DE BEBIDAS ALCOOLICAS PARA MENORES DE 18 ANOS.";

        String footer = "WWW.POCKETPOS.COM.BR";

        try {

            Report report = (Report) Class.
                    forName("br.com.pocketpos.app.report.PT7003Report").
                    newInstance();

            report.printCouponsOfLastGeneratedSale(
                    getBaseContext(),
                    this,
                    title,
                    subtitle,
                    deviceAlias,
                    userName,
                    note,
                    footer);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}