package com.ifpb.suaconsulta.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDB extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String DB_NAME = "DB_SUA_CONSULTA";

    public static String TABELA_ALARME = "alarme";

    public SQLiteDB(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public void SQLiteDB(){}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String criar_tabela_alarme = "CREATE TABLE IF NOT EXISTS " + TABELA_ALARME + "(" +
                                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                        "medicamento VARCHAR(50) NOT NULL," +
                                        "dosagem VARCHAR(100)," +
                                        "inicioTratamento DATE NOT NULL," +
                                        "duracaoDias INTEGER NOT NULL," +
                                        "intervaloHoras INTEGER NOT NULL);";

        try {
            db.execSQL(
                    criar_tabela_alarme
            );
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
