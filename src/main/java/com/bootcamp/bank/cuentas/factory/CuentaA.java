package com.bootcamp.bank.cuentas.factory;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class CuentaA {
    private String  id;
    private String  idCliente;
    private String  numeroCuenta;
    private LocalDateTime fechaCreacion;
    private String  fechaCreacionT;
    private String  estado;
    private String  tipoCuenta; // AHO: ahorro  , CTE : cuenta corriente , PZF: plazo fijo
    private Boolean flgComisionMantenimiento;
    private Boolean flgLimiteMovMensual;
    private Integer numMaximoMovimientos;
    private Double  montoMinimoApertura;
    private Double  montoMinimoMensual;
    private Integer numeroMaximoTransaccionesLibres;

    protected CuentaA() {
        this.configurarCuenta();
    }

    abstract void configurarCuenta();


}
