package com.example.rodentshelper.ClassAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodentshelper.R;
import com.example.rodentshelper.SQL.DBHelperVet;
import com.example.rodentshelper.ClassModels.VetsModelClass;

import java.util.List;

public class VetsAdapterClass extends RecyclerView.Adapter<VetsAdapterClass.ViewHolder>{

    List<VetsModelClass> vetsList;
    Context context;
    DBHelperVet databaseHelper;



    public VetsAdapterClass(List<VetsModelClass> vetsList, Context context) {
        this.vetsList = vetsList;
        this.context = context;
        databaseHelper = new DBHelperVet(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.vets_item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final VetsModelClass vetsModelClass = vetsList.get(position);

        holder.editTextListName.setText(vetsModelClass.getName());
        holder.editTextListAddress.setText(vetsModelClass.getAddress());
        holder.editTextListPhone.setText(vetsModelClass.getPhone_number());
        holder.editTextListNotes.setText(vetsModelClass.getNotes());


        holder.buttonListEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            }
        });


        holder.buttonListDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.deleteVet(vetsModelClass.getId());
                vetsList.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });



    }

    @Override
    public int getItemCount() {
        return vetsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText editTextListID, editTextListName, editTextListAddress, editTextListPhone, editTextListNotes;
        Button buttonListEdit, buttonListDelete;


        public ViewHolder (@NonNull View itemView) {
            super(itemView);

            editTextListID = itemView.findViewById(R.id.editTextListID);

            editTextListName = itemView.findViewById(R.id.editTextListName);
            editTextListAddress = itemView.findViewById(R.id.editTextListAddress);
            editTextListPhone = itemView.findViewById(R.id.editTextListPhone);
            editTextListNotes = itemView.findViewById(R.id.editTextListNotes);

            buttonListEdit = itemView.findViewById(R.id.buttonListEdit);
            buttonListDelete = itemView.findViewById(R.id.buttonListDelete);
        }


    }



}
