package com.example.rodentshelper.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.TypeConverters;

import com.example.rodentshelper.ROOM.Visits.VisitModel;
import com.example.rodentshelper.ROOM._MTM._RodentMed.RodentMedModel;
import com.example.rodentshelper.ROOM._MTM._RodentVet.VetWithRodentsCrossRef;
import com.example.rodentshelper.ROOM._MTM._RodentVisit.VisitsWithRodentsCrossRef;
import com.example.rodentshelper.ROOM._MTM._RodentVisit.RodentVisitModel;

import java.sql.Date;
import java.util.List;

@Dao
public interface DAOVisits {

    @Insert
    void insertRecordVisit(VisitModel visits);

    @Query("SELECT * FROM visits")
    List<VisitModel> getAllVisits();

    @Query("DELETE FROM visits WHERE id_visit = :id")
    void deleteVisitById(Integer id);

    @TypeConverters(Converters.class)
    @Query("UPDATE visits SET id_vet = :id_vet, date = :date, time = :time, reason = :reason WHERE id_visit = :id")
    void updateVisitById(Integer id, Integer id_vet, Date date, String time, String reason);



    @Query("SELECT vets.name FROM vets WHERE vets.id_vet = :id")
    List<String> getAllVisitsVets(Integer id);

    @Query ("UPDATE visits SET id_vet = NULL WHERE id_vet = :id_vet")
    void SetVisitsIdVetNull(Integer id_vet);

    @Query ("UPDATE visits SET id_vet = :id_vet WHERE id_visit = :id")
    void SetVisitsIdVet(Integer id_vet, Integer id);



    @Transaction
    @Query("SELECT * FROM visits")
    public List<VisitsWithRodentsCrossRef> getVisitsWithRodents();

    @Transaction
    @Query ("SELECT visits.id_visit, visits.id_vet, visits.date, visits.time, visits.reason FROM visits\n" +
            "JOIN rodents  ON (rodents_visits.id_visit = visits.id_visit)\n" +
            "JOIN rodents_visits ON (rodents.id_rodent = rodents_visits.id_rodent)\n" +
            "WHERE rodents.id_rodent = :id")
    List<VisitsWithRodentsCrossRef> getVisitsWithRodentsWhereIdRodent(Integer id);


    @Insert
    void insertRecordRodentVisit(RodentVisitModel rodents_visits);

    @Query("SELECT id_visit FROM visits WHERE id_visit = (SELECT MAX(id_visit) FROM visits)")
    List<Integer> getLastIdVisit();

    @Query ("SELECT COUNT(*) FROM visits WHERE id_visit <= :id_visit")
    Integer getRealPositionFromVisit(Integer id_visit);

    @Query ("DELETE FROM rodents_visits WHERE id_visit = :id")
    void DeleteAllRodentsVisitsByVisit(Integer id);

    /** NEW QUERY FOR BUGFIX */
    @Query ("DELETE FROM rodents_visits WHERE id_visit = :id AND id_rodent = :id_rodent")
    void DeleteAllRodentsVisitsByVisitAndRodent(Integer id, Integer id_rodent);


    /** visit - vet */
    @Transaction
    @Query ("SELECT vets.name FROM vets\n" +
            "JOIN visits  ON (vets.id_vet = visits.id_vet)\n" +
            "WHERE visits.id_vet = :id_vet")
    String getVetByVisitId(Integer id_vet);


    @Transaction
    @Query ("SELECT rodents.name1 FROM rodents\n" +
            "JOIN visits  ON (rodents_visits.id_visit = visits.id_visit)\n" +
            "JOIN rodents_visits ON (rodents.id_rodent = rodents_visits.id_rodent)\n" +
            "WHERE visits.id_visit = :id_visit")
    List<String> test1(Integer id_visit);

}


