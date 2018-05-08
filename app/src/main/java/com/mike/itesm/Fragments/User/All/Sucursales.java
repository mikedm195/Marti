package com.mike.itesm.Fragments.User.All;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mike.itesm.marti.R;

public class Sucursales extends Fragment {

    public Sucursales() {
        // Required empty public constructor
    }

    public static Sucursales newInstance(String param1, String param2) {
        Sucursales fragment = new Sucursales();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sucursales, container, false);
    }
}
