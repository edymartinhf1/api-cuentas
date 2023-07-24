package com.bootcamp.bank.cuentas.factory;

import com.bootcamp.bank.cuentas.utils.Util;

public class CuentaCorriente extends CuentaA{
    /**
     * Posee comisión de mantenimiento y sin límite de movimientos mensuales.
     */
    @Override
    void configurarCuenta() {
        int randomNumber = Util.generateRandomNumber(1, 100000);
        this.setTipoCuenta("CTE");
        this.setFlgComisionMantenimiento(true);
        this.setFlgLimiteMovMensual(false);
        this.setNumeroCuenta("CTE"+Integer.toString(randomNumber));
        this.setFechaCreacion(Util.getCurrentLocalDate());
        this.setFechaCreacionT(Util.getCurrentDateAsString("yyyy-MM-dd"));
        this.setNumMaximoMovimientos(999999); // ilimitado
        this.setMontoMinimoApertura(0.00);
        this.setEstado("ACT");
        this.setNumeroMaximoTransaccionesLibres(20);
    }
}
