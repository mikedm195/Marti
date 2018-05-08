package com.mike.itesm.Fragments.User.ClientCRUD;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
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
import com.mike.itesm.Fragments.User.User.ProductsFragment;
import com.mike.itesm.marti.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.mike.itesm.Services.Services.PRODUCTS_API;
import static com.mike.itesm.Services.Services.SIGNUP_API;

public class UpdateClient extends Fragment {
    Button signupBtn, deleteBtn;
    EditText firstnameTxt, lastnameTxt, lastSecondnameTxt, contactTxt, photoText;

    public UpdateClient() {
        // Required empty public constructor
    }

    public static UpdateClient newInstance(String param1, String param2) {
        UpdateClient fragment = new UpdateClient();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        view.setBackgroundResource(R.color.white);

        signupBtn = (Button)view.findViewById(R.id.editProdAdminDetailButton);
        deleteBtn = (Button) view.findViewById(R.id.deleteProdAdminDetailButton);
        firstnameTxt = (EditText)view.findViewById(R.id.firstNameTextField);
        lastnameTxt = (EditText)view.findViewById(R.id.lastNameTextField);
        lastSecondnameTxt = (EditText)view.findViewById(R.id.lastSecondNameTextField);
        contactTxt = (EditText)view.findViewById(R.id.contactTextField);
        photoText = (EditText)view.findViewById(R.id.photoTextField);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateClient();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteProduct();
            }
        });

        return view;
    }

    void confirmDeleteProduct() {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Product?")
                .setMessage("Are you sure you want to delete this product? This action can't be undone")
                .setNegativeButton(android.R.string.cancel, null) // dismisses by default
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doProductDelete();
                    }
                })
                .create()
                .show();
    }
    private void UpdateClient()
    {
        StringRequest updateReq = new StringRequest(Request.Method.PUT, SIGNUP_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);
                            if(res.getString("user_id").equals("-1"))
                            {
                                Toast.makeText(getContext(), R.string.queryErrorText , Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(getContext(), R.string.signedupText , Toast.LENGTH_SHORT).show();
                                Fragment fragment = new LoginFragment();
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.frame_layout, fragment);
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
                params.put("name",firstnameTxt.getText().toString());
                params.put("last_name",lastnameTxt.getText().toString());
                params.put("last_second_name",lastSecondnameTxt.getText().toString());
                params.put("contact",contactTxt.getText().toString());
                params.put("photo",photoText.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(updateReq);
    }
    void doProductDelete() {

        StringRequest deleteProduct = new StringRequest(Request.Method.PUT, SIGNUP_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(getContext(), "Product Deleted", Toast.LENGTH_SHORT).show();
                            Fragment newFragment = new ProductsFragment();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout_admin, newFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Error! " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error: " + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                    }
                }) {

        };

        Volley.newRequestQueue(getContext()).add(deleteProduct);
    }
}
