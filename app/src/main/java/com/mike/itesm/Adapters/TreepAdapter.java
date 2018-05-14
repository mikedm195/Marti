package com.mike.itesm.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mike.itesm.Objects.Treep;
import com.mike.itesm.marti.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TreepAdapter extends RecyclerView.Adapter<TreepAdapter.ViewHolder>{

    ArrayList<Treep> mDataSet = null;

    public TreepAdapter(ArrayList<Treep> treeps)
    {
        mDataSet = treeps;
    }

    @Override
    public TreepAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View childView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.treep_detail, parent, false);
        return new ViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(TreepAdapter.ViewHolder holder,int position) {
        Treep data = mDataSet.get(position);

        String destiny = data.getDestiny();
        String description = data.getDescription();
        String imageURL = data.getPhoto();
        String price = data.getPrice();

        holder.destinyTxt.setText(destiny);
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
        holder.photo.setImageBitmap(bmp);
        holder.descriptionTxt.setText(description);

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView destinyTxt, descriptionTxt, photoTxt, priceTxt;
        ImageView photo;
        CardView card;

        public ViewHolder(final View viewItem) {
            super(viewItem);
            photo = (ImageView) viewItem.findViewById(R.id.thumbnailCart);
            card = (CardView)  viewItem.findViewById(R.id.shoppingcart_card);
            destinyTxt = (TextView) viewItem.findViewById(R.id.destinyText);
            descriptionTxt = (TextView) viewItem.findViewById(R.id.descriptionText);
            priceTxt = (TextView) viewItem.findViewById(R.id.priceCartText);

        }

    }
}

