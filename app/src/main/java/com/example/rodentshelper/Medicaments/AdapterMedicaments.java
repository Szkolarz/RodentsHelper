package com.example.rodentshelper.Medicaments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.MainViews.ViewRodents;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAO;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterMedicaments extends RecyclerView.Adapter<AdapterMedicaments.viewHolder>
{
    List<MedicamentModel> medicamentModel;

    List<String> aaa;
    private boolean flag = false;

    public AdapterMedicaments(List<MedicamentModel> medicamentModel) {
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

        holder.editTextName_med.setEnabled(false);
        holder.editTextDescription_med.setEnabled(false);
        holder.editTextPeriodicity_med.setEnabled(false);
        holder.listViewMed.setVisibility(View.GONE);

        holder.checkBoxMed.setVisibility(View.GONE);
        holder.buttonAdd_med.setVisibility(View.GONE);
        holder.buttonSaveEdit_med.setVisibility(View.GONE);
        holder.imageViewDate1_med.setVisibility(View.GONE);
        holder.imageViewDate2_med.setVisibility(View.GONE);


        holder.editTextName_med.setText(medicamentModel.get(position).getName());
        holder.editTextDescription_med.setText(medicamentModel.get(position).getDescription());
        holder.editTextPeriodicity_med.setText(medicamentModel.get(position).getPeriodicity());

        if (medicamentModel.get(position).getDate_start() == null)
            holder.textViewDateStart_med.setText("nie podano");
        else
            holder.textViewDateStart_med.setText(medicamentModel.get(position).getDate_start().toString());

        if (medicamentModel.get(position).getDate_end() == null)
            holder.textViewDateEnd_med.setText("nie podano");
        else
            holder.textViewDateEnd_med.setText(medicamentModel.get(position).getDate_end().toString());





        AppDatabase db = Room.databaseBuilder(holder.editTextName_med.getContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAO dao = db.dao();

        if (flag == false) {
            aaa = dao.getAllNameRodents();
            flag = true;
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(holder.listViewMed.getContext(), android.R.layout.simple_list_item_multiple_choice, holder.arrayListSelected);
        holder.listViewMed.setAdapter(adapter);

        List<String> list = dao.getAllRodentsMeds(medicamentModel.get(position).getId());


        for (int j = 0; j < aaa.size(); j ++) {
            holder.arrayListSelected.add(aaa.get(j));
            for(int i = 0; i < list.size(); i++) {

                if (aaa.get(j).equals(list.get(i))) {

                    if ((i + 1) < list.size())
                        holder.textViewRodentRelations_med.append(list.get(i) + "\n");
                    else
                        holder.textViewRodentRelations_med.append(list.get(i));

                    holder.listViewMed.setItemChecked(i, true);
                    holder.checkBoxMed.setChecked(true);
                }
            }
        }

        checkCheckBox(holder.checkBoxMed, holder.listViewMed,
                holder.textViewRodentRelations_med, holder.textViewRodentRelationsInfo_med);


        holder.checkBoxMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCheckBox(holder.checkBoxMed, holder.listViewMed,
                        holder.textViewRodentRelations_med, holder.textViewRodentRelationsInfo_med);
            }
        });


        holder.buttonDelete_med.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  deleteMed(holder.buttonDelete_med.getContext(), holder);
              }
        });

         holder.buttonEdit_med.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent intent = new Intent(new Intent(holder.buttonEdit_med.getContext(), AddMedicaments.class));
                  intent.putExtra("idKey",String.valueOf(medicamentModel.get(holder.getAdapterPosition()).getId()));
                  intent.putExtra("id_vetKey",String.valueOf(medicamentModel.get(holder.getAdapterPosition()).getId_vet()));
                  intent.putExtra("nameKey",String.valueOf(medicamentModel.get(holder.getAdapterPosition()).getName()));
                  intent.putExtra("descriptionKey",String.valueOf(medicamentModel.get(holder.getAdapterPosition()).getDescription()));
                  intent.putExtra("periodicityKey",String.valueOf(medicamentModel.get(holder.getAdapterPosition()).getPeriodicity()));
                  intent.putExtra("date_startKey",String.valueOf(medicamentModel.get(holder.getAdapterPosition()).getDate_start()));
                  intent.putExtra("date_endKey",String.valueOf(medicamentModel.get(holder.getAdapterPosition()).getDate_end()));

                  //0 = edit
                  FlagSetup.setFlagMedAdd(0);
                  holder.buttonEdit_med.getContext().startActivity(intent);
              }
          });

    }

    /** usuwanie **/
    private void deleteMed(Context context, viewHolder holder) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Usuwanie lekarstwa");
        alert.setMessage("Czy na pewno chcesz usunąć lek z listy?\n\nProces jest nieodwracalny!");
        alert.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Pomyślnie usunięto", Toast.LENGTH_SHORT).show();
                AppDatabase db = Room.databaseBuilder(context,
                        AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
                DAO medDao = db.dao();

                medDao.DeleteAllRodentsMedsByMed(medicamentModel.get(holder.getAdapterPosition()).getId());
                medDao.deleteMedById(medicamentModel.get(holder.getAdapterPosition()).getId());

                medicamentModel.remove(holder.getAdapterPosition());

                Intent intent = new Intent(context, ViewMedicaments.class);
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
        return medicamentModel.size();
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

           EditText editTextName_med, editTextDescription_med, editTextPeriodicity_med;
           TextView textViewDateStart_med, textViewDateEnd_med, textViewRodentRelations_med, textViewRodentRelationsInfo_med;
           Button buttonEdit_med, buttonAdd_med, buttonSaveEdit_med, buttonDelete_med;
           ImageView imageViewDate1_med, imageViewDate2_med;
           ListView listViewMed;
           CheckBox checkBoxMed;


        private ArrayList<String> arrayListSelected;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            editTextName_med = itemView.findViewById(R.id.editTextName_med);
            editTextDescription_med = itemView.findViewById(R.id.editTextDescription_med);
            editTextPeriodicity_med = itemView.findViewById(R.id.editTextPeriodicity_med);

            textViewDateStart_med = itemView.findViewById(R.id.textViewDateStart_med);
            textViewDateEnd_med = itemView.findViewById(R.id.textViewDateEnd_med);
            imageViewDate1_med = itemView.findViewById(R.id.imageViewDate1_med);
            imageViewDate2_med = itemView.findViewById(R.id.imageViewDate2_med);

            buttonEdit_med = itemView.findViewById(R.id.buttonEdit_med);
            buttonAdd_med = itemView.findViewById(R.id.buttonAdd_med);
            buttonSaveEdit_med = itemView.findViewById(R.id.buttonSaveEdit_med);
            buttonDelete_med = itemView.findViewById(R.id.buttonDelete_med);

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
