package com.mike.itesm.Fragments.User.All;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mike.itesm.Adapters.ProductsAdapter;
import com.mike.itesm.Adapters.ShoppingCartAdapter;
import com.mike.itesm.Fragments.User.Admin.CheckoutAdminFragment;
import com.mike.itesm.Fragments.User.User.CheckoutUserFragment;
import com.mike.itesm.Objects.Product;
import com.mike.itesm.Objects.ShoppingCart;
import com.mike.itesm.Objects.User;
import com.mike.itesm.Utilities.ParserJSONCarts;
import com.mike.itesm.Utilities.ParserJSONProducts;
import com.mike.itesm.marti.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static com.mike.itesm.Services.Services.CART_LIST_API;

public class ShoppingCartFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<ShoppingCart> cartList;
    private ShoppingCartAdapter adapter;
    private Button checkoutBtn;
    private TextView totalTxt;

    public static ShoppingCartFragment newInstance() {
        ShoppingCartFragment fragment = new ShoppingCartFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);

        checkoutBtn = (Button)view.findViewById(R.id.checkoutButton);
        totalTxt = (TextView)view.findViewById(R.id.totalCartText);

        StringRequest productsReq = new StringRequest(Request.Method.GET, CART_LIST_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            double total = 0;
                            JSONArray carts = new JSONArray(response);
                            cartList = ParserJSONCarts.parseaArreglo(carts);
                            for(int i = 0; i<cartList.size();i++){
                                total+=cartList.get(i).getProduct().getPrice();
                            }
                            totalTxt.setText("Total: $" + total);
                            adapter = new ShoppingCartAdapter(getContext(), cartList);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Error! " + e.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), R.string.commsErrorText + " " + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(getContext()).add(productsReq);



        recyclerView = (RecyclerView) view.findViewById(R.id.shoppingcart_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //adapter = new ShoppingCartAdapter(getContext(), ShoppingCart.getInstance().shoppingCartArray);
        //recyclerView.setAdapter(adapter);

        //adapter.notifyDataSetChanged();

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (ShoppingCart.getInstance().getShoppingCartTotal() > 0) {
                    doCheckout();
                } else {
                    Toast.makeText(getContext(), R.string.cartEmptyText , Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.shoppingCartTitle);
    }

    void doCheckout() {
        if(User.getInstance().getRole() == 3) {
            Fragment newFragment = new CheckoutUserFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, newFragment);
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        } else if (User.getInstance().getRole() < 3 && User.getInstance().getRole() > 0)
        {
            Fragment newFragment = new CheckoutAdminFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, newFragment);
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        } else {
            Toast.makeText(getContext(), R.string.credentialsErrorText , Toast.LENGTH_SHORT).show();
        }
    }

    public void onStop () {
        super.onStop();
        getActivity().setTitle(R.string.app_name);
    }

    public void onDestroy () {
        super.onDestroy();
        getActivity().setTitle(R.string.app_name);
    }
}
