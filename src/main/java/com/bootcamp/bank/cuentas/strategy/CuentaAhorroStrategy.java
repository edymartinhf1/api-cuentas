package com.bootcamp.bank.cuentas.strategy;

import com.bootcamp.bank.cuentas.clients.ClientApiClientes;
import com.bootcamp.bank.cuentas.clients.ClientApiCreditos;
import com.bootcamp.bank.cuentas.exception.BusinessException;
import com.bootcamp.bank.cuentas.model.dao.CuentaDao;
import com.bootcamp.bank.cuentas.model.dao.repository.CuentaRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CuentaAhorroStrategy implements CuentasStrategy{

    /**
     * Un cliente personal solo puede tener un máximo de una cuenta de ahorro, una cuenta corriente o cuentas a plazo fijo
     * Si se va a crear una cuenta de ahorro no deberia de pre-existir una cuenta previa
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

        String idCliente  = cuentaDao.getIdCliente();
        String tipoCuenta = cuentaDao.getTipoCuenta();
        return cuentaRepository.findByIdClienteAndTipoCuenta(idCliente,tipoCuenta)
                .collectList()
                .map(list ->
                    !list.isEmpty() ? Mono.just(new BusinessException("El cliente : "+idCliente+" ya tiene una cuenta de tipo :"+tipoCuenta)):Mono.just(list)
                )
                .then(Mono.just(Boolean.TRUE));


    }
}
