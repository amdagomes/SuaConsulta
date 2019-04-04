package com.ifpb.suaconsulta.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UnidadeMedica {
    private String id;
    private String nome;
    private String telefone;
    private String email;
    private String senha;
    private String tipo;
    private String rua;
    private String bairro;
    Map<String, Medico> medicos;

    public UnidadeMedica() {
        this.medicos = new HashMap<String, Medico>();
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Map<String, Medico> getMedicos() {
        return medicos;
    }

    public void setMedicos(Map<String, Medico> medicos) {
        this.medicos = medicos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnidadeMedica that = (UnidadeMedica) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(telefone, that.telefone) &&
                Objects.equals(email, that.email) &&
                Objects.equals(senha, that.senha) &&
                Objects.equals(tipo, that.tipo) &&
                Objects.equals(rua, that.rua) &&
                Objects.equals(bairro, that.bairro) &&
                Objects.equals(medicos, that.medicos);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, nome, telefone, email, senha, tipo, rua, bairro, medicos);
    }

    @Override
    public String toString() {
        return "UnidadeMedica{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", tipo='" + tipo + '\'' +
                ", rua='" + rua + '\'' +
                ", bairro='" + bairro + '\'' +
                ", medicos=" + medicos +
                '}';
    }
}
