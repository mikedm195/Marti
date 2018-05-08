package com.mike.itesm.Utilities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mike.itesm.Fragments.User.TreepCRUD.AddInventoryAdminFragment;
import com.mike.itesm.Fragments.User.TreepCRUD.AddProductAdminFragment;
import com.mike.itesm.Fragments.User.User.ProductsFragment;
import com.mike.itesm.marti.R;

/**
 * Created by mike on 03/05/18.
 */

public class CommonClass {
    public static boolean HandleMenu(Context c, int MenuEntry) {
        Intent actividad;
        Fragment newFragment;
        FragmentTransaction transaction;

        switch (MenuEntry) {
            case R.id.action_cart:
                newFragment = new ShoppingCartFragment();
                transaction = ((AppCompatActivity)c).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.logoutMenuBtn:
                //ShoppingCart.getInstance().shoppingCartArray.clear();
                //ShoppingCart.getInstance().setShoppingCartTotal(0.0);
                newFragment = new ProductsFragment();
                transaction = ((AppCompatActivity)c).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.addProductMenuBtn:
                newFragment = new AddProductAdminFragment();
                transaction = ((AppCompatActivity)c).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout_admin, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.addInventoryMenuBtn:
                newFragment = new AddInventoryAdminFragment();
                transaction = ((AppCompatActivity)c).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout_admin, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;


        }
        return true;
    }
}
