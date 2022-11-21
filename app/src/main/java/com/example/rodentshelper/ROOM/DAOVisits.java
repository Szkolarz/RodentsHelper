package com.example.rodentshelper.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.example.rodentshelper.ROOM.Visits.VisitModel;

import java.sql.Date;
import java.util.List;

@Dao
public interface DAOVisits {

    @Insert
    void insertRecordVisit(VisitModel visits);

    @Query("SELECT * FROM visits")
    List<VisitModel> getAllVisits();

    @Query("DELETE FROM visits WHERE id = :id")
    void deleteVisitById(Integer id);

    @TypeConverters(Converters.class)
    @Query("UPDATE visits SET id_vet = :id_vet, date = :date, time = :time, reason = :reason WHERE id = :id")
    void updateVisitById(Integer id, Integer id_vet, Date date, String time, String reason);

    @Query("SELECT id_medicament FROM medicaments WHERE id_medicament = (SELECT MAX(id_medicament) FROM medicaments)")
    List<Integer> getLastIdVisit();

    @Query("SELECT vets.name FROM vets WHERE vets.id_vet = :id")
    List<String> getAllVisitsVets(Integer id);

    @Query ("UPDATE visits SET id_vet = NULL WHERE id_vet = :id_vet")
    void SetVisitsIdVetNull(Integer id_vet);

    @Query ("UPDATE visits SET id_vet = :id_vet WHERE id = :id")
    void SetVisitsIdVet(Integer id_vet, Integer id);

}


