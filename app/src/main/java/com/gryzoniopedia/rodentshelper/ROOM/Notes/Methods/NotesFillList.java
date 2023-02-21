package com.gryzoniopedia.rodentshelper.ROOM.Notes.Methods;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.widget.CompoundButtonCompat;
import androidx.room.Room;

import com.example.rodentshelper.R;
import com.gryzoniopedia.rodentshelper.ROOM.AppDatabase;
import com.gryzoniopedia.rodentshelper.ROOM.DAONotes;

import java.util.List;

public class NotesFillList {

    public List getList (Context context) {

        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAONotes dao = db.daoNotes();
        db.close();

        SharedPreferences prefsGetRodentId = context.getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);

        SharedPreferences spNotesRadioOrder = context.getSharedPreferences("spNotesRadioOrder", MODE_PRIVATE);
        SharedPreferences spNotesRadioDisplay = context.getSharedPreferences("spNotesRadioDisplay", MODE_PRIVATE);

        if (spNotesRadioOrder.getInt("spNotesRadioOrder", 1) == 1) {
            if (spNotesRadioDisplay.getInt("spNotesRadioDisplay", 1) == 1)
                return dao.getRodentWithNotesDESC(prefsGetRodentId.getInt("rodentId", 0));
            if (spNotesRadioDisplay.getInt("spNotesRadioDisplay", 1) == 2)
                return dao.getRodentWithNotes1MonthDESC(prefsGetRodentId.getInt("rodentId", 0));
            if (spNotesRadioDisplay.getInt("spNotesRadioDisplay", 1) == 3)
                return dao.getRodentWithNotes3MonthsDESC(prefsGetRodentId.getInt("rodentId", 0));
            if (spNotesRadioDisplay.getInt("spNotesRadioDisplay", 1) == 4)
                return dao.getRodentWithNotes6MonthsDESC(prefsGetRodentId.getInt("rodentId", 0));
            if (spNotesRadioDisplay.getInt("spNotesRadioDisplay", 1) == 5)
                return dao.getRodentWithNotes1YearDESC(prefsGetRodentId.getInt("rodentId", 0));
        }

        if (spNotesRadioOrder.getInt("spNotesRadioOrder", 1) == 2) {
            if (spNotesRadioDisplay.getInt("spNotesRadioDisplay", 1) == 1)
                return dao.getRodentWithNotesASC(prefsGetRodentId.getInt("rodentId", 0));
            if (spNotesRadioDisplay.getInt("spNotesRadioDisplay", 1) == 2)
                return dao.getRodentWithNotes1MonthASC(prefsGetRodentId.getInt("rodentId", 0));
            if (spNotesRadioDisplay.getInt("spNotesRadioDisplay", 1) == 3)
                return dao.getRodentWithNotes3MonthsASC(prefsGetRodentId.getInt("rodentId", 0));
            if (spNotesRadioDisplay.getInt("spNotesRadioDisplay", 1) == 4)
                return dao.getRodentWithNotes6MonthsASC(prefsGetRodentId.getInt("rodentId", 0));
            if (spNotesRadioDisplay.getInt("spNotesRadioDisplay", 1) == 5)
                return dao.getRodentWithNotes1YearASC(prefsGetRodentId.getInt("rodentId", 0));
        }


        db.close();
        //List<RodentWithNotes> notesModel = dao.getRodentWithNotes(prefsGetRodentId.getInt("rodentId", 0));
        return null;

    }
}
