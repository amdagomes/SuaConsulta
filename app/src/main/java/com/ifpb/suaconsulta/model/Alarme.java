package com.ifpb.suaconsulta.model;

import java.util.Objects;

public class Alarme {
    private int id;
    private String medicamento;
    private String dosagem;
    private String inicio;
    private int duracaoDias;
    private int intervaloHoras;

    public Alarme() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public int getDuracaoDias() {
        return duracaoDias;
    }

    public void setDuracaoDias(int duracaoDias) {
        this.duracaoDias = duracaoDias;
    }

    public int getIntervaloHoras() {
        return intervaloHoras;
    }

    public void setIntervaloHoras(int intervaloHoras) {
        this.intervaloHoras = intervaloHoras;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alarme alarme = (Alarme) o;
        return id == alarme.id &&
                duracaoDias == alarme.duracaoDias &&
                intervaloHoras == alarme.intervaloHoras &&
                Objects.equals(medicamento, alarme.medicamento) &&
                Objects.equals(dosagem, alarme.dosagem) &&
                Objects.equals(inicio, alarme.inicio);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, medicamento, dosagem, inicio, duracaoDias, intervaloHoras);
    }

    @Override
    public String toString() {
        return "Alarme{" +
                "id=" + id +
                ", medicamento='" + medicamento + '\'' +
                ", dosagem='" + dosagem + '\'' +
                ", inicio='" + inicio + '\'' +
                ", duracaoDias=" + duracaoDias +
                ", intervaloHoras=" + intervaloHoras +
                '}';
    }
}
