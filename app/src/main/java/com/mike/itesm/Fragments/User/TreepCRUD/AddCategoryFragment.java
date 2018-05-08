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

import static com.mike.itesm.Services.Services.CATEGORY_API;

public class AddCategoryFragment extends Fragment {
    Button AddCategory, DeleteCategory;
    EditText CategoryName;

    private String category_Id;

    Bundle myIntent;

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
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);
        view.setBackgroundResource(R.color.white);

        myIntent = this.getArguments();

        AddCategory = (Button)view.findViewById(R.id.AddCategoryButton);
        DeleteCategory = (Button)view.findViewById(R.id.DeleteCategoryButton);
        CategoryName = (EditText)view.findViewById(R.id.categoryNameTextField);

        if(myIntent != null) {
            DeleteCategory.setVisibility(View.VISIBLE);
            AddCategory.setText("Actualizar categoría");
            category_Id = myIntent.getString("category_id");
            getCategory();
        }

        AddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myIntent == null)
                {
                    addCategory();
                } else {
                    updateCategory();
                }
            }
        });

        DeleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCategory();
            }
        });

        return view;
    }

    private void getCategory()
    {

        StringRequest getcategoryreq = new StringRequest(Request.Method.GET, CATEGORY_API + "?category_id=" + category_Id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);
                            CategoryName.setText(res.getString("name"));
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
        requestQueue.add(getcategoryreq);
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
                                Fragment newFragment = new CategoryFragment();
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
                params.put("name",CategoryName.getText().toString());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(addcategoryreq);
    }

    private void updateCategory()
    {
        StringRequest addcategoryreq = new StringRequest(Request.Method.PUT, CATEGORY_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Fragment newFragment = new CategoryFragment();
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
                params.put("category_id",category_Id);
                params.put("name",CategoryName.getText().toString());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(addcategoryreq);
    }

    private void deleteCategory()
    {
        StringRequest deletecategoryreq = new StringRequest(Request.Method.DELETE, CATEGORY_API + "?category_id=" + category_Id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Fragment newFragment = new CategoryFragment();
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
