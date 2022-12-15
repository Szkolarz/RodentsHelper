package com.example.rodentshelper.ROOM.Rodent;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
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

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterRodents extends RecyclerView.Adapter<AdapterRodents.viewHolder>
{
    List<RodentModel> rodentModel;

    public AdapterRodents(List<RodentModel> rodentModel) {
        this.rodentModel = rodentModel;
    }

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rodents_item_list,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position) {


        if (rodentModel.get(position).getFur().equals("")) {
            holder.textViewFur_rodentText.setVisibility(View.GONE);
            holder.textViewFur.setVisibility(View.GONE);
        } else {
            holder.textViewFur.setText(rodentModel.get(position).getFur());
        }
        if (rodentModel.get(position).getNotes().equals("")) {
            holder.textViewNotes_rodentText.setVisibility(View.GONE);
            holder.textViewNotes.setVisibility(View.GONE);
        } else {
            holder.textViewNotes.setText(rodentModel.get(position).getNotes());
        }


        Bitmap bitmap = BitmapFactory.decodeByteArray(rodentModel.get(position).getImage(), 0, rodentModel.get(position).getImage().length);
        holder.imageViewList_rodent.setImageBitmap(bitmap);

        holder.textViewName.setText(rodentModel.get(position).getName());
        holder.textViewGender.setText(rodentModel.get(position).getGender());

        holder.textViewDate.setText( DateFormat.formatDate(rodentModel.get(position).getBirth()) );

        holder.textViewNotes.setText(rodentModel.get(position).getNotes());


        int id = rodentModel.get(holder.getAdapterPosition()).getId();



        holder.buttonEdit_rodent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(new Intent(holder.buttonEdit_rodent.getContext(), AddRodents.class));
                intent.putExtra("idKey",String.valueOf(rodentModel.get(holder.getAdapterPosition()).getId()));
                intent.putExtra("id_animalKey",String.valueOf(rodentModel.get(holder.getAdapterPosition()).getId_animal()));
                intent.putExtra("nameKey",String.valueOf(rodentModel.get(holder.getAdapterPosition()).getName()));
                intent.putExtra("genderKey",String.valueOf(rodentModel.get(holder.getAdapterPosition()).getGender()));
                intent.putExtra("birthKey",String.valueOf(rodentModel.get(holder.getAdapterPosition()).getBirth()));
                intent.putExtra("furKey",String.valueOf(rodentModel.get(holder.getAdapterPosition()).getFur()));
                intent.putExtra("notesKey",String.valueOf(rodentModel.get(holder.getAdapterPosition()).getNotes()));


                //0 = edit
                FlagSetup.setFlagRodentAdd(0);
                holder.buttonEdit_rodent.getContext().startActivity(intent);
                ((Activity)holder.buttonEdit_rodent.getContext()).finish();
            }
        });

        holder.buttonRodentsPetHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(new Intent(holder.buttonRodentsPetHealth.getContext(), ViewPetHealth.class));
                intent.putExtra("idKey",String.valueOf(rodentModel.get(holder.getAdapterPosition()).getId()));
                intent.putExtra("id_animalKey",String.valueOf(rodentModel.get(holder.getAdapterPosition()).getId_animal()));
                intent.putExtra("nameKey",String.valueOf(rodentModel.get(holder.getAdapterPosition()).getName()));

                SharedPreferences prefsGetRodentId = holder.buttonRodentsPetHealth.getContext().getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);
                SharedPreferences.Editor editorGetRodentName = prefsGetRodentId.edit();
                editorGetRodentName.putInt("rodentId", rodentModel.get(holder.getAdapterPosition()).getId());
                editorGetRodentName.apply();

                holder.buttonRodentsPetHealth.getContext().startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {
        return rodentModel.size();
    }

    class viewHolder extends RecyclerView.ViewHolder
       {

           TextView textViewName, textViewGender, textViewDate, textViewFur, textViewNotes;
           TextView textViewFur_rodentText, textViewNotes_rodentText;
           Button buttonListDelete;
           ImageButton buttonRodentsPetHealth;
           ImageView buttonEdit_rodent;
           ImageView imageViewList_rodent;

           ImageButton delbtn,edbtn;
           public viewHolder(@NonNull @NotNull View itemView) {
               super(itemView);

               textViewName = itemView.findViewById(R.id.textViewName_rodent);
               textViewGender = itemView.findViewById(R.id.textViewGender_rodent);
               textViewDate = itemView.findViewById(R.id.textViewDate_rodent);
               textViewFur = itemView.findViewById(R.id.textViewFur_rodent);
               textViewNotes = itemView.findViewById(R.id.textViewNotes_rodent);
               imageViewList_rodent = itemView.findViewById(R.id.imageViewList_rodent);

               buttonListDelete = itemView.findViewById(R.id.buttonDelete_rodent);
               buttonEdit_rodent = itemView.findViewById(R.id.buttonEdit_rodent);

               textViewFur_rodentText = itemView.findViewById(R.id.textViewFur_rodentText);
               textViewNotes_rodentText = itemView.findViewById(R.id.textViewNotes_rodentText);

               buttonRodentsPetHealth = itemView.findViewById(R.id.buttonRodentsPetHealth);

           }
       }
}
