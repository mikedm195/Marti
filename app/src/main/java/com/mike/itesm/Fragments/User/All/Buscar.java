package com.mike.itesm.Fragments.User.All;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mike.itesm.Adapters.ProductsAdapter;
import com.mike.itesm.Objects.Category;
import com.mike.itesm.Objects.Product;
import com.mike.itesm.Objects.Seller;
import com.mike.itesm.Utilities.ParserJSONProducts;
import com.mike.itesm.marti.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.mike.itesm.Services.Services.CATEGORY_LIST_API;
import static com.mike.itesm.Services.Services.COLOR_LIST_API;
import static com.mike.itesm.Services.Services.AGE_LIST_API;
import static com.mike.itesm.Services.Services.PRODUCTS_LIST_API;

public class Buscar extends Fragment {

    private Spinner ageSpinner;
    ArrayAdapter<String> ageAdapter;
    private ArrayList<String> ageList = new ArrayList<String>();
    private String selectedAge;

    private Spinner categorySpinner;
    ArrayAdapter<Category> categoryAdapter;
    private ArrayList<Category> categoryList = new ArrayList<Category>();
    private String selectedCategory;

    private Spinner colorSpinner;
    ArrayAdapter<String> colorAdapter;
    private ArrayList<String> colorList = new ArrayList<String>();
    private String selectedColor;

    Button buscarBtn;

    private RecyclerView recyclerView;
    private ArrayList<Product> productList;
    private ProductsAdapter productAdapter;

    public Buscar() {
        // Required empty public constructor
    }

    public static Buscar newInstance(String param1, String param2) {
        Buscar fragment = new Buscar();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscar, container, false);
        view.setBackgroundResource(R.color.white);

        buscarBtn = (Button) view.findViewById(R.id.BuscarButton);
        ageSpinner = (Spinner) view.findViewById(R.id.AgeSpinner);
        categorySpinner = (Spinner) view.findViewById(R.id.CategorySpinner);
        colorSpinner = (Spinner) view.findViewById(R.id.ColorSpinner);

        recyclerView = (RecyclerView) view.findViewById(R.id.products_recycler);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        productList = new ArrayList<>();
        productAdapter = new ProductsAdapter(getContext(),productList);
        recyclerView.setAdapter(productAdapter);

        StringRequest categoryReq = new StringRequest(Request.Method.GET, CATEGORY_LIST_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if(!response.equals("")) {
                                JSONArray categories = new JSONArray(response);
                                categoryList.add(new Category());
                                for(int i = 0; i<categories.length();i++) {
                                    JSONObject category = categories.getJSONObject(i);
                                    Category categoryObject = new Category(
                                            category.getInt("category_id"),
                                            category.getString("name")
                                    );
                                    categoryList.add(categoryObject);
                                }
                                categoryAdapter = new ArrayAdapter<Category> (getContext(), android.R.layout.simple_list_item_1, categoryList);
                                categorySpinner.setAdapter(categoryAdapter);

                                categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        Category c = categoryAdapter.getItem(i);
                                        if(c.getId() != 0)
                                            selectedCategory = String.valueOf(c.getId());
                                        else
                                            selectedCategory = "";
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        Volley.newRequestQueue(getContext()).add(categoryReq);

        StringRequest colorReq = new StringRequest(Request.Method.GET, COLOR_LIST_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if(!response.equals("")) {
                                JSONArray colors = new JSONArray(response);
                                colorList.add("");
                                for(int i = 0; i<colors.length();i++) {
                                    JSONObject color = colors.getJSONObject(i);
                                    colorList.add(color.getString("color"));
                                }
                                colorAdapter = new ArrayAdapter<String> (getContext(), android.R.layout.simple_list_item_1, colorList);
                                colorSpinner.setAdapter(colorAdapter);

                                colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        selectedColor= colorAdapter.getItem(i);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        Volley.newRequestQueue(getContext()).add(colorReq);

        StringRequest ageReq = new StringRequest(Request.Method.GET, AGE_LIST_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if(!response.equals("")) {
                                JSONArray ages = new JSONArray(response);
                                ageList.add("");
                                for(int i = 0; i<ages.length();i++) {
                                    JSONObject age = ages.getJSONObject(i);
                                    ageList.add(age.getString("age"));
                                }
                                ageAdapter = new ArrayAdapter<String> (getContext(), android.R.layout.simple_list_item_1, ageList);
                                ageSpinner.setAdapter(ageAdapter);

                                ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        selectedAge = ageAdapter.getItem(i);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        Volley.newRequestQueue(getContext()).add(ageReq);

        buscarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarProductos();
            }
        });

        return view;
    }

    void buscarProductos() {
        StringRequest productsReq = new StringRequest(Request.Method.GET, PRODUCTS_LIST_API + "?category_id=" + selectedCategory + "&color=" + selectedColor + "&age=" + selectedAge,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray products = new JSONArray(response);

                            productList = ParserJSONProducts.parseaArreglo(products);

                            productAdapter = new ProductsAdapter(getContext(), productList);
                            recyclerView.setAdapter(productAdapter);
                            productAdapter.notifyDataSetChanged();


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
                });
        Volley.newRequestQueue(getContext()).add(productsReq);

    }

}
