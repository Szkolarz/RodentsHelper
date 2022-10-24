package com.example.rodentshelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodentshelper.SQL.SQLiteHelper;

import java.sql.Date;
import java.util.List;

public class RodentsAdapterClass extends RecyclerView.Adapter<RodentsAdapterClass.ViewHolder>{

    List<RodentsModelClass> rodentsList;
    Context context;
    SQLiteHelper databaseHelper;



    public RodentsAdapterClass(List<RodentsModelClass> rodentsList, Context context) {
        this.rodentsList = rodentsList;
        this.context = context;
        databaseHelper = new SQLiteHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rodents_item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final RodentsModelClass rodentsModelClass = rodentsList.get(position);

        holder.textViewListID.setText(Integer.toString(rodentsModelClass.getId()));
        holder.editTextListName.setText(rodentsModelClass.getName());
        holder.textViewListGender.setText(rodentsModelClass.getGender());
        holder.editTextListDate.setText((rodentsModelClass.getBirth()).toString());
        holder.editTextListFur.setText(rodentsModelClass.getFur());
        holder.editTextListNotes.setText(rodentsModelClass.getNotes());



        holder.buttonListEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(holder.editTextListName.getText().toString());
                System.out.println(rodentsModelClass.getName());

                Intent intent = new Intent(context, RodentEdit.class);

                intent.putExtra("idKey", rodentsModelClass.getId());
                intent.putExtra("nameKey", holder.editTextListName.getText().toString());
                intent.putExtra("genderKey",  rodentsModelClass.getGender());
                intent.putExtra("birthKey",  holder.editTextListDate.getText().toString());
                intent.putExtra("furKey",  holder.editTextListFur.getText().toString());
                intent.putExtra("notesKey",  holder.editTextListNotes.getText().toString());

                System.out.println(holder.editTextListName.getText().toString());
                System.out.println(rodentsModelClass.getName());


                context.startActivity(intent);

            }
        });


        holder.buttonListDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.deleteRodent(rodentsModelClass.getId());
                //tu byl blad przy position
                rodentsList.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });

        holder.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

    }

    @Override
    public int getItemCount() {
        return rodentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewListID, textViewListGender;
        EditText editTextListName, editTextListNotes, editTextListDate, editTextListFur;
        RadioButton radioButtonGender1, radioButtonGender2;
        Button buttonListEdit, buttonListDelete, button2;
        RadioGroup radioGroup;
        RadioButton radioButton;

        public ViewHolder (@NonNull View itemView) {
            super(itemView);

            button2 = itemView.findViewById(R.id.button2);

            textViewListID = itemView.findViewById(R.id.textViewListID);
            editTextListName = itemView.findViewById(R.id.editTextEditName);
            editTextListDate = itemView.findViewById(R.id.editTextListDate);
            editTextListFur = itemView.findViewById(R.id.editTextListFur);

            textViewListGender = itemView.findViewById(R.id.textViewListGender);

            editTextListNotes = itemView.findViewById(R.id.editTextListNotes);
            buttonListEdit = itemView.findViewById(R.id.buttonListEdit);
            buttonListDelete = itemView.findViewById(R.id.buttonListDelete);

        }


    }

}
