package com.bootcamp.bank.cuentas.service;

import com.bootcamp.bank.cuentas.model.MonederoMovilPost;
import com.bootcamp.bank.cuentas.model.Response;

public interface MonederoMovilService {
    Response registrarMonedero(MonederoMovilPost monederoMovilPost);
}
