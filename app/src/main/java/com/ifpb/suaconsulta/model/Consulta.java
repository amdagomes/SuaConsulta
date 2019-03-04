package com.ifpb.suaconsulta.model;

import java.util.Date;

public class Consulta {
    private String id;
    private Date data;
    private Date horaIncioAgendamento;
    private Date horaFimAgendamento;
    private int numVagas;
    private Medico medico;
    private UnidadeMedica unidadeMedica;

    public Consulta() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getHoraIncioAgendamento() {
        return horaIncioAgendamento;
    }

    public void setHoraIncioAgendamento(Date horaIncioAgendamento) {
        this.horaIncioAgendamento = horaIncioAgendamento;
    }

    public Date getHoraFimAgendamento() {
        return horaFimAgendamento;
    }

    public void setHoraFimAgendamento(Date horaFimAgendamento) {
        this.horaFimAgendamento = horaFimAgendamento;
    }

    public int getNumVagas() {
        return numVagas;
    }

    public void setNumVagas(int numVagas) {
        this.numVagas = numVagas;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public UnidadeMedica getUnidadeMedica() {
        return unidadeMedica;
    }

    public void setUnidadeMedica(UnidadeMedica unidadeMedica) {
        this.unidadeMedica = unidadeMedica;
    }
}
