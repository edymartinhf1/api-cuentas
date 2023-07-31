package com.bootcamp.bank.cuentas.strategy.monedero;
import com.bootcamp.bank.cuentas.model.enums.MonederoTypes;
import org.springframework.stereotype.Component;


import java.util.EnumMap;
import java.util.Map;
@Component
public class MonederoStrategyFactory {
    private Map<MonederoTypes, MonederoStrategy> strategies = new EnumMap<>(MonederoTypes.class);

    public MonederoStrategyFactory() {
        initStrategies();
    }

    public MonederoStrategy getStrategy(MonederoTypes monederoTypes) {
        if (monederoTypes == null || !strategies.containsKey(monederoTypes)) {
            throw new IllegalArgumentException("Invalid " + monederoTypes);
        }
        return strategies.get(monederoTypes);
    }

    private void initStrategies() {
        strategies.put(MonederoTypes.MOVIL, new MonederoMovilStrategy());
        strategies.put(MonederoTypes.MOVIL_P2P, new MonederoP2PStrategy());
    }
}
