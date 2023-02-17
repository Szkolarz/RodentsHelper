package com.gryzoniopedia.rodentshelper.ROOM.Medicaments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.gryzoniopedia.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;
import com.gryzoniopedia.rodentshelper.ROOM.AppDatabase;
import com.gryzoniopedia.rodentshelper.ROOM.DAOMedicaments;
import com.gryzoniopedia.rodentshelper.ROOM._MTM._RodentMed.MedicamentWithRodentsCrossRef;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterMedicaments extends RecyclerView.Adapter<AdapterMedicaments.viewHolder>
{
    private final List<MedicamentWithRodentsCrossRef> medicamentModel;

    public AdapterMedicaments(List<MedicamentWithRodentsCrossRef> medicamentModel) {
        this.medicamentModel = medicamentModel;
    }

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicaments_item_list,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position) {

        //have to be resetted, cause recyclerview messes while scrolling
        holder.textViewDescription_med.setVisibility(View.VISIBLE);
        holder.editTextDescription_med.setVisibility(View.VISIBLE);
        holder.textViewPeriodicity_med.setVisibility(View.VISIBLE);
        holder.editTextPeriodicity_med.setVisibility(View.VISIBLE);


        if (medicamentModel.get(position).medicamentModel.getDescription().equals("")) {
            holder.textViewDescription_med.setVisibility(View.GONE);
            holder.editTextDescription_med.setVisibility(View.GONE);
        } else {
            holder.textViewDescription_med.setVisibility(View.VISIBLE);
            holder.editTextDescription_med.setVisibility(View.VISIBLE);
        }

        if (medicamentModel.get(position).medicamentModel.getPeriodicity().equals("")) {
            holder.textViewPeriodicity_med.setVisibility(View.GONE);
            holder.editTextPeriodicity_med.setVisibility(View.GONE);
        } else {
            holder.textViewPeriodicity_med.setVisibility(View.VISIBLE);
            holder.editTextPeriodicity_med.setVisibility(View.VISIBLE);
        }


        AppDatabase db = Room.databaseBuilder(holder.editTextName_med.getContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOMedicaments daoMedicaments = db.daoMedicaments();

        holder.editTextName_med.setEnabled(false);
        holder.editTextDescription_med.setEnabled(false);
        holder.editTextPeriodicity_med.setEnabled(false);
        holder.listViewMed.setVisibility(View.GONE);
        holder.textViewRodentRelationsInfo_med.setVisibility(View.GONE);
        holder.textViewRodentRelations_med.setVisibility(View.GONE);
        holder.textViewRodentRelations_med.setText("");

        holder.checkBoxMed.setVisibility(View.GONE);
        holder.buttonAdd_med.setVisibility(View.GONE);
        holder.buttonSaveEdit_med.setVisibility(View.GONE);
        holder.imageButtonDate_med1.setVisibility(View.GONE);
        holder.imageButtonDate_med2.setVisibility(View.GONE);


        holder.editTextName_med.setText(medicamentModel.get(position).medicamentModel.getName());
        holder.editTextDescription_med.setText(medicamentModel.get(position).medicamentModel.getDescription());
        holder.editTextPeriodicity_med.setText(medicamentModel.get(position).medicamentModel.getPeriodicity());

        if (medicamentModel.get(position).medicamentModel.getDate_start() == null)
            holder.textViewDateStart_med.setText("nie podano");
        else
            holder.textViewDateStart_med.setText(medicamentModel.get(position).medicamentModel.getDate_start().toString());

        if (medicamentModel.get(position).medicamentModel.getDate_end() == null)
            holder.textViewDateEnd_med.setText("nie podano");
        else
            holder.textViewDateEnd_med.setText(medicamentModel.get(position).medicamentModel.getDate_end().toString());



      //  holder.textViewRodentRelations_med.setText(null);
        try {
            for (int i = 0; i < medicamentModel.get(position).rodents.size(); i++) {

                if ((i + 1) < medicamentModel.get(position).rodents.size())
                    holder.textViewRodentRelations_med.append(medicamentModel.get(position).rodents.get(i).getName() + "\n");
                else
                    holder.textViewRodentRelations_med.append(medicamentModel.get(position).rodents.get(i).getName());
                holder.textViewRodentRelationsInfo_med.setVisibility(View.VISIBLE);
                holder.textViewRodentRelations_med.setVisibility(View.VISIBLE);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("There is no any rodent left in relation");
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(holder.listViewMed.getContext(), android.R.layout.simple_list_item_multiple_choice, holder.arrayListSelected);
        holder.listViewMed.setAdapter(adapter);


        holder.buttonDelete_medicament.setOnClickListener(view -> onClickDeleteMed(holder.buttonDelete_medicament.getContext(), holder, daoMedicaments));

        holder.buttonEdit_medicament.setOnClickListener(view -> onClickEditMed(daoMedicaments, holder));


        holder.arrayListSelected.clear();
        db.close();
    }

    private void onClickEditMed(DAOMedicaments daoMedicaments, viewHolder holder) {
        Intent intent = new Intent(new Intent(holder.buttonEdit_medicament.getContext(), AddEditMedicaments.class));
        intent.putExtra("idKey",String.valueOf(medicamentModel.get(holder.getAdapterPosition()).medicamentModel.getId_medicament()));
        intent.putExtra("id_vetKey",String.valueOf(medicamentModel.get(holder.getAdapterPosition()).medicamentModel.getId_vet()));
        intent.putExtra("nameKey",String.valueOf(medicamentModel.get(holder.getAdapterPosition()).medicamentModel.getName()));
        intent.putExtra("descriptionKey",String.valueOf(medicamentModel.get(holder.getAdapterPosition()).medicamentModel.getDescription()));
        intent.putExtra("periodicityKey",String.valueOf(medicamentModel.get(holder.getAdapterPosition()).medicamentModel.getPeriodicity()));
        intent.putExtra("date_startKey",String.valueOf(medicamentModel.get(holder.getAdapterPosition()).medicamentModel.getDate_start()));
        intent.putExtra("date_endKey",String.valueOf(medicamentModel.get(holder.getAdapterPosition()).medicamentModel.getDate_end()));

        intent.putExtra("positionKey",String.valueOf(daoMedicaments.getRealPositionFromMed(medicamentModel.get(holder.getAdapterPosition()).medicamentModel.getId_medicament()) -1 ));
        //0 = edit
        FlagSetup.setFlagMedAdd(0);
        holder.buttonEdit_medicament.getContext().startActivity(intent);
        ((Activity)holder.buttonEdit_medicament.getContext()).finish();
    }

    /** usuwanie **/
    private void onClickDeleteMed(Context context, viewHolder holder, DAOMedicaments daoMedicaments) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Usuwanie lekarstwa");
        alert.setMessage("Czy na pewno chcesz usunąć lek z listy?\n\nProces jest nieodwracalny!");
        alert.setPositiveButton("Tak", (dialogInterface, i) -> {
            Toast.makeText(context, "Pomyślnie usunięto", Toast.LENGTH_SHORT).show();

            daoMedicaments.DeleteAllRodentsMedsByMed(medicamentModel.get(holder.getAdapterPosition()).medicamentModel.getId_medicament());
            daoMedicaments.deleteMedById(medicamentModel.get(holder.getAdapterPosition()).medicamentModel.getId_medicament());

            medicamentModel.remove(holder.getAdapterPosition());

            notifyDataSetChanged();
        });
        alert.setNegativeButton("Nie", (dialogInterface, i) ->
                Toast.makeText(context, "Anulowano", Toast.LENGTH_SHORT).show());
        alert.create().show();
    }

    @Override
    public int getItemCount() {
        return medicamentModel.size();
    }



    static class viewHolder extends RecyclerView.ViewHolder
    {

           EditText editTextName_med, editTextDescription_med, editTextPeriodicity_med;
           TextView textViewDateStart_med, textViewDateEnd_med, textViewRodentRelations_med, textViewRodentRelationsInfo_med,
                   textViewDescription_med, textViewPeriodicity_med, textViewDate1_med, textViewDate2_med;
           Button buttonAdd_med, buttonSaveEdit_med;
           ImageView buttonEdit_medicament, buttonDelete_medicament, imageButtonDate_med1, imageButtonDate_med2;
           ListView listViewMed;
           CheckBox checkBoxMed;


        private final ArrayList<String> arrayListSelected;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            editTextName_med = itemView.findViewById(R.id.editTextName_med);
            editTextDescription_med = itemView.findViewById(R.id.editTextDescription_med);
            editTextPeriodicity_med = itemView.findViewById(R.id.editTextPeriodicity_med);

            textViewDescription_med = itemView.findViewById(R.id.textViewDescription_med);
            textViewPeriodicity_med = itemView.findViewById(R.id.textViewPeriodicity_med);
            textViewDate1_med = itemView.findViewById(R.id.textViewDate1_med);
            textViewDate2_med = itemView.findViewById(R.id.textViewDate2_med);

            textViewDateStart_med = itemView.findViewById(R.id.textViewDateStart_med);
            textViewDateEnd_med = itemView.findViewById(R.id.textViewDateEnd_med);
            imageButtonDate_med1 = itemView.findViewById(R.id.imageButtonDate_med1);
            imageButtonDate_med2 = itemView.findViewById(R.id.imageButtonDate_med2);


            buttonEdit_medicament = itemView.findViewById(R.id.buttonEdit_medicament);
            buttonAdd_med = itemView.findViewById(R.id.buttonAdd_med);
            buttonSaveEdit_med = itemView.findViewById(R.id.buttonSaveEdit_med);
            buttonDelete_medicament = itemView.findViewById(R.id.buttonDelete_medicament);

            listViewMed = itemView.findViewById(R.id.listViewMed);
            listViewMed.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            listViewMed.setItemsCanFocus(false);

            checkBoxMed = itemView.findViewById(R.id.checkBoxMed);
            textViewRodentRelations_med = itemView.findViewById(R.id.textViewRodentRelations_med);
            textViewRodentRelationsInfo_med = itemView.findViewById(R.id.textViewRodentRelationsInfo_med);

            arrayListSelected = new ArrayList<>();

           }
       }
}
