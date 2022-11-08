package com.example.rodentshelper.ROOM.Rodent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.MainViews.ViewRodents;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAO;

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


        Bitmap bitmap = BitmapFactory.decodeByteArray(rodentModel.get(position).getImage(), 0, rodentModel.get(position).getImage().length);
        holder.imageViewList_rodent.setImageBitmap(bitmap);

        holder.textViewName.setText(rodentModel.get(position).getName());
        holder.textViewGender.setText(rodentModel.get(position).getGender());
        holder.textViewDate.setText(rodentModel.get(position).getBirth().toString());
        holder.textViewFur.setText(rodentModel.get(position).getFur());
        holder.textViewNotes.setText(rodentModel.get(position).getNotes());


        int id = rodentModel.get(holder.getAdapterPosition()).getId();

        holder.buttonListDelete.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  AlertDialog.Builder alert = new AlertDialog.Builder(holder.buttonListDelete.getContext(), R.style.AlertDialogStyle);
                  alert.setTitle("Usuwanie pupila");
                  alert.setMessage("Czy na pewno chcesz usunąć pupila z listy?\n\nProces jest nieodwracalny!");


                  alert.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {
                          Toast.makeText(holder.buttonListDelete.getContext(), "Pomyślnie usunięto", Toast.LENGTH_SHORT).show();
                          AppDatabase db = Room.databaseBuilder(holder.textViewName.getContext(),
                                  AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
                          DAO rodentDao = db.dao();
                          // this is to delete the record from room database

                          rodentDao.DeleteAllRodentsVetsByRodent(rodentModel.get(holder.getAdapterPosition()).getId());
                          rodentDao.deleteRodentById(rodentModel.get(holder.getAdapterPosition()).getId());
                          // this is to delete the record from Array List which is the source of recview data
                          rodentModel.remove(holder.getAdapterPosition());

                          Intent intent = new Intent(holder.buttonListDelete.getContext(), ViewRodents.class);
                          holder.buttonListDelete.getContext().startActivity(intent);

                          //update the fresh list of ArrayList data to recyclerview
                          notifyDataSetChanged();
                      }
                  });
                  alert.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {
                          Toast.makeText(holder.buttonListDelete.getContext(), "Anulowano", Toast.LENGTH_SHORT).show();
                      }
                  });

                  alert.create().show();


              }
          });

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
           Button buttonListDelete, buttonEdit_rodent;
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

           }
       }
}
