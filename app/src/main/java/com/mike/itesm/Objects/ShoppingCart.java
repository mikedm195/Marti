package com.mike.itesm.Objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mike.itesm.Objects.Product;
/**
 * Created by mike on 03/05/18.
 */

public class ShoppingCart {
    private int user_id;
    private int product_id;
    private int quantity;
    private String color;
    private int size;
    private Product product;

    public ShoppingCart(){}

    public ShoppingCart(int user_id, int product_id, int quantity, String color, int size, Product product) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.color = color;
        this.size = size;
        this.product = product;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
