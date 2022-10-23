package com.example.rodentshelper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodentshelper.SQL.SQLiteHelper;

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
        holder.editTextListNotes.setText(rodentsModelClass.getNotes());

        holder.buttonListEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringName = holder.editTextListName.getText().toString();
                String stringNotes = holder.editTextListNotes.getText().toString();

                databaseHelper.updateRodent(new RodentsModelClass(rodentsModelClass.getId(), stringName, stringNotes));
                notifyDataSetChanged();
                ((Activity) context).finish();
                context.startActivity(((Activity) context).getIntent());
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

    }

    @Override
    public int getItemCount() {
        return rodentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewListID;
        EditText editTextListName, editTextListNotes;
        Button buttonListEdit, buttonListDelete;

        public ViewHolder (@NonNull View itemView) {
            super(itemView);

            textViewListID = itemView.findViewById(R.id.textViewListID);
            editTextListName = itemView.findViewById(R.id.editTextListName);
            editTextListNotes = itemView.findViewById(R.id.editTextListNotes);
            buttonListEdit = itemView.findViewById(R.id.buttonListEdit);
            buttonListDelete = itemView.findViewById(R.id.buttonListDelete);
        }

    }

}
