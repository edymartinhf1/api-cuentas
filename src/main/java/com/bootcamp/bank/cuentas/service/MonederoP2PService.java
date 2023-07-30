package com.bootcamp.bank.cuentas.service;

import com.bootcamp.bank.cuentas.model.MonederoP2PPost;
import com.bootcamp.bank.cuentas.model.ResponseMonedero;

public interface MonederoP2PService {
    ResponseMonedero registrarMonedero(MonederoP2PPost monederoP2PPost);
}
