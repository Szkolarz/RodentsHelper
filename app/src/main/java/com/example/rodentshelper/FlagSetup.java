package com.example.rodentshelper;

public class FlagSetup {

    private static Integer flagVetAdd;

    private static Integer flagRodentAdd;

    private static Integer flagMedAdd;


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
}
