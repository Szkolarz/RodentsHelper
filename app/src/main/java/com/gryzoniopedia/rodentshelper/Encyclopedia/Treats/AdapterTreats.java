package com.gryzoniopedia.rodentshelper.Encyclopedia.Treats;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gryzoniopedia.rodentshelper.Encyclopedia.FragmentFlag;
import com.example.rodentshelper.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterTreats extends RecyclerView.Adapter<AdapterTreats.viewHolder>
{
    private final List<TreatsModel> treatsModel;

    public AdapterTreats(List<TreatsModel> treatsModel) {
        this.treatsModel = treatsModel;
    }

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.treats_cagesupply_list,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position) {
        if (FragmentFlag.getFragmentFlag() == 0) {
            holder.linearLayout_treats.setBackgroundColor(Color.parseColor("#a1e3b3"));
            holder.view_treats.setBackgroundColor(Color.parseColor("#6dd188"));
            if (!treatsModel.get(position).getIs_healthy())
                holder.linearLayout_treats.setVisibility(View.GONE);

            if (treatsModel.get(position).getName().equals("Bakalie; nasiona")) {
                holder.linearLayout_treats.setBackgroundColor(Color.parseColor("#f6fad4"));
                holder.view_treats.setBackgroundColor(Color.parseColor("#f3ff8c"));
            }

        }
        else if (FragmentFlag.getFragmentFlag() == 1) {
            holder.linearLayout_treats.setBackgroundColor(Color.parseColor("#e3a6b1"));
            holder.view_treats.setBackgroundColor(Color.parseColor("#d18492"));
            if (treatsModel.get(position).getIs_healthy())
                holder.linearLayout_treats.setVisibility(View.GONE);
        }

        holder.textViewName_treats.setText(treatsModel.get(position).getName());
        holder.textViewDesc_treats.setText(treatsModel.get(position).getDescription());

        try {
            Bitmap bitmap = BitmapFactory.decodeByteArray(treatsModel.get(position).getImage(), 0, treatsModel.get(position).getImage().length);
            holder.imageView_treats.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            holder.imageView_treats.setVisibility(View.INVISIBLE);
            holder.cardView_treats.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return treatsModel.size();
    }

    static class viewHolder extends RecyclerView.ViewHolder
    {
           TextView textViewName_treats, textViewDesc_treats;
           ImageView imageView_treats;
           CardView cardView_treats;
           LinearLayout linearLayout_treats;
           View view_treats;


           public viewHolder(@NonNull @NotNull View itemView) {
               super(itemView);

               textViewName_treats = itemView.findViewById(R.id.textViewName_treats);
               textViewDesc_treats = itemView.findViewById(R.id.textViewDesc_treats);

               imageView_treats = itemView.findViewById(R.id.imageView_treats);
               cardView_treats = itemView.findViewById(R.id.cardView_treats);

               linearLayout_treats = itemView.findViewById(R.id.linearLayout_treats);
               view_treats = itemView.findViewById(R.id.view_treats);

           }
       }
}
