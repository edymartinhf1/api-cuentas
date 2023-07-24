package com.bootcamp.bank.cuentas.model.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("cuentas")
public class CuentaDao {
    @Id
    private String  id;
    private String  idCliente;
    private String  numeroCuenta;
    private LocalDateTime fechaCreacion;
    private String fechaCreacionT;
    private String  estado;
    private String  tipoCuenta; // AHO: ahorro  , CTE : cuenta corriente , PZF: plazo fijo , VIP :PERSONAL VIP , PYM : PYME
    private Boolean flgComisionMantenimiento;
    private Boolean flgLimiteMovMensual;
    private Integer numMaximoMovimientos;
    private Double  montoMinimoApertura;
    private Double  montoMinimoMensual;
    private Integer numeroMaximoTransaccionesLibres;



}