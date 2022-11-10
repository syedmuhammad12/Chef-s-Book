package com.example.cep;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DescFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String name, chef, ingredient, procedure, url;

    public DescFragment() {
        // Required empty public constructor
    }

    public DescFragment(String name, String chef, String ingredient, String procedure, String url) {
        this.name = name;
        this.chef = chef;
        this.ingredient = ingredient;
        this.procedure = procedure;
        this.url = url;
    }

    // TODO: Rename and change types and number of parameters
    public static DescFragment newInstance(String param1, String param2) {
        DescFragment fragment = new DescFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_desc, container, false);

        ImageView imageholder=view.findViewById(R.id.imagegholder);
        TextView nameholder=view.findViewById(R.id.nameholder);
        TextView chefnameholder=view.findViewById(R.id.chefnameholder);
        TextView ingredientholder=view.findViewById(R.id.ingredientholder);
        TextView procdeureholder=view.findViewById(R.id.procedureholder);




        nameholder.setText(name);
        chefnameholder.setText("By: "+chef);
        ingredientholder.setText(ingredient);
        procdeureholder.setText(procedure);
        Glide.with(requireContext()).load(url).into(imageholder);


        return  view;
    }

    public void onBackPressed()
    {
        AppCompatActivity activity=(AppCompatActivity)getContext();
        assert activity != null;
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new RecFragment()).addToBackStack(null).commit();

    }



}