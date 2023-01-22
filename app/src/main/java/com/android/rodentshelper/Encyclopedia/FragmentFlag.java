package com.android.rodentshelper.Encyclopedia;

public class FragmentFlag {

    private static Integer fragmentFlag;

    private static Integer encyclopediaTypeFlag;



    public static Integer getFragmentFlag() {
        return fragmentFlag;
    }

    public static void setFragmentFlag(Integer fragmentFlag) {
        FragmentFlag.fragmentFlag = fragmentFlag;
    }

    public static Integer getEncyclopediaTypeFlag() {
        return encyclopediaTypeFlag;
    }

    public static void setEncyclopediaTypeFlag(Integer encyclopediaTypeFlag) {
        FragmentFlag.encyclopediaTypeFlag = encyclopediaTypeFlag;
    }


}
