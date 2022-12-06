package com.example.rodentshelper.Encyclopedia.Treats;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.rodentshelper.Encyclopedia.FragmentFlag;
import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAOWeight;
import com.example.rodentshelper.ROOM.DateFormat;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;
import com.example.rodentshelper.ROOM.Weights.WeightView;
import com.example.rodentshelper.ROOM._MTM._RodentMed.RodentMedModel;
import com.example.rodentshelper.ROOM._MTM._RodentWeight.RodentWithWeights;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterTreats extends RecyclerView.Adapter<AdapterTreats.viewHolder>
{
    List<TreatsModel> treatsModel;

    public AdapterTreats(List<TreatsModel> treatsModel) {
        this.treatsModel = treatsModel;
    }

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.treats_list,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position) {
        if ( FragmentFlag.getFragmentFlag() == 0 &&
                treatsModel.get(position).getIs_healthy() == false) {
            holder.linearLayout_treats.setVisibility(View.GONE);
        }
        else if ( FragmentFlag.getFragmentFlag() == 1 &&
                treatsModel.get(position).getIs_healthy() == true)
            holder.linearLayout_treats.setVisibility(View.GONE);

        if (FragmentFlag.getFragmentFlag() == 0)
            holder.linearLayout_treats.setBackgroundColor(Color.parseColor("#87d49c"));
        else
            holder.linearLayout_treats.setBackgroundColor(Color.parseColor("#d48795"));

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

    class viewHolder extends RecyclerView.ViewHolder
    {

           TextView textViewName_treats, textViewDesc_treats, textViewDate_view;
           ImageView imageView_treats;
           CardView cardView_treats;
           LinearLayout linearLayout_treats, linearLayoutGlobal_treats;

           ImageButton delbtn,edbtn;
           public viewHolder(@NonNull @NotNull View itemView) {
               super(itemView);

               textViewName_treats = itemView.findViewById(R.id.textViewName_treats);
               textViewDesc_treats = itemView.findViewById(R.id.textViewDesc_treats);

               imageView_treats = itemView.findViewById(R.id.imageView_treats);
               cardView_treats = itemView.findViewById(R.id.cardView_treats);

               linearLayout_treats = itemView.findViewById(R.id.linearLayout_treats);

           }
       }
}
