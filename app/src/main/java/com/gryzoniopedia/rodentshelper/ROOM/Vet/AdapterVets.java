package com.gryzoniopedia.rodentshelper.ROOM.Vet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.gryzoniopedia.rodentshelper.ROOM._MTM._RodentVet.VetWithRodentsCrossRef;
import com.gryzoniopedia.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;
import com.gryzoniopedia.rodentshelper.ROOM.AppDatabase;
import com.gryzoniopedia.rodentshelper.ROOM.DAOVets;
import com.gryzoniopedia.rodentshelper.ROOM.DAOVisits;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterVets extends RecyclerView.Adapter<AdapterVets.viewHolder>
{
    private final List<VetWithRodentsCrossRef> vetModel;


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

        if (vetModel.get(position).vetModel.getAddress().equals("")) {
            holder.textViewAddress_vet.setVisibility(View.GONE);
            holder.editTextAddress_vet.setVisibility(View.GONE);
        } else {
            holder.textViewAddress_vet.setVisibility(View.VISIBLE);
            holder.editTextAddress_vet.setVisibility(View.VISIBLE);
        }

        if (vetModel.get(position).vetModel.getPhone_number().equals("")) {
            holder.textViewPhone_vet.setVisibility(View.GONE);
            holder.editTextPhone_vet.setVisibility(View.GONE);
            holder.imageButtonCall_vet.setVisibility(View.GONE);
        } else {
            holder.textViewPhone_vet.setVisibility(View.VISIBLE);
            holder.editTextPhone_vet.setVisibility(View.VISIBLE);
            holder.imageButtonCall_vet.setVisibility(View.VISIBLE);
        }

        if (vetModel.get(position).vetModel.getNotes().equals("")) {
            holder.textViewNotes_vet.setVisibility(View.GONE);
            holder.editTextNotes_vet.setVisibility(View.GONE);
        } else {
            holder.textViewNotes_vet.setVisibility(View.VISIBLE);
            holder.editTextNotes_vet.setVisibility(View.VISIBLE);
        }

        AppDatabase db = Room.databaseBuilder(holder.editTextName_vet.getContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOVets vetDao = db.daoVets();
        DAOVisits daoVisits = db.daoVisits();

        holder.editTextName_vet.setEnabled(false);
        holder.editTextAddress_vet.setEnabled(false);
        holder.editTextPhone_vet.setEnabled(false);
        holder.editTextNotes_vet.setEnabled(false);
        holder.ListViewVet.setVisibility(View.GONE);
        holder.textViewRodentRelationsInfo_vet.setVisibility(View.GONE);
        holder.textViewRodentRelations_vet.setVisibility(View.GONE);
        holder.textViewRodentRelations_vet.setText("");

        holder.checkBoxVet.setVisibility(View.GONE);
        holder.buttonAdd_vet.setVisibility(View.GONE);
        holder.buttonSaveEdit_vet.setVisibility(View.GONE);


        holder.editTextName_vet.setText(vetModel.get(position).vetModel.getName());
        holder.editTextAddress_vet.setText(vetModel.get(position).vetModel.getAddress());
        holder.editTextPhone_vet.setText(vetModel.get(position).vetModel.getPhone_number());
        holder.editTextNotes_vet.setText(vetModel.get(position).vetModel.getNotes());



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

        holder.imageButtonCall_vet.setOnClickListener(view -> makeACall(holder.editTextPhone_vet.getText().toString(),
                holder.imageButtonCall_vet));

        holder.buttonDelete_vet.setOnClickListener(view -> onClickDelete(holder.buttonDelete_vet.getContext(), vetDao, daoVisits, holder));

        holder.buttonEdit_vet.setOnClickListener(view -> onClickEdit(vetDao, holder));


        holder.arrayListSelected.clear();
        db.close();
    }

    private void onClickDelete(Context context, DAOVets vetDao, DAOVisits daoVisits, viewHolder holder) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Usuwanie weterynarza");
        alert.setMessage("Czy na pewno chcesz usunąć weterynarza z listy?\n\nProces jest nieodwracalny!");
        alert.setPositiveButton("Tak", (dialogInterface, i) -> {
            Toast.makeText(context, "Pomyślnie usunięto", Toast.LENGTH_SHORT).show();

            //vetDao.DeleteAllRodentsVetsByVet(vetModel.get(holder.getAdapterPosition()).getId());

            daoVisits.SetVisitsIdVetNull(vetModel.get(holder.getAdapterPosition()).vetModel.getId_vet());
            vetDao.DeleteAllRodentsVetsByVet(vetModel.get(holder.getAdapterPosition()).vetModel.getId_vet());
            vetDao.deleteVetById(vetModel.get(holder.getAdapterPosition()).vetModel.getId());

            //vetDao.SetVisitsIdVetNull(vetModel.get(holder.getAdapterPosition()).getId());
            vetModel.remove(holder.getAdapterPosition());

            notifyDataSetChanged();
        });
        alert.setNegativeButton("Nie", (dialogInterface, i) ->
                Toast.makeText(context, "Anulowano", Toast.LENGTH_SHORT).show());
        alert.create().show();
    }

    private void onClickEdit(DAOVets vetDao, viewHolder holder) {
        Intent intent = new Intent(new Intent(holder.buttonEdit_vet.getContext(), AddEditVets.class));
        intent.putExtra("idKey",String.valueOf(vetModel.get(holder.getAdapterPosition()).vetModel.getId()));
        intent.putExtra("nameKey",String.valueOf(vetModel.get(holder.getAdapterPosition()).vetModel.getName()));
        intent.putExtra("addressKey",String.valueOf(vetModel.get(holder.getAdapterPosition()).vetModel.getAddress()));
        intent.putExtra("phoneKey",String.valueOf(vetModel.get(holder.getAdapterPosition()).vetModel.getPhone_number()));
        intent.putExtra("notesKey",String.valueOf(vetModel.get(holder.getAdapterPosition()).vetModel.getNotes()));

        intent.putExtra("positionKey",String.valueOf(vetDao.getRealPositionFromVet( vetModel.get(holder.getAdapterPosition()).vetModel.getId()) -1 ));
        //0 = edit
        FlagSetup.setFlagVetAdd(0);

        holder.buttonEdit_vet.getContext().startActivity(intent);
        ((Activity)holder.buttonEdit_vet.getContext()).finish();
    }

    private void makeACall(String phoneNumber, ImageButton buttonCall) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        buttonCall.getContext().startActivity(intent);
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
           Button buttonAdd_vet, buttonSaveEdit_vet;
           ImageView buttonEdit_vet, buttonDelete_vet;
           ListView ListViewVet;
           TextView textViewRodentRelations_vet, textViewRodentRelationsInfo_vet,
                   textViewAddress_vet, textViewPhone_vet, textViewNotes_vet;
           CheckBox checkBoxVet;
           ImageButton imageButtonCall_vet;

           //lista z querry join
           private final ArrayList<String> arrayListSelected;



          public viewHolder(@NonNull @NotNull View itemView) {
               super(itemView);

               editTextName_vet = itemView.findViewById(R.id.editTextName_vet);
               editTextAddress_vet = itemView.findViewById(R.id.editTextAddress_vet);
               editTextPhone_vet = itemView.findViewById(R.id.editTextPhone_vet);
               editTextNotes_vet = itemView.findViewById(R.id.editTextNotes_vet);

              textViewAddress_vet = itemView.findViewById(R.id.textViewAddress_vet);
              textViewPhone_vet = itemView.findViewById(R.id.textViewPhone_vet);
              textViewNotes_vet = itemView.findViewById(R.id.textViewNotes_vet);


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

               imageButtonCall_vet = itemView.findViewById(R.id.imageButtonCall_vet);

               arrayListSelected = new ArrayList<>();


           }
       }
}
