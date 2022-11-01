package com.example.rodentshelper.ROOM.Vet;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.rodentshelper.AddRodents;
import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.MainViews.ViewRodents;
import com.example.rodentshelper.MainViews.ViewVets;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAO;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;

import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AdapterVets extends RecyclerView.Adapter<AdapterVets.myviewholder>
{
    List<VetModel> vetModel;

    public AdapterVets(List<VetModel> vetModel) {
        this.vetModel = vetModel;
    }

    @NonNull
    @NotNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.vets_item_list,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull myviewholder holder, int position) {


        holder.editTextName_vet.setText(vetModel.get(position).getName());
        holder.editTextAddress_vet.setText(vetModel.get(position).getAddress());
        holder.editTextPhone_vet.setText(vetModel.get(position).getPhone_number());
        holder.editTextNotes_vet.setText(vetModel.get(position).getNotes());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(holder.ListViewVet.getContext(), android.R.layout.simple_list_item_multiple_choice, holder.ArrayListLV);
        holder.ListViewVet.setAdapter(adapter);


        AppDatabase db = Room.databaseBuilder(holder.editTextName_vet.getContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAO vetDao = db.dao();

        List<String> list = vetDao.getAllRodentsVets(vetModel.get(position).getId());

        for(int i = 0; i < list.size(); i++) {
            holder.ArrayListLV.add(list.get(i));
            holder.ListViewVet.setItemChecked(i, true);
        }

        if (holder.ArrayListLV.isEmpty())
            holder.checkBoxVet.setChecked(false);
        else
            holder.checkBoxVet.setChecked(true);


        holder.checkBoxVet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.checkBoxVet.isChecked()) {
                    holder.ListViewVet.setVisibility(View.VISIBLE);
                    holder.ListViewVet.setSelected(true);
                }
                else {
                    holder.ListViewVet.setVisibility(View.GONE);
                }

            }
        });

        /*if (vetDao.getNameRelations_VetAndRodent(vetModel.get(position).getId()) != null)
            holder.editTextNotes_vet.setText(vetDao.getNameRelations_VetAndRodent(vetModel.get(position).getId()).toString());
        System.out.println(vetDao.getNameRelations_VetAndRodent(vetModel.get(position).getId()));*/



        int id = vetModel.get(holder.getAdapterPosition()).getId();

          holder.buttonDelete_vet.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  AppDatabase db = Room.databaseBuilder(holder.editTextName_vet.getContext(),
                          AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
                  DAO vetDao = db.dao();

                  vetDao.deleteVetById(vetModel.get(holder.getAdapterPosition()).getId());

                  vetModel.remove(holder.getAdapterPosition());

                  //update the fresh list of ArrayList data to recview
                  notifyDataSetChanged();
              }
          });

         holder.buttonEdit_vet.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  AppDatabase db = Room.databaseBuilder(holder.editTextName_vet.getContext(),
                          AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
                  DAO vetDao = db.dao();

                  System.out.println(  holder.editTextPhone_vet.getText().toString());
                  System.out.println(  vetModel.get(holder.getAdapterPosition()).getId().toString());


                  vetDao.updateVetById(vetModel.get(holder.getAdapterPosition()).getId(),
                          holder.editTextName_vet.getText().toString(), holder.editTextAddress_vet.getText().toString(),
                          holder.editTextPhone_vet.getText().toString(), holder.editTextNotes_vet.getText().toString());


                  Intent intent = new Intent(holder.editTextName_vet.getContext(), ViewVets.class);
                  holder.editTextName_vet.getContext().startActivity(intent);


                  //0 = edit


              }
          });




    }

    @Override
    public int getItemCount() {
        return vetModel.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
       {

           EditText editTextName_vet, editTextAddress_vet, editTextPhone_vet, editTextNotes_vet;
           Button buttonDelete_vet, buttonEdit_vet, buttonAdd_vet;
           ListView ListViewVet;
           CheckBox checkBoxVet;
           private ArrayList<String> ArrayListLV;



           public myviewholder(@NonNull @NotNull View itemView) {
               super(itemView);

               editTextName_vet = itemView.findViewById(R.id.editTextName_vet);
               editTextAddress_vet = itemView.findViewById(R.id.editTextAddress_vet);
               editTextPhone_vet = itemView.findViewById(R.id.editTextPhone_vet);
               editTextNotes_vet = itemView.findViewById(R.id.editTextNotes_vet);


               buttonDelete_vet = itemView.findViewById(R.id.buttonDelete_vet);
               buttonEdit_vet = itemView.findViewById(R.id.buttonEdit_vet);
               buttonAdd_vet = itemView.findViewById(R.id.buttonAdd_vet);


               ListViewVet = itemView.findViewById(R.id.ListViewVet);
               ListViewVet.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
               ListViewVet.setItemsCanFocus(false);

               checkBoxVet = itemView.findViewById(R.id.checkBoxVet);

               ArrayListLV = new ArrayList<>();


           }
       }
}
