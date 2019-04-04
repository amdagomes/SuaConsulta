package com.ifpb.suaconsulta.activity.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ifpb.suaconsulta.R;
import com.ifpb.suaconsulta.database.ConfiguracaoFirebase;
import com.ifpb.suaconsulta.database.UsuarioFirebase;
import com.ifpb.suaconsulta.model.Consulta;
import com.ifpb.suaconsulta.model.Medico;
import com.ifpb.suaconsulta.model.UnidadeMedica;

import java.util.List;

public class AgendarConsultaAdapter extends RecyclerView.Adapter<AgendarConsultaAdapter.MyViewHolder> {

    private static DatabaseReference unidadeReference;
    private Context context;
    private List<Consulta> consultas;
    private List<Consulta> minhasConsultas;

    public AgendarConsultaAdapter(List<Consulta> consultas, Context context) {
        this.consultas = consultas;
        this.context = context;
        this.minhasConsultas = UsuarioFirebase.getConsultas();
        this.unidadeReference = ConfiguracaoFirebase.getDatabaseReference().child("unidades");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.agendar_consulta_adapter, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int itemView) {
        Log.i("CONSULTA", "onBindViewHolder");
        final Consulta consulta = consultas.get(itemView);
        Log.i("AGENDA_CONSULTA_ADAPTER", consulta.toString());
//        Log.i("AGENDA_CONSULTA_MINHASC", minhasConsultas.toString());
//        unidadeMedica = null;
        if (!minhasConsultas.contains(consulta)) {
            unidadeReference.child(consulta.getUnidadeMedica()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UnidadeMedica unidadeMedica = dataSnapshot.getValue(UnidadeMedica.class);
                    Log.i("AGENDA_CONSULTA_ADAPTER", "==Unidade== : "+unidadeMedica.toString());
                    myViewHolder.local.setText(unidadeMedica.getNome());
                    for (Medico m : unidadeMedica.getMedicos().values()) {
                        if (m.getCrm() == Integer.parseInt(consulta.getMedico())) {
                            myViewHolder.nomeMedico.setText(m.getNome());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            myViewHolder.dataConsulta.setText(consulta.getData());
            myViewHolder.numVagas.setText(String.valueOf(consulta.getNumVagas()));
        }
    }

    @Override
    public int getItemCount() {
        return consultas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView local, nomeMedico, dataConsulta, numVagas;

        public MyViewHolder(View itemView) {
            super(itemView);
            Log.i("CONSULTA", "no construtor do MyViewHolder");
            local = itemView.findViewById(R.id.textAgendarLocal);
            nomeMedico = itemView.findViewById(R.id.textAgendarNomeMedico);
            dataConsulta = itemView.findViewById(R.id.textAgendarDataConsulta);
            numVagas = itemView.findViewById(R.id.textAgendarNumVagas);
        }
    }
}
