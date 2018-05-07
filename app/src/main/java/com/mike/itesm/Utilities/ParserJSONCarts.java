package com.mike.itesm.Utilities;

import android.util.Log;

import com.mike.itesm.Objects.Product;
import com.mike.itesm.Objects.ShoppingCart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by mike on 05/05/18.
 */

public class ParserJSONCarts {
    public static ArrayList<ShoppingCart> _array_carts = new ArrayList<>();

    public static Product parseaObjeto(JSONObject obj) {

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

    public static ArrayList<ShoppingCart> parseaArreglo(JSONArray arr) {

        JSONObject obj = null;
        Product product = null;
        ShoppingCart cart = null;
        _array_carts.clear();
        try {
            for(int i = 0;i<arr.length();i++) {

                obj = arr.getJSONObject(i);

                Log.w("objeto", obj.toString(4));

                JSONObject productObject = obj.getJSONObject("product");

                product = new Product();
                cart = new ShoppingCart();
                product.setProduct_id(productObject.getInt("id"));
                product.setCategory_id(productObject.getInt("category_id"));
                product.setName(productObject.getString("name"));
                product.setPhoto(productObject.getString("photo"));
                product.setVideo(productObject.getString("video"));
                product.setPrice(productObject.getDouble("price"));
                product.setColor(productObject.getString("color"));
                product.setAge(productObject.getString("age"));

                cart.setProduct(product);
                cart.setUser_id(obj.getInt("user_id"));
                cart.setProduct_id(obj.getInt("product_id"));
                cart.setQuantity(obj.getInt("quantity"));
                cart.setSize(obj.getDouble("size"));
                _array_carts.add(cart);
            }
            return _array_carts;

        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }
}
