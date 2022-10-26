package com.example.rodentshelper;

import java.sql.Date;

public class FlagSetup {

    private static Integer flagVetAdd;


    public FlagSetup() {
    }

    public static Integer getFlagVetAdd() {
        return flagVetAdd;
    }

    public static void setFlagVetAdd(Integer flagVetAdd) {
        FlagSetup.flagVetAdd = flagVetAdd;
    }
}
