package com.ifpb.suaconsulta.model;

import java.io.Serializable;
import java.util.Objects;

public class Medico implements Serializable {
    private String id;
    private String nome;
    private int crm;
    private String telefone;
    private String email;
    private String especialidade;

    public Medico() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCrm() {
        return crm;
    }

    public void setCrm(int crm) {
        this.crm = crm;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medico medico = (Medico) o;
        return crm == medico.crm &&
                Objects.equals(id, medico.id) &&
                Objects.equals(nome, medico.nome) &&
                Objects.equals(telefone, medico.telefone) &&
                Objects.equals(email, medico.email) &&
                Objects.equals(especialidade, medico.especialidade);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, nome, crm, telefone, email, especialidade);
    }

    @Override
    public String toString() {
        return "Medico{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", crm=" + crm +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", especialidade='" + especialidade + '\'' +
                '}';
    }
}
