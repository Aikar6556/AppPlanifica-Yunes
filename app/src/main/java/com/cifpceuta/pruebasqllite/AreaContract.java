package com.cifpceuta.pruebasqllite;

import android.provider.BaseColumns;

public final class AreaContract {

    private AreaContract() {
    }

    public static class AreaEntry implements BaseColumns {
        public static final String TABLE_NAME = "usuarios";



        public static final String COLUMN_USUARIO = "usuario";
        public static final String COLUMN_PASSW = "passw";

    }
}
