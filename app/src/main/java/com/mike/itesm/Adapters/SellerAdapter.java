package com.mike.itesm.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mike.itesm.Fragments.User.Admin.AddCategoryFragment;
import com.mike.itesm.Fragments.User.Admin.AddSellerFragment;
import com.mike.itesm.Fragments.User.Admin.SellerFragment.OnListFragmentInteractionListener;
import com.mike.itesm.Objects.Seller;
import com.mike.itesm.marti.R;

import java.util.List;

public class SellerAdapter extends RecyclerView.Adapter<SellerAdapter.ViewHolder> {

    private final List<Seller> mValues;
    private final OnListFragmentInteractionListener mListener;

    Context context = null;

    public SellerAdapter(Context context, List<Seller> items, OnListFragmentInteractionListener listener) {
        this.context = context;
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_seller, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getId()+"");
        holder.mContentView.setText(mValues.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    Bundle id = new Bundle();
                    id.putString("seller_id", String.valueOf(mValues.get(position).getId()));
                    mListener.onListFragmentInteraction(holder.mItem);
                    Fragment newFragment = new AddSellerFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                    newFragment.setArguments(id);
                    transaction.replace(R.id.frame_layout, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Seller mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
