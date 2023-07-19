package com.bootcamp.bank.cuentas.model;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class TarjetaDebitoCuenta {
    private String id;
    private String idCliente;
    private String numeroTarjetaDebito;
    private LocalDateTime fechaCreacion;
    private String numeroCuenta;
}
