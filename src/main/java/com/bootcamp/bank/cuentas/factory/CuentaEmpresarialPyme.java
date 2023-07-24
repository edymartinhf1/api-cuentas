package com.bootcamp.bank.cuentas.factory;

import com.bootcamp.bank.cuentas.utils.Util;

public class CuentaEmpresarialPyme extends CuentaA{
    /**
     *
     */
    @Override
    void configurarCuenta() {
        int randomNumber = Util.generateRandomNumber(1, 100000);
        this.setTipoCuenta("PYM");
        this.setFlgComisionMantenimiento(false);
        this.setNumeroCuenta("PYME"+Integer.toString(randomNumber));
        this.setFechaCreacion(Util.getCurrentLocalDate());
        this.setFechaCreacionT(Util.getCurrentDateAsString("dd/MM/yyyy"));
        this.setEstado("ACT");
        this.setNumeroMaximoTransaccionesLibres(20);
    }
}
