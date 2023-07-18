package com.bootcamp.bank.cuentas.model.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("cuentas")
public class CuentaDao {
    @Id
    private String  id;
    private String  idCliente;
    private String  numeroCuenta;
    private String  fechaCreacion;
    private String  estado;
    private String  tipoCuenta; // AHO: ahorro  , CTE : cuenta corriente , PZF: plazo fijo , VIP :PERSONAL VIP , PYM : PYME
    private Boolean flgComisionMantenimiento;
    private Boolean flgLimiteMovMensual;
    private Integer numMaximoMovimientos;
    private Double  montoMinimoApertura;
    private Integer numeroMaximoTransaccionesLibres;



}