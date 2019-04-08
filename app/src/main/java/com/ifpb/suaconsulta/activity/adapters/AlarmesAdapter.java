package com.ifpb.suaconsulta.activity.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ifpb.suaconsulta.R;
import com.ifpb.suaconsulta.model.Alarme;

import java.util.List;

public class AlarmesAdapter extends RecyclerView.Adapter<AlarmesAdapter.MyViewHolder> {

    private List<Alarme> alarmes;

    public AlarmesAdapter(List<Alarme> alarmes) {
        this.alarmes = alarmes;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.medicamentos_adapter, viewGroup, false);
        return new AlarmesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        Alarme alarme = alarmes.get(i);
        myViewHolder.medicamento.setText(alarme.getMedicamento());
        myViewHolder.dosagem.setText("Dosagem: " + alarme.getDosagem());
        myViewHolder.duracaoDias.setText(String.valueOf(alarme.getDuracaoDias()) + " dias");
        myViewHolder.duracaoHoras.setText(String.valueOf(alarme.getIntervaloHoras()) + " horas");
    }

    @Override
    public int getItemCount() {
        return alarmes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView medicamento, dosagem, inicio, duracaoDias, duracaoHoras;

        public MyViewHolder(View itemView) {
            super(itemView);

            medicamento = itemView.findViewById(R.id.medicNome);
            dosagem = itemView.findViewById(R.id.medicDosagem);
            duracaoDias = itemView.findViewById(R.id.medicDias);
            duracaoHoras = itemView.findViewById(R.id.medicHoras);
        }
    }
}
