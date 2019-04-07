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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ifpb.suaconsulta.R;
import com.ifpb.suaconsulta.activity.adapters.AgendarConsultaAdapter;
import com.ifpb.suaconsulta.database.ConfiguracaoFirebase;
import com.ifpb.suaconsulta.database.UsuarioFirebase;
import com.ifpb.suaconsulta.model.Consulta;
import com.ifpb.suaconsulta.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class AgendaConsultaActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
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
        auth = ConfiguracaoFirebase.getFirebaseAuth();
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

    private void recuperarConsultas() {
        //RECUPERA CONTULAS DO USUARIO
        UsuarioFirebase.getUsuariosRef().child(auth.getCurrentUser().getUid()).child("consultas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                minhasConsultas.clear();
                consultas.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Consulta consulta = data.getValue(Consulta.class);
                    consulta.setUid(data.getKey());
                    minhasConsultas.add(consulta);
                }

                //RECUPERA TODAS AS CONSULTAS
                childEventListener = databaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        for (DataSnapshot data : dataSnapshot.getChildren()){
                            Consulta consulta = data.getValue(Consulta.class);

                            //VERIFICA QUANTIDADE DE VAGAS DA CONSULTA
                            if (consulta.getVagasRestantes() != 0 ){
                                boolean aux = false;
                                //VERIFICA SE USUÁRIO JA AGENDOU ESSA CONSULTA
                                for(Consulta mConsulta : minhasConsultas){
                                    if (mConsulta.getUid().equals(consulta.getUid())){
                                        aux = true;
                                        break;
                                    }
                                }
                                if(!aux){
                                    consultas.add(consulta);
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        recuperarConsultas();
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

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
