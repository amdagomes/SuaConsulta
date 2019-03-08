package com.ifpb.suaconsulta.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ifpb.suaconsulta.model.Alarme;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlarmeDAO {

    private SQLiteDatabase writeDatabase;
    private SQLiteDatabase readDatabase;
    private SQLiteDB db;

    public AlarmeDAO(Context context) {
        this.db = new SQLiteDB(context);
        writeDatabase = db.getWritableDatabase();
        readDatabase = db.getReadableDatabase();
    }

    public boolean adiciona(Alarme alarme){
        ContentValues values = new ContentValues();
        values.put("medicamento", alarme.getMedicamento());
        values.put("dosagem", alarme.getDosagem());
        values.put("inicioTratamento", alarme.getInicio());
        values.put("duracaoDias", alarme.getDuracaoDias());
        values.put("intervaloHoras", alarme.getIntervaloHoras());

        try {
            writeDatabase.insert(SQLiteDB.TABELA_ALARME, null, values);
        }catch (Exception e){
        Log.e("INFO", "Erro ao salvar alarme");
            return false;
        }

        return true;
    }

    public List<Alarme> listar(){
        List<Alarme> list = new ArrayList<>();

        Cursor cursor = readDatabase.rawQuery("SELECT * FROM " + SQLiteDB.TABELA_ALARME, null);

        while (cursor.moveToNext()){
            Alarme alarme = new Alarme();
            alarme.setId(cursor.getInt(cursor.getColumnIndex("id")));
            alarme.setMedicamento(cursor.getString(cursor.getColumnIndex("medicamento")));
            alarme.setDosagem(cursor.getString(cursor.getColumnIndex("dosagem")));
            alarme.setInicio(cursor.getString(cursor.getColumnIndex("inicioTratamento")));
            alarme.setDuracaoDias(cursor.getInt(cursor.getColumnIndex("duracaoDias")));
            alarme.setIntervaloHoras(cursor.getInt(cursor.getColumnIndex("intervaloHoras")));

            list.add(alarme);
        }

        return list;
    }
}
