package com.mike.itesm.Fragments.User.All;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.mike.itesm.marti.R;

public class Buscar extends Fragment {


    public Buscar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Buscar.
     */
    // TODO: Rename and change types and number of parameters
    public static Buscar newInstance(String param1, String param2) {
        Buscar fragment = new Buscar();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscar, container, false);
        view.setBackgroundResource(R.color.white);

        Spinner spinner_gender = (Spinner) view.findViewById(R.id.GenderSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> Spiner2adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.gender_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        Spiner2adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_gender.setAdapter(Spiner2adapter);

        Spinner spinner_category = (Spinner) view.findViewById(R.id.CategorySpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> Spiner3adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.category_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        Spiner3adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_category.setAdapter(Spiner3adapter);

        Spinner spinner_age = (Spinner) view.findViewById(R.id.AgeSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> Spiner1adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.age_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        Spiner1adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_age.setAdapter(Spiner1adapter);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buscar, container, false);
    }


}
