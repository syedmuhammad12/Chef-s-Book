package com.example.cep;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class myadapter extends FirebaseRecyclerAdapter <model, myadapter.myviewholder>{

    public myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model) {

        holder.nametext.setText(model.getName());
        holder.chefnametext.setText("By: "+model.getChef());
        Glide.with(holder.img1.getContext()).load(model.getUrl()).into(holder.img1);

        holder.img1.setOnClickListener(view -> {
            AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new DescFragment(model.getName(),model.getChef(), model.getIngredient(), model.getProcedure(),model.getUrl())).addToBackStack(null).commit();
        });

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singleworddesign,parent,false);
        return new myviewholder(view);

    }






    public class myviewholder extends RecyclerView.ViewHolder {


        ImageView img1;
        TextView nametext,chefnametext;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            img1=itemView.findViewById(R.id.img1);
            nametext=itemView.findViewById(R.id.nametext);
            chefnametext=itemView.findViewById(R.id.chefnametext);
        }


    }

}
