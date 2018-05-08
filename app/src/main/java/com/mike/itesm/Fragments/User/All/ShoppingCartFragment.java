package com.mike.itesm.Fragments.User.All;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mike.itesm.Adapters.ShoppingCartAdapter;
import com.mike.itesm.Fragments.User.User.ProductsFragment;
import com.mike.itesm.Objects.Seller;
import com.mike.itesm.Objects.ShoppingCart;
import com.mike.itesm.Objects.User;
import com.mike.itesm.Utilities.ParserJSONCarts;
import com.mike.itesm.marti.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mike.itesm.Services.Services.CART_API;
import static com.mike.itesm.Services.Services.CART_LIST_API;
import static com.mike.itesm.Services.Services.ORDER_API;
import static com.mike.itesm.Services.Services.SELLER_LIST_API;

public class ShoppingCartFragment extends Fragment {

    private int sellerID;
    private RecyclerView recyclerView;
    private ArrayList<ShoppingCart> cartList;
    private ShoppingCartAdapter adapter;
    private Button checkoutBtn;
    private Spinner sellerSpinner;
    ArrayAdapter<Seller> spinnerAdapter;
    private ArrayList<Seller> sellerList = new ArrayList<Seller>();
    private TextView totalTxt;
    String stringDetails;

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
        recyclerView = (RecyclerView) view.findViewById(R.id.shoppingcart_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sellerSpinner = (Spinner) view.findViewById(R.id.spinnerSeller);

        StringRequest productsReq = new StringRequest(Request.Method.GET, CART_LIST_API + "?user_id=" + User.getInstance().getUserID(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if(!response.equals("")) {
                                double total = 0;
                                JSONArray carts = new JSONArray(response);
                                cartList = ParserJSONCarts.parseaArreglo(carts);
                                for (int i = 0; i < cartList.size(); i++) {
                                    total += cartList.get(i).getProduct().getPrice();
                                }
                                totalTxt.setText("Total: $" + total);
                                adapter = new ShoppingCartAdapter(getContext(), cartList);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }else{
                                Toast.makeText(getContext(),"Add items before purchasing", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Error! " + e.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        Volley.newRequestQueue(getContext()).add(productsReq);

        StringRequest sellersReq = new StringRequest(Request.Method.GET, SELLER_LIST_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if(!response.equals("")) {
                                JSONArray sellers = new JSONArray(response);
                                for(int i = 0; i<sellers.length();i++) {
                                    JSONObject seller = sellers.getJSONObject(i);
                                    Seller sellerObject = new Seller(
                                        seller.getInt("seller_id"),
                                        seller.getString("name")
                                    );
                                    sellerList.add(sellerObject);
                                }
                                spinnerAdapter = new ArrayAdapter<Seller> (getContext(), android.R.layout.simple_list_item_1, sellerList);
                                sellerSpinner.setAdapter(spinnerAdapter);

                                sellerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        Seller s = spinnerAdapter.getItem(i);
                                        sellerID = s.getId();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });

                            }else{
                                Toast.makeText(getContext(),"Add items before purchasing", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Error! " + e.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(getContext()).add(sellersReq);

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartList != null) {
                    try {
                        doCheckout();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), R.string.cartEmptyText , Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.shoppingCartTitle);
    }

    void doCheckout() throws JSONException {
        stringDetails = "[{";
        for(int i = 0; i<cartList.size();i++){
            if(i!=0)
                stringDetails += ",{";
            stringDetails += "\"product_id\":\"" + cartList.get(i).getProduct_id() + "\",";
            stringDetails += "\"quantity\":\"" + cartList.get(i).getQuantity() + "\",";
            stringDetails += "\"size\":\"" + cartList.get(i).getSize() + "\"";
            stringDetails += "}";
        }
        stringDetails += "]";

        StringRequest addCartReq = new StringRequest(Request.Method.POST, ORDER_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getContext(),"Compra realizada", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), R.string.commsErrorText + " " + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id", User.getInstance().getUserID()+"");
                params.put("seller_id",sellerID+"");
                params.put("details", stringDetails);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(addCartReq);

        StringRequest deleteCartReq = new StringRequest(Request.Method.DELETE, CART_API + "?user_id=" + User.getInstance().getUserID(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Fragment fragment = new ProductsFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, fragment);
                        transaction.commit();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), R.string.commsErrorText + " " + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(deleteCartReq);
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
