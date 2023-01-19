package com.example.rodentshelper.Encyclopedia.Diseases;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodentshelper.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterDiseases extends RecyclerView.Adapter<AdapterDiseases.viewHolder>
{
    private final List<DiseasesModel> diseasesModel;

    public AdapterDiseases(List<DiseasesModel> diseasesModel) {
        this.diseasesModel = diseasesModel;
    }

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.general_list,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position) {

        holder.linearLayout_general.setBackgroundColor(Color.parseColor("#f6fad4"));
        holder.textViewName_general.setText(diseasesModel.get(position).getName());
        holder.textViewDesc_general.setText(diseasesModel.get(position).getDescription());
    }


    @Override
    public int getItemCount() {
        return diseasesModel.size();
    }

    static class viewHolder extends RecyclerView.ViewHolder
    {
           TextView textViewName_general, textViewDesc_general;
           LinearLayout linearLayout_general;

           public viewHolder(@NonNull @NotNull View itemView) {
               super(itemView);

               textViewName_general = itemView.findViewById(R.id.textViewName_general);
               textViewDesc_general = itemView.findViewById(R.id.textViewDesc_general);
               linearLayout_general = itemView.findViewById(R.id.linearLayout_general);
           }
       }
}
