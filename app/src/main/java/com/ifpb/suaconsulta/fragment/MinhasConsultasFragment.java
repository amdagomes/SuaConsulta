package com.ifpb.suaconsulta.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.ifpb.suaconsulta.R;
import com.ifpb.suaconsulta.activity.MinhasConsultasActivity;
import com.ifpb.suaconsulta.activity.adapters.AgendarConsultaAdapter;
import com.ifpb.suaconsulta.activity.adapters.MinhasConsultasAdapter;
import com.ifpb.suaconsulta.database.ConfiguracaoFirebase;
import com.ifpb.suaconsulta.model.Consulta;

import java.util.ArrayList;
import java.util.List;

public class MinhasConsultasFragment extends Fragment {

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    private RecyclerView recycler;
    private MinhasConsultasAdapter adapter;
    private List<Consulta> consultas;

    private ChildEventListener childEventListener;

    public MinhasConsultasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_minhas_consultas, container, false);

        recycler = rootView.findViewById(R.id.recyclerMinhasConusltas);

        auth = ConfiguracaoFirebase.getFirebaseAuth();
        databaseReference = ConfiguracaoFirebase.getDatabaseReference().child("usuarios").child(auth.getCurrentUser().getUid());

        consultas = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(layoutManager);
        recycler.setHasFixedSize(true);

        adapter = new MinhasConsultasAdapter(consultas);
        recycler.setAdapter(adapter);

        return rootView;
    }

    public void recuperaConsultas(){
        childEventListener = databaseReference.child("consultas").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                consultas.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    try{
                        Consulta consulta = data.getValue(Consulta.class);
                        consultas.add(consulta);
                        Log.i("MINHAS_CONSULTAS", consulta.toString());
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                consultas.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Consulta consulta = data.getValue(Consulta.class);
                    consultas.add(consulta);
                    Log.i("MINHAS_CONSULTAS", consulta.toString());
                }
                adapter.notifyDataSetChanged();
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
    public void onStart() {
        super.onStart();
        recuperaConsultas();
    }

    @Override
    public void onStop() {
        super.onStop();
        databaseReference.removeEventListener(childEventListener);
    }
}
