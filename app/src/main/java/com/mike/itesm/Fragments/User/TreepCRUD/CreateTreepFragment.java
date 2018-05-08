package com.mike.itesm.Fragments.User.TreepCRUD;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mike.itesm.marti.R;


public class CreateTreepFragment extends Fragment {
    Button createTreepBtn;
    EditText priceTxt, destiny_nameTxt, descriptionTxt, photoText;


    public CreateTreepFragment() {
        // Required empty public constructor
    }

    public static CreateTreepFragment newInstance(String param1, String param2) {
        CreateTreepFragment fragment = new CreateTreepFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_treep, container, false);
        view.setBackgroundResource(R.color.white);

        createTreepBtn = (Button)view.findViewById(R.id.createTreepButton);
        priceTxt = (EditText)view.findViewById(R.id.priceTextField);
        destiny_nameTxt = (EditText)view.findViewById(R.id.destinyTextField);
        descriptionTxt = (EditText)view.findViewById(R.id.descriptionTextField);
        photoText = (EditText)view.findViewById(R.id.photoTextField);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

        return view;;
    }
}
