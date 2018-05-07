package com.mike.itesm.Fragments.User.Admin;

import android.content.Context;
import android.net.Uri;
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
import com.mike.itesm.Fragments.User.All.LoginFragment;
import com.mike.itesm.marti.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.mike.itesm.Services.Services.SELLER_API;
import static com.mike.itesm.Services.Services.SIGNUP_API;

public class AddSellerFragment extends Fragment {



    Button AddSeller;
    EditText SellerName;

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
            View view = inflater.inflate(R.layout.fragment_signup, container, false);
            view.setBackgroundResource(R.color.white);

            AddSeller = (Button)view.findViewById(R.id.AddSellerButton);
            SellerName = (EditText)view.findViewById(R.id.sellerNameTextField);

            AddSeller.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (true)
                    {
                        addSeller();
                    } else {
                        Toast.makeText(getContext(), R.string.errorText, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        return view;
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
                                Toast.makeText(getContext(), "Agregado exitosamente" , Toast.LENGTH_SHORT).show();
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
}
