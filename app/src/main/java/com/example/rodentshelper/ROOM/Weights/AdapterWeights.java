package com.example.rodentshelper.ROOM.Weights;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.MainViews.ViewPetHealth;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.DateFormat;
import com.example.rodentshelper.ROOM.Rodent.AddRodents;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;
import com.example.rodentshelper.ROOM._MTM.RodentWeight.RodentWithWeights;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterWeights extends RecyclerView.Adapter<AdapterWeights.viewHolder>
{
    List<RodentWithWeights> weightModel;

    public AdapterWeights(List<RodentWithWeights> weightModel) {
        this.weightModel = weightModel;
    }

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weight_list,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position) {



        holder.textViewWeight_view.setText(weightModel.get(position).weightModel.getWeight().toString());
        holder.textViewDate_view.setText( DateFormat.formatDate(weightModel.get(position).weightModel.getDate()) );




        /*holder.buttonEdit_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               *//* Intent intent = new Intent(new Intent(holder.buttonEdit_rodent.getContext(), AddRodents.class));
                intent.putExtra("idKey",String.valueOf(rodentModel.get(holder.getAdapterPosition()).getId()));
                intent.putExtra("id_animalKey",String.valueOf(rodentModel.get(holder.getAdapterPosition()).getId_animal()));
                intent.putExtra("nameKey",String.valueOf(rodentModel.get(holder.getAdapterPosition()).getName()));
                intent.putExtra("genderKey",String.valueOf(rodentModel.get(holder.getAdapterPosition()).getGender()));
                intent.putExtra("birthKey",String.valueOf(rodentModel.get(holder.getAdapterPosition()).getBirth()));
                intent.putExtra("furKey",String.valueOf(rodentModel.get(holder.getAdapterPosition()).getFur()));
                intent.putExtra("notesKey",String.valueOf(rodentModel.get(holder.getAdapterPosition()).getNotes()));


                //0 = edit
                FlagSetup.setFlagRodentAdd(0);
                holder.buttonEdit_weight.getContext().startActivity(intent);*//*
            }
        });*/






    }

    @Override
    public int getItemCount() {
        return weightModel.size();
    }

    class viewHolder extends RecyclerView.ViewHolder
    {

           TextView textViewWeight_view, textViewDate_view;
           ImageView buttonEdit_weight, buttonDelete_weight;

           ImageButton delbtn,edbtn;
           public viewHolder(@NonNull @NotNull View itemView) {
               super(itemView);

               textViewWeight_view = itemView.findViewById(R.id.textViewWeight_view);
               textViewDate_view = itemView.findViewById(R.id.textViewDate_view);
               buttonEdit_weight = itemView.findViewById(R.id.buttonEdit_weight);
               buttonDelete_weight = itemView.findViewById(R.id.buttonDelete_weight);


           }
       }
}
