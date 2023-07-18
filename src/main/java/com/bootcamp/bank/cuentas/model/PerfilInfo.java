package com.bootcamp.bank.cuentas.model;

import lombok.Data;

import java.util.List;

@Data
public class PerfilInfo {
    private Cliente cliente;
    private List<String> perfiles;
}
