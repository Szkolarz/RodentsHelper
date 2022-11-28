package com.example.rodentshelper.ROOM.Notes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAONotes;
import com.example.rodentshelper.ROOM._MTM._RodentNotes.RodentWithNotes;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterNotes extends RecyclerView.Adapter<AdapterNotes.viewHolder>
{
    List<RodentWithNotes> notesModel;


    public AdapterNotes(List<RodentWithNotes> notesModel) {
        this.notesModel = notesModel;
    }

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_item_list,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position) {

        holder.editTextTopic_notes.setEnabled(false);
        holder.editTextContent_notes.setEnabled(false);


        holder.buttonAdd_notes.setVisibility(View.GONE);
        holder.buttonSaveEdit_notes.setVisibility(View.GONE);



        holder.editTextTopic_notes.setText(notesModel.get(position).notesModel.getTopic());
        holder.editTextContent_notes.setText(notesModel.get(position).notesModel.getContent());


        if (notesModel.get(position).notesModel.getTopic().equals(""))
            holder.editTextTopic_notes.setText("brak tematu");
        else
            holder.editTextTopic_notes.setText(notesModel.get(position).notesModel.getTopic().toString());


        AppDatabase db = Room.databaseBuilder(holder.editTextTopic_notes.getContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAONotes dao = db.daoNotes();



        holder.buttonDelete_notes.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  deleteNotes(holder.buttonDelete_notes.getContext(), holder);
              }
        });

         holder.buttonEdit_notes.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent intent = new Intent(new Intent(holder.buttonEdit_notes.getContext(), AddNotes.class));
                  intent.putExtra("idKey",String.valueOf(notesModel.get(holder.getAdapterPosition()).notesModel.getId_notes()));
                  intent.putExtra("id_animalKey",String.valueOf(notesModel.get(holder.getAdapterPosition()).notesModel.getId_rodent()));
                  intent.putExtra("topicKey",String.valueOf(notesModel.get(holder.getAdapterPosition()).notesModel.getTopic()));
                  intent.putExtra("contentKey",String.valueOf(notesModel.get(holder.getAdapterPosition()).notesModel.getContent()));

                  //0 = edit
                  FlagSetup.setFlagNotesAdd(0);
                  holder.buttonEdit_notes.getContext().startActivity(intent);
              }
          });

        holder.arrayListSelected.clear();
        db.close();
    }

    /** usuwanie **/
    private void deleteNotes(Context context, viewHolder holder) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Usuwanie notatki");
        alert.setMessage("Czy na pewno chcesz usunąć notatkę z listy?\n\nProces jest nieodwracalny!");

        alert.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Pomyślnie usunięto notatkę", Toast.LENGTH_SHORT).show();

                AppDatabase db = Room.databaseBuilder(context,
                        AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
                DAONotes dao = db.daoNotes();

                dao.deleteNotesById(notesModel.get(holder.getAdapterPosition()).notesModel.getId_notes());

                notesModel.remove(holder.getAdapterPosition());

                Intent intent = new Intent(context, ViewNotes.class);
                context.startActivity(intent);

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

    @Override
    public int getItemCount() {
        return notesModel.size();
    }




    class viewHolder extends RecyclerView.ViewHolder
    {

        EditText editTextTopic_notes, editTextContent_notes;
        Button buttonEdit_notes, buttonAdd_notes, buttonSaveEdit_notes, buttonDelete_notes;


        private ArrayList<String> arrayListSelected;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            editTextTopic_notes = itemView.findViewById(R.id.editTextTopic_notes);
            editTextContent_notes = itemView.findViewById(R.id.editTextContent_notes);

            buttonEdit_notes = itemView.findViewById(R.id.buttonEdit_notes);
            buttonAdd_notes = itemView.findViewById(R.id.buttonAdd_notes);
            buttonSaveEdit_notes = itemView.findViewById(R.id.buttonSaveEdit_notes);
            buttonDelete_notes = itemView.findViewById(R.id.buttonDelete_notes);


            arrayListSelected = new ArrayList<>();

           }
       }
}
