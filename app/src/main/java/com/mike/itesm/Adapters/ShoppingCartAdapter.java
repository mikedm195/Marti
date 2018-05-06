package com.mike.itesm.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.mike.itesm.Fragments.User.All.ProductDetailFragment;
import com.mike.itesm.Objects.Product;
import com.mike.itesm.Objects.ShoppingCart;
import com.mike.itesm.marti.R;
import com.mike.itesm.Services.AppController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>{

    ArrayList<ShoppingCart> list = null;
    Context context = null;

    public ShoppingCartAdapter(Context context, ArrayList<ShoppingCart> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ShoppingCartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View childView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.shoppingcart_card, parent, false);
        return new ViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(ShoppingCartAdapter.ViewHolder holder,int position) {
        String name = list.get(position).getProduct().getName();
        String imageURL = list.get(position).getProduct().getPhoto();
        String color = list.get(position).getProduct().getColor();
        Double price = list.get(position).getProduct().getPrice();
        double size = list.get(position).getSize();
        String age = list.get(position).getProduct().getAge();

        holder.nameTxt.setText(name);
        //holder.brandTxt.setText(brand);
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
        holder.image.setImageBitmap(bmp);
        holder.sizeTxt.setText("Size: " + size);
        holder.colorTxt.setText("Color: "+color);
        holder.ageTxt.setText("Age: "+age);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTxt, priceTxt, colorTxt, sizeTxt, ageTxt;
        ImageView image;
        CardView card;

        public ViewHolder(final View viewItem) {
            super(viewItem);
            image = (ImageView) viewItem.findViewById(R.id.thumbnailCart);
            card = (CardView)  viewItem.findViewById(R.id.shoppingcart_card);
            nameTxt = (TextView) viewItem.findViewById(R.id.nameCartText);
            priceTxt = (TextView) viewItem.findViewById(R.id.priceCartText);
            colorTxt = (TextView) viewItem.findViewById(R.id.colorCartText);
            ageTxt = (TextView) viewItem.findViewById(R.id.ageCartText);
            sizeTxt = (TextView) viewItem.findViewById(R.id.sizeCartText);

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle id = new Bundle();
                    id.putString("product_id", String.valueOf(list.get(getAdapterPosition()).getProduct_id()));
                    Fragment newFragment = new ProductDetailFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                    newFragment.setArguments(id);
                    transaction.replace(R.id.frame_layout, newFragment);
                    transaction.addToBackStack(null);
                    // Commit the transaction
                    transaction.commit();
                }
            });

        }

    }
}
