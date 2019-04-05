package com.ifpb.suaconsulta.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Consulta implements Serializable {

    private String uid;
    private String data;
    private int numVagas;
    private int vagasRestantes;
    private String medico;
    private String unidadeMedica;
    private String confirmada;

    public Consulta() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getNumVagas() {
        return numVagas;
    }

    public void setNumVagas(int numVagas) {
        this.numVagas = numVagas;
    }

    public int getVagasRestantes() {
        return vagasRestantes;
    }

    public void setVagasRestantes(int vagasRestantes) {
        this.vagasRestantes = vagasRestantes;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getUnidadeMedica() {
        return unidadeMedica;
    }

    public void setUnidadeMedica(String unidadeMedica) {
        this.unidadeMedica = unidadeMedica;
    }

    public String getConfirmada() {
        return confirmada;
    }

    public void setConfirmada(String confirmada) {
        this.confirmada = confirmada;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consulta consulta = (Consulta) o;
        return numVagas == consulta.numVagas &&
                vagasRestantes == consulta.vagasRestantes &&
                Objects.equals(uid, consulta.uid) &&
                Objects.equals(data, consulta.data) &&
                Objects.equals(medico, consulta.medico) &&
                Objects.equals(unidadeMedica, consulta.unidadeMedica) &&
                Objects.equals(confirmada, consulta.confirmada);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uid, data, numVagas, vagasRestantes, medico, unidadeMedica, confirmada);
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "uid='" + uid + '\'' +
                ", data='" + data + '\'' +
                ", numVagas=" + numVagas +
                ", vagasRestantes=" + vagasRestantes +
                ", medico='" + medico + '\'' +
                ", unidadeMedica='" + unidadeMedica + '\'' +
                ", confirmada='" + confirmada + '\'' +
                '}';
    }
}
