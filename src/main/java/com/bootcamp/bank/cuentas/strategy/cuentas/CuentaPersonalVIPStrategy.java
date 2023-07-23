package com.bootcamp.bank.cuentas.strategy.cuentas;

import com.bootcamp.bank.cuentas.clients.ClientApiClientes;
import com.bootcamp.bank.cuentas.clients.ClientApiCreditos;
import com.bootcamp.bank.cuentas.exception.BusinessException;
import com.bootcamp.bank.cuentas.model.PerfilInfo;
import com.bootcamp.bank.cuentas.model.dao.CuentaDao;
import com.bootcamp.bank.cuentas.model.dao.repository.CuentaRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Component
@Log4j2
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
            log.info("No es un tipo de cuenta permitido "+cuentaDao.getTipoCuenta()+" para el tipo de cliente "+cuentaDao.getIdCliente());
            return Mono.just(Boolean.FALSE);
        }
        return clientApiCreditos.getCreditosDeudaPorIdCliente(idCliente)
                .collectList()
                .flatMap(listDeudas-> {
                    if (!listDeudas.isEmpty()) {
                        log.info(" contiene productos de credito con deuda con deuda");
                        return Mono.just(Boolean.FALSE);
                    }

                    return clientApiCreditos.getCreditosPorIdClientAndTipoCredito(idCliente,tipoCreditoPreCondicion)
                            .switchIfEmpty(Mono.error(()->new BusinessException(" cuentaVip error : no existe un producto de tarjeta de credito con el cliente id "+idCliente)))
                            .collectList()
                            .flatMap(list -> {
                                log.info(" cuenta personal vip - numero de productos de tarjeta de credito "+list.size());
                                return list.isEmpty() ? Mono.just(Boolean.FALSE) : Mono.just(Boolean.TRUE);
                            });


                });





    }
}
