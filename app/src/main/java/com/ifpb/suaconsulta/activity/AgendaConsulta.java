package com.ifpb.suaconsulta.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ifpb.suaconsulta.R;

public class AgendaConsulta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_consulta);

        Toolbar toolbar = findViewById(R.id.toolbarAgendarConsulta);
        toolbar.setTitle("Agendar Consulta");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void carregaTelaHome(View view){
        Intent intent = new Intent(AgendaConsulta.this, MainActivity.class);
        startActivity(intent);
    }
}
