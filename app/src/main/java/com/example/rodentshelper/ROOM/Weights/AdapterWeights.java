package com.example.rodentshelper.ROOM.Weights;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAOWeight;
import com.example.rodentshelper.ROOM.DateFormat;
import com.example.rodentshelper.ROOM._MTM._RodentWeight.RodentWithWeights;

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


        holder.buttonDelete_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteWeight(holder.buttonDelete_weight.getContext(), holder);
            }
        });

        holder.buttonEdit_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editWeight(holder);
            }
        });



    }

    private void deleteWeight(Context context, viewHolder holder) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.AlertDialogStyle);
        alert.setTitle("Usuwanie wagi");
        alert.setMessage("Czy na pewno chcesz usunąć wagę z listy?\n\nProces jest nieodwracalny!");

        alert.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Pomyślnie usunięto wagę", Toast.LENGTH_SHORT).show();

                AppDatabase db = Room.databaseBuilder(context,
                        AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
                DAOWeight daoWeight = db.daoWeight();

                daoWeight.deleteWeightById(weightModel.get(holder.getAdapterPosition()).weightModel.getId_weight());

                weightModel.remove(holder.getAdapterPosition());

                FlagSetup.setFlagWeightAdd(1);

                Intent intent = new Intent(context, WeightView.class);
                context.startActivity(intent);

                ((Activity) holder.buttonDelete_weight.getContext()).finish();

                notifyDataSetChanged();

            }
        });
        alert.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Anulowano", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();

    }

    private void editWeight(viewHolder holder) {
        Intent intent = new Intent(new Intent(holder.buttonEdit_weight.getContext(), WeightView.class));
        intent.putExtra("idKey",String.valueOf(weightModel.get(holder.getAdapterPosition()).weightModel.getId_weight()));
        intent.putExtra("weightKey",String.valueOf(weightModel.get(holder.getAdapterPosition()).weightModel.getWeight()));
        intent.putExtra("dateKey",String.valueOf(weightModel.get(holder.getAdapterPosition()).weightModel.getDate()));
        //0 = edit
        FlagSetup.setFlagWeightAdd(0);

        holder.buttonEdit_weight.getContext().startActivity(intent);
        ((Activity) holder.buttonEdit_weight.getContext()).finish();
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
