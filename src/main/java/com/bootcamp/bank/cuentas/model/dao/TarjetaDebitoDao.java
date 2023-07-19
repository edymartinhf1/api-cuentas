package com.bootcamp.bank.cuentas.model.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document("tarjetasdebito")
public class TarjetaDebitoDao {
    @Id
    private String id;
    private String idCliente;
    private String numeroTarjetaDebito;
    private String numeroCuentaPrincipal;
    private String numeroCuenta;
    private LocalDateTime fechaCreacion;
    private String fechaCreacionT;
    private List<TarjetaDebitoCuentaDao> tarjetaDebitoCuentaDaoList;
}

