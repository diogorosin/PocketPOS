package br.com.pocketpos.app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import br.com.pocketpos.R;
import br.com.pocketpos.app.report.Report;
import br.com.pocketpos.app.report.ReportName;
import br.com.pocketpos.app.report.adapter.OnPrintListener;
import br.com.pocketpos.app.repository.CashViewModel;
import br.com.pocketpos.app.widget.CashEntryRecyclerViewAdapter;
import br.com.pocketpos.app.widget.CashSummaryRecyclerViewAdapter;
import br.com.pocketpos.app.widget.CloseCashDialogFragment;
import br.com.pocketpos.app.widget.OpenCashDialogFragment;
import br.com.pocketpos.app.widget.RemovalCashDialogFragment;
import br.com.pocketpos.app.widget.SupplyCashDialogFragment;
import br.com.pocketpos.core.task.CloseCashAsyncTask;
import br.com.pocketpos.core.task.OpenCashAsyncTask;
import br.com.pocketpos.core.task.RemovalCashAsyncTask;
import br.com.pocketpos.core.task.SummaryCashReportAsyncTask;
import br.com.pocketpos.core.task.SupplyCashAsyncTask;
import br.com.pocketpos.core.util.Constants;
import br.com.pocketpos.core.util.StringUtils;
import br.com.pocketpos.data.room.CashModel;
import br.com.pocketpos.data.util.Messaging;

public class CashActivity extends AppCompatActivity implements
        OpenCashAsyncTask.Listener,
        OpenCashDialogFragment.Listener,
        CloseCashAsyncTask.Listener,
        CloseCashDialogFragment.Listener,
        SupplyCashAsyncTask.Listener,
        SupplyCashDialogFragment.Listener,
        RemovalCashAsyncTask.Listener,
        RemovalCashDialogFragment.Listener,
        OnPrintListener,
        SummaryCashReportAsyncTask.Listener,
        View.OnClickListener {


    private FloatingActionButton openCloseFAB;

    private CashSummaryRecyclerViewAdapter cashSummaryRecyclerViewAdapter;

    private CashEntryRecyclerViewAdapter cashEntryRecyclerViewAdapter;

    private FloatingActionsMenu menuFAM;

    private SharedPreferences preferences;

    private ProgressDialog progressDialog;

    private Boolean cashOpen = false;


    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cash);


        preferences = getSharedPreferences(
                Constants.SHARED_PREFERENCES_NAME, 0);


        progressDialog = new ProgressDialog(this);

        openCloseFAB = findViewById(R.id.activity_cash_fab);

        openCloseFAB.setOnClickListener(CashActivity.this);

        menuFAM = findViewById(R.id.activity_cash_fam);

        com.getbase.floatingactionbutton.FloatingActionButton supplyFAB = findViewById(R.id.activity_cash_supply_fab);

        supplyFAB.setOnClickListener(this);

        com.getbase.floatingactionbutton.FloatingActionButton removalFAB = findViewById(R.id.activity_cash_removal_fab);

        removalFAB.setOnClickListener(this);


        Window window = getWindow();

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(Color.TRANSPARENT);


        Toolbar toolbar = findViewById(R.id.activity_cash_toolbar);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);


        final AppBarLayout barLayout = findViewById(R.id.activity_cash_register_bar_layout);

        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.activity_cash_toolbar_layout);


        CashViewModel cashViewModel = ViewModelProviders.of(this).get(CashViewModel.class);

        cashViewModel.isOpen().observe(CashActivity.this, new Observer<Boolean>() {

            public void onChanged(Boolean isOpen) {

                cashOpen = isOpen;

                if (menuFAM.isExpanded())

                    menuFAM.collapseImmediately();

                menuFAM.setVisibility(cashOpen ? View.VISIBLE : View.GONE);

                barLayout.setBackgroundColor(

                        cashOpen ? getResources().getColor(R.color.colorCashOpened) : getResources().getColor(R.color.colorCashClosed)

                );

                collapsingToolbarLayout.setTitle(

                        cashOpen ? getResources().getString(R.string.cash_opened) : getResources().getString(R.string.cash_closed)

                );

                collapsingToolbarLayout.setStatusBarScrimColor(

                        cashOpen ? getResources().getColor(R.color.colorCashOpened) : getResources().getColor(R.color.colorCashClosed)

                );

                collapsingToolbarLayout.setContentScrimColor(

                        cashOpen ? getResources().getColor(R.color.colorCashOpened) : getResources().getColor(R.color.colorCashClosed)

                );

                openCloseFAB.setBackgroundTintList(

                        cashOpen ? ContextCompat.getColorStateList(CashActivity.this, R.color.white) : ContextCompat.getColorStateList(CashActivity.this, R.color.white)

                );

                openCloseFAB.setImageDrawable(

                        cashOpen ? ContextCompat.getDrawable(CashActivity.this, R.drawable.lock_24) : ContextCompat.getDrawable(CashActivity.this, R.drawable.unlock_24)

                );

            }

        });

        cashViewModel.value().observe(CashActivity.this, new Observer<Double>() {

            public void onChanged(final Double money) {

                TextView moneyTextView = findViewById(R.id.activity_cash_money_textview);

                moneyTextView.setText(StringUtils.formatCurrencyWithSymbol(money));

                TextView totalMoneyTextView = findViewById(R.id.activity_cash_summary_value_textview);

                totalMoneyTextView.setText(StringUtils.formatCurrencyWithSymbol(money));

            }

        });

        RecyclerView cashSummaryRecyclerView = findViewById(R.id.activity_cash_summary_recyclerview);

        cashSummaryRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        cashSummaryRecyclerViewAdapter = new CashSummaryRecyclerViewAdapter(new ArrayList<CashModel>());

        cashSummaryRecyclerView.setAdapter(cashSummaryRecyclerViewAdapter);

        cashViewModel.cashSummary().observe(CashActivity.this, new Observer<List<CashModel>>() {

            public void onChanged(@Nullable List<CashModel> cashModelList) {

                cashSummaryRecyclerViewAdapter.setCashSummary(cashModelList);

            }

        });


        RecyclerView cashEntryRecyclerView = findViewById(R.id.activity_cash_entry_recyclerview);

        cashEntryRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        cashEntryRecyclerViewAdapter = new CashEntryRecyclerViewAdapter(cashEntryRecyclerView, new ArrayList<CashModel>());

        cashEntryRecyclerView.setAdapter(cashEntryRecyclerViewAdapter);

        cashViewModel.cashEntry().observe(CashActivity.this, new Observer<List<CashModel>>() {

            public void onChanged(@Nullable List<CashModel> cashModelList) {

                cashEntryRecyclerViewAdapter.setCashEntries(cashModelList);

            }

        });

    }


    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                NavUtils.navigateUpFromSameTask(this);

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }

    }


    private void showAlertDialog(Messaging messaging){

        AlertDialog.Builder builder = new AlertDialog.Builder(CashActivity.this.getBaseContext());

        builder.setMessage(TextUtils.join("\n", messaging.getMessages()));

        builder.setCancelable(true);

        builder.setTitle(R.string.dlg_title_request_failure);

        builder.setPositiveButton(android.R.string.ok,

                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                    }

                });

        builder.create().show();

    }


    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.activity_cash_fab:

                if (cashOpen){

                    DialogFragment dialog = new CloseCashDialogFragment();

                    dialog.show(CashActivity.this.getSupportFragmentManager(), "CloseCashDialogFragment");

                } else {

                    DialogFragment dialog = new OpenCashDialogFragment();

                    dialog.show(CashActivity.this.getSupportFragmentManager(), "OpenCashDialogFragment");

                }

                break;

            case R.id.activity_cash_supply_fab:

                menuFAM.collapse();

                DialogFragment supplyDialog = new SupplyCashDialogFragment();

                supplyDialog.show(CashActivity.this.getSupportFragmentManager(), "SupplyCashDialogFragment");

                break;

            case R.id.activity_cash_removal_fab:

                menuFAM.collapse();

                DialogFragment removalDialog = new RemovalCashDialogFragment();

                removalDialog.show(CashActivity.this.getSupportFragmentManager(), "RemovalCashDialogFragment");

                break;

        }

    }


    public void onOpenCash(Double value) {

        new OpenCashAsyncTask(this).
                execute(value, preferences.getInt(Constants.USER_IDENTIFIER_PROPERTY, 0));

    }


    public void onOpenCashSuccess(final CashModel cashModel){

        String title = preferences.getString(Constants.COUPON_TITLE_PROPERTY,"");

        String subtitle = preferences.getString(Constants.COUPON_SUBTITLE_PROPERTY,"");

        Date dateTime = cashModel.getDateTime();

        String deviceAlias = preferences.getString(Constants.DEVICE_ALIAS_PROPERTY,"");

        try {

            Report report = (Report) Class.
                    forName("br.com.pocketpos.app.report.PT7003Report").
                    newInstance();

            report.printOpenCashCoupon(
                    this,
                    title,
                    subtitle,
                    dateTime,
                    deviceAlias,
                    cashModel);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }


    public void onOpenCashFailure(Messaging messaging){

        showAlertDialog(messaging);

    }


    public void onSupplyCash(Double value) {

        new SupplyCashAsyncTask(this).
                execute(value, preferences.getInt(Constants.USER_IDENTIFIER_PROPERTY, 0));

    }


    public void onSupplyCashSuccess(CashModel cashModel) {

        String title = preferences.getString(Constants.COUPON_TITLE_PROPERTY,"");

        String subtitle = preferences.getString(Constants.COUPON_SUBTITLE_PROPERTY,"");

        Date dateTime = new Date();

        String deviceAlias = preferences.getString(Constants.DEVICE_ALIAS_PROPERTY,"");

        try {

            Report report = (Report) Class.
                    forName("br.com.pocketpos.app.report.PT7003Report").
                    newInstance();

            report.printSupplyCashCoupon(
                    this,
                    title,
                    subtitle,
                    dateTime,
                    deviceAlias,
                    cashModel);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }


    public void onSupplyCashFailure(Messaging messaging) {

        showAlertDialog(messaging);

    }


    public void onRemoveCash(Double value) {

        new RemovalCashAsyncTask(this).
                execute(value, preferences.getInt(Constants.USER_IDENTIFIER_PROPERTY, 0));

    }


    public void onRemoveCashSuccess(CashModel cashModel) {

        String title = preferences.getString(Constants.COUPON_TITLE_PROPERTY,"");

        String subtitle = preferences.getString(Constants.COUPON_SUBTITLE_PROPERTY,"");

        Date dateTime = cashModel.getDateTime();

        String deviceAlias = preferences.getString(Constants.DEVICE_ALIAS_PROPERTY,"");

        try {

            Report report = (Report) Class.
                    forName("br.com.pocketpos.app.report.PT7003Report").
                    newInstance();

            report.printRemoveCashCoupon(
                    this,
                    title,
                    subtitle,
                    dateTime,
                    deviceAlias,
                    cashModel);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }


    public void onRemoveCashFailure(Messaging messaging) {

        showAlertDialog(messaging);

    }


    public void onCloseCash(Double value) {

        new CloseCashAsyncTask(this).
                execute(value, preferences.getInt(Constants.USER_IDENTIFIER_PROPERTY, 0));

    }


    public void onCloseCashSuccess(CashModel cashModel) {

        new SummaryCashReportAsyncTask<>(this).execute();

    }


    public void onCloseCashFailure(Messaging messaging) {

        showAlertDialog(messaging);

    }


    public void onPrintPreExecute(ReportName report) {

        progressDialog.setCancelable(false);

        progressDialog.setTitle("Aguarde");

        progressDialog.setMessage("Imprimindo comprovante...");

        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        progressDialog.show();

    }

    public void onPrintProgressInitialize(ReportName report, int progress, int max) {

        progressDialog.setProgress(progress);

        progressDialog.setMax(max);

    }

    public void onPrintPostExecute(ReportName report, List<Object> printed) {

        progressDialog.hide();

        switch (report){

            case OPEN_CASH_COUPON:

                Toast.makeText(getBaseContext(), getResources().getString(R.string.success_cash_opened), Toast.LENGTH_SHORT).show();

                break;

            case SUPPLY_CASH_COUPON:

                Toast.makeText(getBaseContext(), R.string.success_cash_supplied, Toast.LENGTH_SHORT).show();

                break;

            case REMOVE_CASH_COUPON:

                Toast.makeText(getBaseContext(), R.string.success_cash_removed, Toast.LENGTH_SHORT).show();

                break;

            case CLOSE_CASH_COUPON:

                Toast.makeText(getBaseContext(), getResources().getString(R.string.success_cash_closed), Toast.LENGTH_SHORT).show();

                break;

        }

    }


    public void onPrintCancelled(ReportName report) {

        progressDialog.hide();

    }


    public void onPrintProgressUpdate(ReportName report, int status) {

        progressDialog.incrementProgressBy(status);

    }


    public void onSummaryReportSuccess(List<CashModel> cashModelList) {

        String title = preferences.getString(Constants.COUPON_TITLE_PROPERTY,"");

        String subtitle = preferences.getString(Constants.COUPON_SUBTITLE_PROPERTY,"");

        Date dateTime = new Date();

        String deviceAlias = preferences.getString(Constants.DEVICE_ALIAS_PROPERTY,"");

        try {

            CashModel[] list = new CashModel[cashModelList.size()];

            list = cashModelList.toArray(list);

            Report report = (Report) Class.
                    forName("br.com.pocketpos.app.report.PT7003Report").
                    newInstance();

            report.printCloseCashCoupon(
                    this,
                    title,
                    subtitle,
                    dateTime,
                    deviceAlias,
                    list);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }


    public void onSummaryReportFailure(Messaging messaging) {}


}