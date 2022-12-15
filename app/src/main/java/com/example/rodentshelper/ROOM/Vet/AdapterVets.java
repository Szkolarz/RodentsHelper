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
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAOVets;
import com.example.rodentshelper.ROOM._MTM._RodentVet.VetWithRodentsCrossRef;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterVets extends RecyclerView.Adapter<AdapterVets.viewHolder>
{
    List<VetWithRodentsCrossRef> vetModel;


    public AdapterVets(List<VetWithRodentsCrossRef> vetModel) {
        this.vetModel = vetModel;
    }

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vets_item_list,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position) {

        AppDatabase db = Room.databaseBuilder(holder.editTextName_vet.getContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOVets vetDao = db.daoVets();

        holder.editTextName_vet.setEnabled(false);
        holder.editTextAddress_vet.setEnabled(false);
        holder.editTextPhone_vet.setEnabled(false);
        holder.editTextNotes_vet.setEnabled(false);
        holder.ListViewVet.setVisibility(View.GONE);
        holder.textViewRodentRelationsInfo_vet.setVisibility(View.GONE);
        holder.textViewRodentRelations_vet.setVisibility(View.GONE);

        holder.checkBoxVet.setVisibility(View.GONE);
        holder.buttonAdd_vet.setVisibility(View.GONE);
        holder.buttonSaveEdit_vet.setVisibility(View.GONE);


        holder.editTextName_vet.append(vetModel.get(position).vetModel.getName());
        holder.editTextAddress_vet.append(vetModel.get(position).vetModel.getAddress());
        holder.editTextPhone_vet.append(vetModel.get(position).vetModel.getPhone_number());
        holder.editTextNotes_vet.append(vetModel.get(position).vetModel.getNotes());




        try {
            for (int i = 0; i < vetModel.get(position).rodents.size(); i++) {
                if ((i + 1) < vetModel.get(position).rodents.size())
                    holder.textViewRodentRelations_vet.append(vetModel.get(position).rodents.get(i).getName() + "\n");
                else
                    holder.textViewRodentRelations_vet.append(vetModel.get(position).rodents.get(i).getName());
                holder.textViewRodentRelationsInfo_vet.setVisibility(View.VISIBLE);
                holder.textViewRodentRelations_vet.setVisibility(View.VISIBLE);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("There is no any rodent left in relation");
        }




        ArrayAdapter<String> adapter = new ArrayAdapter<>(holder.ListViewVet.getContext(), android.R.layout.simple_list_item_multiple_choice, holder.arrayListSelected);
        holder.ListViewVet.setAdapter(adapter);


        holder.buttonDelete_vet.setOnClickListener(view -> onClickDelete(vetDao, holder));

        holder.buttonEdit_vet.setOnClickListener(view -> onClickEdit(vetDao, holder));


        holder.arrayListSelected.clear();
        db.close();
    }

    private void onClickDelete(DAOVets vetDao, viewHolder holder) {

        //vetDao.DeleteAllRodentsVetsByVet(vetModel.get(holder.getAdapterPosition()).getId());
        vetDao.deleteVetById(vetModel.get(holder.getAdapterPosition()).vetModel.getId());

        //vetDao.SetVisitsIdVetNull(vetModel.get(holder.getAdapterPosition()).getId());
        vetModel.remove(holder.getAdapterPosition());

        notifyDataSetChanged();

        Intent intent = new Intent(holder.buttonDelete_vet.getContext(), ViewVets.class);
        holder.buttonDelete_vet.getContext().startActivity(intent);
    }

    private void onClickEdit(DAOVets vetDao, viewHolder holder) {
        Intent intent = new Intent(new Intent(holder.buttonEdit_vet.getContext(), AddVets.class));
        intent.putExtra("idKey",String.valueOf(vetModel.get(holder.getAdapterPosition()).vetModel.getId()));
        intent.putExtra("nameKey",String.valueOf(vetModel.get(holder.getAdapterPosition()).vetModel.getName()));
        intent.putExtra("addressKey",String.valueOf(vetModel.get(holder.getAdapterPosition()).vetModel.getAddress()));
        intent.putExtra("phoneKey",String.valueOf(vetModel.get(holder.getAdapterPosition()).vetModel.getPhone_number()));
        intent.putExtra("notesKey",String.valueOf(vetModel.get(holder.getAdapterPosition()).vetModel.getNotes()));

        intent.putExtra("positionKey",String.valueOf(vetDao.getRealPositionFromVet( vetModel.get(holder.getAdapterPosition()).vetModel.getId()) -1 ));
        //0 = edit
        FlagSetup.setFlagVetAdd(0);

        holder.buttonEdit_vet.getContext().startActivity(intent);
    }

    @Override
    public int getItemCount() {

        if (vetModel.isEmpty())
            System.out.println("empty\n");

        return vetModel.size();
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


           }
       }
}
