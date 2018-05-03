package com.mike.itesm.Fragments.User.All;

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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mike.itesm.Objects.Product;
import com.mike.itesm.Objects.ShoppingCart;
import com.mike.itesm.marti.R;
import com.mike.itesm.Services.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static com.mike.itesm.Services.Services.PRODUCTS_API;

public class ProductDetailFragment extends Fragment {

    private String productID;
    private TextView nameTxt, colorTxt, descriptionTxt, priceTxt, sizeTxt;
    private String name, color, description, imageURL;
    private Double price;
    private Float size = 0.0f;
    private Integer id;
    private Button addToCartBtn;
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

        Toast.makeText(getContext(), PRODUCTS_API + "?product_id=" + productID, Toast.LENGTH_SHORT).show();
        StringRequest productsReq = new StringRequest(Request.Method.GET, PRODUCTS_API + "?product_id=" + productID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress_bar.cancel();
                        try {
                            JSONObject product = new JSONObject(response);

                            name = product.getString("name");
                            color = product.getString("color");
                            imageURL = product.getString("photo");
                            price = product.getDouble("price");

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
                if (size > 5) {
                    addToCart();
                } else {
                    Toast.makeText(getContext(), R.string.sizeErrorText, Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void addToCart() {
        Product thisProduct = new Product();
        thisProduct.setProduct_id(id);
        thisProduct.setName(name);
        thisProduct.setPrice(price);
        thisProduct.setColor(color);
        //thisProduct.setAge(age);
        thisProduct.setPhoto(imageURL);
        //thisProduct.setSize(size);
        ShoppingCart.getInstance().setShoppingCartTotal(ShoppingCart.getInstance().getShoppingCartTotal() + price);
        ShoppingCart.getInstance().shoppingCartArray.add(thisProduct);
        Toast.makeText(getContext(), getContext().getString(R.string.productAddedToCartText) + " " + size , Toast.LENGTH_SHORT).show();
    }

}
