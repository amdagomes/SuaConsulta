package com.ifpb.suaconsulta.activity.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private Context context;
    private List<Consulta> consultas;
    private List<Consulta> minhasConsultas;

    public AgendarConsultaAdapter(List<Consulta> consultas, Context context) {
        this.consultas = consultas;
        this.context = context;
        this.auth = ConfiguracaoFirebase.getFirebaseAuth();
        this.databaseReference = ConfiguracaoFirebase.getDatabaseReference();
        this.minhasConsultas = UsuarioFirebase.getConsultas();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.agendar_consulta_adapter, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int itemView) {
        final Consulta consulta = consultas.get(itemView);
        Log.i("AGENDA_CONSULTA_ADAPTER", consulta.toString());

        //trata clique do botão
        myViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //atualiza consultas
                consulta.setVagasRestantes(consulta.getVagasRestantes()-1);
                databaseReference.child("consultas").child(consulta.getUnidadeMedica()).child(consulta.getUid()).setValue(consulta);

                //atualiza consultas do usuário
                databaseReference.child("usuarios").child(auth.getCurrentUser().getUid()).child("consultas").child(consulta.getUid()).setValue(consulta);
                Toast.makeText(context, "Agendamento realizado!", Toast.LENGTH_SHORT).show();
            }
        });

        //verifica se o usuário já realizou agendamento nessa consulta
        boolean contem = false;
        for (Consulta c : minhasConsultas){
            Log.i("AGENDA_", "CONTEN: "+ contem);
            if (c.getUid().equals(consulta.getUid())){
                contem = true;
                break;
            }
        }
        if (contem == true) {
            myViewHolder.layout.setVisibility(View.GONE);
        } else{
            //recupera os locais (unidades) da consulta
                databaseReference.child("unidades").child(consulta.getUnidadeMedica()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UnidadeMedica unidadeMedica = dataSnapshot.getValue(UnidadeMedica.class);
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
                myViewHolder.numVagas.setText(String.valueOf(consulta.getVagasRestantes()));
            }

    }

    @Override
    public int getItemCount() {
        return consultas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        Button button;
        TextView local, nomeMedico, dataConsulta, numVagas;
        LinearLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.btnAgendarConsulta);
            local = itemView.findViewById(R.id.textAgendarLocal);
            nomeMedico = itemView.findViewById(R.id.textAgendarNomeMedico);
            dataConsulta = itemView.findViewById(R.id.textAgendarDataConsulta);
            numVagas = itemView.findViewById(R.id.textAgendarNumVagas);
            layout = itemView.findViewById(R.id.layoutAdapterConsulta);
        }
    }
}
