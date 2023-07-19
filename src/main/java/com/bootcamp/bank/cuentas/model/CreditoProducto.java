package com.bootcamp.bank.cuentas.model;

import lombok.Data;

import java.math.BigDecimal;

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
    private BigDecimal montoDeudaVencida;
    private Boolean flgDeudaVencida;
}