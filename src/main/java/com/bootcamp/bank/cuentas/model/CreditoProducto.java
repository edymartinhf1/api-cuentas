package com.bootcamp.bank.cuentas.model;

import lombok.Data;

@Data
public class CreditoProducto {
    private String  idCliente;
    private String  tipoCredito;
    private String  numeroCredito;
    private String  numeroTarjeta;
    private String  fechaCreacion;
    private Double  lineaCredito;
    private Double  saldo;
    private Double  consumos;
    private Double  pagos;
}