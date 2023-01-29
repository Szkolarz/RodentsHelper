package com.android.rodentshelper.ROOM.Visits;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.rodentshelper.ROOM.DAONotifications;
import com.android.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;
import com.android.rodentshelper.ROOM.AppDatabase;
import com.android.rodentshelper.ROOM.DAOVisits;
import com.android.rodentshelper.ROOM._MTM._RodentVisit.VisitsWithRodentsCrossRef;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterVisits extends RecyclerView.Adapter<AdapterVisits.viewHolder>
{
    private final List<VisitsWithRodentsCrossRef> visitModel;


    public AdapterVisits(List<VisitsWithRodentsCrossRef> visitModel) {
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

        if (visitModel.get(position).visitModel.getTime().equals("Ustaw...")) {
            holder.textViewTimeInfo_visit.setVisibility(View.GONE);
            holder.textViewTime_visit.setVisibility(View.GONE);
        }
        if (visitModel.get(position).visitModel.getReason().equals("")) {
            holder.textViewReasonInfo_visit.setVisibility(View.GONE);
            holder.editTextReason_visit.setVisibility(View.GONE);
        }

        AppDatabase db = Room.databaseBuilder(holder.editTextReason_visit.getContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOVisits daoVisits = db.daoVisits();
        DAONotifications daoNotifications = db.daoNotifications();

        try {
            if (daoNotifications.getIdVisitFromVisit(visitModel.get(position).visitModel.getId_visit()) != null) {
                holder.textViewNotificationSet_visit.setVisibility(View.VISIBLE);
                holder.textViewNotificationSet_visit.append(daoNotifications.getSendTimeFromNotificationVisit(visitModel.get(position).visitModel.getId_visit()));
            }
        }catch (NullPointerException e) {
            System.out.println(e);
        }

        holder.editTextReason_visit.setEnabled(false);
        holder.textViewTime_visit.setEnabled(false);
        holder.listViewVisit.setVisibility(View.GONE);

        holder.textViewVetRelationsInfo_visit.setVisibility(View.GONE);
        holder.textViewVetRelations_visit.setVisibility(View.GONE);
        holder.textViewVetRelations_visit.setText("");
        holder.textViewVetRelationsInfo_visit2.setVisibility(View.GONE);
        holder.textViewVetRelations_visit2.setVisibility(View.GONE);
        holder.textViewVetRelations_visit2.setText("");
        holder.checkBoxVisit1.setVisibility(View.GONE);
        holder.checkBoxVisit2.setVisibility(View.GONE);
        holder.checkBoxVisit3.setVisibility(View.GONE);
        holder.buttonAdd_visit.setVisibility(View.GONE);
        holder.buttonSaveEdit_visit.setVisibility(View.GONE);

        //holder.imageViewDate1_med.setVisibility(View.GONE);
        //holder.imageViewDate2_med.setVisibility(View.GONE);


        holder.editTextReason_visit.setText(visitModel.get(position).visitModel.getReason());
        holder.textViewTime_visit.setText(visitModel.get(position).visitModel.getTime());


        if (visitModel.get(position).visitModel.getDate() == null)
            holder.textViewDate_visit.setText("nie podano");
        else
            holder.textViewDate_visit.setText(visitModel.get(position).visitModel.getDate().toString());




        ArrayAdapter<String> adapter = new ArrayAdapter<>(holder.listViewVisit.getContext(), android.R.layout.simple_list_item_multiple_choice, holder.arrayListSelected);
        holder.listViewVisit.setAdapter(adapter);



        try {
            for (int i = 0; i < visitModel.get(position).rodents.size(); i++) {
                if ((i + 1) < visitModel.get(position).rodents.size())
                    holder.textViewVetRelations_visit2.append(visitModel.get(position).rodents.get(i).getName() + "\n");
                else
                    holder.textViewVetRelations_visit2.append(visitModel.get(position).rodents.get(i).getName());
                holder.textViewVetRelationsInfo_visit2.setVisibility(View.VISIBLE);
                holder.textViewVetRelations_visit2.setVisibility(View.VISIBLE);

               // holder.textViewVetRelations_visit.append(daoVisits.getVetByVisitId(visitModel.get(position).visitModel.getId_vet()));
            }



        } catch (NullPointerException | IndexOutOfBoundsException e) {
            System.out.println("There is no any rodent left in relation");
        }


        if (visitModel.get(position).visitModel.getId_vet() != null) {
            holder.textViewVetRelationsInfo_visit.setVisibility(View.VISIBLE);
            holder.textViewVetRelations_visit.setVisibility(View.VISIBLE);

            holder.textViewVetRelations_visit.append(daoVisits.getVetByVisitId(visitModel.get(position).visitModel.getId_vet()));
        }


        holder.buttonDelete_visit.setOnClickListener(view -> deleteVisit(holder.buttonDelete_visit.getContext(), holder, daoVisits));

        holder.buttonEdit_visit.setOnClickListener(view -> {
            Intent intent = new Intent(new Intent(holder.buttonEdit_visit.getContext(), AddEditVisits.class));
            intent.putExtra("idKey",String.valueOf(visitModel.get(holder.getAdapterPosition()).visitModel.getId_visit()));
            intent.putExtra("id_vetKey",String.valueOf(visitModel.get(holder.getAdapterPosition()).visitModel.getId_vet()));
            intent.putExtra("dateKey",String.valueOf(visitModel.get(holder.getAdapterPosition()).visitModel.getDate()));
            intent.putExtra("timeKey",String.valueOf(visitModel.get(holder.getAdapterPosition()).visitModel.getTime()));
            intent.putExtra("reasonKey",String.valueOf(visitModel.get(holder.getAdapterPosition()).visitModel.getReason()));

            intent.putExtra("positionKey",String.valueOf(daoVisits.getRealPositionFromVisit(visitModel.get(holder.getAdapterPosition()).visitModel.getId_visit()) -1 ));
            //0 = edit
            FlagSetup.setFlagVisitAdd(0);
            holder.buttonEdit_visit.getContext().startActivity(intent);
            ((Activity)holder.buttonEdit_visit.getContext()).finish();
        });

        holder.arrayListSelected.clear();
        db.close();
    }

    /** usuwanie **/
    private void deleteVisit(Context context, viewHolder holder, DAOVisits daoVisits) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Usuwanie wizyty");
        alert.setMessage("Czy na pewno chcesz usunąć wizytę z listy?\n\nProces jest nieodwracalny!");
        alert.setPositiveButton("Tak", (dialogInterface, i) -> {
            Toast.makeText(context, "Pomyślnie usunięto", Toast.LENGTH_SHORT).show();

            daoVisits.DeleteAllRodentsVisitsByVisit(visitModel.get(holder.getAdapterPosition()).visitModel.getId_visit());
            daoVisits.deleteVisitById(visitModel.get(holder.getAdapterPosition()).visitModel.getId_visit());

            visitModel.remove(holder.getAdapterPosition());

            notifyDataSetChanged();
        });
        alert.setNegativeButton("Nie", (dialogInterface, i) -> Toast.makeText(context, "Anulowano", Toast.LENGTH_SHORT).show());
        alert.create().show();
    }

    @Override
    public int getItemCount() {
        return visitModel.size();
    }



    static class viewHolder extends RecyclerView.ViewHolder
    {
           EditText editTextReason_visit;
           TextView textViewDate_visit, textViewTime_visit, textViewVetRelationsInfo_visit, textViewVetRelations_visit,
                    textViewVetRelationsInfo_visit2, textViewVetRelations_visit2, textViewNotificationSet_visit,
                    textViewReasonInfo_visit, textViewTimeInfo_visit;
           Button buttonEdit_visit, buttonAdd_visit, buttonSaveEdit_visit, buttonDelete_visit;
           ListView listViewVisit, listViewVisit2;
           CheckBox checkBoxVisit1, checkBoxVisit2, checkBoxVisit3;


        private final ArrayList<String> arrayListSelected;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            editTextReason_visit = itemView.findViewById(R.id.editTextReason_visit);

            textViewTimeInfo_visit = itemView.findViewById(R.id.textViewTimeInfo_visit);
            textViewReasonInfo_visit = itemView.findViewById(R.id.textViewReasonInfo_visit);

            textViewDate_visit = itemView.findViewById(R.id.textViewDate_visit);
            textViewTime_visit = itemView.findViewById(R.id.textViewTime_visit);

            buttonEdit_visit = itemView.findViewById(R.id.buttonEdit_visit);
            buttonAdd_visit = itemView.findViewById(R.id.buttonAdd_visit);
            buttonSaveEdit_visit = itemView.findViewById(R.id.buttonSaveEdit_visit);
            buttonDelete_visit = itemView.findViewById(R.id.buttonDelete_visit);

            listViewVisit = itemView.findViewById(R.id.listViewVisit);
            listViewVisit.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            listViewVisit.setItemsCanFocus(false);
            listViewVisit2 = itemView.findViewById(R.id.listViewVisit2);
            listViewVisit2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            listViewVisit2.setItemsCanFocus(false);

            checkBoxVisit1 = itemView.findViewById(R.id.checkBoxVisit1);
            checkBoxVisit2 = itemView.findViewById(R.id.checkBoxVisit2);
            checkBoxVisit3 = itemView.findViewById(R.id.checkBoxVisit3);

            textViewVetRelationsInfo_visit = itemView.findViewById(R.id.textViewVetRelationsInfo_visit);
            textViewVetRelations_visit = itemView.findViewById(R.id.textViewVetRelations_visit);
            textViewVetRelationsInfo_visit2 = itemView.findViewById(R.id.textViewVetRelationsInfo_visit2);
            textViewVetRelations_visit2 = itemView.findViewById(R.id.textViewVetRelations_visit2);

            textViewNotificationSet_visit = itemView.findViewById(R.id.textViewNotificationSet_visit);

            arrayListSelected = new ArrayList<>();

           }
       }
}
