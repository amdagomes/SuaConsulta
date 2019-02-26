package com.ifpb.suaconsulta.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ifpb.suaconsulta.R;

public class AgendaConsulta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_consulta);
    }

    public void carregaTelaHome(View view){
        Intent intent = new Intent(AgendaConsulta.this, MainActivity.class);
        startActivity(intent);
    }
}
