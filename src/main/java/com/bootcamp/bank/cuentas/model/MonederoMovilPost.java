package com.bootcamp.bank.cuentas.model;

import lombok.Data;

@Data
public class MonederoMovilPost {
    private String idRequest;
    private String idCliente; //Debe ser cliente del banco
    private String nombre;
    private String numeroCuentaPrincipal;
    private String numeroDocumento;
    private String tipoDocumento; // DNI , CEX , PAS =PASAPORTE
    private String numeroCelular;
    private String imeiCelular;
    private String tipoMonedero;  // NOR = Normal  .P2P  = P2P,
    private String correo;
}
