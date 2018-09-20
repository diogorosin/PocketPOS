package br.com.pocketpos.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;

import br.com.pocketpos.R;
import br.com.pocketpos.app.task.BindCompanyListAsyncTask;
import br.com.pocketpos.core.task.AuthenticateAsyncTask;
import br.com.pocketpos.core.task.CreateAccountAsyncTask;
import br.com.pocketpos.core.task.RetrieveDeviceBySerialNumberAsyncTask;
import br.com.pocketpos.core.task.RetrieveUserByLoginAsyncTask;
import br.com.pocketpos.core.util.Constants;
import br.com.pocketpos.data.exception.ValidationException;
import br.com.pocketpos.data.jersey.AccountBean;
import br.com.pocketpos.data.jersey.CompanyBean;
import br.com.pocketpos.data.jersey.DatasetBean;
import br.com.pocketpos.data.jersey.DeviceBean;
import br.com.pocketpos.data.jersey.UserBean;
import br.com.pocketpos.data.room.UserVO;
import br.com.pocketpos.data.util.DB;
import br.com.pocketpos.data.util.DBSync;
import br.com.pocketpos.data.util.Messaging;

public class AccountActivity extends AppCompatActivity implements
        RetrieveDeviceBySerialNumberAsyncTask.Listener,
        RetrieveUserByLoginAsyncTask.Listener,
        AuthenticateAsyncTask.Listener,
        BindCompanyListAsyncTask.Listener,
        CreateAccountAsyncTask.Listener{


    private static final int WELCOME_STEP = 0;

    private static final int REGISTER_USER_STEP = 1;

    private static final int LOGIN_STEP = 2;

    private static final int SELECT_COMPANY_STEP = 3;

    private static final int REGISTER_COMPANY_STEP = 4;

    private static final int FINISH_STEP = 5;


    private ViewPager viewPager;

    private LinearLayout dotsLayout;

    private Button previewButton, nextButton;

    private View progressView;

    private View accountFormView;

    private AccountBean account;

    private int[] layouts;


    @SuppressLint("HardwareIds")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        DeviceBean deviceBean = new DeviceBean();

        deviceBean.setActive(true);

        deviceBean.setSerialNumber(Settings.Secure.getString(
                getBaseContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));

        deviceBean.setManufacturer(Build.MANUFACTURER.toUpperCase());

        deviceBean.setModel(Build.MODEL);

        UserBean userBean = new UserBean();

        CompanyBean companyBean = new CompanyBean();

        AccountBean accountBean = new AccountBean();

        accountBean.setDevice(deviceBean);

        accountBean.setUser(userBean);

        accountBean.setCompany(companyBean);

        setAccount(accountBean);

        setContentView(R.layout.activity_account);

        viewPager = findViewById(R.id.activity_account_view_pager);

        dotsLayout = findViewById(R.id.activity_account_layout_dots);

        previewButton = findViewById(R.id.activity_account_preview_button);

        nextButton = findViewById(R.id.activity_account_next_button);

        progressView = findViewById(R.id.activity_account_progress);

        accountFormView = findViewById(R.id.activity_account_body);

        layouts = new int[]{
                R.layout.activity_account_welcome_step,
                R.layout.activity_account_register_user_step,
                R.layout.activity_account_login_step,
                R.layout.activity_account_select_company_step,
                R.layout.activity_account_register_company_step,
                R.layout.activity_account_finish_step};

        addBottomDots(0);

        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter();

        viewPager.setAdapter(viewPagerAdapter);

        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        previewButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int currentPage = getItem(0);

                switch (currentPage){

                    case WELCOME_STEP:

                        break;

                    case REGISTER_USER_STEP:

                        TextView nameTextView = (EditText) findViewById(R.id.activity_account_register_name_edittext);

                        TextView loginTextView = (EditText) findViewById(R.id.activity_account_register_email_edittext);

                        TextView passwordTextView = (EditText) findViewById(R.id.activity_account_register_password_edittext);

                        TextView confirmPasswordTextView = (EditText) findViewById(R.id.activity_account_register_confirmpassword_edittext);

                        nameTextView.setError(null);

                        loginTextView.setError(null);

                        passwordTextView.setError(null);

                        confirmPasswordTextView.setError(null);

                        saveRegisterUserStep(nameTextView.getText().toString(),
                                loginTextView.getText().toString(),
                                passwordTextView.getText().toString());

                        viewPager.setCurrentItem(WELCOME_STEP, false);

                        break;

                    case LOGIN_STEP:

                        viewPager.setCurrentItem(WELCOME_STEP, false);

                        break;

                    case SELECT_COMPANY_STEP:

                        CompanyBean company = new CompanyBean();

                        RadioGroup companyRadioGroup = findViewById(R.id.account_company_radiogroup);

                        switch (companyRadioGroup.getCheckedRadioButtonId()){

                            case R.id.activity_account_use_company_radiobutton:

                                Spinner useCompanySpinner = findViewById(R.id.activity_account_use_company_spinner);

                                company = (CompanyBean) useCompanySpinner.getSelectedItem();

                                break;

                        }

                        saveSelectCompanyStep(company);

                        if (getAccount().getUser().getIdentifier() != null
                                && getAccount().getUser().getIdentifier() > 0) {

                            getAccount().setUser(new UserBean());

                            getAccount().setCompany(new CompanyBean());

                            viewPager.setCurrentItem(LOGIN_STEP, false);

                        } else

                            viewPager.setCurrentItem(REGISTER_USER_STEP, false);

                        break;

                    case REGISTER_COMPANY_STEP:

                        EditText denominationTextView = findViewById(R.id.activity_account_company_denomination_edittext);

                        EditText fancyNameTextView = findViewById(R.id.activity_account_company_fancyname_edittext);

                        denominationTextView.setError(null);

                        fancyNameTextView.setError(null);

                        saveRegisterCompanyStep(denominationTextView.getText().toString(),
                                fancyNameTextView.getText().toString());

                        viewPager.setCurrentItem(SELECT_COMPANY_STEP, false);

                        break;

                }

            }

        });

        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int currentPage = getItem(0);

                switch (currentPage) {

                    case WELCOME_STEP:

                        onRetrieveDeviceBySerialNumber();

                        break;

                    case REGISTER_USER_STEP:

                        validateRegisterUserStep();

                        break;

                    case LOGIN_STEP:

                        validateLoginStep();

                        break;

                    case SELECT_COMPANY_STEP:

                        CompanyBean company = new CompanyBean();

                        RadioGroup companyRadioGroup = findViewById(R.id.account_company_radiogroup);

                        switch (companyRadioGroup.getCheckedRadioButtonId()) {

                            case R.id.activity_account_use_company_radiobutton:

                                Spinner useCompanySpinner = findViewById(R.id.activity_account_use_company_spinner);

                                company = (CompanyBean) useCompanySpinner.getSelectedItem();

                                break;

                        }

                        saveSelectCompanyStep(company);

                        if (getAccount().getCompany().getIdentifier() != null &&
                                getAccount().getCompany().getIdentifier() > 0) {

                            onCreateAccount();

                        } else {

                            viewPager.setCurrentItem(REGISTER_COMPANY_STEP, false);

                        }

                        break;

                    case REGISTER_COMPANY_STEP:

                        validateRegisterCompanyStep();

                        break;

                    case FINISH_STEP:

                        onAuthenticate();

                        break;

                }

            }

        });

    }

    public AccountBean getAccount() {

        return account;

    }


    public void setAccount(AccountBean account) {

        this.account = account;

    }


    // VIEW ////////////////////////////////////////////////////


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        accountFormView.setVisibility(show ? View.GONE : View.VISIBLE);

        accountFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {

            public void onAnimationEnd(Animator animation) {

                accountFormView.setVisibility(show ? View.GONE : View.VISIBLE);

            }

        });

        progressView.setVisibility(show ? View.VISIBLE : View.GONE);

        progressView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {

            public void onAnimationEnd(Animator animation) {

                progressView.setVisibility(show ? View.VISIBLE : View.GONE);

            }

        });

    }


    private void addBottomDots(int currentPage) {

        TextView[] dotsTextView = new TextView[layouts.length - 1];

        dotsLayout.removeAllViews();

        for (int i = 0; i < dotsTextView.length; i++) {

            dotsTextView[i] = new TextView(this);

            dotsTextView[i].setText(Html.fromHtml("&#8226;"));

            dotsTextView[i].setTextSize(35);

            dotsTextView[i].setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimaryDark));

            dotsLayout.addView(dotsTextView[i]);

        }

        int selectedDot;

        switch (currentPage){

            case REGISTER_USER_STEP:
            case LOGIN_STEP:

                selectedDot = 1;
                break;

            case SELECT_COMPANY_STEP:

                selectedDot = 2;
                break;

            case REGISTER_COMPANY_STEP:

                selectedDot = 3;
                break;

            case FINISH_STEP:

                selectedDot = 4;
                break;

            default:

                selectedDot = currentPage;
                break;

        }

        dotsTextView[selectedDot].setTextColor(
                ContextCompat.getColor(getBaseContext(),
                        R.color.colorAccent));

    }


    private int getItem(int i) {

        return viewPager.getCurrentItem() + i;

    }


    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        public void onPageSelected(int position) {

            addBottomDots(position);

            switch (position){

                case WELCOME_STEP:

                    RadioButton newUserRadioButton = findViewById(R.id.activity_account_new_user_radiobutton);

                    RadioButton useUserRadioButton = findViewById(R.id.activity_account_use_user_radiobutton);

                    newUserRadioButton.setChecked(getAccount().getUser().getIdentifier() == null);

                    useUserRadioButton.setChecked(getAccount().getUser().getIdentifier() != null);

                    break;

                case REGISTER_USER_STEP:

                    final EditText nameEditText = findViewById(R.id.activity_account_register_name_edittext);

                    EditText loginEditText = findViewById(R.id.activity_account_register_email_edittext);

                    EditText passwordEditText = findViewById(R.id.activity_account_register_password_edittext);

                    EditText confirmPasswordEditText = findViewById(R.id.activity_account_register_confirmpassword_edittext);

                    String name = getAccount().getUser().getName();

                    if (name != null && !name.isEmpty())

                        nameEditText.setText(name);

                    String login = getAccount().getUser().getLogin();

                    if (login != null && !login.isEmpty())

                        loginEditText.setText(login);

                    String password = getAccount().getUser().getPassword();

                    if (password != null && !password.isEmpty()) {

                        passwordEditText.setText(password);

                        confirmPasswordEditText.setText(password);

                    }

                    confirmPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                            if (i == EditorInfo.IME_ACTION_GO) {

                                InputMethodManager imm = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                                Objects.requireNonNull(imm).hideSoftInputFromWindow(textView.getWindowToken(), 0);

                                nextButton.callOnClick();

                                return true;

                            }

                            return false;

                        }

                    });

                    break;

                case LOGIN_STEP:

                    final EditText loginPasswordEditText = findViewById(R.id.activity_account_login_password_edittext);

                    loginPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                            if (i == EditorInfo.IME_ACTION_GO) {

                                InputMethodManager imm = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                                Objects.requireNonNull(imm).hideSoftInputFromWindow(textView.getWindowToken(), 0);

                                nextButton.callOnClick();

                                return true;

                            }

                            return false;

                        }

                    });

                    break;

                case SELECT_COMPANY_STEP:

                    onBindCompanyList();

                    break;

                case REGISTER_COMPANY_STEP:

                    final EditText denominationEditText = findViewById(R.id.activity_account_company_denomination_edittext);

                    EditText fancyNameEditText = findViewById(R.id.activity_account_company_fancyname_edittext);

                    String denomination = getAccount().getCompany().getDenomination();

                    if (denomination != null && !denomination.isEmpty())

                        denominationEditText.setText(denomination);

                    String fancyName = getAccount().getCompany().getFancyName();

                    if (fancyName != null && !fancyName.isEmpty())

                        fancyNameEditText.setText(fancyName);

                    fancyNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                            if (i == EditorInfo.IME_ACTION_GO) {

                                InputMethodManager imm = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                                Objects.requireNonNull(imm).hideSoftInputFromWindow(textView.getWindowToken(), 0);

                                nextButton.callOnClick();

                                return true;

                            }

                            return false;

                        }

                    });

                    denominationEditText.requestFocus();

                    break;

                case FINISH_STEP:

                    break;

            }

            if (position == WELCOME_STEP || position == FINISH_STEP)

                previewButton.setVisibility(View.INVISIBLE);

            else

                previewButton.setVisibility(View.VISIBLE);

            if (position == layouts.length - 1) {

                nextButton.setText(getString(R.string.finish));

            } else {

                if (position == WELCOME_STEP)

                    nextButton.setText(getString(R.string.start));

                else

                    nextButton.setText(getString(R.string.next));

            }

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {}

        public void onPageScrollStateChanged(int arg0) {}

    };


    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        MyViewPagerAdapter() {}

        @NonNull
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            assert layoutInflater != null;

            View view = layoutInflater.inflate(layouts[position], container, false);

            container.addView(view);

            return view;

        }

        public int getCount() {

            return layouts.length;

        }

        public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {

            return view == obj;

        }

        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

            View view = (View) object;

            container.removeView(view);

        }

    }


    // VALIDATORS ///////////////////////////////////////////////////


    private void validateRegisterUserStep() {

        EditText nameEditText = findViewById(R.id.activity_account_register_name_edittext);

        EditText loginEditText = findViewById(R.id.activity_account_register_email_edittext);

        EditText passwordEditText = findViewById(R.id.activity_account_register_password_edittext);

        EditText confirmPasswordEditText = findViewById(R.id.activity_account_register_confirmpassword_edittext);

        nameEditText.setError(null);

        loginEditText.setError(null);

        passwordEditText.setError(null);

        confirmPasswordEditText.setError(null);

        String name = nameEditText.getText().toString();

        String login = loginEditText.getText().toString();

        String password = passwordEditText.getText().toString();

        String confirmPassword = confirmPasswordEditText.getText().toString();

        boolean cancel = false;

        View focusView = null;

        if (TextUtils.isEmpty(confirmPassword)) {

            confirmPasswordEditText.setError(getString(R.string.error_field_required));

            focusView = confirmPasswordEditText;

            cancel = true;

        } else {

            if(!confirmPassword.equals(password)){

                confirmPasswordEditText.setError(getString(R.string.error_password_does_not_match));

                passwordEditText.setError(getString(R.string.error_password_does_not_match));

                focusView = passwordEditText;

                cancel = true;

            }

        }

        if (TextUtils.isEmpty(password)) {

            passwordEditText.setError(getString(R.string.error_field_required));

            focusView = passwordEditText;

            cancel = true;

        } else {

            if(isValidPassword(password)){

                passwordEditText.setError(getString(R.string.error_invalid_password));

                focusView = passwordEditText;

                cancel = true;

            }

        }

        if (TextUtils.isEmpty(login)) {

            loginEditText.setError(getString(R.string.error_field_required));

            focusView = loginEditText;

            cancel = true;

        } else {

            if(isValidEmail(login)){

                loginEditText.setError(getString(R.string.error_invalid_email));

                focusView = loginEditText;

                cancel = true;

            }

        }

        if (TextUtils.isEmpty(name)) {

            nameEditText.setError(getString(R.string.error_field_required));

            focusView = nameEditText;

            cancel = true;

        } else {

            if(!isValidName(name)) {

                nameEditText.setError(getString(R.string.error_invalid_name));

                focusView = nameEditText;

                cancel = true;

            }

        }

        if (cancel) {

            focusView.requestFocus();

        } else {

            saveRegisterUserStep(name, login, password);

            viewPager.setCurrentItem(SELECT_COMPANY_STEP, false);

        }

    }


    private void validateLoginStep() {

        EditText loginEditText = findViewById(R.id.activity_account_login_email_edittext);

        EditText passwordEditText = findViewById(R.id.activity_account_login_password_edittext);

        loginEditText.setError(null);

        passwordEditText.setError(null);

        String login = loginEditText.getText().toString();

        String password = passwordEditText.getText().toString();

        boolean cancel = false;

        View focusView = null;

        if (TextUtils.isEmpty(password)) {

            passwordEditText.setError(getString(R.string.error_field_required));

            focusView = passwordEditText;

            cancel = true;

        } else {

            if(isValidPassword(password)){

                passwordEditText.setError(getString(R.string.error_invalid_password));

                focusView = passwordEditText;

                cancel = true;

            }

        }

        if (TextUtils.isEmpty(login)) {

            loginEditText.setError(getString(R.string.error_field_required));

            focusView = loginEditText;

            cancel = true;

        } else {

            if(isValidEmail(login)){

                loginEditText.setError(getString(R.string.error_invalid_email));

                focusView = loginEditText;

                cancel = true;

            }

        }

        if (cancel) {

            focusView.requestFocus();

        } else {

            onRetrieveUserByLogin(login);

        }

    }


    private void validateRegisterCompanyStep() {

        TextView denominationTextView = (EditText) findViewById(R.id.activity_account_company_denomination_edittext);

        TextView fancyNameTextView = (EditText) findViewById(R.id.activity_account_company_fancyname_edittext);

        denominationTextView.setError(null);

        fancyNameTextView.setError(null);

        String denomination = denominationTextView.getText().toString();

        String fancyName = fancyNameTextView.getText().toString();

        boolean cancel = false;

        View focusView = null;

        if(!fancyName.isEmpty() && !isValidFancyName(fancyName)) {

            fancyNameTextView.setError(getString(R.string.error_invalid_company_fancyname));

            focusView = fancyNameTextView;

            cancel = true;

        }

        if (TextUtils.isEmpty(fancyName)) {

            fancyNameTextView.setError(getString(R.string.error_field_required));

            focusView = fancyNameTextView;

            cancel = true;

        } else {

            if(!isValidCompanyFancyName(fancyName)) {

                fancyNameTextView.setError(getString(R.string.error_invalid_company_fancyname));

                focusView = fancyNameTextView;

                cancel = true;

            }

        }

        if (TextUtils.isEmpty(denomination)) {

            denominationTextView.setError(getString(R.string.error_field_required));

            focusView = denominationTextView;

            cancel = true;

        } else {

            if(!isValidCompanyDenomination(denomination)) {

                denominationTextView.setError(getString(R.string.error_invalid_company_denomination));

                focusView = denominationTextView;

                cancel = true;

            }

        }

        if (cancel) {

            focusView.requestFocus();

        } else {

            saveRegisterCompanyStep(denomination, fancyName);

            onCreateAccount();

        }

    }


    private boolean isValidName(String name) {

        return name.trim().length() > 4 && name.trim().contains(" ");

    }


    private boolean isValidCompanyDenomination(String denomination) {

        return denomination.trim().length() > 4 && denomination.trim().contains(" ");

    }


    private boolean isValidCompanyFancyName(String fancyName) {

        return fancyName.trim().length() > 1;

    }


    private boolean isValidEmail(String email) {

        return !email.contains("@") || !email.contains(".");

    }


    private boolean isValidPassword(String passoword) {

        return passoword.trim().length() < 5;

    }


    private boolean isValidFancyName(String fancyName) {

        return fancyName.trim().length() > 0;

    }


    // CONTROLLER ///////////////////////////////////////////////////


    private void saveRegisterUserStep(String name, String login, String password){

        getAccount().getUser().setName(name);

        getAccount().getUser().setLogin(login);

        getAccount().getUser().setPassword(password);

    }


    private void saveSelectCompanyStep(CompanyBean company) {

        getAccount().setCompany(company);

    }


    private void saveRegisterCompanyStep(String denomination, String fancyName){

        getAccount().getCompany().setDenomination(denomination);

        getAccount().getCompany().setFancyName(fancyName);

    }


    // IMPLEMENTATIONS //////////////////////////////////////////////


    public void onRetrieveUserByLogin(String login){

        new RetrieveUserByLoginAsyncTask<>(AccountActivity.this)
                .execute(login);

    }


    public void onRetrieveUserByLoginPreExecute() {

        showProgress(true);

    }


    public void onRetrieveUserByLoginSuccess(UserBean userBean) {



        try {

            EditText passwordEditText = findViewById(R.id.activity_account_login_password_edittext);

            String password = passwordEditText.getText().toString();

            if (!userBean.getActive())

                throw new ValidationException(getResources().getString(R.string.error_user_not_active));

            MessageDigest alg = MessageDigest.getInstance("SHA-256");

            byte messageDigest[] = alg.digest(password.getBytes("UTF-8"));

            StringBuilder hexString = new StringBuilder();

            for (byte b : messageDigest)

                hexString.append(String.format("%02X", 0xFF & b));

            String hexPassword = hexString.toString();

            if (!hexPassword.equals(userBean.getPassword()))

                throw new ValidationException(getResources().getString(R.string.error_invalid_password));

            getAccount().getUser().setIdentifier(userBean.getIdentifier());

            getAccount().getUser().setName(userBean.getName() );

            getAccount().getUser().setActive(userBean.getActive());

            getAccount().getUser().setLogin(userBean.getLogin());

            getAccount().getUser().setPassword(password);

            viewPager.setCurrentItem(SELECT_COMPANY_STEP, false);

        } catch (ValidationException e){

            AlertDialog.Builder builder = new AlertDialog.Builder(accountFormView.getContext());

            builder.setMessage(e.getMessage());

            builder.setCancelable(true);

            builder.setTitle(R.string.dlg_title_login_failure);

            builder.setPositiveButton(
                    R.string.try_again,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();

                        }
                    });

            AlertDialog alert = builder.create();

            alert.show();

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ignored) {


        } finally {

            showProgress(false);

        }

    }


    public void onRetrieveUserByLoginFailure(Messaging messaging) {

        showProgress(false);

    }


    public void onRetrieveDeviceBySerialNumber(){

        new RetrieveDeviceBySerialNumberAsyncTask<>(AccountActivity.this)
                .execute(getAccount().getDevice().getSerialNumber());

    }


    public void onRetrieveDeviceBySerialNumberPreExecute() {

        showProgress(true);

    }


    public void onRetrieveDeviceBySerialNumberSuccess(DeviceBean deviceBean) {

        getAccount().setDevice(deviceBean);

        RadioGroup userRadioGroup = findViewById(R.id.account_user_radiogroup);

        switch (userRadioGroup.getCheckedRadioButtonId()) {

            case R.id.activity_account_new_user_radiobutton:

                viewPager.setCurrentItem(REGISTER_USER_STEP, false);

                break;

            case R.id.activity_account_use_user_radiobutton:

                viewPager.setCurrentItem(LOGIN_STEP, false);

                break;

        }

        showProgress(false);

    }


    public void onRetrieveDeviceBySerialNumberFailure(Messaging messaging) {

        if (messaging instanceof Exception) {

            showProgress(false);

            AlertDialog.Builder builder = new AlertDialog.Builder(accountFormView.getContext());

            builder.setTitle(R.string.dlg_title_retrieve_device_failure);

            builder.setMessage(TextUtils.join("\n", messaging.getMessages()));

            builder.setPositiveButton(
                    R.string.button_ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();

                        }
                    });

            AlertDialog alert = builder.create();

            alert.show();

        } else {

            RadioGroup userRadioGroup = findViewById(R.id.account_user_radiogroup);

            switch (userRadioGroup.getCheckedRadioButtonId()) {

                case R.id.activity_account_new_user_radiobutton:

                    viewPager.setCurrentItem(REGISTER_USER_STEP, false);

                    break;

                case R.id.activity_account_use_user_radiobutton:

                    viewPager.setCurrentItem(LOGIN_STEP, false);

                    break;

            }

            showProgress(false);

        }

    }


    public void onCreateAccount(){

        new CreateAccountAsyncTask<>(this).execute(getAccount());

    }


    public void onCreateAccountPreExecute() {

        showProgress(true);

    }


    public void onCreateAccountSuccess(DatasetBean datasetBean) {

        @SuppressLint("HardwareIds")
        String serialNumber = Settings.Secure.getString(getBaseContext().
                getContentResolver(), Settings.Secure.ANDROID_ID);

        CompanyBean companyBean = datasetBean.getCompany();

        SharedPreferences preferences = getSharedPreferences(
                Constants.SHARED_PREFERENCES_NAME, 0);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(Constants.COMPANY_IDENTIFIER_PROPERTY, companyBean.getIdentifier());

        editor.putBoolean(Constants.COMPANY_ACTIVE_PROPERTY, companyBean.getActive());

        editor.putString(Constants.COMPANY_DENOMINATION_PROPERTY, companyBean.getDenomination());

        editor.putString(Constants.COMPANY_FANCYNAME_PROPERTY, companyBean.getFancyName());

        if (companyBean.getCouponTitle() != null)

            switch (companyBean.getCouponTitle()){

                case Constants.COMPANY_FANCYNAME_PROPERTY:

                    editor.putString(Constants.COUPON_TITLE_PROPERTY, companyBean.getFancyName());

                    break;

                default:

                    editor.putString(Constants.COUPON_TITLE_PROPERTY, companyBean.getCouponTitle());

            }

        if (companyBean.getCouponSubtitle() != null)

            switch (companyBean.getCouponSubtitle()){

                case Constants.COMPANY_FANCYNAME_PROPERTY:

                    editor.putString(Constants.COUPON_SUBTITLE_PROPERTY, companyBean.getFancyName());

                    break;

                default:

                    editor.putString(Constants.COUPON_SUBTITLE_PROPERTY, companyBean.getCouponSubtitle());

            }

        for (DeviceBean deviceBean : datasetBean.getDevices()) {

            if (deviceBean.getSerialNumber().equals(serialNumber)) {

                editor.putInt(Constants.DEVICE_IDENTIFIER_PROPERTY, deviceBean.getIdentifier());

                editor.putBoolean(Constants.DEVICE_ACTIVE_PROPERTY, deviceBean.getActive());

                editor.putString(Constants.DEVICE_ALIAS_PROPERTY, deviceBean.getAlias());

            }

        }

        editor.putBoolean(Constants.DEVICE_CONFIGURED_PROPERTY, true);

        editor.apply();

        new DBSync(DB.getInstance(getBaseContext())).syncDataset(datasetBean);

        viewPager.setCurrentItem(FINISH_STEP, false);

        showProgress(false);

    }


    public void onCreateAccountFailure(Messaging messaging) {

        showProgress(false);

        showAlertDialog(messaging);

    }


    public void onAuthenticate(){

        new AuthenticateAsyncTask<>(this).
                execute(getAccount().getUser().getLogin(),
                        getAccount().getUser().getPassword());

    }


    public void onAuthenticatePreExecute() {

        showProgress(true);

    }


    public void onAuthenticateSuccess(UserVO userVO) {

        SharedPreferences preferences = getSharedPreferences(
                Constants.SHARED_PREFERENCES_NAME, 0);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(Constants.USER_IDENTIFIER_PROPERTY, userVO.getIdentifier());

        editor.putString(Constants.USER_NAME_PROPERTY, userVO.getName());

        editor.putString(Constants.USER_LOGIN_PROPERTY, userVO.getLogin());

        editor.apply();

        Intent intent = new Intent(AccountActivity.this, MainActivity.class);

        startActivity(intent);

        finish();

    }


    public void onAuthenticateFailure(Messaging messaging) {

        showProgress(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(accountFormView.getContext());

        builder.setMessage(TextUtils.join("\n", messaging.getMessages()));

        builder.setTitle(R.string.dlg_title_login_failure);

        builder.setPositiveButton(
                R.string.try_again,
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(AccountActivity.this, LoginActivity.class);

                        startActivity(intent);

                        finish();

                    }

                });

        AlertDialog alert = builder.create();

        alert.show();

    }


    public void onBindCompanyList(){

        RadioButton newCompanyRadioButton = findViewById(R.id.activity_account_new_company_radiobutton);

        RadioButton useCompanyRadioButton = findViewById(R.id.activity_account_use_company_radiobutton);

        final Spinner useCompanySpinner = findViewById(R.id.activity_account_use_company_spinner);

        ArrayAdapter<CompanyBean> adapter = new ArrayAdapter<>(
                AccountActivity.this,
                android.R.layout.simple_spinner_item,
                new ArrayList<CompanyBean>());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        useCompanySpinner.setAdapter(adapter);

        useCompanySpinner.setEnabled(false);

        useCompanyRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                useCompanySpinner.setEnabled(b);

            }

        });

        new BindCompanyListAsyncTask<>(this,
                newCompanyRadioButton,
                useCompanyRadioButton,
                useCompanySpinner,
                adapter,
                getAccount().getCompany().getIdentifier())
                .execute();

    }


    public void onBindCompanyListPreExecute() {

        showProgress(true);

    }


    public void onBindCompanyListSuccess() {

        showProgress(false);

    }


    public void onBindCompanyListFailure(Messaging messaging) {

        showProgress(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(accountFormView.getContext());

        builder.setMessage(TextUtils.join("\n", messaging.getMessages()));

        builder.setTitle(R.string.dlg_title_request_failure);

        builder.setPositiveButton(R.string.try_again,

                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        onBindCompanyList();

                    }

                });

        AlertDialog alert = builder.create();

        alert.show();

    }


    // FUNCTIONS ////////////////////////////////////////////////////


    public void showAlertDialog(Messaging messaging){

        AlertDialog.Builder builder = new AlertDialog.Builder(accountFormView.getContext());

        builder.setMessage(TextUtils.join("\n", messaging.getMessages()));

        builder.setTitle(R.string.dlg_title_request_failure);

        builder.setPositiveButton(android.R.string.ok,

                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                    }

                });

        AlertDialog alert = builder.create();

        alert.show();

    }


}