package com.mike.itesm.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.mike.itesm.Fragments.User.All.Buscar;
import com.mike.itesm.Fragments.User.All.LoginFragment;
import com.mike.itesm.Fragments.User.All.ShoppingCartFragment;
import com.mike.itesm.Fragments.User.User.ProductsFragment;
import com.mike.itesm.Objects.User;
import com.mike.itesm.marti.R;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    int waitTime = 2000;
    NavigationView navigationView;
    Menu menu2;
    public boolean loggedin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

      //  HeaderLayout header = (HeaderLayout) findViewById(R.id.header_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                Fragment selectedFragment = ProductsFragment.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, waitTime);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu2 = navigationView.getMenu();
        if(!loggedin) {
            MenuItem logout = menu2.findItem(R.id.nav_logout);
            logout.setVisible(false);
            this.invalidateOptionsMenu();
            MenuItem login = menu2.findItem(R.id.nav_login);
            login.setVisible(true);
        }else {
            MenuItem login = menu2.findItem(R.id.nav_login);
            login.setVisible(false);
            this.invalidateOptionsMenu();
            MenuItem logout = menu2.findItem(R.id.nav_logout);
            logout.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        boolean fragmentSeleccionado = false;
        Fragment fragment = null;


        if(User.getInstance().getUserID()<=0){
            if (id == R.id.nav_login) {
                fragment = new LoginFragment();
                fragmentSeleccionado = true;
            } else if (id == R.id.nav_principal) {
                fragment = new ProductsFragment();
                fragmentSeleccionado = true;
            }  else if (id == R.id.nav_buscar) {
                fragment = new Buscar();
                fragmentSeleccionado = true;
                Toast.makeText(this, "buscar" , Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_Cart) {
                fragment = new ShoppingCartFragment();
                fragmentSeleccionado = true;
            }
        } else if (loggedin || User.getInstance().getUserID() > 0){
            loggedin = true;
            String user_name = User.getInstance().getFirstName();
            View headerView = navigationView.getHeaderView(0);
            TextView navUsername = (TextView) headerView.findViewById(R.id.name);
            navUsername.setText(user_name);

            if (id == R.id.nav_principal) {
                fragment = new ProductsFragment();
                fragmentSeleccionado = true;
                Toast.makeText(this, "Principal" , Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_principal) {
                fragment = new ProductsFragment();
                fragmentSeleccionado = true;
            } else if (id == R.id.nav_buscar) {
                fragment = new Buscar();
                fragmentSeleccionado = true;
                Toast.makeText(this, "buscar" , Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_Cart) {
                fragment = new ShoppingCartFragment();
                fragmentSeleccionado = true;
            } else if (id == R.id.nav_logout) {
                User.getInstance().setUserID(0);
                loggedin = false;
                MenuItem login = menu2.findItem(R.id.nav_login);
                login.setVisible(true);
                Toast.makeText(this, "logout" , Toast.LENGTH_SHORT).show();
            }
        }


        if(fragmentSeleccionado){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
