package com.mike.itesm.Fragments.User.AgencyCRUD;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mike.itesm.marti.R;

public class CreateAgencyFragment extends Fragment {

    public CreateAgencyFragment() {
        // Required empty public constructor
    }

    public static CreateAgencyFragment newInstance(String param1, String param2) {
        CreateAgencyFragment fragment = new CreateAgencyFragment();
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
        return inflater.inflate(R.layout.fragment_create_agency, container, false);
    }
}
