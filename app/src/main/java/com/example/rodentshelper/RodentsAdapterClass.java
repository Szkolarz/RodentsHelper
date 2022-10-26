package com.example.rodentshelper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodentshelper.SQL.DBHelperAnimal;

import java.util.List;

public class RodentsAdapterClass extends RecyclerView.Adapter<RodentsAdapterClass.ViewHolder>{

    List<RodentsModelClass> rodentsList;
    Context context;
    DBHelperAnimal databaseHelper;



    public RodentsAdapterClass(List<RodentsModelClass> rodentsList, Context context) {
        this.rodentsList = rodentsList;
        this.context = context;
        databaseHelper = new DBHelperAnimal(context);
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
        holder.textViewListName.setText(rodentsModelClass.getName());
        holder.textViewListGender.setText(rodentsModelClass.getGender());
        holder.textViewListDate.setText((rodentsModelClass.getBirth()).toString());
        holder.textViewListFur.setText(rodentsModelClass.getFur());
        holder.textViewListNotes.setText(rodentsModelClass.getNotes());



        holder.buttonListEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println(rodentsModelClass.getName());

                Intent intent = new Intent(context, RodentEdit.class);

                intent.putExtra("idKey", rodentsModelClass.getId());
                intent.putExtra("nameKey", holder.textViewListName.getText().toString());
                intent.putExtra("genderKey",  rodentsModelClass.getGender());
                intent.putExtra("birthKey",  holder.textViewListDate.getText().toString());
                intent.putExtra("furKey",  holder.textViewListFur.getText().toString());
                intent.putExtra("notesKey",  holder.textViewListNotes.getText().toString());



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
        TextView textViewListID, textViewListName, textViewListGender, textViewListDate, textViewListFur, textViewListNotes;
        Button buttonListEdit, buttonListDelete, button2;


        public ViewHolder (@NonNull View itemView) {
            super(itemView);

            button2 = itemView.findViewById(R.id.button2);

            textViewListID = itemView.findViewById(R.id.editTextListID);

            textViewListName = itemView.findViewById(R.id.editTextListName);
            textViewListGender = itemView.findViewById(R.id.editTextListAddress);
            textViewListDate = itemView.findViewById(R.id.textViewListDate);
            textViewListFur = itemView.findViewById(R.id.editTextListPhone);
            textViewListNotes = itemView.findViewById(R.id.editTextListNotes);


            buttonListEdit = itemView.findViewById(R.id.buttonListEdit);
            buttonListDelete = itemView.findViewById(R.id.buttonListDelete);

        }


    }

}
