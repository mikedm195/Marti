package com.mike.itesm.Fragments.User.TreepCRUD;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mike.itesm.marti.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.mike.itesm.Services.Services.SELLER_API;

public class AddSellerFragment extends Fragment {

    Button AddSeller, DeleteSeller;
    EditText SellerName;

    private String seller_id;

    Bundle myIntent;

    public AddSellerFragment() {
        // Required empty public constructor
    }


    public static AddSellerFragment newInstance(String param1, String param2) {
        AddSellerFragment fragment = new AddSellerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_seller, container, false);
        view.setBackgroundResource(R.color.white);

        myIntent = this.getArguments();

        AddSeller = (Button)view.findViewById(R.id.AddSellerButton);
        DeleteSeller = (Button)view.findViewById(R.id.DeleteSellerButton);
        SellerName = (EditText)view.findViewById(R.id.sellerNameTextField);

        if(myIntent != null) {
            DeleteSeller.setVisibility(View.VISIBLE);
            AddSeller.setText("Actualizar vendedor");
            seller_id = myIntent.getString("seller_id");
            getSeller();
        }

        AddSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myIntent == null)
                {
                    addSeller();
                } else {
                    updateSeller();
                }
            }
        });

        DeleteSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSeller();
            }
        });

        return view;
    }


    private void getSeller()
    {
        StringRequest getSellerreq = new StringRequest(Request.Method.GET, SELLER_API + "?seller_id=" + seller_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);
                            SellerName.setText(res.getString("name"));
                        } catch (JSONException e) {
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

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(getSellerreq);
    }

    private void addSeller()
    {

        StringRequest addsellerreq = new StringRequest(Request.Method.POST, SELLER_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);
                            if(res.getString("seller_id").equals("-1"))
                            {
                                Toast.makeText(getContext(), R.string.queryErrorText , Toast.LENGTH_SHORT).show();
                            } else {
                                Fragment newFragment = new SellerFragment();
                                FragmentTransaction transaction = (getFragmentManager().beginTransaction());
                                transaction.replace(R.id.frame_layout, newFragment);
                                transaction.commit();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Error! " + e.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                        }
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
                params.put("name",SellerName.getText().toString());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(addsellerreq);
    }

    private void updateSeller()
    {
        StringRequest addcategoryreq = new StringRequest(Request.Method.PUT, SELLER_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Fragment newFragment = new SellerFragment();
                        FragmentTransaction transaction = (getFragmentManager().beginTransaction());
                        transaction.replace(R.id.frame_layout, newFragment);
                        transaction.commit();
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
                params.put("seller_id",seller_id);
                params.put("name",SellerName.getText().toString());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(addcategoryreq);
    }

    private void deleteSeller()
    {
        StringRequest deletecategoryreq = new StringRequest(Request.Method.DELETE, SELLER_API + "?seller_id=" + seller_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Fragment newFragment = new SellerFragment();
                        FragmentTransaction transaction = (getFragmentManager().beginTransaction());
                        transaction.replace(R.id.frame_layout, newFragment);
                        transaction.commit();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), R.string.commsErrorText + " " + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(deletecategoryreq);
    }
}
