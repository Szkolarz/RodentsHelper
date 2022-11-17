package com.example.rodentshelper.ROOM.Visits;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAO;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterVisits extends RecyclerView.Adapter<AdapterVisits.viewHolder>
{
    List<VisitModel> visitModel;

    List<String> aaa;
    private boolean flag = false;

    public AdapterVisits(List<VisitModel> visitModel) {
        this.visitModel = visitModel;
    }

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.visits_item_list,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position) {

        holder.editTextReason_visit.setEnabled(false);
        holder.textViewTime_visit.setEnabled(false);
        holder.listViewVisit.setVisibility(View.GONE);

        holder.textViewVetRelationsInfo_visit.setVisibility(View.GONE);
        holder.textViewVetRelations_visit.setVisibility(View.GONE);

        holder.checkBoxVisit1.setVisibility(View.GONE);
        holder.checkBoxVisit2.setVisibility(View.GONE);
        holder.buttonAdd_visit.setVisibility(View.GONE);
        holder.buttonSaveEdit_visit.setVisibility(View.GONE);

        //holder.imageViewDate1_med.setVisibility(View.GONE);
        //holder.imageViewDate2_med.setVisibility(View.GONE);


        holder.editTextReason_visit.setText(visitModel.get(position).getReason());
        holder.textViewTime_visit.setText(visitModel.get(position).getTime());


        if (visitModel.get(position).getDate() == null)
            holder.textViewDate_visit.setText("nie podano");
        else
            holder.textViewDate_visit.setText(visitModel.get(position).getDate().toString());



        AppDatabase db = Room.databaseBuilder(holder.editTextReason_visit.getContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAO dao = db.dao();

        if (flag == false) {
            aaa = dao.getAllNameVets();
            flag = true;
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(holder.listViewVisit.getContext(), android.R.layout.simple_list_item_multiple_choice, holder.arrayListSelected);
        holder.listViewVisit.setAdapter(adapter);


        System.out.println(visitModel.get(position).getId_vet() + "kj");

        if (visitModel.get(position).getId_vet() != null) {
            List<String> list = dao.getAllVisitsVets(visitModel.get(position).getId_vet());

            holder.textViewVetRelations_visit.setText(null);
            for (int j = 0; j < aaa.size(); j++) {
                holder.arrayListSelected.add(aaa.get(j));
                for (int i = 0; i < list.size(); i++) {

                    if (aaa.get(j).equals(list.get(i))) {

                        if ((i + 1) < list.size())
                            holder.textViewVetRelations_visit.append(list.get(i) + "\n");
                        else
                            holder.textViewVetRelations_visit.append(list.get(i));

                        holder.textViewVetRelationsInfo_visit.setVisibility(View.VISIBLE);
                        holder.textViewVetRelations_visit.setVisibility(View.VISIBLE);
                    }
                }
            }
        }



        holder.buttonDelete_visit.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  deleteVisit(holder.buttonDelete_visit.getContext(), holder);
              }
        });

        holder.buttonEdit_visit.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent intent = new Intent(new Intent(holder.buttonEdit_visit.getContext(), AddVisits.class));
                  intent.putExtra("idKey",String.valueOf(visitModel.get(holder.getAdapterPosition()).getId()));
                  intent.putExtra("id_vetKey",String.valueOf(visitModel.get(holder.getAdapterPosition()).getId_vet()));
                  intent.putExtra("dateKey",String.valueOf(visitModel.get(holder.getAdapterPosition()).getDate()));
                  intent.putExtra("timeKey",String.valueOf(visitModel.get(holder.getAdapterPosition()).getTime()));
                  intent.putExtra("reasonKey",String.valueOf(visitModel.get(holder.getAdapterPosition()).getReason()));

                  //0 = edit
                  FlagSetup.setFlagVisitAdd(0);
                  holder.buttonEdit_visit.getContext().startActivity(intent);
              }
        });

        holder.arrayListSelected.clear();
        db.close();
    }

    /** usuwanie **/
    private void deleteVisit(Context context, viewHolder holder) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Usuwanie wizyty");
        alert.setMessage("Czy na pewno chcesz usunąć wizytę z listy?\n\nProces jest nieodwracalny!");
        alert.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Pomyślnie usunięto", Toast.LENGTH_SHORT).show();
                AppDatabase db = Room.databaseBuilder(context,
                        AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
                DAO visitDao = db.dao();

                visitDao.deleteVisitById(visitModel.get(holder.getAdapterPosition()).getId());

                visitModel.remove(holder.getAdapterPosition());

                Intent intent = new Intent(context, ViewVisits.class);
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
        return visitModel.size();
    }


    private void checkCheckBox(CheckBox checkBoxVet, ListView listViewVisit, TextView textViewVetRelations_visit, TextView textViewVetRelationsInfo_visit) {
        if (textViewVetRelations_visit.getText() != "") {
            listViewVisit.setVisibility(View.GONE);
            listViewVisit.setSelected(true);

            textViewVetRelations_visit.setVisibility(View.VISIBLE);
            textViewVetRelationsInfo_visit.setVisibility(View.VISIBLE);
        }
        else {
            listViewVisit.setVisibility(View.GONE);

            textViewVetRelations_visit.setVisibility(View.GONE);
            textViewVetRelationsInfo_visit.setVisibility(View.GONE);
        }
    }


    class viewHolder extends RecyclerView.ViewHolder
    {

           EditText editTextReason_visit;
           TextView textViewDate_visit, textViewTime_visit, textViewVetRelationsInfo_visit, textViewVetRelations_visit;
           Button buttonEdit_visit, buttonAdd_visit, buttonSaveEdit_visit, buttonDelete_visit;
           ListView listViewVisit;
           CheckBox checkBoxVisit1, checkBoxVisit2;


        private ArrayList<String> arrayListSelected;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            editTextReason_visit = itemView.findViewById(R.id.editTextReason_visit);

            textViewDate_visit = itemView.findViewById(R.id.textViewDate_visit);
            textViewTime_visit = itemView.findViewById(R.id.textViewTime_visit);

            buttonEdit_visit = itemView.findViewById(R.id.buttonEdit_visit);
            buttonAdd_visit = itemView.findViewById(R.id.buttonAdd_visit);
            buttonSaveEdit_visit = itemView.findViewById(R.id.buttonSaveEdit_visit);
            buttonDelete_visit = itemView.findViewById(R.id.buttonDelete_visit);

            listViewVisit = itemView.findViewById(R.id.listViewVisit);
            listViewVisit.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            listViewVisit.setItemsCanFocus(false);

            checkBoxVisit1 = itemView.findViewById(R.id.checkBoxVisit1);
            checkBoxVisit2 = itemView.findViewById(R.id.checkBoxVisit2);
            textViewVetRelationsInfo_visit = itemView.findViewById(R.id.textViewVetRelationsInfo_visit);
            textViewVetRelations_visit = itemView.findViewById(R.id.textViewVetRelations_visit);

            arrayListSelected = new ArrayList<>();

           }
       }
}
