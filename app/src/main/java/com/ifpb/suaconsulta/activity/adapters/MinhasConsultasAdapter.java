package com.ifpb.suaconsulta.activity.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ifpb.suaconsulta.R;
import com.ifpb.suaconsulta.database.ConfiguracaoFirebase;
import com.ifpb.suaconsulta.model.Consulta;
import com.ifpb.suaconsulta.model.Medico;
import com.ifpb.suaconsulta.model.UnidadeMedica;

import java.util.List;

public class MinhasConsultasAdapter extends RecyclerView.Adapter<MinhasConsultasAdapter.MyViewHolder> {

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    private List<Consulta> consultas;

    public MinhasConsultasAdapter(List<Consulta> consultas){
        this.databaseReference = ConfiguracaoFirebase.getDatabaseReference();
        this.auth = ConfiguracaoFirebase.getFirebaseAuth();
        this.consultas = consultas;
    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.minhas_consultas_adapter, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        final Consulta consulta = consultas.get(i);
        Log.i("MINHAS_CONSULTAS", consulta.toString());
        if (consulta.getConfirmada().equals("true")){
            myViewHolder.btnConfirmar.setVisibility(View.GONE);
            myViewHolder.btnCancelar.setVisibility(View.VISIBLE);
        } else {
            myViewHolder.btnConfirmar.setVisibility(View.VISIBLE);
            myViewHolder.btnCancelar.setVisibility(View.GONE);
        }

        myViewHolder.data.setText("AGENDADO PARA " + consulta.getData());

        databaseReference.child("unidades").child(consulta.getUnidadeMedica()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UnidadeMedica unidadeMedica = dataSnapshot.getValue(UnidadeMedica.class);
                myViewHolder.local.setText(unidadeMedica.getNome());

                for (Medico m : unidadeMedica.getMedicos().values()) {
                    if (m.getCrm() == Integer.parseInt(consulta.getMedico())) {
                        myViewHolder.titulo.setText("CONSULTA EM " + m.getEspecialidade());
                        myViewHolder.medico.setText(m.getNome());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return consultas.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView titulo, local, data, medico;
        Button btnConfirmar, btnCancelar;

        public MyViewHolder(View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.textMConsultaTitulo);
            local = itemView.findViewById(R.id.textMConsultaUnidadeSaude);
            data = itemView.findViewById(R.id.textMConsultaData);
            medico = itemView.findViewById(R.id.textMConsultaNomeMedico);
            btnConfirmar = itemView.findViewById(R.id.btnConfirmarConsulta);
            btnCancelar = itemView.findViewById(R.id.btnCancelarConsulta);
        }
    }
}
