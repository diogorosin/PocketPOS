package br.com.pocketpos.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import br.com.pocketpos.R;
import br.com.pocketpos.core.util.Constants;
import br.com.pocketpos.data.room.UserVO;
import br.com.pocketpos.data.util.DB;

public class MainActivity
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.activity_main_toolbar);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.activity_main_drawer);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.app_name, R.string.app_name);

        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.activity_main_navigator);

        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences preferences = getSharedPreferences(
                Constants.SHARED_PREFERENCES_NAME, 0);

        TextView userNameTextView = navigationView.getHeaderView(0).findViewById(R.id.activity_main_navigator_header_name);

        TextView userLoginTextView = navigationView.getHeaderView(0).findViewById(R.id.activity_main_navigator_header_login);

        userNameTextView.setText(preferences.getString(Constants.USER_NAME_PROPERTY,"Anônimo"));

        userLoginTextView.setText(preferences.getString(Constants.USER_LOGIN_PROPERTY,""));

    }


    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.activity_main_drawer);

        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);

        } else {

            super.onBackPressed();

        }

    }


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        DrawerLayout drawer = findViewById(R.id.activity_main_drawer);

        switch (item.getItemId()){

            case R.id.activity_main_menu_home:

                break;

            case R.id.activity_main_menu_sale:

                drawer.closeDrawers();

                Intent saleIntent = new Intent(MainActivity.this, CatalogActivity.class);

                startActivity(saleIntent);

                break;

            case R.id.activity_main_menu_cash:

                drawer.closeDrawer(GravityCompat.START);

                Intent cashIntent = new Intent(MainActivity.this, CashActivity.class);

                startActivity(cashIntent);

                break;

            case R.id.activity_main_menu_logout:

                SharedPreferences preferences = getSharedPreferences(
                        Constants.SHARED_PREFERENCES_NAME, 0);

                SharedPreferences.Editor editor = preferences.edit();

                editor.remove(Constants.USER_IDENTIFIER_PROPERTY);

                editor.remove(Constants.USER_NAME_PROPERTY);

                editor.remove(Constants.USER_LOGIN_PROPERTY);

                editor.apply();

                drawer.closeDrawer(GravityCompat.START);

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                startActivity(intent);

                finish();

                break;

        }

        return true;

    }

}