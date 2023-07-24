package com.bootcamp.bank.cuentas.factory;

import com.bootcamp.bank.cuentas.utils.Util;

public class CuentaPlazoFijo extends  CuentaA{
    /**
     * libre de comisión por mantenimiento, solo permite un movimiento de retiro o depósito en un día específico del mes
     */
    @Override
    void configurarCuenta() {
        int randomNumber = Util.generateRandomNumber(1, 100000);
        this.setTipoCuenta("PZF");
        this.setFlgComisionMantenimiento(false);
        this.setNumMaximoMovimientos(1); // solo uno
        this.setNumeroCuenta("PZF"+Integer.toString(randomNumber));
        this.setFechaCreacion(Util.getCurrentLocalDate());
        this.setFechaCreacionT(Util.getCurrentDateAsString("yyyy-MM-dd"));
        this.setMontoMinimoApertura(0.00);
        this.setEstado("ACT");
    }
}
