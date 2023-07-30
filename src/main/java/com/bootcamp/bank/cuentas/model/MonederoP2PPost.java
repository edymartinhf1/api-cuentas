package com.bootcamp.bank.cuentas.model;

import lombok.Data;

@Data
public class MonederoP2PPost {
    private String numeroDocumento;
    private String tipoDocumento;
    private String numeroMonederoP2P;
    private String numeroCelular;
    private String correo;
}
