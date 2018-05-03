package com.mike.itesm.Objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mike on 03/05/18.
 */

public class ShoppingCart {
    private double shoppingCartTotal;
    private int orderID;
    Map<String,Integer> shoppingCartProducts = new HashMap<String,Integer>();
    public ArrayList<Product> shoppingCartArray = new ArrayList<Product>();

    public int getOrderID() { return orderID; }

    public void setOrderID(int orderID) { this.orderID = orderID; }

    public Double getShoppingCartTotal() {
        return shoppingCartTotal;
    }

    public void setShoppingCartTotal(Double val) {
        shoppingCartTotal = val;
    }

    private static final ShoppingCart holder = new ShoppingCart();

    public static ShoppingCart getInstance() {
        return holder;
    }
}
