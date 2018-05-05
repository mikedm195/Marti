package com.mike.itesm.Fragments.User.User;

import android.app.ProgressDialog;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mike.itesm.Objects.User;
import com.mike.itesm.marti.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.mike.itesm.Services.Services.USER_PROFILE;

public class EditUserProfileFragment extends Fragment {

    ProgressDialog progress_bar;
    EditText firstnameTxt, lastnameTxt, emailTxt, passwordOneTxt, passwordTwoText, addressTxt, genderText, heightText, weightText;
    Button editProfileDoBtn;

    public static EditUserProfileFragment newInstance() {
        EditUserProfileFragment fragment = new EditUserProfileFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_user_profile, container, false);
        view.setBackgroundResource(R.color.white);

        firstnameTxt = (EditText)view.findViewById(R.id.firstNameTextField);
        lastnameTxt = (EditText)view.findViewById(R.id.lastNameTextField);
        addressTxt = (EditText)view.findViewById(R.id.addressTextField);
        emailTxt = (EditText)view.findViewById(R.id.emailTextFieldSignup);
        passwordOneTxt = (EditText)view.findViewById(R.id.passwordOneTextField);
        passwordTwoText = (EditText)view.findViewById(R.id.passwordTwoTextField);
        genderText = (EditText)view.findViewById(R.id.genderTextField);
        heightText = (EditText)view.findViewById(R.id.heightTextField);
        weightText = (EditText)view.findViewById(R.id.weightTextField);
        editProfileDoBtn = (Button)view.findViewById(R.id.editProfileDoButton);

        editProfileDoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUpdate();
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void doUpdate() {

        progress_bar = new ProgressDialog(getContext());
        progress_bar.setMessage(getContext().getString(R.string.loadingDataText));
        progress_bar.setCancelable(false);
        progress_bar.show();

        StringRequest editProfileReq = new StringRequest(Request.Method.POST, USER_PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress_bar.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            if (res.getString("code").equals("01"))
                            {

                                User.getInstance().setFirstName(firstnameTxt.getText().toString());
                                User.getInstance().setLastName(lastnameTxt.getText().toString());
                                User.getInstance().setEmail(emailTxt.getText().toString());
                                User.getInstance().setAddress(addressTxt.getText().toString());
                                User.getInstance().setGender(Integer.parseInt(genderText.getText().toString()));
                                User.getInstance().setHeight(Integer.parseInt(heightText.getText().toString()));
                                User.getInstance().setWeight(Integer.parseInt(weightText.getText().toString()));

                                Toast.makeText(getContext(), R.string.profileEditedSuccessText , Toast.LENGTH_SHORT).show();

                                Fragment newFragment = new UserProfileFragment();
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.frame_layout, newFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();

                            }  else {
                                Toast.makeText(getContext(), R.string.unknownResponseText , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Error! " + e.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress_bar.cancel();
                        Toast.makeText(getContext(), R.string.commsErrorText + " " + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id", "" + User.getInstance().getUserID());
                params.put("first_name",firstnameTxt.getText().toString());
                params.put("last_name",lastnameTxt.getText().toString());
                params.put("email",emailTxt.getText().toString());
                params.put("address",addressTxt.getText().toString());
                params.put("gender",genderText.getText().toString());
                params.put("height",heightText.getText().toString());
                params.put("weight",weightText.getText().toString());

                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(editProfileReq);
    }

}
