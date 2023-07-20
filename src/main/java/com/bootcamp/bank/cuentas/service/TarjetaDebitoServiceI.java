package com.bootcamp.bank.cuentas.service;

import com.bootcamp.bank.cuentas.model.dao.CuentaDao;
import com.bootcamp.bank.cuentas.model.dao.TarjetaDebitoCuentaDao;
import com.bootcamp.bank.cuentas.model.dao.TarjetaDebitoDao;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TarjetaDebitoServiceI {
    Mono<TarjetaDebitoDao> createCardDebit(TarjetaDebitoDao tarjetaDebitoDao);
    Mono<TarjetaDebitoCuentaDao> associateCardDebit(TarjetaDebitoDao tarjetaDebitoDao);
    Flux<TarjetaDebitoDao> getCardDebitPorIdCliente(String idCliente);
    Flux<TarjetaDebitoCuentaDao> getNumberAccountsPorCardDebit(String tarjetaDebito);
    Flux<CuentaDao> findAccountsByCardDebit(String numeroTarjetaDebito);
    Mono<TarjetaDebitoDao> getCardDebitPorCardDebit(String numeroTarjetaDebito);
}
