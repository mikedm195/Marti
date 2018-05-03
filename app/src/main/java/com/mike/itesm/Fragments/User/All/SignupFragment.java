package com.mike.itesm.Fragments.User.All;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.mike.itesm.marti.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.mike.itesm.Services.Services.SIGNUP_API;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {

    Button signupBtn;
    EditText firstnameTxt, lastnameTxt, emailTxt, passwordOneTxt, passwordTwoText, addressTxt, genderText, heightText, weightText;

    public SignupFragment() {
        // Required empty public constructor
    }

    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
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

        signupBtn = (Button)view.findViewById(R.id.signupButton);
        firstnameTxt = (EditText)view.findViewById(R.id.firstNameTextField);
        lastnameTxt = (EditText)view.findViewById(R.id.lastNameTextField);
        addressTxt = (EditText)view.findViewById(R.id.addressTextField);
        emailTxt = (EditText)view.findViewById(R.id.emailTextFieldSignup);
        passwordOneTxt = (EditText)view.findViewById(R.id.passwordOneTextField);
        passwordTwoText = (EditText)view.findViewById(R.id.passwordTwoTextField);
        genderText = (EditText)view.findViewById(R.id.genderTextField);
        heightText = (EditText)view.findViewById(R.id.heightTextField);
        weightText = (EditText)view.findViewById(R.id.weightTextField);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passwordOneTxt.getText().toString().equals(passwordTwoText.getText().toString()))
                {
                    signup();
                } else {
                    Toast.makeText(getContext(), R.string.passwordsNotMatchText, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void signup()
    {

        StringRequest signupReq = new StringRequest(Request.Method.POST, SIGNUP_API,
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
                params.put("first_name",firstnameTxt.getText().toString());
                params.put("last_name",lastnameTxt.getText().toString());
                params.put("email",emailTxt.getText().toString());
                params.put("password",passwordOneTxt.getText().toString());
                params.put("address",addressTxt.getText().toString());
                params.put("gender",genderText.getText().toString());
                params.put("weight",weightText.getText().toString());
                params.put("height",heightText.getText().toString());
                params.put("rol","0");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(signupReq);
    }

}
