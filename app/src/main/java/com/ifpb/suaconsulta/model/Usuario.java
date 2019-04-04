package com.ifpb.suaconsulta.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Usuario implements Serializable{

    private String id;
    private String Nome;
    private String cpf;
    private String numSus;
    private String telefone;
    private String email;
    private String senha;
    private String sexo;
    private String dataNascimento;
    private String rua;
    private String bairro;
    private String caminhoFoto;
    private List<Consulta> consultas;

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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
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
                Objects.equals(sexo, usuario.sexo) &&
                Objects.equals(dataNascimento, usuario.dataNascimento) &&
                Objects.equals(rua, usuario.rua) &&
                Objects.equals(bairro, usuario.bairro) &&
                Objects.equals(caminhoFoto, usuario.caminhoFoto) &&
                Objects.equals(consultas, usuario.consultas);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, Nome, cpf, numSus, telefone, email, senha, sexo, dataNascimento, rua, bairro, caminhoFoto, consultas);
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
                ", sexo='" + sexo + '\'' +
                ", dataNascimento='" + dataNascimento + '\'' +
                ", rua='" + rua + '\'' +
                ", bairro='" + bairro + '\'' +
                ", caminhoFoto='" + caminhoFoto + '\'' +
                ", consultas=" + consultas +
                '}';
    }
}
