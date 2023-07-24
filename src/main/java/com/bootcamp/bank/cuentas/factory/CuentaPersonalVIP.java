package com.bootcamp.bank.cuentas.factory;

import com.bootcamp.bank.cuentas.utils.Util;

public class CuentaPersonalVIP extends CuentaA {
    @Override
    void configurarCuenta() {
        int randomNumber = Util.generateRandomNumber(1, 100000);
        this.setTipoCuenta("VIP");
        this.setFlgComisionMantenimiento(false);
        this.setNumeroCuenta("VIP"+Integer.toString(randomNumber));
        this.setFechaCreacion(Util.getCurrentLocalDate());
        this.setFechaCreacionT(Util.getCurrentDateAsString("yyyy-MM-dd"));
        this.setEstado("ACT");
        this.setMontoMinimoMensual(500.00);
        this.setNumeroMaximoTransaccionesLibres(20);
    }
}
