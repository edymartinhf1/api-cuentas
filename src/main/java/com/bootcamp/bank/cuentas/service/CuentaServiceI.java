package com.bootcamp.bank.cuentas.service;

import com.bootcamp.bank.cuentas.model.dao.CuentaDao;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CuentaServiceI {
    Mono<CuentaDao> save2(CuentaDao cuentaDao);
    Mono<CuentaDao> save(CuentaDao cuentaDao);
    Flux<CuentaDao> findAll();
    Mono<CuentaDao> findById(String id);
    Mono<CuentaDao> findByNumeroCuenta(String numeroCuenta);
    Flux<CuentaDao> findByIdCliente(String idCliente);
    Flux<CuentaDao> findByIdClienteAndTipoCuenta(String idCliente,String tipoCuenta);
    Mono<CuentaDao> update( CuentaDao cuentaDao);
    Mono<Void> delete( String id);
}
