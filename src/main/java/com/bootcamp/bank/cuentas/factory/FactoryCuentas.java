package com.bootcamp.bank.cuentas.factory;

import org.springframework.stereotype.Component;

@Component
public class FactoryCuentas {
    public CuentaA getCuentaBancaria(String tipo){
        if (tipo.equals("AHO")){
            return new CuentaAhorro();
        } else if (tipo.equals("CTE")){
            return new CuentaCorriente();
        } else if (tipo.equals("PZF")) {
            return new CuentaPlazoFijo();
        } else if (tipo.equals("VIP")) {
            return new CuentaPersonalVIP();
        } else if (tipo.equals("PYM")) {
            return new CuentaEmpresarialPyme();
        }
        return null;
    }
}
