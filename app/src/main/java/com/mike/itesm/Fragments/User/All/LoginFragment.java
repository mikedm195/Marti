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
import com.mike.itesm.Activities.Admin.MainActivityAdmin;
import com.mike.itesm.Activities.LoginActivity;
import com.mike.itesm.Activities.SignupActivity;
import com.mike.itesm.Fragments.User.User.ProductsFragment;
import com.mike.itesm.Objects.User;
import com.mike.itesm.marti.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.mike.itesm.Services.Services.LOGIN_API;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    Button loginBtn, signupBtn;
    EditText emailTxt, passwordTxt;
    private User userData;

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        view.setBackgroundResource(R.color.white);

        loginBtn = (Button)view.findViewById(R.id.loginButton);
        signupBtn = (Button)view.findViewById(R.id.signupButton);
        emailTxt = (EditText)view.findViewById(R.id.emailTextField);
        passwordTxt = (EditText)view.findViewById(R.id.passwordTextField);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        return view;
    }

    private void signup(){
        Fragment fragment = new SignupFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
    }

    private void login()
    {

        StringRequest loginReq = new StringRequest(Request.Method.GET, LOGIN_API+"?email="+emailTxt.getText()+"&password="+passwordTxt.getText(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);
                            if (!res.getString("user_id").equals("-1"))
                            {
                                Toast.makeText(getContext(), R.string.welcomeText , Toast.LENGTH_SHORT).show();
                                userData.getInstance().setUserID(res.getInt("user_id"));
                                userData.getInstance().setRole(res.getInt("rol"));

                                if(res.getString("rol").equals("0")) {
                                    Fragment fragment = new ProductsFragment();
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    transaction.replace(R.id.frame_layout, fragment);
                                    transaction.commit();
                                }

                            } else {
                                Toast.makeText(getContext(), "Invalid email/password" , Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getContext(), R.string.commsErrorText + " " + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("email",emailTxt.getText().toString());
                map.put("password",passwordTxt.getText().toString());
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(loginReq);
    }

}
