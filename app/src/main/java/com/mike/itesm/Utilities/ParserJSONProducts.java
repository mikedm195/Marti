package com.mike.itesm.Utilities;

import android.util.Log;

import com.mike.itesm.Objects.Product;
import com.mike.itesm.marti.R;
import com.mike.itesm.Services.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParserJSONProducts {

    public static ArrayList<Product> _array_products = new ArrayList<>();

    public static Product paseaObjeto(JSONObject obj) {

        try {
            Product product = new Product();

            product.setProduct_id(obj.getInt("product_id"));
            product.setCategory_id(obj.getInt("category_id"));
            product.setName(obj.getString("name"));
            product.setPhoto(obj.getString("photo"));
            product.setVideo(obj.getString("video"));
            product.setPrice(obj.getDouble("price"));
            product.setColor(obj.getString("color"));
            product.setAge(obj.getString("age"));
            return product;

        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Product> parseaArreglo(JSONArray arr) {

        JSONObject obj = null;
        Product product = null;
        _array_products.clear();

        try {
            for(int i = 0;i<arr.length();i++) {

                obj = arr.getJSONObject(i);
                product = new Product();

                product.setProduct_id(obj.getInt("product_id"));
                product.setCategory_id(obj.getInt("category_id"));
                product.setName(obj.getString("name"));
                product.setPhoto(obj.getString("photo"));
                product.setVideo(obj.getString("video"));
                product.setPrice(obj.getDouble("price"));
                product.setColor(obj.getString("color"));
                product.setAge(obj.getString("age"));

                _array_products.add(product);
            }
            return _array_products;

        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Product> parseaArregloOrderProducts(JSONArray arr) {

        JSONObject obj = null;
        Product product = null;
        _array_products.clear();

        try {
            for(int i = 0;i<arr.length();i++) {

                obj = arr.getJSONObject(i);
                product = new Product();

                product.setProduct_id(obj.getInt("product_id"));
                product.setCategory_id(obj.getInt("category_id"));
                product.setName(obj.getString("name"));
                product.setPhoto(obj.getString("photo"));
                product.setVideo(obj.getString("video"));
                product.setPrice(obj.getDouble("price"));
                product.setColor(obj.getString("color"));
                product.setAge(obj.getString("age"));

                _array_products.add(product);
            }
            return _array_products;

        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String> stringArray(JSONArray arr){

        JSONObject obj=null;
        ArrayList<String> res = new ArrayList<>();

        try {
            for(int i = 0;i<arr.length();i++) {
                obj = arr.getJSONObject(i);

                res.add(obj.getString("nombre"));
            }
            Log.i("Debug note: ", "returned " + res.toString());
            return res;

        } catch (JSONException e1) {
            Log.e("Error: ", "returned null");
            e1.printStackTrace();
            return null;
        }

    }
}
