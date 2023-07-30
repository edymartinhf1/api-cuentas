package com.bootcamp.bank.cuentas.model;

import lombok.Data;

@Data
public class ResponseMonedero {
    private String codigo;
    private String mensaje;
    private String idCliente;
    private String numeroCuenta;
    private String idRequest;
}
