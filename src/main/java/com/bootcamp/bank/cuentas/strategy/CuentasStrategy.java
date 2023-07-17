package com.bootcamp.bank.cuentas.strategy;

import com.bootcamp.bank.cuentas.clients.ClientApiClientes;
import com.bootcamp.bank.cuentas.clients.ClientApiCreditos;
import com.bootcamp.bank.cuentas.model.dao.CuentaDao;
import com.bootcamp.bank.cuentas.model.dao.repository.CuentaRepository;
import reactor.core.publisher.Mono;

public interface CuentasStrategy {
    Mono<Boolean> verifyCuenta(
            CuentaRepository cuentaRepository,
            ClientApiClientes clientApiClientes,
            ClientApiCreditos clientApiCreditos,
            CuentaDao cuentaDao);
}
