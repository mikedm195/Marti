package com.mike.itesm.Fragments.User.DateCRUD;

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

import static com.mike.itesm.Services.Services.DATE_API;
import static com.mike.itesm.Services.Services.SIGNUP_API;


public class CreateDateFragment extends Fragment {
    Button createDateBtn;
    EditText leaving_dateTxt, return_dateTxt, transportationTxt, durationTxt;
   public CreateDateFragment() {
        // Required empty public constructor
    }

    public static CreateDateFragment newInstance(String param1, String param2) {
        CreateDateFragment fragment = new CreateDateFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_date, container, false);
        view.setBackgroundResource(R.color.white);

        createDateBtn = (Button)view.findViewById(R.id.signupButton);
        leaving_dateTxt = (EditText)view.findViewById(R.id.firstNameTextField);
        return_dateTxt = (EditText)view.findViewById(R.id.lastNameTextField);
        transportationTxt = (EditText)view.findViewById(R.id.lastSecondNameTextField);
        durationTxt = (EditText)view.findViewById(R.id.contactTextField);

        createDateBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            createdate();
        }
    });

        return view;
}

    private void createdate()
    {
        StringRequest signupReq = new StringRequest(Request.Method.POST, DATE_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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
                params.put("name",leaving_dateTxt.getText().toString());
                params.put("last_name",return_dateTxt.getText().toString());
                params.put("last_second_name",transportationTxt.getText().toString());
                params.put("contact",durationTxt.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(signupReq);
    }



}
