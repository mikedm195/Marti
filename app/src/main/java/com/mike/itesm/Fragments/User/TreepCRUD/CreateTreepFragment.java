package com.mike.itesm.Fragments.User.TreepCRUD;

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
import com.mike.itesm.Fragments.User.ClientCRUD.LoginFragment;
import com.mike.itesm.marti.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.mike.itesm.Services.Services.SIGNUP_API;
import static com.mike.itesm.Services.Services.TREEP_API;


public class CreateTreepFragment extends Fragment {
    Button createTreepBtn;
    EditText priceTxt, destiny_nameTxt, descriptionTxt, photoText;


    public CreateTreepFragment() {
        // Required empty public constructor
    }

    public static CreateTreepFragment newInstance(String param1, String param2) {
        CreateTreepFragment fragment = new CreateTreepFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_treep, container, false);
        view.setBackgroundResource(R.color.white);

        createTreepBtn = (Button)view.findViewById(R.id.createTreepButton);
        priceTxt = (EditText)view.findViewById(R.id.priceTextField);
        destiny_nameTxt = (EditText)view.findViewById(R.id.destinyTextField);
        descriptionTxt = (EditText)view.findViewById(R.id.descriptionTextField);
        photoText = (EditText)view.findViewById(R.id.photoTextField);

        createTreepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTreep();
            }
        });

        return view;
    }

    private void createTreep()
    {
        StringRequest createTreepReq = new StringRequest(Request.Method.POST, TREEP_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), "Viaje agregado" , Toast.LENGTH_SHORT).show();
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
                params.put("price",priceTxt.getText().toString());
                params.put("destiny",destiny_nameTxt.getText().toString());
                params.put("description",descriptionTxt.getText().toString());
                params.put("photo",photoText.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(createTreepReq);
    }
}
