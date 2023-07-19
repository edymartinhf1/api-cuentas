package com.bootcamp.bank.cuentas.model.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("tarjetasdebitocuentas")
public class TarjetaDebitoCuentaDao {
    @Id
    private String id;
    private String idCliente;
    private String numeroTarjetaDebito;
    private LocalDateTime fechaCreacion;
    private String numeroCuenta;
}
