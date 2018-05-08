package com.mike.itesm.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mike.itesm.Fragments.User.Admin.ProductDetailAdminFragment;
import com.mike.itesm.Fragments.User.All.ProductDetailFragment;
import com.mike.itesm.Objects.Product;
import com.mike.itesm.Objects.User;
import com.mike.itesm.marti.R;
import com.mike.itesm.Services.AppController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder>{

    ArrayList<Product> list = null;
    Context context = null;

    public ProductsAdapter(Context context, ArrayList<Product> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View childView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false);
        return new ViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(ProductsAdapter.ViewHolder holder,int position) {
        String name = list.get(position).getName();
        String imageURL = list.get(position).getPhoto();
        String color = list.get(position).getColor();
        Double price = list.get(position).getPrice();

        holder.nameTxt.setText(name);
        holder.brandTxt.setText(color);
        holder.priceTxt.setText("$" + price.toString());
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
        holder.imageURL.setImageBitmap(bmp);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTxt, brandTxt, priceTxt;
        ImageView imageURL;
        CardView card;

        public ViewHolder(final View viewItem) {
            super(viewItem);
            imageURL = (ImageView) viewItem.findViewById(R.id.thumbnail);
            card = (CardView)  viewItem.findViewById(R.id.product_card);
            nameTxt = (TextView) viewItem.findViewById(R.id.nameText);
            brandTxt = (TextView) viewItem.findViewById(R.id.brandText);
            priceTxt = (TextView) viewItem.findViewById(R.id.priceText);

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle id = new Bundle();
                    id.putString("product_id", String.valueOf(list.get(getAdapterPosition()).getProduct_id()));

                    if(User.getInstance().getRole() == 1) {
                        Fragment newFragment = new ProductDetailAdminFragment();
                        FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                        newFragment.setArguments(id);
                        transaction.replace(R.id.frame_layout, newFragment);
                        transaction.addToBackStack(null);
                        // Commit the transaction
                        transaction.commit();
                    } else {
                        Fragment newFragment = new ProductDetailFragment();
                        FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                        newFragment.setArguments(id);
                        transaction.replace(R.id.frame_layout, newFragment);
                        transaction.addToBackStack(null);
                        // Commit the transaction
                        transaction.commit();
                    }
                }
            });

        }

    }
}
