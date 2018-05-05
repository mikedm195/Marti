package com.mike.itesm.Fragments.User.User;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.mike.itesm.Objects.User;
import com.mike.itesm.marti.R;

import org.json.JSONException;
import org.json.JSONObject;

import static com.mike.itesm.Services.Services.USER_PROFILE;

public class UserProfileFragment extends Fragment {

    TextView firstnameTxt, emailTxt, addressTxt, genderText, heightText, weightText, nameTxt;
    String firstname, lastname, email, address;
    Integer weight;
    Button editProfileBtn;

    public static UserProfileFragment newInstance() {
        UserProfileFragment fragment = new UserProfileFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        view.setBackgroundResource(R.color.white);

        nameTxt = (TextView)view.findViewById(R.id.nameProfileText);
        emailTxt  = (TextView)view.findViewById(R.id.emailProfileText);
        addressTxt = (TextView)view.findViewById(R.id.addressProfileText);
        weightText  = (TextView)view.findViewById(R.id.weightProfileText);
        editProfileBtn = (Button)view.findViewById(R.id.editProfileButton);

        final ProgressDialog progress_bar = new ProgressDialog(getContext());
        progress_bar.setMessage(getContext().getString(R.string.loadingDataText));
        progress_bar.setCancelable(false);
        progress_bar.show();

        StringRequest userProfileReq = new StringRequest(Request.Method.GET, USER_PROFILE + "?id=" + User.getInstance().getUserID(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress_bar.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            if (!res.getString("user_id").equals("-1"))
                            {
                                JSONObject userDetails = res.getJSONObject("user_data");

                                firstname = userDetails.getString("first_name");
                                lastname = userDetails.getString("last_name");
                                email = userDetails.getString("email");
                                address = userDetails.getString("address");
                                //heightText = userDetails.getInt("height");

                                nameTxt.setText(firstname + " " + lastname);
                                emailTxt.setText(email);
                                addressTxt.setText(address);
                                weightText.setText("" + weight);

                                User.getInstance().setFirstName(firstname);
                                User.getInstance().setLastName(lastname);
                                User.getInstance().setEmail(email);
                                User.getInstance().setAddress(address);
                                User.getInstance().setWeight(weight);


                            }  else {
                                Toast.makeText(getContext(), R.string.unknownResponseText , Toast.LENGTH_SHORT).show();
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
                        progress_bar.cancel();
                        Toast.makeText(getContext(), R.string.commsErrorText + " " + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(getContext()).add(userProfileReq);


        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new EditUserProfileFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
