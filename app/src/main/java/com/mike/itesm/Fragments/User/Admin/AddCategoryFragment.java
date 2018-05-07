package com.mike.itesm.Fragments.User.Admin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import static com.mike.itesm.Services.Services.CATEGORY_API;
import static com.mike.itesm.Services.Services.SELLER_API;

public class AddCategoryFragment extends Fragment {
    Button AddCategory;
    EditText CategoryName;
    public AddCategoryFragment() {
        // Required empty public constructor
    }

    public static AddCategoryFragment newInstance(String param1, String param2) {
        AddCategoryFragment fragment = new AddCategoryFragment();
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

        AddCategory = (Button)view.findViewById(R.id.AddCategoryButton);
        CategoryName = (EditText)view.findViewById(R.id.categoryNameTextField);
        AddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (true)
                {
                    addCategory();
                } else {
                    Toast.makeText(getContext(), R.string.errorText, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    private void addCategory()
    {

        StringRequest addcategoryreq = new StringRequest(Request.Method.POST, CATEGORY_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);
                            if(res.getString("category_id").equals("-1"))
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
                params.put("name",CategoryName.getText().toString());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(addcategoryreq);
    }

}
