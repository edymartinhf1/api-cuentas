package com.bootcamp.bank.cuentas.factory;

import com.bootcamp.bank.cuentas.utils.Util;

public class CuentaAhorro  extends CuentaA{
    /**
     * libre de comisión por mantenimiento y con un límite máximo de movimientos mensuales
     */
    @Override
    void configurarCuenta() {
        int randomNumber = Util.generateRandomNumber(1, 100000);
        this.setTipoCuenta("AHO");
        this.setFlgComisionMantenimiento(false);
        this.setFlgLimiteMovMensual(true);
        this.setNumMaximoMovimientos(20); // numero maximo limitado
        this.setNumeroCuenta("AHO".concat(Integer.toString(randomNumber)));
        this.setFechaCreacion(Util.getCurrentLocalDate());
        this.setFechaCreacionT(Util.getCurrentDateAsString("yyyy-MM-dd"));
        this.setEstado("ACT");
        this.setMontoMinimoApertura(0.00);
        this.setNumeroMaximoTransaccionesLibres(20);
    }
}
