package com.ifpb.suaconsulta.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ifpb.suaconsulta.R;

public class AgendarConsultaAdapter extends RecyclerView.Adapter<AgendarConsultaAdapter.MyViewHolder>  {

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.agendar_consulta_adapter, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int itemView) {
        myViewHolder.local.setText("Consulta com especialista");
        myViewHolder.nomeMedico.setText("Dr. Francisco");
        myViewHolder.dataConsulta.setText("02/03/2019");
        myViewHolder.numVagas.setText("5");
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView local, nomeMedico, dataConsulta, numVagas;

        public MyViewHolder(View itemView) {
            super(itemView);

            local = itemView.findViewById(R.id.textAgendarLocal);
            nomeMedico = itemView.findViewById(R.id.textAgendarNomeMedico);
            dataConsulta = itemView.findViewById(R.id.textAgendarDataConsulta);
            numVagas = itemView.findViewById(R.id.textAgendarNumVagas);
        }
    }
}
