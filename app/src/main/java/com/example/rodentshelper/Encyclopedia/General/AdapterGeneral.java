package com.example.rodentshelper.Encyclopedia.General;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodentshelper.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterGeneral extends RecyclerView.Adapter<AdapterGeneral.viewHolder>
{
    private final List<GeneralModel> generalModel;

    public AdapterGeneral(List<GeneralModel> generalModel) {
        this.generalModel = generalModel;
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

        holder.textViewName_general.setText(generalModel.get(position).getName());
        holder.textViewDesc_general.setText(generalModel.get(position).getDescription());
        System.out.println(generalModel.get(position).getDescription());
    }


    @Override
    public int getItemCount() {
        return generalModel.size();
    }

    static class viewHolder extends RecyclerView.ViewHolder
    {
           TextView textViewName_general, textViewDesc_general;


           public viewHolder(@NonNull @NotNull View itemView) {
               super(itemView);

               textViewName_general = itemView.findViewById(R.id.textViewName_general);
               textViewDesc_general = itemView.findViewById(R.id.textViewDesc_general);

           }
       }
}
