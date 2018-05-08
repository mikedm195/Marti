package com.mike.itesm.Fragments.User.ClientCRUD;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mike.itesm.Objects.User;
import com.mike.itesm.marti.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.mike.itesm.Services.Services.PRODUCTS_API;
import static com.mike.itesm.Services.Services.CART_API;

public class ProductDetailFragment extends Fragment {

    private String productID;
    private int userID;
    private TextView nameTxt, colorTxt, descriptionTxt, priceTxt, sizeTxt;
    private String name, color, imageURL, video, age;
    private Double price;
    private Float size = 0.0f;
    private Integer id, category_id;
    private Button addToCartBtn;
    Boolean isProductInCart = false;
    ImageView photo;
    NetworkImageView image;
    SeekBar sizeBar;

    public static ProductDetailFragment newInstance() {
        ProductDetailFragment fragment = new ProductDetailFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        view.setBackgroundResource(R.color.white);

        userID = User.getInstance().getUserID();
        Toast.makeText(getContext(), userID+"", Toast.LENGTH_SHORT).show();

        final ProgressDialog progress_bar = new ProgressDialog(getContext());
        progress_bar.setMessage(getContext().getString(R.string.loadingDataText));
        progress_bar.setCancelable(false);
        progress_bar.show();

        nameTxt = (TextView) view.findViewById(R.id.nameDetailText);
        colorTxt = (TextView) view.findViewById(R.id.brandDetailText);
        descriptionTxt = (TextView) view.findViewById(R.id.descriptionDetailText);
        priceTxt = (TextView) view.findViewById(R.id.priceDetailText);
        sizeTxt = (TextView) view.findViewById(R.id.sizeText);
        addToCartBtn = (Button) view.findViewById(R.id.addToCartButton);
        photo = (ImageView) view.findViewById((R.id.photo));
        sizeBar = (SeekBar)view.findViewById(R.id.sizeBar);

        Bundle myIntent = this.getArguments();

        sizeBar.setProgress(0);
        sizeBar.incrementProgressBy(1);
        sizeBar.setMax(25);
        sizeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                size = (float)progress / 2;
                sizeTxt.setText(getContext().getString(R.string.pickSizeText) + " " + size);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        if(myIntent != null) {
            productID = myIntent.getString("product_id");
        }

        StringRequest cartReq = new StringRequest(Request.Method.GET, CART_API + "?user_id=" + userID + "&product_id="+ productID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress_bar.cancel();
                        try {
                            if(response!=null){
                                addToCartBtn.setText("REMOVE FROM CART");
                                isProductInCart = true;
                            }else{
                                addToCartBtn.setText("ADD TO CART");
                                isProductInCart = false;
                            }

                            JSONObject product = new JSONObject(response);

                        } catch (JSONException e) {
                            addToCartBtn.setText("ADD TO CART");
                            isProductInCart = false;
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
        Volley.newRequestQueue(getContext()).add(cartReq);

        if(isProductInCart){
            addToCartBtn.setText("REMOVE FROM CART");
        }else{
            addToCartBtn.setText("ADD TO CART");
        }

        StringRequest productsReq = new StringRequest(Request.Method.GET, PRODUCTS_API + "?product_id=" + productID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress_bar.cancel();
                        try {
                            JSONObject product = new JSONObject(response);
                            id = product.getInt("product_id");
                            category_id = product.getInt("category_id");
                            name = product.getString("name");
                            color = product.getString("color");
                            imageURL = product.getString("photo");
                            price = product.getDouble("price");
                            video = product.getString("video");
                            age = product.getString("age");

                            nameTxt.setText(name);
                            colorTxt.setText(color);
                            priceTxt.setText("$" + price);
                            URL url = null;
                            try {
                                url = new URL(imageURL);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                            Bitmap bmp = null;
                            try {
                                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            photo.setImageBitmap(bmp);

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
        Volley.newRequestQueue(getContext()).add(productsReq);

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(userID<=0)
                Toast.makeText(getContext(), "Please Login to start purchasing!", Toast.LENGTH_SHORT).show();
            else if(!isProductInCart)
                addToCart();
            else
                removeFromCart();

            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void addToCart() {

        StringRequest addCartReq = new StringRequest(Request.Method.POST, CART_API,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject res = new JSONObject(response);
                        if(res.getString("cart_id").equals("-1"))
                        {
                            Toast.makeText(getContext(), R.string.queryErrorText , Toast.LENGTH_SHORT).show();
                        } else {
                            isProductInCart = true;
                            addToCartBtn.setText("REMOVE FROM CART");
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
                params.put("user_id", userID+"");
                params.put("product_id",id.toString());
                params.put("quantity","1");
                params.put("size",size.toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(addCartReq);
        Toast.makeText(getContext(), "Product added to shopping cart" , Toast.LENGTH_SHORT).show();

    }

    public void removeFromCart() {

        StringRequest addCartReq = new StringRequest(Request.Method.DELETE, CART_API + "?user_id=" + userID + "&product_id="+ productID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), "Product removed from shopping cart" , Toast.LENGTH_SHORT).show();
                        isProductInCart = false;
                        addToCartBtn.setText("ADD TO CART");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), R.string.commsErrorText + " " + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id", userID+"");
                params.put("product_id",productID);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(addCartReq);

    }

}
