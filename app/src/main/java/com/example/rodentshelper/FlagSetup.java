package com.example.rodentshelper;

public class FlagSetup {

    private static Integer flagVetAdd;

    private static Integer flagRodentAdd;

    private static Integer flagMedAdd;

    private static Integer flagVisitAdd;

    private static Integer flagNotesAdd;


    public FlagSetup() {
    }

    public static Integer getFlagVetAdd() {
        return flagVetAdd;
    }

    public static void setFlagVetAdd(Integer flagVetAdd) {
        FlagSetup.flagVetAdd = flagVetAdd;
    }

    public static Integer getFlagRodentAdd() {
        return flagRodentAdd;
    }

    public static void setFlagRodentAdd(Integer flagRodentAdd) {
        FlagSetup.flagRodentAdd = flagRodentAdd;
    }

    public static Integer getFlagMedAdd() {
        return flagMedAdd;
    }

    public static void setFlagMedAdd(Integer flagMedAdd) {
        FlagSetup.flagMedAdd = flagMedAdd;
    }

    public static Integer getFlagVisitAdd() {
        return flagVisitAdd;
    }

    public static void setFlagVisitAdd(Integer flagVisitAdd) {
        FlagSetup.flagVisitAdd = flagVisitAdd;
    }

    public static Integer getFlagNotesAdd() {
        return flagNotesAdd;
    }

    public static void setFlagNotesAdd(Integer flagNotesAdd) {
        FlagSetup.flagNotesAdd = flagNotesAdd;
    }
}
