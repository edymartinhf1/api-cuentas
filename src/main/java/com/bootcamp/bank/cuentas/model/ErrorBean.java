package com.bootcamp.bank.cuentas.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorBean {
    private String codigoEstadoHttp;
    private String mensaje;
}
