package com.bootcamp.bank.cuentas.model;

import lombok.Data;

@Data
public class TarjetaDebitoPost {
    private String id;
    private String idCliente;
    private String numeroTarjetaDebito;
    private String numeroCuentaPrincipal;
    private String numeroCuenta;
}
