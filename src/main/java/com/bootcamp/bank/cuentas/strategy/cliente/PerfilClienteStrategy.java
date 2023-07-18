package com.bootcamp.bank.cuentas.strategy.cliente;

import com.bootcamp.bank.cuentas.model.Cliente;
import com.bootcamp.bank.cuentas.model.PerfilInfo;

public interface PerfilClienteStrategy {
    PerfilInfo configurarPerfil(Cliente cliente);
}
