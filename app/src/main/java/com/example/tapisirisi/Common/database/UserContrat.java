package com.example.tapisirisi.Common.database;

import android.provider.BaseColumns;

public class UserContrat {
    private UserContrat(){}

    public static class UserTable implements BaseColumns {
        public static final String ID_USER = "ID_USER";
        public static final String TABLE_NAME = "USER";
        public static final String COLUMN_NAME_NOM = "nom";
        public static final String COLUMN_NAME_PRENOM = "prenom";
        public static final String COLUMN_NAME_LOGIN = "login";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_ROLE = "role";
        public static final String COLUMN_NAME_PROFILE_IMAGE = "profile_image";
    }
}
