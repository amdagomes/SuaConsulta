package com.ifpb.suaconsulta.model;

import com.google.firebase.database.DatabaseReference;
import com.ifpb.suaconsulta.database.ConfiguracaoFirebase;
import com.ifpb.suaconsulta.model.enums.Sexo;

import java.io.Serializable;
import java.util.Objects;

public class Usuario implements Serializable{

    private String id;
    private String Nome;
    private String cpf;
    private String numSus;
    private String telefone;
    private String email;
    private String senha;
    private Sexo sexo;
    private String dataNascimento;
    private String rua;
    private String bairro;

    public Usuario() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNumSus() {
        return numSus;
    }

    public void setNumSus(String numSus) {
        this.numSus = numSus;
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

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) &&
                Objects.equals(Nome, usuario.Nome) &&
                Objects.equals(cpf, usuario.cpf) &&
                Objects.equals(numSus, usuario.numSus) &&
                Objects.equals(telefone, usuario.telefone) &&
                Objects.equals(email, usuario.email) &&
                Objects.equals(senha, usuario.senha) &&
                sexo == usuario.sexo &&
                Objects.equals(dataNascimento, usuario.dataNascimento) &&
                Objects.equals(rua, usuario.rua) &&
                Objects.equals(bairro, usuario.bairro);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, Nome, cpf, numSus, telefone, email, senha, sexo, dataNascimento, rua, bairro);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", Nome='" + Nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", numSus='" + numSus + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", sexo=" + sexo +
                ", dataNascimento='" + dataNascimento + '\'' +
                ", rua='" + rua + '\'' +
                ", bairro='" + bairro + '\'' +
                '}';
    }
}
