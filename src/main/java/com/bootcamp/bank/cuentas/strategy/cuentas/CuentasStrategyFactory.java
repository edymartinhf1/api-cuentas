package com.bootcamp.bank.cuentas.strategy.cuentas;

import com.bootcamp.bank.cuentas.model.enums.CuentasTipoTypes;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
@Component
public class CuentasStrategyFactory {

    private Map<CuentasTipoTypes, CuentasStrategy> strategies = new EnumMap<>(CuentasTipoTypes.class);

    public CuentasStrategyFactory() {
        initStrategies();
    }

    public CuentasStrategy getStrategy(CuentasTipoTypes cuentasType) {
        if (cuentasType == null || !strategies.containsKey(cuentasType)) {
            throw new IllegalArgumentException("Invalid " + cuentasType);
        }
        return strategies.get(cuentasType);
    }

    private void initStrategies() {
        strategies.put(CuentasTipoTypes.AHORRO, new CuentaAhorroStrategy());
        strategies.put(CuentasTipoTypes.CORRIENTE, new CuentaCorrienteStrategy());
        strategies.put(CuentasTipoTypes.PLAZOFIJO, new CuentaPlazoFijoStrategy());
        strategies.put(CuentasTipoTypes.PERSONALVIP, new CuentaPersonalVIPStrategy());
        strategies.put(CuentasTipoTypes.PYME, new CuentaPymeStrategy());
    }
}
