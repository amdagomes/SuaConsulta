package com.ifpb.suaconsulta.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;
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

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MinhasConsultasAdapter mAdapter;
    private List<Consulta> consultas;

    private ChildEventListener childEventListener;

    public MinhasConsultasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_minhas_consultas, container, false);

        auth = ConfiguracaoFirebase.getFirebaseAuth();
        databaseReference = ConfiguracaoFirebase.getDatabaseReference().child("usuarios").child(auth.getCurrentUser().getUid());

        consultas = new ArrayList<>();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerMinhasConsultas);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(), LinearLayout.VERTICAL));

        mAdapter = new MinhasConsultasAdapter(consultas, this.getActivity());
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    public void recuperaConsultas() {
        consultas.clear();
        childEventListener = databaseReference.child("consultas").getParent().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Consulta consulta = data.getValue(Consulta.class);
                    consultas.add(consulta);
                    Log.i("MINHAS_CONSULTAS_FRAG", data.getValue().toString());
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("MINHAS_CONSULTAS", "onChildChanged");
//                recuperaConsultas();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.i("MINHAS_CONSULTAS", "onChildRemoved");
                recuperaConsultas();
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
//        consultas.clear();
        recuperaConsultas();
    }

    @Override
    public void onStop() {
        super.onStop();
        databaseReference.removeEventListener(childEventListener);
    }
}
