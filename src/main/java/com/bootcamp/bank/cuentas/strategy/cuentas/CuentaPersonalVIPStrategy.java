package com.bootcamp.bank.cuentas.strategy.cuentas;

import com.bootcamp.bank.cuentas.clients.ClientApiClientes;
import com.bootcamp.bank.cuentas.clients.ClientApiCreditos;
import com.bootcamp.bank.cuentas.exception.BusinessException;
import com.bootcamp.bank.cuentas.model.PerfilInfo;
import com.bootcamp.bank.cuentas.model.dao.CuentaDao;
import com.bootcamp.bank.cuentas.model.dao.repository.CuentaRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Component
public class CuentaPersonalVIPStrategy implements CuentasStrategy{

    /**
     * Adicionalmente, para solicitar este producto el cliente debe tener una tarjeta de crédito con el banco al
     * momento de la creación de la cuenta.
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
                                      CuentaDao cuentaDao,
                                      PerfilInfo perfilInfo
    ) {

        String idCliente  = cuentaDao.getIdCliente();
        String tipoCreditoPreCondicion = "TJC";

        if (!perfilInfo.getPerfiles().contains(cuentaDao.getTipoCuenta().trim())){
            return Mono.just(Boolean.FALSE);
        }
        return clientApiCreditos.getCreditosPorIdClinteAndTipoCredito(idCliente,tipoCreditoPreCondicion)
                .collectList()
                .map(list ->
                        list.isEmpty() ? Mono.just(new BusinessException("El cliente : "+idCliente+" no tiene producto de tarjeta de credito de tipo :"+tipoCreditoPreCondicion)):Mono.just(list)
                )
                //.switchIfEmpty(Mono.error(()->new BusinessException("No existe cliente con credito con el id "+idCliente)))
                .then(Mono.just(Boolean.TRUE));


    }
}
