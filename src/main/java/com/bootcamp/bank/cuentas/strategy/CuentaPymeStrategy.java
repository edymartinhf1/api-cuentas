package com.bootcamp.bank.cuentas.strategy;

import com.bootcamp.bank.cuentas.clients.ClientApiClientes;
import com.bootcamp.bank.cuentas.clients.ClientApiCreditos;
import com.bootcamp.bank.cuentas.exception.BusinessException;
import com.bootcamp.bank.cuentas.model.dao.CuentaDao;
import com.bootcamp.bank.cuentas.model.dao.repository.CuentaRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Component
public class CuentaPymeStrategy implements CuentasStrategy{

    /**
     * Como requisito debe de tener una cuenta corriente
     * Como requisito, el cliente debe tener una tarjeta de crédito con el banco al momento de la creación de la cuenta
     * @param cuentaRepository
     * @param clientApiClientes
     * @param clientApiCreditos
     * @param cuentaDao
     * @return
     */
    @Override
    public Mono<Boolean> verifyCuenta(CuentaRepository cuentaRepository,
                                      ClientApiClientes clientApiClientes,
                                      ClientApiCreditos clientApiCreditos,
                                      CuentaDao cuentaDao) {

        String idCliente = cuentaDao.getIdCliente();
        String tipoCuentaPreCondicion  = "CTE"; // cuenta corriente
        String tipoCreditoPreCondicion = "TJC"; // tarjeta credito
        return cuentaRepository.findByIdClienteAndTipoCuenta(idCliente,tipoCuentaPreCondicion)
                //.switchIfEmpty(Mono.error(()->new BusinessException("CuentaPyme error : no existe cuenta corriente con el cliente id "+idCliente)))
                //.next()
                .collectList()
                .map(list ->
                        list.isEmpty() ? Mono.just(new BusinessException("CuentaPyme error : El cliente : "+idCliente+" no tiene cuenta  de tipo :"+tipoCuentaPreCondicion)):Mono.just(list)
                )
                .flatMap( cue-> {
                    return clientApiCreditos.getCreditosPorIdClinteAndTipoCredito(idCliente,tipoCreditoPreCondicion)
                            .switchIfEmpty(Mono.error(()->new BusinessException("CuentaPyme error : no existe producto credito tarjeta con el cliente id "+idCliente)))
                            .next()
                            .flatMap(cre ->Mono.just(Boolean.TRUE));
                });


    }
}
