package com.example.rodentshelper.MainViews.GoogleMaps;

import android.content.Context;

import androidx.room.Room;

import com.example.rodentshelper.Alerts;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAOVets;
import com.example.rodentshelper.ROOM.Vet.VetModel;

import java.util.List;

public class AddVetFromMap {

    public void onClickAddVet(String name, String address, String phone, Context context) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOVets daoVets = db.daoVets();

        List<VetModel> vetModel = daoVets.getAllVets();

        Boolean isAlreadyAdded = false;

        for (int i = 0; i < vetModel.size(); i++) {
            if (vetModel.get(i).getName().equals(name) && vetModel.get(i).getAddress().equals(address)
                    && vetModel.get(i).getPhone_number().equals(phone))
                isAlreadyAdded = true;
        }

        Alerts alert = new Alerts();
        if (isAlreadyAdded == false) {
            daoVets.insertRecordVet(new VetModel(name, address, phone, ""));
            alert.simpleInfo("Dodano weterynarza", "Pomyślnie dodano weterynarza do twojej listy!\n\n" +
                    "Listę weterynarzy możesz przejrzeć w: 'Zdrowie' > 'Weterynarz'.", context);
        } else {
            alert.simpleError("Weterynarz już istnieje", "Nie udało się dodać weterynarza do listy! Weterynarz z takimi " +
                    "danymi znajduje się już na twojej liście.\n\n" +
                    "Listę weterynarzy możesz przejrzeć w: 'Zdrowie' > 'Weterynarz'.", context);
        }


    }

}
