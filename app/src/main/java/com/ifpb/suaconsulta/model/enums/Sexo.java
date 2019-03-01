package com.ifpb.suaconsulta.model.enums;

public enum Sexo {
    FEMININO("Feminino"),
    MASCULINO("Masculino");

    private final String sexo;

    Sexo(String sexo) {
        this.sexo = sexo;
    }

    public String getSexo() {
        return sexo;
    }
}
