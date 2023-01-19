package com.example.rodentshelper.Encyclopedia.CageSupply;

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

import com.example.rodentshelper.Encyclopedia.FragmentFlag;
import com.example.rodentshelper.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterCageSupply extends RecyclerView.Adapter<AdapterCageSupply.viewHolder>
{
    private final List<CageSupplyModel> cageSupplyModel;

    public AdapterCageSupply(List<CageSupplyModel> cageSupplyModel) {
        this.cageSupplyModel = cageSupplyModel;
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
            if (!cageSupplyModel.get(position).getIs_good())
                holder.linearLayout_treats.setVisibility(View.GONE);
        }
        else if (FragmentFlag.getFragmentFlag() == 1) {
            holder.linearLayout_treats.setBackgroundColor(Color.parseColor("#e3a6b1"));
            holder.view_treats.setBackgroundColor(Color.parseColor("#d18492"));
            if (cageSupplyModel.get(position).getIs_good())
                holder.linearLayout_treats.setVisibility(View.GONE);
        }


        holder.textViewName_treats.setText(cageSupplyModel.get(position).getName());
        holder.textViewDesc_treats.setText(cageSupplyModel.get(position).getDescription());

        try {
            Bitmap bitmap = BitmapFactory.decodeByteArray(cageSupplyModel.get(position).getImage(),
                    0, cageSupplyModel.get(position).getImage().length);
            holder.imageView_treats.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            holder.imageView_treats.setVisibility(View.INVISIBLE);
            holder.cardView_treats.setVisibility(View.INVISIBLE);
        }



    }


    @Override
    public int getItemCount() {
        return cageSupplyModel.size();
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
