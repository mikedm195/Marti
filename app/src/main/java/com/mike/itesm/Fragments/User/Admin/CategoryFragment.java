package com.mike.itesm.Fragments.User.Admin;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mike.itesm.Adapters.CategoryAdapter;
import com.mike.itesm.Objects.Category;
import com.mike.itesm.marti.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.mike.itesm.Services.Services.CATEGORY_LIST_API;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CategoryFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    RecyclerView recyclerView;
    private OnListFragmentInteractionListener mListener;

    private ArrayList<Category> categoryList = new ArrayList<Category>();

    Button nuevaCategoria;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CategoryFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CategoryFragment newInstance(int columnCount) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);

        nuevaCategoria = (Button) view.findViewById(R.id.nuevaCategoria);
        recyclerView = (RecyclerView) view.findViewById(R.id.category_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        nuevaCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new AddCategoryFragment();
                FragmentTransaction transaction = (getFragmentManager().beginTransaction());
                transaction.replace(R.id.frame_layout, newFragment);
                transaction.commit();
            }
        });


        StringRequest categoryReq = new StringRequest(Request.Method.GET, CATEGORY_LIST_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if(!response.equals("")) {
                                categoryList.clear();
                                JSONArray categories = new JSONArray(response);
                                for(int i = 0; i<categories.length();i++) {
                                    JSONObject category = categories.getJSONObject(i);
                                    Category categoryObject = new Category(
                                            category.getInt("category_id"),
                                            category.getString("name")
                                    );
                                    Log.w("value", categoryObject.getId() + " " + categoryObject.name);
                                    categoryList.add(categoryObject);
                                }

                                recyclerView.setAdapter(new CategoryAdapter(getContext(), categoryList, mListener));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        Volley.newRequestQueue(getContext()).add(categoryReq);



        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Category item);
    }

}
