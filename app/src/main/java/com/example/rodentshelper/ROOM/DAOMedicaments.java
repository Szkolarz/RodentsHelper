package com.example.rodentshelper.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.TypeConverters;

import com.example.rodentshelper.ROOM.Medicaments.MedicamentModel;
import com.example.rodentshelper.ROOM._MTM._RodentMed.MedicamentWithRodentsCrossRef;
import com.example.rodentshelper.ROOM._MTM._RodentMed.RodentMedModel;

import java.sql.Date;
import java.util.List;

@Dao
public interface DAOMedicaments {

    @Insert
    void insertRecordMed(MedicamentModel medicaments);

    @Query("SELECT * FROM medicaments")
    List<MedicamentModel> getAllMedicaments();

    @Query("DELETE FROM medicaments WHERE id_medicament = :id")
    void deleteMedById(Integer id);

    @TypeConverters(Converters.class)
    @Query("UPDATE medicaments SET id_vet = :id_vet, name = :name, description = :description, periodicity = :periodicity, date_start = :date_start, date_end = :date_end WHERE id_medicament = :id")
    void updateMedById(Integer id, Integer id_vet, String name, String description, String periodicity, Date date_start, Date date_end);

    @Query("SELECT id_medicament FROM medicaments WHERE id_medicament = (SELECT MAX(id_medicament) FROM medicaments)")
    List<Integer> getLastIdMed();







    /** relations **/

    @Insert
    void insertRecordRodentMed(RodentMedModel rodents_medicaments);

    @Query ("DELETE FROM rodents_medicaments WHERE id_medicament = :id")
    void DeleteAllRodentsMedsByMed(Integer id);

    @Transaction
    @Query("SELECT * FROM medicaments")
    public List<MedicamentWithRodentsCrossRef> getMedsWithRodents();

    @Transaction
    @Query ("SELECT medicaments.id_medicament, medicaments.id_vet, medicaments.name, medicaments.description," +
            "medicaments.periodicity, medicaments.date_start, medicaments.date_end FROM medicaments\n" +
            "LEFT JOIN rodents  ON (rodents_medicaments.id_medicament = medicaments.id_medicament)\n" +
            "LEFT JOIN rodents_medicaments ON (rodents.id_rodent = rodents_medicaments.id_rodent)\n" +
            "WHERE rodents.id_rodent = :id")
    List<MedicamentWithRodentsCrossRef> getMedsWithRodentsWhereIdRodent(Integer id);

    @Query ("SELECT COUNT(*) FROM medicaments WHERE id_medicament <= :id_medicament")
    Integer getRealPositionFromMed(Integer id_medicament);


    //???
    @Query ("DELETE FROM rodents_medicaments WHERE id_rodent = :id")
    void DeleteAllRodentsMedsByRodent(Integer id);

}


