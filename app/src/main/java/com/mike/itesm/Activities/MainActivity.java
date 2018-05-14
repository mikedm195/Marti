package com.mike.itesm.Activities;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mike.itesm.Adapters.TreepAdapter;
import com.mike.itesm.Fragments.User.ClientCRUD.SignupFragment;
import com.mike.itesm.Fragments.User.ClientCRUD.UpdateClient;
import com.mike.itesm.Fragments.User.ClientCRUD.LoginFragment;
import com.mike.itesm.Objects.Treep;
import com.mike.itesm.Objects.User;
import com.mike.itesm.marti.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.mike.itesm.Services.Services.AGENCY_API;
import static com.mike.itesm.Services.Services.TREEP_API;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    int waitTime = 2000;
    NavigationView navigationView;


    private RecyclerView RecyclerView;
    private TreepAdapter mAdapter;

    private TextView agencyName;
    ArrayList<Treep> treepList = new ArrayList<Treep>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        //navigationView.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });



        agencyName = (TextView) findViewById(R.id.agency);
        RecyclerView = (RecyclerView) findViewById(R.id.treep_list_recycler);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAgencia();

        StringRequest loginReq = new StringRequest(Request.Method.GET, TREEP_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray res = new JSONArray(response);

                            for(int i = 0;i<res.length();i++){
                                JSONObject viajeObject = res.getJSONObject(i);
                                Treep treep = new Treep();
                                treep.setDestiny(viajeObject.getString("destiny"));
                                treep.setDescription(viajeObject.getString("description"));
                                treep.setPhoto(viajeObject.getString("photo"));
                                treep.setPrice(viajeObject.getString("price"));
                                treepList.add(treep);
                                Log.w("value",treep.getDestiny()+"");
                            }
                            mAdapter = new TreepAdapter(treepList);
                            RecyclerView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error! " + e.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(loginReq);

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
        //menu2 = navigationView.getMenu();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        boolean fragmentSeleccionado = false;
        Fragment fragment = null;

        if (id == R.id.nav_login) {
            fragment = new LoginFragment();
            fragmentSeleccionado = true;
            Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_principal) {
            fragment = new SignupFragment();
            fragmentSeleccionado = true;
            Toast.makeText(this, "Signup", Toast.LENGTH_SHORT).show();
        } /*  else if (id == R.id.nav_buscar) {
            fragment = new Buscar();
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_Cart) {
            fragment = new ShoppingCartFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_edit) {
            fragment = new EditUserProfileFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_category) {
            fragment = new CategoryFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_seller) {
            fragment = new SellerFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_logout) {
            User.getInstance().setUserID(0);
            loggedin = false;
            MenuItem login = menu2.findItem(R.id.nav_login);
            login.setVisible(true);
        } else if (id == R.id.nav_sucursales) {
            fragment = new Sucursales();
            fragmentSeleccionado = true;
            Toast.makeText(this, "Sucursales", Toast.LENGTH_SHORT).show();
        }*/

        if(fragmentSeleccionado){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    void getAgencia(){
        StringRequest loginReq = new StringRequest(Request.Method.GET, AGENCY_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray res = new JSONArray(response);

                            JSONObject ag = res.getJSONObject(0);

                            agencyName.setText(ag.getString("name"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error! " + e.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(loginReq);
    }
}
