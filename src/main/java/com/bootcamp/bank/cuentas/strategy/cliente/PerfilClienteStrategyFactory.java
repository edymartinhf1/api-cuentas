package com.bootcamp.bank.cuentas.strategy.cliente;

import com.bootcamp.bank.cuentas.model.enums.PerfilClienteTypes;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
@Component
public class PerfilClienteStrategyFactory {
    private Map<PerfilClienteTypes, PerfilClienteStrategy> strategies = new EnumMap<>(PerfilClienteTypes.class);

    public PerfilClienteStrategyFactory() {
        initStrategies();
    }

    public PerfilClienteStrategy getStrategy(PerfilClienteTypes perfilClienteType) {
        if (perfilClienteType == null || !strategies.containsKey(perfilClienteType)) {
            throw new IllegalArgumentException("Invalid " + perfilClienteType);
        }
        return strategies.get(perfilClienteType);
    }

    private void initStrategies() {
        strategies.put(PerfilClienteTypes.PERSONAL, new PerfilPersonalSstrategy());
        strategies.put(PerfilClienteTypes.EMPRESARIAL, new PerfilEmpresarialStrategy());
    }
}
