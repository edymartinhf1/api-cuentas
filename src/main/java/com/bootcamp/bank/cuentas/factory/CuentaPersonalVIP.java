package com.bootcamp.bank.cuentas.factory;

import com.bootcamp.bank.cuentas.utils.Util;

public class CuentaPersonalVIP extends CuentaA {
    @Override
    void configurarCuenta() {
        int randomNumber = Util.generateRandomNumber(1, 100000);
        this.setTipoCuenta("VIP");
        this.setFlgComisionMantenimiento(false);
        this.setNumeroCuenta("VIP"+Integer.toString(randomNumber));
        this.setFechaCreacion (Util.getCurrentDateAsString("dd/MM/yyyy"));
        this.setEstado("ACT");
        this.setMontoMinimoApertura(0.00);
        this.setNumeroMaximoTransaccionesLibres(20);
    }
}
