package com.bootcamp.bank.cuentas.service;

import com.bootcamp.bank.cuentas.model.MonederoMovilPost;
import com.bootcamp.bank.cuentas.model.ResponseMonedero;
import reactor.core.publisher.Mono;

public interface MonederoMovilService {
    Mono<ResponseMonedero> registrarMonedero(MonederoMovilPost monederoMovilPost);
}
