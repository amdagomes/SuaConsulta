package com.ifpb.suaconsulta.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.ifpb.suaconsulta.R;
import com.ifpb.suaconsulta.activity.adapters.AgendarConsultaAdapter;
import com.ifpb.suaconsulta.database.ConfiguracaoFirebase;
import com.ifpb.suaconsulta.database.UsuarioFirebase;
import com.ifpb.suaconsulta.model.Consulta;

import java.util.ArrayList;
import java.util.List;

public class AgendaConsultaActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    private List<Consulta> consultas;
    private List<Consulta> minhasConsultas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_consulta);

        Toolbar toolbar = findViewById(R.id.toolbarAgendarConsulta);
        toolbar.setTitle("Agendar Consulta");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseReference = ConfiguracaoFirebase.getDatabaseReference().child("consultas");
        consultas = new ArrayList<>();
        minhasConsultas = new ArrayList<>();

        recycler = findViewById(R.id.recyclerAgendarConsulta);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recycler.setLayoutManager(layoutManager);
        recycler.setHasFixedSize(true);
        adapter = new AgendarConsultaAdapter(consultas, getApplicationContext());
        recycler.setAdapter(adapter);
//        recycler.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

    }

    public void recuperarConsultas(){

        minhasConsultas = UsuarioFirebase.getConsultas();

        childEventListener = databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                consultas.clear();
               for(DataSnapshot data : dataSnapshot.getChildren()){
                   Consulta consulta = data.getValue(Consulta.class);
                   if (consulta.getVagasRestantes() != 0){
                       boolean contem = false;
                       //verifica se o usuario ja fez o agendamento da consulta
                       for (Consulta c : minhasConsultas){
                           Log.i("MINHAS_CONSULTAS", "MC: " + c.toString());
                           if(c.getUid().equals(consulta.getUid())){
                               contem = true;
                               break;
                           }
                       }
                       if (!contem){
                           consulta.setUid(data.getKey());
                           consultas.add(consulta);
                       }
                   }
               }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                consultas.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Consulta consulta = data.getValue(Consulta.class);
                    if (consulta.getVagasRestantes() != 0){
                        boolean contem = false;
                        //verifica se o usuario ja fez o agendamento da consulta
                        for (Consulta c : minhasConsultas){
                            Log.i("MINHAS_CONSULTAS", "MC: " + c.toString());
                            if(c.getUid().equals(consulta.getUid())){
                                contem = true;
                                break;
                            }
                        }
                        if (!contem){
                            consulta.setUid(data.getKey());
                            consultas.add(consulta);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                consultas.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Consulta consulta = data.getValue(Consulta.class);
                    if (consulta.getVagasRestantes() != 0){
                        boolean contem = false;
                        //verifica se o usuario ja fez o agendamento da consulta
                        for (Consulta c : minhasConsultas){
                            Log.i("MINHAS_CONSULTAS", "MC: " + c.toString());
                            if(c.getUid().equals(consulta.getUid())){
                                contem = true;
                                break;
                            }
                        }
                        if (!contem){
                            consulta.setUid(data.getKey());
                            consultas.add(consulta);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarConsultas();
    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseReference.removeEventListener(childEventListener);
    }
}
