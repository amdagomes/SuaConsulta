package com.ifpb.suaconsulta.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.ifpb.suaconsulta.R;
import com.ifpb.suaconsulta.activity.adapters.AgendarConsultaAdapter;

public class AgendaConsulta extends AppCompatActivity {

    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_consulta);

        Toolbar toolbar = findViewById(R.id.toolbarAgendarConsulta);
        toolbar.setTitle("Agendar Consulta");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView.Adapter adapter = new AgendarConsultaAdapter();

        recycler = findViewById(R.id.recyclerAgendarConsulta);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);
        recycler.setHasFixedSize(true);
        recycler.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));


    }

}
