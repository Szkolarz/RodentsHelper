package com.example.rodentshelper.ROOM.Vet;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.MainViews.ViewVets;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAO;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterVets extends RecyclerView.Adapter<AdapterVets.viewHolder>
{
    List<VetModel> vetModel;


    private ArrayList<String> arrayListAll;
    private boolean flag = false;
    private List<RodentModel> lisjtOfAllRodents;
    List<String> aaa;


    public AdapterVets(List<VetModel> vetModel) {
        this.vetModel = vetModel;
    }

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vets_item_list,parent,false);
        flag = false;

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position) {



        holder.editTextName_vet.setEnabled(false);
        holder.editTextAddress_vet.setEnabled(false);
        holder.editTextPhone_vet.setEnabled(false);
        holder.editTextNotes_vet.setEnabled(false);

        holder.checkBoxVet.setVisibility(View.GONE);
        holder.buttonAdd_vet.setVisibility(View.GONE);
        holder.buttonSaveEdit_vet.setVisibility(View.GONE);


        holder.editTextName_vet.setText(vetModel.get(position).getName());
        holder.editTextAddress_vet.setText(vetModel.get(position).getAddress());
        holder.editTextPhone_vet.setText(vetModel.get(position).getPhone_number());
        holder.editTextNotes_vet.setText(vetModel.get(position).getNotes());



        ArrayAdapter<String> adapter = new ArrayAdapter<>(holder.ListViewVet.getContext(), android.R.layout.simple_list_item_multiple_choice, holder.arrayListSelected);
        holder.ListViewVet.setAdapter(adapter);



        AppDatabase db = Room.databaseBuilder(holder.editTextName_vet.getContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAO dao = db.dao();

        if (flag == false) {
            aaa = dao.getAllNameRodents();
            flag = true;
        }

       /* if (flag == false) {

            for(int i = 0; i < listOfAllRodents.size(); i++) {
                System.out.println(i);
                arrayListAll.add(listOfAllRodents.get(i).getName());
            }
            flag = true;
        }*/

        List<String> list = dao.getAllRodentsVets(vetModel.get(position).getId());


        for (int j = 0; j < aaa.size(); j ++) {
            holder.arrayListSelected.add(aaa.get(j));
            for(int i = 0; i < list.size(); i++) {


                if (aaa.get(j).equals(list.get(i))) {

                    if ((i + 1) < list.size())
                        holder.textViewRodentRelations_vet.append(list.get(i) + "\n");
                    else
                        holder.textViewRodentRelations_vet.append(list.get(i));


                    holder.ListViewVet.setItemChecked(i, true);
                    holder.checkBoxVet.setChecked(true);
                }

            }
        }

        /*if (holder.arrayListSelected.isEmpty())
            holder.checkBoxVet.setChecked(false);
        else
            holder.checkBoxVet.setChecked(true);*/

        checkCheckBox(holder.checkBoxVet, holder.ListViewVet,
                holder.textViewRodentRelations_vet, holder.textViewRodentRelationsInfo_vet);



        holder.checkBoxVet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCheckBox(holder.checkBoxVet, holder.ListViewVet,
                        holder.textViewRodentRelations_vet, holder.textViewRodentRelationsInfo_vet);
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

                  vetDao.DeleteAllRodentsVetsByVet(vetModel.get(holder.getAdapterPosition()).getId());
                  vetDao.deleteVetById(vetModel.get(holder.getAdapterPosition()).getId());

                  vetDao.SetVisitsIdVetNull(vetModel.get(holder.getAdapterPosition()).getId());
                  vetModel.remove(holder.getAdapterPosition());


                  notifyDataSetChanged();


                  Intent intent = new Intent(holder.buttonDelete_vet.getContext(), ViewVets.class);
                  holder.buttonDelete_vet.getContext().startActivity(intent);

              }
          });



        holder.buttonEdit_vet.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {


                  Intent intent = new Intent(new Intent(holder.buttonEdit_vet.getContext(), AddVets.class));
                  intent.putExtra("idKey",String.valueOf(vetModel.get(holder.getAdapterPosition()).getId()));
                  intent.putExtra("nameKey",String.valueOf(vetModel.get(holder.getAdapterPosition()).getName()));
                  intent.putExtra("addressKey",String.valueOf(vetModel.get(holder.getAdapterPosition()).getAddress()));
                  intent.putExtra("phoneKey",String.valueOf(vetModel.get(holder.getAdapterPosition()).getPhone_number()));
                  intent.putExtra("notesKey",String.valueOf(vetModel.get(holder.getAdapterPosition()).getNotes()));

                  //0 = edit
                  FlagSetup.setFlagVetAdd(0);

                  holder.buttonEdit_vet.getContext().startActivity(intent);



                 /* AppDatabase db = Room.databaseBuilder(holder.editTextName_vet.getContext(),
                          AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
                  DAO vetDao = db.dao();

                  System.out.println(  holder.editTextPhone_vet.getText().toString());
                  System.out.println(  vetModel.get(holder.getAdapterPosition()).getId().toString());


                  vetDao.updateVetById(vetModel.get(holder.getAdapterPosition()).getId(),
                          holder.editTextName_vet.getText().toString(), holder.editTextAddress_vet.getText().toString(),
                          holder.editTextPhone_vet.getText().toString(), holder.editTextNotes_vet.getText().toString());


                  Intent intent = new Intent(holder.editTextName_vet.getContext(), ViewVets.class);
                  holder.editTextName_vet.getContext().startActivity(intent);*/


                  //0 = edit


              }
          });




    }

    @Override
    public int getItemCount() {

        if (vetModel.isEmpty())
            System.out.println("dsf\n");

        return vetModel.size();
    }


    private void checkCheckBox(CheckBox checkBoxVet, ListView listViewVet, TextView textViewRodentRelations_vet, TextView textViewRodentRelationsInfo_vet) {
        if (textViewRodentRelations_vet.getText() != "") {
            listViewVet.setVisibility(View.GONE);
            listViewVet.setSelected(true);

            textViewRodentRelations_vet.setVisibility(View.VISIBLE);
            textViewRodentRelationsInfo_vet.setVisibility(View.VISIBLE);
        }
        else {
            listViewVet.setVisibility(View.GONE);

            textViewRodentRelations_vet.setVisibility(View.GONE);
            textViewRodentRelationsInfo_vet.setVisibility(View.GONE);
        }
    }


    class viewHolder extends RecyclerView.ViewHolder
    {

           EditText editTextName_vet, editTextAddress_vet, editTextPhone_vet, editTextNotes_vet;
           Button buttonDelete_vet, buttonEdit_vet, buttonAdd_vet, buttonSaveEdit_vet;
           ListView ListViewVet;
           TextView textViewRodentRelations_vet, textViewRodentRelationsInfo_vet;
           CheckBox checkBoxVet;

           //lista z querry join
           private ArrayList<String> arrayListSelected;



          public viewHolder(@NonNull @NotNull View itemView) {
               super(itemView);

               editTextName_vet = itemView.findViewById(R.id.editTextName_vet);
               editTextAddress_vet = itemView.findViewById(R.id.editTextAddress_vet);
               editTextPhone_vet = itemView.findViewById(R.id.editTextPhone_vet);
               editTextNotes_vet = itemView.findViewById(R.id.editTextPeriodicity_med);


               buttonDelete_vet = itemView.findViewById(R.id.buttonDelete_vet);
               buttonEdit_vet = itemView.findViewById(R.id.buttonEdit_vet);
               buttonAdd_vet = itemView.findViewById(R.id.buttonAdd_vet);
               buttonSaveEdit_vet = itemView.findViewById(R.id.buttonSaveEdit_vet);


               ListViewVet = itemView.findViewById(R.id.ListViewVet);
               ListViewVet.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
               ListViewVet.setItemsCanFocus(false);

               checkBoxVet = itemView.findViewById(R.id.checkBoxVet);

               textViewRodentRelations_vet = itemView.findViewById(R.id.textViewRodentRelations_vet);
               textViewRodentRelationsInfo_vet = itemView.findViewById(R.id.textViewRodentRelationsInfo_vet);

               arrayListSelected = new ArrayList<>();


               System.out.println(";lkijuhytrfghj");

           }
       }
}
